package cw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cw.dao.ArticleDao;
import cw.model.Article;

@Service("articleService")
@Transactional
public class ArticleService {
	
	@Autowired
	private ArticleDao articleDao;
	
	public List<Article> queryAllArticles(){
    	return articleDao.queryAllArticles();
    }
	
	public Article queryArticleById(int id){
		return articleDao.queryArticle(id);
	}
}
