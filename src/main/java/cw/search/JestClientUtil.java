package cw.search;

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

public class JestClientUtil {
	public static String serverUri = "http://localhost:9200";
    public static JestClient client;
    
    static{
		// Configuration
		ClientConfig clientConfig = new ClientConfig.Builder(
				serverUri).multiThreaded(true).build();

		// Construct a new Jest client according to configuration via factory
		JestClientFactory factory = new JestClientFactory();
		factory.setClientConfig(clientConfig);
		client = factory.getObject();
    }
    
    public static JestResult openIndex(String index) throws Exception{    	
    	return client.execute(new OpenIndex.Builder(index).build());    	
    }
    
    public static JestResult closeIndex(String index) throws Exception{    	
    	return client.execute(new CloseIndex.Builder(index).build());    	
    }
    
    public static JestResult deleteIndex(String index) throws Exception{    	
    	return client.execute(new DeleteIndex.Builder(index).build());    	
    }
    
    public static JestResult createIndex(String index) throws Exception{    	
    	return client.execute(new CreateIndex.Builder(index).build());    	
    }
    
    public static Index indexDocument(Object source, String index, String type){
    	return new Index.Builder(source).index(index).type(type).build();
    }
    
    public static Update updatDocuments(Object source, String index, String type){
    	return new Update.Builder(source).index(index).type(type).build();
    }
    
    public static Update updatDocument(Object source, String index, String type, String id){
    	return new Update.Builder(source).index(index).type(type).id(id).build();
    }
    
    public static Delete deleteDocument(String index, String type, String id){
    	return new Delete.Builder().index(index).type(type).id(id).build();
    }
    
    public static JestResult execute(Action paramAction) throws Exception{
    	return client.execute(paramAction);
    }
}
