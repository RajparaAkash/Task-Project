package com.example.demo.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.Adapter.MainListAdapter;
import com.example.demo.Api.JsonPlaceholderApi;
import com.example.demo.Api.RetrofitClient;
import com.example.demo.Model.Post;
import com.example.demo.R;
import com.example.demo.Utils.TinyDB;
import com.example.demo.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDataActivity extends AppCompatActivity {

    RecyclerView mainListRV;
    Button resetBtn;
    ProgressBar progressBar;

    MainListAdapter mainListAdapter;
    DatabaseHelper databaseHelper;
    ArrayList<Post> postList;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        idBinding();
        tinyDB = new TinyDB(this);
        databaseHelper = DatabaseHelper.getInstance(this);
        postList = new ArrayList<>();
        fetchDataFromApi();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.noteDao().nukeTable();
                tinyDB.putBoolean("isData", false);
                fetchDataFromApi();
            }
        });
    }

    private void idBinding() {
        mainListRV = findViewById(R.id.mainListRV);
        resetBtn = findViewById(R.id.resetBtn);
        progressBar = findViewById(R.id.progressBar);
    }

    private void fetchDataFromApi() {
        mainListRV.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        JsonPlaceholderApi jsonApi = RetrofitClient.getJsonPlaceholderApi();

        Call<List<Post>> call = jsonApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> posts = response.body();

                    if (!tinyDB.getBoolean("isData")) {
                        if (posts != null && !posts.isEmpty()) {
                            for (Post post : posts) {
                                databaseHelper.noteDao().insert(new Post(
                                        post.getUserId(), post.getId(), post.getTitle(), post.getBody()));
                            }
                            tinyDB.putBoolean("isData", true);
                        }
                    }

                    postList = (ArrayList<Post>) databaseHelper.noteDao().getAll();
                    mainListAdapter = new MainListAdapter(postList, ListDataActivity.this, databaseHelper, new MainListAdapter.UpdateClick() {
                        @Override
                        public void onUpdate() {
                            postList = (ArrayList<Post>) databaseHelper.noteDao().getAll();
                            mainListAdapter.updateData(postList);
                        }
                    });

                    mainListRV.setLayoutManager(new LinearLayoutManager(ListDataActivity.this, RecyclerView.VERTICAL, false));
                    mainListRV.setAdapter(mainListAdapter);

                    progressBar.setVisibility(View.GONE);
                    mainListRV.setVisibility(View.VISIBLE);

                } else {
                    Log.e("TAG", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("TAG", "Network Failure: " + t.getMessage());
            }
        });
    }
}