package cw.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;

import cw.model.Article;
import cw.model.KeyWord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("articleDao")
public class ArticleDao extends BaseDao{
    
    @Autowired
    private KeywordDao keywordDao;
    
    /**
     * 刪除所有文章
     */
    public int deleteAllArticles(){
    	int deleteItemCount = -1;
    	
    	//刪除所有文章
    	deleteItemCount = this.delete("DELETE FROM ARTICLE", null); 
    	//刪除所有文章關鍵字關聯
    	this.delete("DELETE FROM ARTICLE_KEYWORD", null); 
    	
    	return deleteItemCount;
    }
    
    /**
     * 刪除文章
     */
    public int deleteArticle(Article article){
    	int deleteItemCount = -1;
    	
    	Object[] deleteParams = {article.getId()};
    	
    	//刪除所有文章
    	deleteItemCount = this.delete("DELETE FROM ARTICLE WHERE ID=?", deleteParams); 
    	//刪除所有文章關鍵字關聯
    	this.delete("DELETE FROM ARTICLE_KEYWORD WHERE ARTICLE_ID=?", deleteParams); 
    	
    	return deleteItemCount;
    }
    
    /**
     * 查出所有的文章
     * @return
     */
    public List<Article> queryAllArticles(){
    	String sql = "SELECT * FROM ARTICLE ORDER BY ID";
    	List<Article> list = this.queryBeanListData(sql, null, Article.class);
    	
    	//QUERY KEYWORDS
    	for(Article article : list){
    		List<KeyWord> keywords = keywordDao.queryArticleKeywords(article.getId());
    		
    		if(CollectionUtils.isNotEmpty(keywords)){
    			article.setKeyWords(new HashSet(keywords));
    		}
    	}
    	
    	return list;
    }
    
    /**
     * 用ARTICLE ID查詢文章
     * @param articleId
     * @return
     */
    public Article queryArticle(int articleId){    	
    	String sql = "SELECT * FROM ARTICLE WHERE ID=?";
    	Object[] params = {articleId};
    	List<Article> list = this.queryBeanListData(sql, params, Article.class);
    	
    	if(CollectionUtils.isNotEmpty(list)){
    		Article article = list.get(0);
    		
    		List<KeyWord> keywords = keywordDao.queryArticleKeywords(article.getId());
    		if(CollectionUtils.isNotEmpty(keywords)){
    			article.setKeyWords(new HashSet<KeyWord>(keywords));
    		}
    		
    		return article;
    	}
    	
    	return null;
    }
    
    /**
     * 新增 OR 更新文章
     * @param article
     */
    public int insertOrUpdateArticle(Article article){
    	int insertOrUpdateItemCount = -1;
    	
    	//是否存在舊文章
    	Article oldArticle = queryArticle(article.getId());
    	
    	//新增
    	if(oldArticle==null){
    		insertOrUpdateItemCount = this.insertArticle(article);
    	}
    	
    	//更新
    	if(oldArticle!=null){
            insertOrUpdateItemCount = this.updateArticle(article);
    	}

    	//更新KEYWORDS
    	updateArticleKeywords(article);
    	
    	return insertOrUpdateItemCount;
    }
    
    /**
     * 新增文章
     * @param article
     * @return
     */
    public int insertArticle(Article article){
		Object[] insertParams = {
				article.getId(), 
				article.getTitle(),
				article.getAuthor(),
				article.getMimeType(),
				article.getLanguageCode(),
				article.getDate(),
				article.getUrl(),
				article.getPlainText(),
				article.getDescription(),
				article.getSubChannelId()
				}; 
		String sql = "INSERT INTO ARTICLE(" +
				     "ID, TITLE, AUTHOR, " +
				     "MIME_TYPE, LANGUAGE_CODE, DATE, " +
				     "URL, PLAIN_TEXT, DESCRIPTION, " +
				     "SUB_CHANNEL_ID " +
				     ") VALUES (?,?,?,?,?,?,?,?,?,?)";
		return this.insert(sql,insertParams);
    }
    
    /**
     * 更新文章
     * @param article
     * @return
     */
    public int updateArticle(Article article){
		Object[] updateParams = {
				article.getTitle(),
				article.getAuthor(),
				article.getMimeType(),
				article.getLanguageCode(),
				article.getDate(),
				article.getUrl(),
				article.getPlainText(),
				article.getDescription(),
				article.getSubChannelId(),
				article.getId()
             }; 
	    String sql = "UPDATE ARTICLE SET " +
	                 "TITLE=?, " + 
	    		     "AUTHOR=?, " +
	    		     "MIME_TYPE=?, " +
	    		     "LANGUAGE_CODE=?, " +
	    		     "DATE=?, " +
	    		     "URL=?, " +
	    		     "PLAIN_TEXT=?, " +
	    		     "DESCRIPTION=?, " +
	    		     "SUB_CHANNEL_ID=? " +
	                 "WHERE ID=?";
	    return this.update(sql, updateParams);
    }
    
    /**
     * 更新文章KEYWORDS
     * @param article
     * @return
     */
    public void updateArticleKeywords(Article article){
    	if(CollectionUtils.isNotEmpty(article.getKeyWords())){
    		for(KeyWord keyword : article.getKeyWords()){
    			//建立keyword
    			keyword = keywordDao.insertOrUpdateKeyWord(keyword);
    			
    			//建立Article & keyword關聯
    			insertArticleKeywordRelation(article.getId(), keyword.getId());
    		}
    	}
    }
    
    /**
     * 建立Article & keyword關聯
     * @param subChannelId
     * @param keywordId
     */
    private void insertArticleKeywordRelation(int articleId, int keywordId){
    	List<Map<String, Object>> list = this.queryMapListData(
    			"SELECT * FROM ARTICLE_KEYWORD WHERE ARTICLE_ID=? AND KEYWORD_ID=? ", 
    			new Object[]{articleId, keywordId});
    	
    	if(CollectionUtils.isEmpty(list)){
    		this.insert("INSERT INTO ARTICLE_KEYWORD(ARTICLE_ID, KEYWORD_ID) VALUES (?,?)",
    				new Object[]{articleId, keywordId});
    	}
    }
}
