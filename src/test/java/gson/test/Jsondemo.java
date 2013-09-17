package gson.test;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Jsondemo {
	public static void main(String[] args) {
		//======= Object to JSON =======
		//建立Book物件
		List<Book> list = new ArrayList<Book>();
		list.add( new Book("956-987236-1", "Java歷險記", 550) );
		list.add( new Book("956-987236-2", "Java歷險記2", 600) );
		
		//建立GSON物件
		Gson gson = new Gson();
		
		//將Book物件轉成JSON
		String json = gson.toJson(list);
		
		//把JSON格式的資料秀出來
		System.out.println(json);
		
		//將JSON格式的資料轉成物件
		List<Book> jbooks = gson.fromJson(json, List.class);
		System.out.println(jbooks.get(0));
		System.out.println(jbooks.get(1));
	}
}
