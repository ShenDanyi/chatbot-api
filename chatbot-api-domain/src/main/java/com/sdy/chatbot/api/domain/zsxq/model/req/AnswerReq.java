package com.sdy.chatbot.api.domain.zsxq.model.req;

import com.alibaba.fastjson.annotation.JSONField;

// 请求问答接口信息
public class AnswerReq {
    @JSONField(name = "req_data")
    private ReqData reqData;

    public AnswerReq(ReqData reqData) {
        this.reqData = reqData;
    }

    public ReqData getReqData() {
        return reqData;
    }

    public void setReqData(ReqData reqData) {
        this.reqData = reqData;
    }
}
