package com.sdy.chatbot.api.test;

import com.alibaba.fastjson.JSON;
import com.sdy.chatbot.api.domain.ai.IOpenAI;
import com.sdy.chatbot.api.domain.ai.service.OpenAI;
import com.sdy.chatbot.api.domain.zsxq.IZsxqApi;
import com.sdy.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.sdy.chatbot.api.domain.zsxq.model.vo.Topics;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAI openAI;

    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnswerQuestionsTopicId(groupId, cookie);
        logger.info("测试结果：{}",JSON.toJSONString(unAnsweredQuestionsAggregates));
        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
        if(topics==null){
            logger.info("topics为空");
            return;
        }
        for(Topics topic: topics){
            String topicId = topic.getTopic_id();
            String text = topic.getTalk().getText();
            String userId = topic.getTalk().getOwner().getUser_id();
            logger.info("userId：{} topicId：{} text：{}", userId, topicId, text);

            // 回答问题
            if(userId.equals("412455542181828")) {
                zsxqApi.answer(groupId, cookie, topicId, text, false);
            }
        }
    }

    @Test
    public void test_openai() throws IOException {
        String response = openAI.doChatGPT("帮我写一个Java冒泡排序");
        logger.info("reponse:" + response);
    }
}
