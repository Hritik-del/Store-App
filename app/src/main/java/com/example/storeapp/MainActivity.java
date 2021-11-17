package com.example.storeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View listStore = findViewById(R.id.listGroups);
        View createNewStore = findViewById(R.id.createNewGroup);
        listStore.setOnClickListener(this);
        createNewStore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId())
        {
            case R.id.listGroups : intent = new Intent(this, View_Stores_Activity.class);
            startActivity(intent);
            break;

            case R.id.createNewGroup : intent = new Intent(this, Add_Details.class);
            startActivity(intent);
            break;
        }
    }
}


