package cw;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Set;

import cw.dao.MasterChannelDao;
import cw.dao.SubChannelDao;
import cw.dao.ArticleDao;
import cw.dao.KeywordDao;
import cw.html.parser.CwArticleHtmlParser;
import cw.html.parser.CwMasterChannelHtmlParser;
import cw.model.Article;
import cw.model.MasterChannel;
import cw.model.SubChannel;

public class CwDataBuild {
	/**
	 * 清除所有資料(非必要，不要使用)
	 */
    public static void cleanAllData(){
    	int deleteItemCount;
    	
    	deleteItemCount = KeywordDao.getInstance().deleteAllRelation();
    	System.out.println("關鍵字關聯刪除筆數 = "+deleteItemCount);
    	
    	deleteItemCount = KeywordDao.getInstance().deleteAllKeywords();
    	System.out.println("關鍵字刪除筆數 = "+deleteItemCount);
    	
    	deleteItemCount = ArticleDao.getInstance().deleteAllArticles();
    	System.out.println("文章刪除筆數 = "+deleteItemCount);
    	
    	deleteItemCount = SubChannelDao.getInstance().deleteAllSubChannels();
    	System.out.println("次分類刪除筆數 = "+deleteItemCount);
    	
    	deleteItemCount = MasterChannelDao.getInstance().deleteAllMasterChannels();
    	System.out.println("大分類刪除筆數 = "+deleteItemCount);

    }
    
    /**
     * 重建所有資料
     */
    public static void rebuildAllData(){
		CwMasterChannelHtmlParser parser = new CwMasterChannelHtmlParser();
		
		try {
			Set<MasterChannel> list = parser.extractMasterChannels();
			for(MasterChannel masterChannel : list){
				//建立大分類資料
				MasterChannelDao.getInstance().insertOrUpdateMasterChannel(masterChannel);
				
				for(SubChannel subChannel : masterChannel.getSubChannels()){
					//建立次分類資料
					SubChannelDao.getInstance().insertOrUpdateSubChannel(subChannel);
					
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
    public static void rebuildAllArticleData(List<SubChannel> subChannels){
		for(SubChannel subChannel : subChannels){
			rebuildAllArticleData(subChannel);
		}
    }
    
    /**
     * 重建所有文章資料
     * @param subChannel
     */
    public static void rebuildAllArticleData(SubChannel subChannel){

		CwArticleHtmlParser cwArticleHtmlParser = new CwArticleHtmlParser();
		List<String> urls = cwArticleHtmlParser.extracLinksFromSubChannel(subChannel.getId());
	
		for(String url : urls){
			Article article = cwArticleHtmlParser.extracLinkToArticle(url);
			ArticleDao.getInstance().insertOrUpdateArticle(article);
		}
    }
    
    public static void main(String args[]){
    	//清除所有資料
    	//cleanAllData();
    	
    	//重建所有資料
    	rebuildAllData();
    }
}
