package com.louisgeek.louispinnedsectionitemdecorationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.kiguruming.recyclerview.itemdecoration.PinnedHeaderItemDecoration;

public class MainActivity extends AppCompatActivity {

    MyRecyclerViewAdapter myRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.id_rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myRecyclerViewAdapter=new MyRecyclerViewAdapter(this);

        myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                Toast.makeText(getApplicationContext(), "onItemClick pos:" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int position, Object object) {
                myRecyclerViewAdapter.deleteDataItem(position);
            }
        });

        myRecyclerViewAdapter.setOnFooterClickListener(new MyRecyclerViewAdapter.OnFooterClickListener() {
            @Override
            public void onFooterClick(int position, Object object) {
                myRecyclerViewAdapter.addDataItem(position, null);
            }
        });

        recyclerView.setAdapter(myRecyclerViewAdapter);

        recyclerView.addItemDecoration(new PinnedHeaderItemDecoration());

    }
}
