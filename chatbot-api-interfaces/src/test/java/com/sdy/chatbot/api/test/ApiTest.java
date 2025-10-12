package com.sdy.chatbot.api.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class ApiTest {

    @Test
    public void query_unanswered_questions() throws IOException {
        // 1️⃣ 创建一个可关闭的 HttpClient 实例，用于发送 HTTP 请求
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 2️⃣ 创建一个 GET 请求对象，指定请求 URI（获取群组话题）
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/28885518425541/topics?scope=all&count=1");

        // 3️⃣ 添加请求头 - cookie：用于携带登录信息（访问受保护的接口）
        get.addHeader("cookie","zsxq_access_token=7BB17750-B8D2-48C2-A3ED-9F03F4AC337C_D8F3C3F6DC43738B; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22412455542181828%22%2C%22first_id%22%3A%22195b75c9beab30-0eb0131a7a58a28-4c657b58-1821369-195b75c9beb1fe%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTk1Yjc1YzliZWFiMzAtMGViMDEzMWE3YTU4YTI4LTRjNjU3YjU4LTE4MjEzNjktMTk1Yjc1YzliZWIxZmUiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI0MTI0NTU1NDIxODE4MjgifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22412455542181828%22%7D%2C%22%24device_id%22%3A%22195b75c9beab30-0eb0131a7a58a28-4c657b58-1821369-195b75c9beb1fe%22%7D; abtest_env=product");

        // 4️⃣ 添加请求头 - Content-Type：告诉服务器请求的数据格式是 JSON
        get.addHeader("Content-Type","application/json; charset=UTF-8");

        // 5️⃣ 执行请求，得到服务器响应
        CloseableHttpResponse response = httpClient.execute(get);

        // 6️⃣ 判断响应状态码是否为 200（HttpStatus.SC_OK），表示请求成功
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 7️⃣ 读取响应体，将响应内容转换为字符串
            String res = EntityUtils.toString(response.getEntity());
            // 8️⃣ 打印返回的 JSON 数据（群组话题信息）
            System.out.println(res);
        } else {
            // 9️⃣ 如果请求不成功，打印响应状态码
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer() throws IOException {
        // 1️⃣ 创建一个可关闭的 HttpClient 实例，用于发送 HTTP 请求
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/5124244184148184/comments");
        // 3️⃣ 添加请求头 - cookie：用于携带登录信息（访问受保护的接口）
        post.addHeader("cookie","zsxq_access_token=7BB17750-B8D2-48C2-A3ED-9F03F4AC337C_D8F3C3F6DC43738B; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22412455542181828%22%2C%22first_id%22%3A%22195b75c9beab30-0eb0131a7a58a28-4c657b58-1821369-195b75c9beb1fe%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTk1Yjc1YzliZWFiMzAtMGViMDEzMWE3YTU4YTI4LTRjNjU3YjU4LTE4MjEzNjktMTk1Yjc1YzliZWIxZmUiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI0MTI0NTU1NDIxODE4MjgifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22412455542181828%22%7D%2C%22%24device_id%22%3A%22195b75c9beab30-0eb0131a7a58a28-4c657b58-1821369-195b75c9beb1fe%22%7D; abtest_env=product");
        // 4️⃣ 添加请求头 - Content-Type：告诉服务器请求的数据格式是 JSON
        post.addHeader("Content-Type","application/json; charset=UTF-8");

        // 负载数据
        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"卖报歌\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"mentioned_user_ids\": []\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("application/json", "UTF-8"));

        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);

        // 6️⃣ 判断响应状态码是否为 200（HttpStatus.SC_OK），表示请求成功
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 7️⃣ 读取响应体，将响应内容转换为字符串
            String res = EntityUtils.toString(response.getEntity());
            // 8️⃣ 打印返回的 JSON 数据（群组话题信息）
            System.out.println(res);
        } else {
            // 9️⃣ 如果请求不成功，打印响应状态码
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
}
