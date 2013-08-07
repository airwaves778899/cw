package cw.html.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;

import cw.Constants;
import cw.model.KeyWord;
import cw.model.SubChannel;

public class CwSubChannelHtmlParser extends BaseHtmlParser{
	
	public Set<SubChannel> extractSubChannels(int masterChannelId) throws Exception{ 
		return extractSubChannels(null, masterChannelId);
	}
	
	public Set<SubChannel> extractSubChannels(Parser parser, int masterChannelId) throws Exception{ 
		Set<SubChannel> subChannels = new HashSet();
		
		if(parser==null){
			parser = this.getParser(Constants.CW_URL);  
		}else{
			parser.reset();
		}
		
		//取得Master Channel TabIndex
		int masterTabIndex = getMasterTabIndex(parser, masterChannelId);
		
		parser.reset();
        NodeList list = parser.extractAllNodesThatMatch(new CssSelectorNodeFilter("div.TabbedPanelsContent"));

        for (int i = 0; i < list.size(); i++) { 
        	Node tag = list.elementAt(i);
    	
        	if(tag instanceof Div && masterTabIndex==i){
        		Div div = (Div) tag;
        		
        		Node[] links = ParserUtils.findTypeInNode(div, LinkTag.class);
        		
        		for(Node node : links){
        			LinkTag link = (LinkTag) node;
        			
        			if(StringUtils.startsWith(link.getAttribute("href"), Constants.CW_SUB_CHANNEL_URL)){ 
	        			String herfStr = link.getAttribute("href");
	        			int startIndex = herfStr.indexOf("=")+1;
	        			int endIndex = herfStr.length();
	        			String idSubChannel = StringUtils.substring(herfStr, startIndex, endIndex);

	        			SubChannel subChannel = new SubChannel();
	        			subChannel.setId( Integer.parseInt(idSubChannel) );
	        			subChannel.setName( link.getLinkText() );
	        			subChannel.setMasterChannelId(masterChannelId);
	        			subChannel.setKeyWords( extractSubChannelKeywords(subChannel.getId()) );
	        			subChannels.add( subChannel );
        			}
        		}
        	}
        }
		
		return subChannels;
	}
	
	private int getMasterTabIndex(Parser parser, int masterChannelId) throws ParserException{
		NodeList list = parser.extractAllNodesThatMatch(new CssSelectorNodeFilter("ul.TabbedPanelsTabGroup li.TabbedPanelsTab.m* a"));

		for (int i = 0; i < list.size(); i++) { 
			Node tag = list.elementAt(i);
			
			if(tag instanceof LinkTag){
				if(tag instanceof LinkTag){
					LinkTag link = (LinkTag) tag;
					
					if(StringUtils.startsWith(link.getAttribute("href"), Constants.CW_MASTER_CHANNEL_URL)){ 
						//get li tabindex
						Bullet li = (Bullet) link.getParent();				
	        			String tabindex = li.getAttribute("tabindex");
	        			
	        			//
	        			String herfStr = link.getAttribute("href");
	        			int startIndex = herfStr.indexOf("=")+1;
	        			int endIndex = herfStr.length();
	        			String idMasterChannel = StringUtils.substring(herfStr, startIndex, endIndex);	
	        			//URL idMasterChannel 一樣，則回傳 li tabindex
	        			if(idMasterChannel!=null && Integer.parseInt(idMasterChannel)==masterChannelId){
	        				return Integer.parseInt(tabindex);
	        			}
					}
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * 解析出SubChannel關鍵字
	 * @param subChannelId
	 * @return
	 * @throws ParserException
	 */
	public Set<KeyWord> extractSubChannelKeywords(int subChannelId) throws ParserException{
		String url = Constants.CW_URL + Constants.CW_SUB_CHANNEL_URL + subChannelId;
		Parser parser = this.getParser(url);
		return extractSubChannelKeywords( parser );
	}
	
	/**
	 * 解析出SubChannel關鍵字
	 * @param parser
	 * @return
	 * @throws ParserException 
	 */
	private Set<KeyWord> extractSubChannelKeywords(Parser parser) throws ParserException{
		Set<KeyWord> keywords = new HashSet<KeyWord>();
		
		parser.reset();
		NodeList list = parser.extractAllNodesThatMatch(new CssSelectorNodeFilter("div.keyW a"));
		
		for (int i = 0; i < list.size(); i++) { 
			Node tag = list.elementAt(i);
			
			if(tag instanceof LinkTag){
	
				LinkTag link = (LinkTag) tag;
				
				KeyWord keyWord = new KeyWord();
				keyWord.setWord( link.getLinkText() );
				keywords.add(keyWord);
			}
		}
		
		return keywords;
	}
}
