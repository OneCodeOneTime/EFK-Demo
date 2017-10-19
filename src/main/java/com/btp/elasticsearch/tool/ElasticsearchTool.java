package com.btp.elasticsearch.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.btp.logstandard.model.ExceptionModel;

import io.searchbox.core.SearchResult;

/**
 * elasticsearch交互工具
 * @author baitp
 *
 */
public class ElasticsearchTool {
	/**
	 * 写单个数据到elasticsearch，默认索引名称和类型名称一样，一个索引下一个数据，索引不能有大写字母，只能由小写字母和数字组成
	 */
	public static void dataToElas(String indexName,String data){
		ElasticsearchService.builderSearchIndex(indexName, data);
	}
	
	/**
	 * 多个数据批量到一个索引下，指定索引名称和类型名称，索引不能有大写字母，只能由小写字母和数字组成
	 */
	public static void mulDataToElas(String indexName,String typeName,List<Object> objs) {
		ElasticsearchService.builderIndex(indexName, typeName, objs);
	}
	
	/**
	 * get索引的类型映射
	 * @throws Exception 
	 */
	public static Object getIndexMapping(String indexName, String typeName) throws Exception {
		return ElasticsearchService.getIndexMapping(indexName, typeName);
	}
	
	/**
	 * 搜索一个索引下的exception
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static Map<String,Object> searchDataFromIndex(String indexName, String typeName) throws Exception {
		SearchResult searchResult = ElasticsearchService.searchDataFromIndex(indexName, typeName);
		String jsonString = searchResult.getJsonString();
		JSONObject jo = new JSONObject();
		Object jsob = jo.parse(jsonString);
		
		int count = -1;
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		
		if(jsob instanceof JSONObject) {
			jo = (JSONObject)jsob;
			Object hitsOb = jo.get("hits");
			if(hitsOb instanceof JSONObject) {
				JSONObject hitsjo = (JSONObject)hitsOb;
				Object totalOb = hitsjo.get("total");
				
				if(totalOb instanceof Integer) {
					count = (Integer)totalOb;
				}
				
				Object hits1 = hitsjo.get("hits");
				if(hits1 instanceof JSONArray) {
					JSONArray ja = (JSONArray)hits1;
					for(int i = 0;i < ja.size();i++) {
						jsonList.add(ja.getJSONObject(i));
					}
				}
			}	
		}
		
		List<ExceptionModel> lists = ExceptionModel.jsonToExceptionModel(jsonList);
		lists = ExceptionModel.sortByTime(lists);
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("total", count);
		returnMap.put("exceptions", lists);
		
		return returnMap;
	}
	public static void main(String...args) throws Exception {
		Map<String,Object> ob = searchDataFromIndex("myexceptions","exception");
		Object obj = ob.get("exceptions");
		
		List<ExceptionModel> list = (ArrayList<ExceptionModel>)obj;
		for(ExceptionModel model:list) {
			System.out.println(model);
		}
	}
}
