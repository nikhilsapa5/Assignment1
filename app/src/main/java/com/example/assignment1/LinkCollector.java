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

public class LinkCollector extends AppCompatActivity {
    private ArrayList<CardLink> inputLinkList = new ArrayList<>();
    private RecyclerView recycleView;
    private RecycleAdapter recycleAdapter;
    private RecyclerView.LayoutManager recycleLayoutManger;
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
                addLinkToView(view, position);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar snackBar = Snackbar.make(parentLayout, "Deleted a link", Snackbar.LENGTH_LONG).setAction("Action", null);
                View snackView = snackBar.getView();
                TextView mTextView = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                snackBar.show();
                int position = viewHolder.getLayoutPosition();
                inputLinkList.remove(position);
                recycleAdapter.notifyItemRemoved(position);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
        });
        itemTouchHelper.attachToRecyclerView(recycleView);
    }


    private void init(Bundle savedInstanceState) {
        createRecyclerView();
    }

    private void createRecyclerView() {
        recycleLayoutManger = new LinearLayoutManager(this);
        recycleView = findViewById(R.id.recycleView);
        recycleView.setHasFixedSize(true);
        recycleAdapter = new RecycleAdapter(inputLinkList);
        LinkListener linkListener = new LinkListener() {
            @Override
            public void onItemClick(int position) {
                String urlInput = inputLinkList.get(position).getUrl().toLowerCase();
                if (!urlInput.startsWith("http") && !urlInput.startsWith("https")) {
                    urlInput = "http://" + urlInput;
                }
                if (!urlInput.contains("www.") && !urlInput.startsWith("www.")) {
                    urlInput = "www." + urlInput;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlInput));
                startActivity(intent);
            }
        };
        recycleAdapter.setOnItemClickListener(linkListener);
        recycleView.setAdapter(recycleAdapter);
        recycleView.setLayoutManager(recycleLayoutManger);
    }

    private void addLinkToView (View view, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Link");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newUrl = input.getText().toString();
                String message = "";
                if (Patterns.WEB_URL.matcher(newUrl).matches()) {
                    inputLinkList.add(position, new CardLink(newUrl));
                    recycleAdapter.notifyItemInserted(position);
                    message = "Successfully added link to the View.";
                } else {
                    message = "Invalid Url Format. Please enter correct URL.";
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
