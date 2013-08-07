package cw.html.parser;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

public abstract class BaseHtmlParser {
	public static String HTML_ENCODING = "UTF-8";
	public static int CONNECTION_TIMEOUT = 5000; //5秒
	
    public Parser getParser(String url) throws ParserException{
        Parser parser = new Parser(url);  
        parser.setEncoding(HTML_ENCODING);  
        
        return parser;
    }
}
