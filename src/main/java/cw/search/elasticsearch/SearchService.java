package cw.search.elasticsearch;

import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cw.model.Article;

@Service
public class SearchService {
	@Autowired
	private JestClientUtil jestClientUtil;
	
	private String index = "cw";
	private String Article_TYPE = "article";
	
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
	
	public boolean deleteIndex()throws Exception{
		JestResult result = jestClientUtil.deleteIndex(index);
		return result.isSucceeded();
	}
	
	public boolean indexArticles(List<Article> articles) throws Exception{
		Index _index = new Index.Builder(articles).index(index).type(Article_TYPE).build();
		JestResult result = jestClientUtil.execute(_index);
		return result.isSucceeded();
	}
	
}
