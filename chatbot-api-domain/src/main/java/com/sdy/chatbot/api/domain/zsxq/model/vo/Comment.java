package com.sdy.chatbot.api.domain.zsxq.model.vo;
public class Comment
{
    private String comment_id;

    private String create_time;

    private Owner owner;

    private String text;

    private int likes_count;

    private boolean group_owner_liked;

    private boolean topic_owner_liked;

    private int rewards_count;

    private boolean sticky;

    public void setComment_id(String comment_id){
        this.comment_id = comment_id;
    }
    public String getComment_id(){
        return this.comment_id;
    }
    public void setCreate_time(String create_time){
        this.create_time = create_time;
    }
    public String getCreate_time(){
        return this.create_time;
    }
    public void setOwner(Owner owner){
        this.owner = owner;
    }
    public Owner getOwner(){
        return this.owner;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
    public void setLikes_count(int likes_count){
        this.likes_count = likes_count;
    }
    public int getLikes_count(){
        return this.likes_count;
    }
    public void setGroup_owner_liked(boolean group_owner_liked){
        this.group_owner_liked = group_owner_liked;
    }
    public boolean getGroup_owner_liked(){
        return this.group_owner_liked;
    }
    public void setTopic_owner_liked(boolean topic_owner_liked){
        this.topic_owner_liked = topic_owner_liked;
    }
    public boolean getTopic_owner_liked(){
        return this.topic_owner_liked;
    }
    public void setRewards_count(int rewards_count){
        this.rewards_count = rewards_count;
    }
    public int getRewards_count(){
        return this.rewards_count;
    }
    public void setSticky(boolean sticky){
        this.sticky = sticky;
    }
    public boolean getSticky(){
        return this.sticky;
    }
}
