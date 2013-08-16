package cw.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cw.model.KeyWord;

@Repository("keywordDao")
public class KeywordDao extends BaseDao{
	
    public int deleteAllKeywords(){
    	int deleteItemCount = -1;
    	
    	//刪除所有關鍵字
    	deleteItemCount = this.delete("DELETE FROM KEYWORD", null);
    	return deleteItemCount;
    }
    
    /**
     * 刪除所有與關鍵字的關聯
     */
    public int deleteAllRelation(){
    	int deleteItemCount = -1;
    	
    	//刪除所有文章關鍵字關聯
    	deleteItemCount = this.delete("DELETE FROM ARTICLE_KEYWORD", null); 
    	//刪除所有SubChannel關鍵字關聯
    	deleteItemCount += this.delete("DELETE FROM SUBCHANNEL_KEYWORD", null); 
    	
    	return deleteItemCount;
    }
    
    /**
     * 刪除關鍵字
     * @param keyWord
     */
    public int deleteKeyword(KeyWord keyWord){
    	int deleteItemCount = -1;
    	
    	Object[] deleteParams = {keyWord.getId()};
    	
    	//刪除關鍵字    	
    	deleteItemCount = this.delete("DELETE FROM KEYWORD WHERE ID=?", deleteParams); 
    	//刪除所有文章關鍵字關聯
    	this.delete("DELETE FROM ARTICLE_KEYWORD KEYWORD_ID=?", deleteParams); 
    	//刪除所有SubChannel關鍵字關聯
    	this.delete("DELETE FROM SUBCHANNEL_KEYWORD KEYWORD_ID=?", deleteParams); 
    	
    	return deleteItemCount;
    }
    
    /**
     * 刪除關鍵字
     * @param keyWord
     */
    public int deleteKeyword(String word){
    	return deleteKeyword( queryKeyWord(word) );
    }
	
	/**
	 * 查詢關鍵字(絕對符合查詢)
	 * @param word
	 * @return
	 */
    public KeyWord queryKeyWord(String word){
    	Object[] queryParams = {word};
    	String sql = "SELECT * FROM KEYWORD WHERE WORD=? ";    	
    	List<KeyWord> keyWords = this.queryBeanListData(sql, queryParams, KeyWord.class);
    	if(CollectionUtils.isNotEmpty(keyWords)){
    		return keyWords.get(0);
    	}
    		
    	return null;
    }
    
	/**
	 * 查詢關鍵字
	 * @param word
	 * @return
	 */
    public KeyWord queryKeyWordById(int id){
    	Object[] queryParams = {id};
    	String sql = "SELECT * FROM KEYWORD WHERE ID=? ";    	
    	List<KeyWord> keyWords = this.queryBeanListData(sql, queryParams, KeyWord.class);
    	if(CollectionUtils.isNotEmpty(keyWords)){
    		return keyWords.get(0);
    	}
    	
    	return null;    	
    }
    
    public int queryMaxId(){
    	List<Map<String, Object>> list = this.queryMapListData("SELECT MAX(ID) FROM KEYWORD ", null);
    	
    	if(list.get(0).get("MAX(ID)")!=null){
    		return (Integer) list.get(0).get("MAX(ID)");
    	}
    	
    	return 0;
    }
    
    /**
     * 新增&修改關鍵字
     * @param keyWord
     * @return
     */
    public KeyWord insertOrUpdateKeyWord(KeyWord keyWord){
    	KeyWord oldKeyWord = queryKeyWord(keyWord.getWord());
    	
    	if(oldKeyWord==null){
    		int id = queryMaxId()+1;
    		keyWord.setId(id);
    		
    		this.insert("INSERT INTO KEYWORD(ID, WORD) VALUES (?,?)", 
    				    new Object[]{keyWord.getId(), keyWord.getWord()});
    		
    		return keyWord;
    		
    	}
    	
    	return oldKeyWord;
    }
    
    /**
     * 次分類關鍵字
     * @param subChannelId
     * @return
     */
    public List<KeyWord> querySubChannelKeywords(int subChannelId){
    	String sql = "SELECT * FROM KEYWORD WHERE ID IN "+
                     "(SELECT KEYWORD_ID FROM SUBCHANNEL_KEYWORD WHERE SUB_CHANNEL_ID=?)";
    	Object[] params = {subChannelId};
    	
    	return this.queryBeanListData(sql, params, KeyWord.class);
    }
    
    /**
     * 文章關鍵字
     * @param articleId
     * @return
     */
    public List<KeyWord> queryArticleKeywords(int articleId){
    	String sql = "SELECT * FROM KEYWORD WHERE ID IN "+
                     "(SELECT KEYWORD_ID FROM ARTICLE_KEYWORD WHERE ARTICLE_ID=?)";
    	Object[] params = {articleId};
    	
    	return this.queryBeanListData(sql, params, KeyWord.class);
    }
}
