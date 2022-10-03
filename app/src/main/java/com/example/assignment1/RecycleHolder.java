package com.example.assignment1;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleHolder extends RecyclerView.ViewHolder {
    public TextView url;

    public RecycleHolder(View itemView, final LinkListener listener) {
        super(itemView);
        url = itemView.findViewById(R.id.urlInput);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (RecyclerView.NO_POSITION != position) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
}
