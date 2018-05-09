package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseUser;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    public void logout(View view) {
        ParseUser.logOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (ParseUser.getCurrentUser() != null) {
            Log.i("Signed In Second Activity", ParseUser.getCurrentUser().getUsername());
        } else {
            Log.i("Not Signed In Second Activity", "Nope");
        }

        ListView listView = findViewById(R.id.listView);
        ArrayList<String> groupnames = new ArrayList<String>();
        groupnames.add("Add new group");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, groupnames);
        listView.setAdapter(arrayAdapter);

    }
}
