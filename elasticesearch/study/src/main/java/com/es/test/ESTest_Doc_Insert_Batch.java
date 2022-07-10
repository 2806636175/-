package com.es.test;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * 批量插入数据
 *
 * @author wpw
 * @since 2022/4/26
 */
public class ESTest_Doc_Insert_Batch {
    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 批量插入数据
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("user_test").id("10001").source(XContentType.JSON, "name", "ELK"));
        request.add(new IndexRequest().index("user_test").id("10002").source(XContentType.JSON, "name", "lisi"));
        request.add(new IndexRequest().index("user_test").id("10003").source(XContentType.JSON, "name", "wangwu"));

        // 插入数据
        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.getIngestTook() );
        System.out.println(response.getItems() );
        // 关闭ES客户端
        esClient.close();
    }
}
