package cw.test.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cw.dao.ArticleDao;
import cw.model.Article;
import cw.service.ArticleService;
import cw.test.BaseTest;

public class ArticleServiceTest extends BaseTest{
	
	@Resource
	private ArticleService articleService;
	
	@Test
	public void test() {
		//articleService.queryArticleById(5000000);
		System.out.println( articleService.queryArticleById(5000000) );
//		for(Article article : articles){
//			System.out.println(article);
//		}
		//fail("Not yet implemented");
	}

}
