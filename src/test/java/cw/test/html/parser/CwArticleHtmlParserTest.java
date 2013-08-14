package cw.test.html.parser;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cw.dao.ArticleDao;
import cw.dao.SubChannelDao;
import cw.html.parser.CwArticleHtmlParser;
import cw.model.Article;
import cw.model.SubChannel;
import cw.test.BaseTest;

public class CwArticleHtmlParserTest extends BaseTest{
	
	@Autowired
	private SubChannelDao subChannelDao;

	@Test
	public void testExtracLinksFromSubChannel() {
		List<SubChannel> subChannels = subChannelDao.queryAllSubChannels();
		for(SubChannel subChannel : subChannels){
			CwArticleHtmlParser cwArticleHtmlParser = new CwArticleHtmlParser();
			List<String> urls = cwArticleHtmlParser.extracLinksFromSubChannel(subChannel.getId());
	
			for(String url : urls){
				Article article = cwArticleHtmlParser.extracLinkToArticle(url);
				System.out.println(article);
			}
		}

	}
	
	@Test
	public void testExtracLinkToArticle() {
		String url = "http://www.cw.com.tw/article/article.action?id=5003809&idSubChannel=26";
		CwArticleHtmlParser cwArticleHtmlParser = new CwArticleHtmlParser();
		Article article = cwArticleHtmlParser.extracLinkToArticle(url);
		System.out.println(article.getPlainText());
	}

}
