package com.es.test;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

/**
 * TODO
 *
 * @author wpw
 * @since 2022/4/26
 */
public class ESTest_client_Search {
    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 查询索引
        GetIndexRequest getIndexRequest = new GetIndexRequest("test");
        GetIndexResponse getIndexResponse = esClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);

        // 响应状态
        System.out.println(getIndexResponse.getAliases());
        System.out.println(getIndexResponse.getMappings());
        System.out.println(getIndexResponse.getSettings());
        // 关闭ES客户端
        esClient.close();
    }
}
