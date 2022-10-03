package com.example.assignment1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleHolder> {
    private final ArrayList<CardLink> inputLinkList;
    private LinkListener listener;

    public RecycleAdapter(ArrayList<CardLink> inputLinkList) {
        this.inputLinkList = inputLinkList;
    }

    public void setOnItemClickListener(LinkListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return inputLinkList.size();
    }

    @Override
    public RecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_link, parent, false);
        return new RecycleHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RecycleHolder holder, int position) {
        CardLink item = inputLinkList.get(position);
        holder.url.setText(item.getUrl());
    }
}
