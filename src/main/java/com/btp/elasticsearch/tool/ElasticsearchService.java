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
 * @Description:���������  �򵥲�������
 *
 */
public class ElasticsearchService {

	static JestClient jestClient = JestService.getJestClient();
	/**
	 * 
	 * @Description:����������dataΪһ���ĵ�
	 */
	public static void builderSearchIndex(String indexName,String data) {
		

		long start = System.currentTimeMillis();
		try {
			// �����������,ɾ������
			IndicesExists indicesExists = new IndicesExists.Builder(indexName).build();
			JestResult isIndexExist = jestClient.execute(indicesExists);
			if (isIndexExist != null && !isIndexExist.isSucceeded()) {
				System.out.println("===����"+indexName+"������");
				//������ ������
			}else{
				DeleteIndex index = new DeleteIndex.Builder(indexName).build();
				JestResult result = jestClient.execute(index);
				if(result.isSucceeded()) {
					System.out.println("===����"+indexName+"ɾ���ɹ�");
				}else {
					System.out.println("===����"+indexName+"ɾ��ʧ��");
				}
				
			}

			//��������
			JestResult result = jestClient.execute(new CreateIndex.Builder(indexName).build());
			if (result == null || !result.isSucceeded()) {
				throw new Exception(result.getErrorMessage() + "��������"+indexName+"ʧ��!");
			}
			Bulk.Builder bulk = new Bulk.Builder();
			// �������ȥ�����(ES)
			Index index = new Index.Builder(data).index(indexName)
	                .type(indexName).build();
			bulk.addAction(index);
			
			
			jestClient.execute(bulk.build());

		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("����"+indexName+"����ʱ��:����ʱ�� -->> " + (end - start) + " ����");
	}
	
	/**
	 * ����index�Լ�ͬ��index�¶���ĵ�
	 * @param indexName
	 * @param data
	 */
	public static void builderIndex(String indexName,String typeName,List<Object> objs) {
		

		long start = System.currentTimeMillis();
		try {
			// �����������,ɾ������
			IndicesExists indicesExists = new IndicesExists.Builder(indexName).build();
			JestResult isIndexExist = jestClient.execute(indicesExists);
			if (isIndexExist != null && !isIndexExist.isSucceeded()) {
				System.out.println("===����"+indexName+"������");
				//����������
				//��������
				JestResult result = jestClient.execute(new CreateIndex.Builder(indexName).build());
				if (result == null || !result.isSucceeded()) {
					throw new Exception(result.getErrorMessage() + "����"+indexName+"����ʧ��!");
				}	
			}else{
				//�������ڣ������������ĵ�
				System.out.println("===����"+indexName+"����");
			}
			
			// �������ȥ�����(ES)
			JestService.index(jestClient, indexName, typeName, objs);

		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("����"+indexName+"������ݹ���ʱ�� -->> " + (end - start) + " ����");
	}

	/**
	 * ��ȡһ��������ӳ��
	 * @throws Exception 
	 */
	public static Object getIndexMapping(String indexName, String typeName) throws Exception {
		return JestService.getIndexMapping(jestClient, indexName, typeName);
	}
	
	/**
	 * ����һ�������µ��ض����͵��ĵ�
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