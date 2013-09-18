package cw.search.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.searchbox.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Update;
import io.searchbox.indices.CloseIndex;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.OpenIndex;

@Service
public class JestClientUtil {
	
	@Autowired
	@Qualifier("jestClient")
	public JestClient client;
	
	public JestResult indicesExists(String index) throws Exception{  
		return client.execute(new IndicesExists.Builder().build());    
	}
    
    public JestResult openIndex(String index) throws Exception{    	
    	return client.execute(new OpenIndex.Builder(index).build());    	
    }
    
    public JestResult closeIndex(String index) throws Exception{    	
    	return client.execute(new CloseIndex.Builder(index).build());    	
    }
    
    public JestResult deleteIndex(String index) throws Exception{    	
    	return client.execute(new DeleteIndex.Builder(index).build());    	
    }
    
    public JestResult createIndex(String index) throws Exception{    	
    	return client.execute(new CreateIndex.Builder(index).build());    	
    }
    
    public Index indexDocument(Object source, String index, String type){
    	return new Index.Builder(source).index(index).type(type).build();
    }
    
//    public Update updatDocuments(Object source, String index, String type){
//    	return new Update.Builder(source).index(index).type(type).build();
//    }
    
    public Update updatDocument(Object source, String index, String type, String id){
    	return new Update.Builder(source).index(index).type(type).id(id).build();
    }
    
    public Update updatDocument(Object source, String index, String type){
    	return new Update.Builder(source).index(index).type(type).build();
    }
    
    public Delete deleteDocument(String index, String type, String id){
    	return new Delete.Builder().index(index).type(type).id(id).build();
    }
    
    public JestResult execute(Action paramAction) throws Exception{
    	return client.execute(paramAction);
    }
}
