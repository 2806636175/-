package com.es.test;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * TODO
 *
 * @author wpw
 * @since 2022/4/26
 */
public class ESTest_Doc_Insert {
    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        IndexRequest request = new IndexRequest();
        request.index("user_test").id("10001");

        User user = new User();
        user.setAge(20);
        user.setName("张三");
        user.setSex("男");
        // 向ES传入数据必须是JSON格式
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        request.source(userJson, XContentType.JSON);

        // 插入数据
        IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult() );
        // 关闭ES客户端
        esClient.close();
    }
}
