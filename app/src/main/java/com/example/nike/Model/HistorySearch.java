package com.example.nike.Model;

public class HistorySearch {
    private int id;
    private int user_id;
    private String text_search;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getText_search() {
        return text_search;
    }

    public void setText_search(String text_search) {
        this.text_search = text_search;
    }

    public HistorySearch(){

    }

    public HistorySearch(int id, int user_id, String text_search) {
        this.id = id;
        this.user_id = user_id;
        this.text_search = text_search;
    }
}
