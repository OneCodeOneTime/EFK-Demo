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
 * elasticsearch��������
 * @author baitp
 *
 */
public class ElasticsearchTool {
	/**
	 * д�������ݵ�elasticsearch��Ĭ���������ƺ���������һ����һ��������һ�����ݣ����������д�д��ĸ��ֻ����Сд��ĸ���������
	 */
	public static void dataToElas(String indexName,String data){
		ElasticsearchService.builderSearchIndex(indexName, data);
	}
	
	/**
	 * �������������һ�������£�ָ���������ƺ��������ƣ����������д�д��ĸ��ֻ����Сд��ĸ���������
	 */
	public static void mulDataToElas(String indexName,String typeName,List<Object> objs) {
		ElasticsearchService.builderIndex(indexName, typeName, objs);
	}
	
	/**
	 * get����������ӳ��
	 * @throws Exception 
	 */
	public static Object getIndexMapping(String indexName, String typeName) throws Exception {
		return ElasticsearchService.getIndexMapping(indexName, typeName);
	}
	
	/**
	 * ����һ�������µ�exception
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
