package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        TextView groupNameTextView = findViewById(R.id.groupNameTextView);


        Intent intent = getIntent();
        String groupname = intent.getStringExtra("groupname");
        groupNameTextView.setText(groupname);
    }
}
