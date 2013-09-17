package cw.search.elasticsearch;

import io.searchbox.client.JestResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
	@Autowired
	private JestClientUtil jestClientUtil;
	
	private String index = "cw";
	
	public boolean openIndex() throws Exception{
		JestResult result = jestClientUtil.openIndex(index);
		
		if( !result.isSucceeded() ){
			jestClientUtil.createIndex(index);
			result = jestClientUtil.openIndex(index);
		}
		
		if(result!=null){
		    return result.isSucceeded();
		}
		
		return false;
	}
	
	public boolean closeIndex() throws Exception{
		JestResult result = jestClientUtil.closeIndex(index);
		return result.isSucceeded();
	}
	
}
