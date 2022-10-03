package com.example.assignment1;

public class CardLink implements LinkListener {

    private final String url;

    public CardLink(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void onItemClick(int position) {

    }
}
