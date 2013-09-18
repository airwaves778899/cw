package cw.search.elasticsearch;

import java.util.Arrays;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Get;
import io.searchbox.core.Search;
import io.searchbox.core.Bulk.Builder;
import io.searchbox.core.Index;

import org.elasticsearch.index.query.QueryBuilders;
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
	
	public boolean indicesExists() throws Exception{
		JestResult result = jestClientUtil.indicesExists(index);
		System.out.println(result.getErrorMessage());
		return result.isSucceeded();
	}
	
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
		Builder bulk = new Bulk.Builder()
		    .defaultIndex(index)
		    .defaultType(Article_TYPE);

		for(Article article : articles){
			bulk.addAction( new Index.Builder(article).build() );
		}
		
		jestClientUtil.execute(bulk.build());
	}
	
	public boolean updateArticleIndex(Article article) throws Exception{
		JestResult result = jestClientUtil.execute( jestClientUtil.updatDocument(article, index, Article_TYPE) );
		return result.isSucceeded();
	}
	
	public boolean deleteArticleIndex(Article article) throws Exception{
		JestResult result = jestClientUtil.execute( jestClientUtil.deleteDocument(index, Article_TYPE, article.getId()+"") );
		return result.isSucceeded();
	}
	
	public boolean deleteArticleIndexById(int articleId) throws Exception{
		JestResult result = jestClientUtil.execute( jestClientUtil.deleteDocument(index, Article_TYPE, articleId+"") );
		return result.isSucceeded();
	}
	
	private Search getSearch(String query){
		Search search = new Search.Builder(query)
		.addIndex(index)
		.addType(Article_TYPE)
		.build();
		
		return search;
	}
	
	public Article searchArticleById(long id) throws Exception{
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("id", id));
		
		Search search = getSearch(searchSourceBuilder.toString());		
		JestResult result = jestClientUtil.execute(search);
		
		if(result.isSucceeded()){
			return result.getSourceAsObject(Article.class);
		}else{
			throw new Exception( result.getErrorMessage() );
		}
	}
	
	public List<Article> searchArticles(String queryString, int size) throws Exception{

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query( QueryBuilders.queryString(queryString) );
		searchSourceBuilder.size(size);
		
		Search search = getSearch(searchSourceBuilder.toString());		
		JestResult result = jestClientUtil.execute(search);
		
		if(result.isSucceeded()){
			return result.getSourceAsObjectList(Article.class);
		}else{
			throw new Exception( result.getErrorMessage() );
		}
	}
	
}
