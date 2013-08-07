package cw.html.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import cw.Constants;
import cw.model.MasterChannel;
import cw.model.SubChannel;

public class CwMasterChannelHtmlParser extends BaseHtmlParser{
	
	public Set<MasterChannel> extractMasterChannels() throws Exception{
		Set<MasterChannel> masterChannels = new HashSet<MasterChannel>();
		
		CwSubChannelHtmlParser cwSubChannelHtmlParser = new CwSubChannelHtmlParser();
		
		Parser parser = this.getParser(Constants.CW_URL);  
        
        NodeList list = parser.extractAllNodesThatMatch(new CssSelectorNodeFilter("ul.TabbedPanelsTabGroup li.TabbedPanelsTab.m* a"));
        
        for (int i = 0; i < list.size(); i++) { 
        	Node tag = list.elementAt(i);
        	
        	if(tag instanceof LinkTag){
        		LinkTag link = (LinkTag) tag;
        		
        		if(StringUtils.startsWith(link.getAttribute("href"), Constants.CW_MASTER_CHANNEL_URL)){        			
        			String herfStr = link.getAttribute("href");
        			int startIndex = herfStr.indexOf("=")+1;
        			int endIndex = herfStr.length();
        			String idMasterChannel = StringUtils.substring(herfStr, startIndex, endIndex);
        			
        			MasterChannel masterChannel = new MasterChannel();
        			masterChannel.setId(Integer.parseInt(idMasterChannel));
        			masterChannel.setName(link.getLinkText());
        			
        			//get sub channels
        			masterChannel.setSubChannels( cwSubChannelHtmlParser.extractSubChannels(parser, masterChannel.getId()) );
        		
        			masterChannels.add( masterChannel );
        		}
        	    
        	}
        }
		
		return masterChannels;
	}
}
