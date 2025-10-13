package com.sdy.chatbot.api.domain.ai.service;

import com.alibaba.fastjson.JSON;
import com.sdy.chatbot.api.domain.ai.IOpenAI;
import com.sdy.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import com.sdy.chatbot.api.domain.ai.model.vo.Choices;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class OpenAI implements IOpenAI {

    private Logger logger = LoggerFactory.getLogger(OpenAI.class);

    @Value("${chatbot-api.openAIKey}")
    private String openAIKey;

    @Override
    public String doChatGPT(String question) throws IOException {
        // 1️⃣ 创建一个可关闭的 HttpClient 实例，用于发送 HTTP 请求
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://apis.itedus.cn/v1/chat/completions");
        post.addHeader("Content-Type","application/json");
        post.addHeader("Authorization","Bearer " + openAIKey);

        String paramJson = "{\n" +
                "  \"model\": \"gpt-4o\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"" + question + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("application/json", "UTF-8"));

        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);

        // 6️⃣ 判断响应状态码是否为 200（HttpStatus.SC_OK），表示请求成功
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 7️⃣ 读取响应体，将响应内容转换为字符串
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for(Choices choice: choices){
                answers.append(choice.getMessage().getContent());
            }
            return answers.toString();
        } else {
            throw new RuntimeException("api.openai.com Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
