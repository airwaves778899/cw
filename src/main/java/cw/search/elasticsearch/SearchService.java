package cw.search.elasticsearch;

import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Bulk.Builder;
import io.searchbox.core.Index;

import org.elasticsearch.search.builder.SearchSourceBuilder;
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
	
	public void indexArticles(List<Article> articles) throws Exception{
		Builder bulk = new Bulk.Builder();

		for(Article article : articles){
			bulk.addAction( new Index.Builder(article).index(index).type(Article_TYPE).build() );
		}
		
		jestClientUtil.execute(bulk.build());
	}
	
	public void searchArticles() throws Exception{
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	}
	
}
