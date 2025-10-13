package com.sdy.chatbot.api.domain.ai.model.vo;

public class Completion_tokens_details
{
    private int reasoning_tokens;

    private int accepted_prediction_tokens;

    private int rejected_prediction_tokens;

    public void setReasoning_tokens(int reasoning_tokens){
        this.reasoning_tokens = reasoning_tokens;
    }
    public int getReasoning_tokens(){
        return this.reasoning_tokens;
    }
    public void setAccepted_prediction_tokens(int accepted_prediction_tokens){
        this.accepted_prediction_tokens = accepted_prediction_tokens;
    }
    public int getAccepted_prediction_tokens(){
        return this.accepted_prediction_tokens;
    }
    public void setRejected_prediction_tokens(int rejected_prediction_tokens){
        this.rejected_prediction_tokens = rejected_prediction_tokens;
    }
    public int getRejected_prediction_tokens(){
        return this.rejected_prediction_tokens;
    }
}
