package cw.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JasonDataBuilder {
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
	
	public static void main(String args[]){
		System.out.println(buildJson());
	}
}
