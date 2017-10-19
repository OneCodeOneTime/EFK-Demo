package com.btp.elasticsearch.tool;

import java.util.List;

import org.elasticsearch.search.builder.SearchSourceBuilder;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;

/**
 * 
 * @Description:简单添加索引  简单查找索引
 *
 */
public class ElasticsearchService {

	static JestClient jestClient = JestService.getJestClient();
	/**
	 * 
	 * @Description:插入索引，data为一个文档
	 */
	public static void builderSearchIndex(String indexName,String data) {
		

		long start = System.currentTimeMillis();
		try {
			// 如果索引存在,删除索引
			IndicesExists indicesExists = new IndicesExists.Builder(indexName).build();
			JestResult isIndexExist = jestClient.execute(indicesExists);
			if (isIndexExist != null && !isIndexExist.isSucceeded()) {
				System.out.println("===索引"+indexName+"不存在");
				//不存在 不处理
			}else{
				DeleteIndex index = new DeleteIndex.Builder(indexName).build();
				JestResult result = jestClient.execute(index);
				if(result.isSucceeded()) {
					System.out.println("===索引"+indexName+"删除成功");
				}else {
					System.out.println("===索引"+indexName+"删除失败");
				}
				
			}

			//创建索引
			JestResult result = jestClient.execute(new CreateIndex.Builder(indexName).build());
			if (result == null || !result.isSucceeded()) {
				throw new Exception(result.getErrorMessage() + "创建索引"+indexName+"失败!");
			}
			Bulk.Builder bulk = new Bulk.Builder();
			// 添加数据去服务端(ES)
			Index index = new Index.Builder(data).index(indexName)
	                .type(indexName).build();
			bulk.addAction(index);
			
			
			jestClient.execute(bulk.build());

		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("创建"+indexName+"索引时间:共用时间 -->> " + (end - start) + " 毫秒");
	}
	
	/**
	 * 建立index以及同个index下多个文档
	 * @param indexName
	 * @param data
	 */
	public static void builderIndex(String indexName,String typeName,List<Object> objs) {
		

		long start = System.currentTimeMillis();
		try {
			// 如果索引存在,删除索引
			IndicesExists indicesExists = new IndicesExists.Builder(indexName).build();
			JestResult isIndexExist = jestClient.execute(indicesExists);
			if (isIndexExist != null && !isIndexExist.isSucceeded()) {
				System.out.println("===索引"+indexName+"不存在");
				//索引不存在
				//创建索引
				JestResult result = jestClient.execute(new CreateIndex.Builder(indexName).build());
				if (result == null || !result.isSucceeded()) {
					throw new Exception(result.getErrorMessage() + "创建"+indexName+"索引失败!");
				}	
			}else{
				//索引存在，索引下增加文档
				System.out.println("===索引"+indexName+"存在");
			}
			
			// 添加数据去服务端(ES)
			JestService.index(jestClient, indexName, typeName, objs);

		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("索引"+indexName+"添加数据共用时间 -->> " + (end - start) + " 毫秒");
	}

	/**
	 * 获取一个索引的映射
	 * @throws Exception 
	 */
	public static Object getIndexMapping(String indexName, String typeName) throws Exception {
		return JestService.getIndexMapping(jestClient, indexName, typeName);
	}
	
	/**
	 * 搜索一个索引下的特定类型的文档
	 * @throws Exception 
	 */
	public static SearchResult searchDataFromIndex(String indexName, String typeName) throws Exception {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();  
          
        searchSourceBuilder.size(10);  
        searchSourceBuilder.from(0);  
        String query = searchSourceBuilder.toString(); 
		 
		return JestService.search(jestClient, indexName, typeName,query);
	}
}