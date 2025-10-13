package com.sdy.chatbot.api.domain.ai.model.vo;

public class Usage
{
    private int prompt_tokens;

    private int completion_tokens;

    private int total_tokens;

    private Prompt_tokens_details prompt_tokens_details;

    private Completion_tokens_details completion_tokens_details;

    public void setPrompt_tokens(int prompt_tokens){
        this.prompt_tokens = prompt_tokens;
    }
    public int getPrompt_tokens(){
        return this.prompt_tokens;
    }
    public void setCompletion_tokens(int completion_tokens){
        this.completion_tokens = completion_tokens;
    }
    public int getCompletion_tokens(){
        return this.completion_tokens;
    }
    public void setTotal_tokens(int total_tokens){
        this.total_tokens = total_tokens;
    }
    public int getTotal_tokens(){
        return this.total_tokens;
    }
    public void setPrompt_tokens_details(Prompt_tokens_details prompt_tokens_details){
        this.prompt_tokens_details = prompt_tokens_details;
    }
    public Prompt_tokens_details getPrompt_tokens_details(){
        return this.prompt_tokens_details;
    }
    public void setCompletion_tokens_details(Completion_tokens_details completion_tokens_details){
        this.completion_tokens_details = completion_tokens_details;
    }
    public Completion_tokens_details getCompletion_tokens_details(){
        return this.completion_tokens_details;
    }
}
