package cw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cw.Constants;

public abstract class BaseDao {
	static {
		DbUtils.loadDriver(Constants.DB_DRIVER);  
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(Constants.DB_URL);
	}
	
	public void closeConnection(Connection conn){
		if(conn!=null){
			DbUtils.closeQuietly(conn);
		}	
	}
	
	
	protected int delete(String sql, Object[] deleteParams){
		return update(sql, deleteParams);
	}
	
	protected int insert(String sql, Object[] insertParams){
		return update(sql, insertParams);
	}
	
	protected int update(String sql, Object[] updateParams){
    	Connection conn = null;
    	QueryRunner qr = new QueryRunner();
    	
    	try{
    		conn = this.getConnection();
    		return qr.update(conn, sql, updateParams);
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    		return -1;
    	}finally{
    		if(conn!=null){
    			this.closeConnection(conn);
    		}
    	}
	}
	
	protected List<Map<String, Object>> queryMapListData(String sql, Object[] params){
    	Connection conn = null;
    	QueryRunner qr = new QueryRunner();
    	
    	try{
    		conn = this.getConnection();
    		
    		return qr.query(conn, sql, new MapListHandler(), params);
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    		return Collections.EMPTY_LIST;
    	}finally{
    		if(conn!=null){
    			this.closeConnection(conn);
    		}
    	}
	}
	
	protected <T> List<T> queryBeanListData(String sql, Object[] params, Class<T> elementType){
    	Connection conn = null;
    	QueryRunner qr = new QueryRunner();
    	
    	try{
    		conn = this.getConnection();
    		
    		return qr.query(conn, sql, new BeanListHandler(elementType), params);
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    		return Collections.EMPTY_LIST;
    	}finally{
    		if(conn!=null){
    			this.closeConnection(conn);
    		}
    	}
	}
}
