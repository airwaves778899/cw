package cw.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cw.dao.MasterChannelDao;
import cw.dao.SubChannelDao;
import cw.model.KeyWord;
import cw.model.MasterChannel;
import cw.model.SubChannel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("jasonDataBuilder")
public class JasonDataBuilder {
	
	@Autowired
	private MasterChannelDao masterChannelDao;
	
	@Autowired
	private SubChannelDao subChannelDao;
	
	public String buildCwJasonData(){
		List<MasterChannel> masterChannels = masterChannelDao.queryAllMasterChannels();

		MasterChannel masterChannel = masterChannels.get(0);
		
		JSONObject root = buildNode(masterChannel.getName());
		
		{
			List<SubChannel> subChannels = subChannelDao.querySubChannelsByMasterChannelId(masterChannel.getId());
		    for(SubChannel subChannel : subChannels){
		    	JSONObject subChannelJSONObject = buildNode(subChannel.getId(), subChannel.getName());
		    	
		    	//KEY WORDS
		    	if(CollectionUtils.isNotEmpty(subChannel.getKeyWords())){
		    		int keywordCount = subChannel.getKeyWords().size();
			    	String[] names = new String[keywordCount];
			    	int[] size = new int[keywordCount];
			    	
			    	int index = 0;
			    	for(KeyWord keyWord : subChannel.getKeyWords()){			    		
			    		names[index] = keyWord.getWord();
			    		size[index] = 0;			    		
			    		index++;
			    	}
			    	
			    	buildData(subChannelJSONObject, names, size);
		    	}
		    	
		    	addChildNodeToParentNode(root, subChannelJSONObject);
		    }
		}
		
		System.out.println( root.toString(1) );
		return root.toString(1);
	}
	
	public static void buildData(JSONObject jsonObject, String[] names, int[] size){
		
		if(jsonObject.get("children")==null){
			jsonObject.put("children", new JSONArray());
		}
		
		JSONArray array = (JSONArray)jsonObject.get("children");
		
		for(int i=0; i<names.length; i++){
			JSONObject obj = new JSONObject();
			obj.put("name", names[i]);
			obj.put("size", size[i]);
			array.add(obj);
		}
	}
	
	public static JSONObject buildNode(String name){

		JSONObject json = new JSONObject();
		json.put("name", name);
		
		return json;
	}
	
	public static JSONObject buildNode(int id, String name){

		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("name", name);
		
		return json;
	}
	
	public static void addChildNodeToParentNode(JSONObject parentJsonObject, JSONObject theJsonObject){
		if(parentJsonObject!=null){
			if(parentJsonObject.get("children")==null){
				parentJsonObject.put("children", new JSONArray());
			}
			
			JSONArray parentArray = (JSONArray)parentJsonObject.get("children");
			parentArray.add(theJsonObject);
		}
	}
	
	public static String buildJson(){
		JSONObject root = buildNode("flare");
		
		{
			JSONObject analytics = buildNode("analytics");
			
			{
				JSONObject cluster = buildNode("cluster");
				
				String[] names = {"AgglomerativeCluster","CommunityStructure"}; 
				int[] size = {3938, 3812};
				buildData(cluster, names, size);
				
				addChildNodeToParentNode(analytics, cluster);
			}
			
			{
				JSONObject graph = buildNode("graph");
				
				addChildNodeToParentNode(analytics, graph);
			}
			
			{
				JSONObject optimization = buildNode("optimization");
				
				addChildNodeToParentNode(analytics, optimization);
			}
			
			addChildNodeToParentNode(root, analytics);
		}
		
		{
			JSONObject animate = buildNode("animate");
			
			String[] names = {"Easing","FunctionSequence"}; 
			int[] size = {17010, 5842};
			buildData(animate, names, size);
			
			addChildNodeToParentNode(root, animate);
		}		
		
		return root.toString(2);
	}
}
