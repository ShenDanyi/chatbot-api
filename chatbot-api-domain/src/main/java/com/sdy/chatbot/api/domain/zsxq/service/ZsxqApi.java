package com.sdy.chatbot.api.domain.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdy.chatbot.api.domain.zsxq.IZsxqApi;
import com.sdy.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.sdy.chatbot.api.domain.zsxq.model.req.AnswerReq;
import com.sdy.chatbot.api.domain.zsxq.model.req.ReqData;
import com.sdy.chatbot.api.domain.zsxq.model.res.AnswerRes;
import com.sdy.chatbot.api.domain.zsxq.model.vo.Resp_data;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ZsxqApi implements IZsxqApi {

    // 为当前类创建日志记录器
    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);

    @Override
    public UnAnsweredQuestionsAggregates queryUnAnswerQuestionsTopicId(String groupId, String cookie) throws IOException {
        // 1️⃣ 创建一个可关闭的 HttpClient 实例，用于发送 HTTP 请求
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 2️⃣ 创建一个 GET 请求对象，指定请求 URI（获取群组话题）
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/"+ groupId +"/topics?scope=all&count=3");

        // 3️⃣ 添加请求头 - cookie：用于携带登录信息（访问受保护的接口）
        get.addHeader("cookie",cookie);

        // 4️⃣ 添加请求头 - Content-Type：告诉服务器请求的数据格式是 JSON
        get.addHeader("Content-Type","application/json; charset=UTF-8");

        // 5️⃣ 执行请求，得到服务器响应
        CloseableHttpResponse response = httpClient.execute(get);

        // 6️⃣ 判断响应状态码是否为 200（HttpStatus.SC_OK），表示请求成功
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 7️⃣ 读取响应体，将响应内容转换为字符串
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("拉取提问数据。groupId：{} jsonStr：{}", groupId, jsonStr);
            logger.info(jsonStr);
            return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);
        } else {
            // 9️⃣ 如果请求不成功，抛出异常信息
            throw new RuntimeException("queryUnAnswerQuestionsTopicId Err Code is " + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {
        // 1️⃣ 创建一个可关闭的 HttpClient 实例，用于发送 HTTP 请求
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        System.out.println("topicId: "+topicId);

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/comments");
        // 3️⃣ 添加请求头 - cookie：用于携带登录信息（访问受保护的接口）
        post.addHeader("cookie", cookie);
        // 4️⃣ 添加请求头 - Content-Type：告诉服务器请求的数据格式是 JSON
        post.addHeader("Content-Type","application/json; charset=UTF-8");
        post.addHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1 Edg/141.0.0.0");

        // 负载数据 测试样例
//        String paramJson = "{\n" +
//                "  \"req_data\": {\n" +
//                "    \"text\": \"卖报歌\\n\",\n" +
//                "    \"image_ids\": [],\n" +
//                "    \"mentioned_user_ids\": []\n" +
//                "  }\n" +
//                "}";
        AnswerReq answerReq = new AnswerReq(new ReqData(text));

        String paramJson = JSONObject.toJSONString(answerReq);

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("application/json", "UTF-8"));

        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);

        // 6️⃣ 判断响应状态码是否为 200（HttpStatus.SC_OK），表示请求成功
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 7️⃣ 读取响应体，将响应内容转换为字符串
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答问题结果。groupId：{} topicId：{} jsonStr：{}", groupId, topicId, jsonStr);
            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            return answerRes.isSucceeded();
        } else {
            // 9️⃣ 如果请求不成功，打印响应状态码
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
