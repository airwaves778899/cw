package cw.test.elasticsearch;

import static org.junit.Assert.*;

import java.util.List;

import io.searchbox.core.Get;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cw.model.Article;
import cw.search.elasticsearch.SearchService;
import cw.service.ArticleService;
import cw.test.BaseTest;

public class ElasticsearchTest extends BaseTest{
	
	@Autowired
	SearchService searchService;
	
	@Autowired
	ArticleService articleService;


	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testDeleteIndex() {
		try {
			searchService.deleteIndex();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testIndexArticles() {
		try {
			searchService.openIndex();
			
			searchService.indexArticles( articleService.queryAllArticles() );
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
	
	@Test
	public void testUpdateArticles(){
		try {
			
			int docId = 5000040;
			
			Article article = articleService.queryArticleById(docId);
			System.out.println( article );
			if(article!=null){
				if( searchService.searchArticleById(docId)!=null ){
					searchService.updateArticleIndex(article);
				}
			}
			
			article = searchService.searchArticleById(docId);
			System.out.println( article );

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSearchArticles() {
		try {
			//searchService.openIndex();
			
			List<Article> list = searchService.searchArticles("5000040", 10);
			if(CollectionUtils.isNotEmpty(list)){
				for(Article article : list){
				    System.out.println(article.getId());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
