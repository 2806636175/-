package com.es.test;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

/**
 * TODO
 *
 * @author wpw
 * @since 2022/4/26
 */
public class ESTest_client_Create {
    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("test");
        CreateIndexResponse createIndexResponse = esClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

        // 响应状态
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println("索引操作" + acknowledged);
        // 关闭ES客户端
        esClient.close();
    }
}
