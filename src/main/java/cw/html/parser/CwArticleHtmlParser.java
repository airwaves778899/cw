package cw.html.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;

import cw.Constants;
import cw.model.Article;
import cw.model.KeyWord;

public class CwArticleHtmlParser extends BaseHtmlParser{
	
	public List<String> extracLinksFromSubChannel(int subChannelId){
		List<String> results = new ArrayList<String>();
		
		String url = Constants.CW_URL
				+ Constants.CW_SUB_CHANNEL_URL + subChannelId
				+ "&page.currentPage=";
		
		int currentPage = 1;
		
		try {			
			Parser parser = this.getParser(url + currentPage);

			while( isCurrentPage(parser, currentPage) ){
				results.addAll( extracLinksFromSubChannel(parser) );
				
				currentPage++;
				parser.setURL(url + currentPage);				
			}
			
		} catch (ParserException e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
	private List<String> extracLinksFromSubChannel(Parser parser) { 
		List<String> results = new ArrayList<String>();
		
	    try { 

	        	parser.reset();
	        	
	        	NodeList list = parser.extractAllNodesThatMatch(new CssSelectorNodeFilter("div[class=\"Topics TopicsW1\"] h3 a"));
	        	
	        	for (int i = 0; i < list.size(); i++) {
	        		Node tag = list.elementAt(i);  
	        		
	        		if (tag instanceof LinkTag){
	        			LinkTag link = (LinkTag) tag;  
	        			results.add( link.getLink() );
	        		}
	        	}

	    } catch (ParserException e) {  
	        e.printStackTrace();  
	    }  
	    
	    return results;
	}
	
	/**
	 * 判斷是否為目前頁面
	 * @param parser
	 * @param currentPage
	 * @return
	 * @throws ParserException
	 */
	private boolean isCurrentPage(Parser parser, int currentPage) throws ParserException{
		//取得頁面標籤
		NodeList list = parser.extractAllNodesThatMatch(new CssSelectorNodeFilter("div.pages a"));
        for (int i = 0; i < list.size(); i++) {
        	Node tag = list.elementAt(i);  
        	
        	if (tag instanceof LinkTag){
        		LinkTag link = (LinkTag) tag;
        		if(StringUtils.equals(link.getAttribute("class"), "select") &&
        				StringUtils.equals(link.getLinkText(), currentPage+"")){

        			return true;
        		}
        	}
        }
        
		return false;
	}
	
//	private static void extractKeywordForDivTag(Div div){		
//		Node[] nodes = ParserUtils.findTypeInNode(div, LinkTag.class);
//		for(Node node : nodes){
//        	LinkTag link = (LinkTag) node;  
//        	String linkUrl = link.getLink();
//        	String text = link.getLinkText();        	
//        	System.out.println( text );
//		}
//	}
//	
//	private static void extractTopicForDivTag(Div div){
//		StringBuilder s = new StringBuilder();
//		
//		Node[] nodes = ParserUtils.findTypeInNode(div, LinkTag.class);
//		for(Node node : nodes){
//        	LinkTag link = (LinkTag) node;
//        	String linkUrl = link.getLink();
//        	String text = link.getLinkText(); 
//        	
//        	if(!StringUtils.equals(text, "詳全文")){
//        		//System.out.println( text+" "+linkUrl );
//        		s.append(text).append(" ").append(linkUrl).append(" ");
//        	}
//		}
//		
//		nodes = ParserUtils.findTypeInNode(div, Span.class);
//		for(Node node : nodes){
//			Span span = (Span) node;
//			String classAttribute = span.getAttribute("class");
//			if(StringUtils.equals(classAttribute, "date")){
//				s.append(span.getStringText());
//			}
//		}
//		
//		System.out.println( s.toString() );
//	}
	
	public Article extracLinkToArticle(String url){
		Article article = new Article();
		article.setUrl(url);
		
		//解析參數
		String[] params = url.substring(url.indexOf("?")+1).split("&"); 		
		for (String param : params)  {
			String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        
	        if(StringUtils.equals(name, "id")){
	            article.setId(Integer.parseInt(value));
	        }
	        
	        if(StringUtils.equals(name, "idSubChannel")){
	        	article.setSubChannelId(Integer.parseInt(value));
	        }
		}
		
		try {
			Parser parser = this.getParser(url);
			
			NodeFilter nodeFilter = new OrFilter(
					new NodeFilter[]{
							new CssSelectorNodeFilter("meta"),
							new CssSelectorNodeFilter("div[class=\"keyW\"]"),
					        new CssSelectorNodeFilter("span[class=\"date\"]"),
					        new CssSelectorNodeFilter("span[class=\"reporter\"]"),
					        new CssSelectorNodeFilter("div#cp[class=\"cp\"]")
			        });
			NodeList list = parser.extractAllNodesThatMatch(nodeFilter);
			
			for (int i = 0; i < list.size(); i++) {
	        	Node tag = list.elementAt(i);  
	        	
	        	if (tag instanceof MetaTag){
	        		MetaTag metaTag = (MetaTag) tag;
	        		
	        		if(StringUtils.equals(metaTag.getAttribute("name"),"title")){
	        			article.setTitle(metaTag.getAttribute("content"));
	        		}
	        		
	        		if(StringUtils.equals(metaTag.getAttribute("name"),"description")){
	        			article.setDescription(metaTag.getAttribute("content"));
	        		}
	        		
	        		if(StringUtils.equals(metaTag.getAttribute("http-equiv"),"Content-Type")){
	        			String[] contents = metaTag.getAttribute("content").split(";");
	        			article.setMimeType(contents[0].trim());
	        			article.setLanguageCode(contents[1].replace("charset=", "").trim());
	        		}
	        	}
	        	
	        	if (tag instanceof Span){
	        		Span span = (Span) tag;
	        		
	        		//日期
	        		if(StringUtils.equals(span.getAttribute("class"),"date")){
	        			article.setDate( span.getStringText() );
	        		}
	        		
	        		//作者
	        		if(StringUtils.equals(span.getAttribute("class"),"reporter")){
	        			article.setAuthor( span.getStringText().replace("作者：", "") );
	        		}
	        	}
	        	
	        	if (tag instanceof Div){
	        		Div div = (Div) tag;
	        		
	        		//關鍵字
	        		if(StringUtils.equals(div.getAttribute("class"),"keyW")){
		        		
		        		Node[] links = ParserUtils.findTypeInNode(div, LinkTag.class);
		        		for(Node node : links){
		        			LinkTag link = (LinkTag) node;
		        			
		        			KeyWord keyWord = new KeyWord();
		        			keyWord.setWord( link.getLinkText() );
		        			article.addKeyWord(keyWord);
		        		}
	        		}
	        		
	        		//內文
	        		if(StringUtils.equals(div.getAttribute("id"),"cp")){
	        			Node[] links = ParserUtils.findTypeInNode(div, ParagraphTag.class);

	        			StringBuilder plainText = new StringBuilder();
	        			for(Node node : links){
	        				ParagraphTag paragraphTag = (ParagraphTag) node;
	        				plainText.append( StringUtils.trim(paragraphTag.toPlainTextString()) );
	        			}

	        			article.setPlainText( StringUtils.replace(plainText.toString(),"　","") );
	        		}
	        	}
			}
			
		} catch (ParserException e) {
			e.printStackTrace();
		}
		
		return article;
	}
	
	public static void main(String args[]){
		String url = "http://www.cw.com.tw/article/article.action?id=5044161&idSubChannel=53";
		String[] params = url.substring(url.indexOf("?")+1).split("&");  
		for (String param : params)  {
			String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        
	        System.out.println(name+", "+value);
		}
	}
}
