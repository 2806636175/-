package com.es.test;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

/**
 * TODO
 *
 * @author wpw
 * @since 2022/4/26
 */
public class ESTest_client_Delete {
    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 删除索引
        DeleteIndexRequest request = new DeleteIndexRequest("test");
        AcknowledgedResponse delete = esClient.indices().delete(request, RequestOptions.DEFAULT);

        // 响应状态
        System.out.println("索引删除响应状态" + delete.isAcknowledged());
        // 关闭ES客户端
        esClient.close();
    }
}
