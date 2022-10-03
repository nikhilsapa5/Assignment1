package com.example.assignment1;

public class card_link implements LinkListener {

    private final String url;

    public card_link(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void onItemClick(int position) {

    }
}
