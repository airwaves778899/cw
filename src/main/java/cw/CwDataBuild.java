package cw;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import cw.dao.MasterChannelDao;
import cw.dao.SubChannelDao;
import cw.dao.ArticleDao;
import cw.dao.KeywordDao;
import cw.html.parser.CwArticleHtmlParser;
import cw.html.parser.CwMasterChannelHtmlParser;
import cw.model.Article;
import cw.model.MasterChannel;
import cw.model.SubChannel;

@Service("cwDataBuild")
public class CwDataBuild {
	
	@Autowired
	private KeywordDao keywordDao;
	
	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private MasterChannelDao masterChannelDao;
	
	@Autowired
	private SubChannelDao subChannelDao;
	
	/**
	 * 清除所有資料(非必要，不要使用)
	 */
    public void cleanAllData(){
    	int deleteItemCount;
    	
    	deleteItemCount = keywordDao.deleteAllRelation();
    	System.out.println("關鍵字關聯刪除筆數 = "+deleteItemCount);
    	
    	deleteItemCount = keywordDao.deleteAllKeywords();
    	System.out.println("關鍵字刪除筆數 = "+deleteItemCount);
    	
    	deleteItemCount = articleDao.deleteAllArticles();
    	System.out.println("文章刪除筆數 = "+deleteItemCount);
    	
    	deleteItemCount = subChannelDao.deleteAllSubChannels();
    	System.out.println("次分類刪除筆數 = "+deleteItemCount);
    	
    	deleteItemCount = masterChannelDao.deleteAllMasterChannels();
    	System.out.println("大分類刪除筆數 = "+deleteItemCount);

    }
    
    /**
     * 重建所有資料
     */
    public void rebuildAllData(){
		CwMasterChannelHtmlParser parser = new CwMasterChannelHtmlParser();
		
		try {
			Set<MasterChannel> list = parser.extractMasterChannels();
			for(MasterChannel masterChannel : list){
				//建立大分類資料
				masterChannelDao.insertOrUpdateMasterChannel(masterChannel);
				
				for(SubChannel subChannel : masterChannel.getSubChannels()){
					//建立次分類資料
					subChannelDao.insertOrUpdateSubChannel(subChannel);
					
					//建立文章資料
					rebuildAllArticleData(subChannel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
    }
    
    /**
     * 重建所有文章資料
     */
    public void rebuildAllArticleData(List<SubChannel> subChannels){
		for(SubChannel subChannel : subChannels){
			rebuildAllArticleData(subChannel);
		}
    }
    
    /**
     * 重建所有文章資料
     * @param subChannel
     */
    public void rebuildAllArticleData(SubChannel subChannel){

		CwArticleHtmlParser cwArticleHtmlParser = new CwArticleHtmlParser();
		List<String> urls = cwArticleHtmlParser.extracLinksFromSubChannel(subChannel.getId());
	
		for(String url : urls){
			Article article = cwArticleHtmlParser.extracLinkToArticle(url);
			articleDao.insertOrUpdateArticle(article);
		}
    }
    
    public static void main(String args[]){
		AnnotationConfigApplicationContext ctx = 
	            new AnnotationConfigApplicationContext("cw");
		CwDataBuild cwDataBuild = ctx.getBean(CwDataBuild.class);
		
    	//清除所有資料
		//cwDataBuild.cleanAllData();
    	
    	//重建所有資料
		cwDataBuild.rebuildAllData();
    }
}
