package cw.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbutils.ProxyFactory;
import org.apache.commons.dbutils.wrappers.SqlNullCheckedResultSet;
import org.springframework.stereotype.Repository;

import cw.Constants;
import cw.model.MasterChannel;

@Repository("masterChannelDao")
public class MasterChannelDao extends BaseDao{
	
    public List<MasterChannel> queryAllMasterChannels(){
    	
    	List<MasterChannel> list = new ArrayList<MasterChannel>();
    	
    	Connection conn = null;    	 
    	
    	try{
    		conn = this.getConnection();
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("SELECT * FROM MASTER_CHANNEL ORDER BY ID");
    		
    		SqlNullCheckedResultSet wrapper = new SqlNullCheckedResultSet(rs);  
            wrapper.setNullString(Constants.NULL_STRING); // Set null string  
            rs = ProxyFactory.instance().createResultSet(wrapper);
            
            MasterChannel masterChannel;
            while(rs.next()){
            	masterChannel = new MasterChannel();
            	masterChannel.setId( rs.getInt("ID") );
            	masterChannel.setName( rs.getString("NAME") );
            	
            	list.add(masterChannel);
            }
    		
            rs.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		if(conn!=null){
    			this.closeConnection(conn);
    		}
    	}
    	
    	return list;
    }
    
    public int deleteAllMasterChannels(){    	
    	return this.delete("DELETE FROM MASTER_CHANNEL", null);
    }
    
    public MasterChannel queryMasterChannel(int masterChannelId){
    	String sql = "SELECT * FROM MASTER_CHANNEL WHERE ID=?";
    	Object[] params = {masterChannelId};
    	List<MasterChannel> list = this.queryBeanListData(sql, params, MasterChannel.class);
    	
    	if( CollectionUtils.isNotEmpty(list) ){
    		return list.get(0);
    	}
    	
    	return null;
    }
    
    /**
     * 新增 OR 更新大分類
     * @param masterChannel
     * @return
     */
    public int insertOrUpdateMasterChannel(MasterChannel masterChannel){
    	int insertOrUpdateItemCount = -1;
    	
    	MasterChannel oldMasterChannel = queryMasterChannel(masterChannel.getId());
    	
    	//新增
    	if(oldMasterChannel==null){
    		insertOrUpdateItemCount = insertMasterChannel(masterChannel);
    	}
    	
    	//更新
    	if(oldMasterChannel!=null){
    		insertOrUpdateItemCount = updateMasterChannel(masterChannel);
    	}
    	
    	return insertOrUpdateItemCount;
    }
    
    public int insertMasterChannel(MasterChannel masterChannel){    	
    	Object[] insertParams = {masterChannel.getId(), masterChannel.getName()}; 
    	String sql = "INSERT INTO MASTER_CHANNEL(ID, NAME) VALUES (?,?)";
    	return this.insert(sql, insertParams);
    	
    }
    
    public int updateMasterChannel(MasterChannel masterChannel){    	
		Object[] updateParams = {masterChannel.getName(),masterChannel.getId()}; 
		String sql = "UPDATE MASTER_CHANNEL SET NAME=? WHERE ID=?";
    	return this.update(sql, updateParams);
    }
}
