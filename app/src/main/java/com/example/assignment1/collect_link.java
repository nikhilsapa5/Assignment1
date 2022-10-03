package com.example.assignment1;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class collect_link extends AppCompatActivity {

    private ArrayList<card_link> linkList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManger;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        init(savedInstanceState);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = 0;
                addLink(view, position);
            }
        });

        //Specify what action a specific gesture performs, in this case swiping right or left deletes the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar snack = Snackbar.make(parentLayout, "Deleted a link", Snackbar.LENGTH_LONG).setAction("Action", null);
                View snackView = snack.getView();
                TextView mTextView = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                snack.show();
                int position = viewHolder.getLayoutPosition();
                linkList.remove(position);
                recyclerAdapter.notifyItemRemoved(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    private void init(Bundle savedInstanceState) {
        createRecyclerView();
    }

    private void createRecyclerView() {
        recyclerLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter = new RecyclerAdapter(linkList);
        LinkListener linkListener = new LinkListener() {
            @Override
            public void onItemClick(int position) {
                String url = linkList.get(position).getUrl().toLowerCase();
                if (!url.contains("www.") && !url.startsWith("www.")) {
                    url = "www." + url;
                }
                if (!url.startsWith("http") && !url.startsWith("https")) {
                    url = "http://" + url;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        };
        recyclerAdapter.setOnItemClickListener(linkListener);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(recyclerLayoutManger);
    }

    private void addLink(View view, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Link");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newUrl = input.getText().toString();
                String message = "";
                if (Patterns.WEB_URL.matcher(newUrl).matches()) {
                    linkList.add(position, new card_link(newUrl));
                    recyclerAdapter.notifyItemInserted(position);
                    message = "Successfully added a new link";
                } else {
                    message = "Invalid Url Format";
                }
                Snackbar snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null);
                View snackView = snack.getView();
                TextView mTextView = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                snack.show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
