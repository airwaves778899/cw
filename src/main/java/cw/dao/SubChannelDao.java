package cw.dao;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import cw.model.KeyWord;
import cw.model.MasterChannel;
import cw.model.SubChannel;

public class SubChannelDao extends BaseDao{
	
	private static SubChannelDao dao;
	
	public static SubChannelDao getInstance(){
		if(dao==null){
			dao = new SubChannelDao();
		}
		
		return dao;
	}
	
	/**
	 * 刪除所有次分類
	 * @return
	 */
    public int deleteAllSubChannels(){
    	int deleteItemCount = -1;
    	
    	//刪除所有次分類
    	deleteItemCount = this.delete("DELETE FROM SUB_CHANNEL", null); 
    	//刪除所有次分類關鍵字關聯
    	this.delete("DELETE FROM SUBCHANNEL_KEYWORD", null); 
    	
    	return deleteItemCount;
    }
    
    public List<SubChannel> queryAllSubChannels(){
    	String sql = "SELECT * FROM SUB_CHANNEL ORDER BY ID";
    	List<SubChannel> list = this.queryBeanListData(sql, null, SubChannel.class);
    	
    	for(SubChannel subChannel : list){
    		List<KeyWord> keywords = KeywordDao.getInstance().querySubChannelKeywords(subChannel.getId());
    		
    		if(CollectionUtils.isNotEmpty(keywords)){
    		    subChannel.setKeyWords(new HashSet(keywords));
    		}
    	}
    	
    	return list;
    }
    
    public SubChannel querySubChannel(int subChannelId){
    	String sql = "SELECT * FROM SUB_CHANNEL WHERE ID=?";
    	Object[] params = {subChannelId};
    	List<SubChannel> list = this.queryBeanListData(sql, params, SubChannel.class);
    	
    	if(CollectionUtils.isNotEmpty(list)){
    		SubChannel subChannel = list.get(0);
    		
    		List<KeyWord> keywords = KeywordDao.getInstance().querySubChannelKeywords(subChannel.getId());
    		if(CollectionUtils.isNotEmpty(keywords)){
    		    subChannel.setKeyWords(new HashSet(keywords));
    		}
    		
    		return subChannel;
    	}
    	
    	return null;
    }
    
    public int insertOrUpdateSubChannel(SubChannel subChannel){
    	int insertOrUpdateItemCount = -1;
    	
    	SubChannel oldSubChannel = querySubChannel( subChannel.getId() );
    	
    	//新增
    	if(oldSubChannel==null){
    		insertOrUpdateItemCount = insertSubChannel(subChannel);
    	}
    	
    	//更新
    	if(oldSubChannel!=null){
    		insertOrUpdateItemCount = updateSubChannel(subChannel);
    	}
    	
    	//更新KEYWORDS
    	updateSubChannelKeywords(subChannel);
    	
    	return insertOrUpdateItemCount;
    }
    
    /**
     * 新增
     * @param subChannel
     * @return
     */
    public int insertSubChannel(SubChannel subChannel){
		Object[] insertParams = {subChannel.getId(), 
                subChannel.getName(),
                subChannel.getMasterChannelId()}; 
		String sql = "INSERT INTO SUB_CHANNEL(ID, NAME, MASTER_CHANNEL_ID) VALUES (?,?,?)";
		return this.insert(sql,insertParams);
    	
    }
    
    /**
     * 更新
     * @param subChannel
     * @return
     */
    public int updateSubChannel(SubChannel subChannel){    	
		Object[] updateParams = {subChannel.getName(),
				                 subChannel.getMasterChannelId(), 
				                 subChannel.getId()}; 
		String sql = "UPDATE SUB_CHANNEL SET NAME=? , MASTER_CHANNEL_ID=? WHERE ID=?";
    	return this.update(sql, updateParams);
    }
    
    public void updateSubChannelKeywords(SubChannel subChannel){
    	if(CollectionUtils.isNotEmpty(subChannel.getKeyWords())){
    		for(KeyWord keyword : subChannel.getKeyWords()){
    			//建立keyword
    			keyword = KeywordDao.getInstance().insertOrUpdateKeyWord(keyword);
    			
    			//建立SubChannel & keyword關聯
    			insertSubChannelKeywordRelation(subChannel.getId(), keyword.getId());
    		}
    	}
    }
    
    /**
     * 建立SubChannel & keyword關聯
     * @param subChannelId
     * @param keywordId
     */
    private void insertSubChannelKeywordRelation(int subChannelId, int keywordId){
    	List<Map<String, Object>> list = this.queryMapListData(
    			"SELECT * FROM SUBCHANNEL_KEYWORD WHERE SUB_CHANNEL_ID=? AND KEYWORD_ID=? ", 
    			new Object[]{subChannelId, keywordId});
    	
    	if(CollectionUtils.isEmpty(list)){
    		this.insert("INSERT INTO SUBCHANNEL_KEYWORD(SUB_CHANNEL_ID, KEYWORD_ID) VALUES (?,?)",
    				new Object[]{subChannelId, keywordId});
    	}
    }
}
