package com.sdy.chatbot.api.application.job;

import com.alibaba.fastjson.JSON;
import com.sdy.chatbot.api.domain.ai.IOpenAI;
import com.sdy.chatbot.api.domain.zsxq.IZsxqApi;
import com.sdy.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.sdy.chatbot.api.domain.zsxq.model.vo.Topics;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

// 问答任务
@EnableScheduling
@Configuration
public class ChatbotSchedule {

    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAI openAI;

    //cron = "0 */1 * * * *"每一分钟，"0/5 * * * * ?"每五秒。表达式网站：cron.qqe2.com
    @Scheduled(cron = "0/55 * * * * ?")
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
                String answer = openAI.doChatGPT(text).trim();

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
