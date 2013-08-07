package cw.test.html.parser;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cw.html.parser.CwSubChannelHtmlParser;
import cw.model.KeyWord;
import cw.model.SubChannel;

public class CwSubChannelHtmlParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExtractSubChannels() {
		try{
			CwSubChannelHtmlParser cwSubChannelHtmlParser = new CwSubChannelHtmlParser();
			 Set<SubChannel> list = cwSubChannelHtmlParser.extractSubChannels(7);
			 for(SubChannel subChannel : list){
				 System.out.println( subChannel );
			 }
			
			
		}catch(Exception e){
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void testextractSubChannelKeywords() {
		try{
			CwSubChannelHtmlParser cwSubChannelHtmlParser = new CwSubChannelHtmlParser();
			Set<KeyWord> keywords = cwSubChannelHtmlParser.extractSubChannelKeywords(6);
			for(KeyWord keyWord : keywords){
				System.out.println(keyWord.getWord());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
