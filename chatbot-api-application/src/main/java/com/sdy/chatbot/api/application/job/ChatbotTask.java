package com.sdy.chatbot.api.application.job;

import com.alibaba.fastjson.JSON;
import com.sdy.chatbot.api.domain.ai.IOpenAI;
import com.sdy.chatbot.api.domain.zsxq.IZsxqApi;
import com.sdy.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.sdy.chatbot.api.domain.zsxq.model.vo.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * @author 小傅哥，微信：fustack
 * @description 任务体
 * @github https://github.com/fuzhengwei
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class ChatbotTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ChatbotTask.class);

    private String groupName;
    private String groupId;
    private String cookie;
    private String openAIKey;

    private IZsxqApi zsxqApi;
    private IOpenAI openAI;

    public ChatbotTask(String groupName, String groupId, String cookie, String openAIKey, IZsxqApi zsxqApi, IOpenAI openAI) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.cookie = cookie;
        this.openAIKey = openAIKey;
        this.zsxqApi = zsxqApi;
        this.openAI = openAI;
    }

    @Override
    public void run() {
        try {
            // 随机打烊，避免风控
            if (new Random().nextBoolean()){
                logger.info("随机打烊中...");
                return;
            }

            // 不在营业范围--->打烊
            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if(hour>23||hour<1){
                logger.info("打样时间不工作，AI下班了");
                return;
            }

            // 1.检索问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnswerQuestionsTopicId(groupId, cookie);
            logger.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            if (topics == null || topics.isEmpty()) {
                logger.info("本次检索未查询到待回答问题");
                return;
            }
            // 2.AI回答
            for(Topics topic: topics){
                String text = topic.getTalk().getText();
                String answer = openAI.doChatGPT(openAIKey, text).trim();

                // 3.问题回复
                String userId = topic.getTalk().getOwner().getUser_id();
                String topicId = topic.getTopic_id();
                int commentsCount = topic.getComments_count();
                if (userId.equals("412455542181828")&&commentsCount==0) {
                    boolean isAnswer = zsxqApi.answer(groupId, cookie, topicId, answer, false);
                    logger.info("编号：{} 用户名：{} 问题：{} 回答：{} 状态：{}", topicId, userId, text, answer, isAnswer);
                    return;
                }
            }
        } catch (Exception e) {
            logger.error("自动回答问题异常",e);
        }
    }

}