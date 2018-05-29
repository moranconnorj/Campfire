package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    public List<ParseObject> parseGroup(String groupID) {
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("GroupMembership");

            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.whereEqualTo("groupID", groupID);

            return query.find();

        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<ParseObject>();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        TextView groupNameTextView = findViewById(R.id.groupNameTextView);


        Intent intent = getIntent();
        String groupID = intent.getStringExtra("groupID");

        ParseObject currentGroup = parseGroup(groupID).get(0);

        groupNameTextView.setText(currentGroup.get("groupname").toString());
    }
}
