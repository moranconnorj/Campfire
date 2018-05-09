package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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

        final EditText addGroupEditText = findViewById(R.id.addGroupEditText);

        ListView listView = findViewById(R.id.listView);
        final ArrayList<String> groupnames = new ArrayList<String>();
        groupnames.add("Add new group");
        groupnames.add("Testgroup1");
        groupnames.add("Testgroup2");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, groupnames);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (addGroupEditText.getText().length() > 0) {
                        ParseObject group = new ParseObject("Group");
                        group.put("groupname", addGroupEditText.getText().toString());
                        group.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
//                                    OK
                                    Log.i("Success", "We Saved the Group");
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        Log.i("No", "No");
                    }
                } else {
                    Log.i("group selected", groupnames.get(position));
                }
            }
        });



    }
}
