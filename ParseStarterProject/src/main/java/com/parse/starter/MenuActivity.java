package com.parse.starter;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    EditText addGroupEditText;
    List<ParseObject> groupResults;
    List<ParseObject> userGroupResults;
    ArrayAdapter arrayAdapter;
    ArrayList<String> groupnames;

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void onClick(View view) {
        hideKeyboard();
    }


    public void logout(View view) {
        ParseUser.logOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void createGroupClicked(String groupName) {
        if (groupName.length() > 0) { // Group Name Not Blank
            if (groupExists()) { // Group/User combo already exists in Parse
                Toast.makeText(MenuActivity.this, "Group Already Exists", Toast.LENGTH_SHORT).show();
            } else { // Group/User combo DOES NOT exist in parse; create group
                createGroup();
            }
        } else { //Groupname length < 0
            Toast.makeText(MenuActivity.this, "Group Name Required", Toast.LENGTH_SHORT).show();
        }
    }

    public void existingGroupClicked() {

    }

    public void createGroup() {
        ParseObject group = new ParseObject("Group");
        group.put("groupname", addGroupEditText.getText().toString());
        group.put("username", ParseUser.getCurrentUser().getUsername());
        group.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // OK
                    Log.i("Success", "We Saved the Group");

                } else {
                    // Group Not Saved
                    e.printStackTrace();
                }
            }
        });
    }


    public void groupParseQuery(String groupName) {
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");

            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.whereEqualTo("groupname", groupName);

            groupResults = query.find();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void userGroupsParseQuery() {
        try {
            ParseQuery<ParseObject> userGroupsQuery = ParseQuery.getQuery("Group");

            userGroupsQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            userGroupResults = userGroupsQuery.find();

            for (ParseObject object: userGroupResults) {
                Log.i("Username", object.getString("username"));
                Log.i("Groupname", object.getString("groupname"));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean groupExists() {
        if (groupResults.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        addGroupEditText = findViewById(R.id.addGroupEditText);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        constraintLayout.setOnClickListener(this);




        ListView listView = findViewById(R.id.listView);
        groupnames = new ArrayList<String>();
        groupnames.add("Add new group");

        userGroupsParseQuery();
        for (ParseObject object:userGroupResults) {
            groupnames.add(object.getString("groupname"));
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, groupnames);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String groupName = addGroupEditText.getText().toString();
                hideKeyboard();
                if (position == 0) { // Create Group Clicked
                    groupParseQuery(groupName);
                    createGroupClicked(groupName);
                } else {
                    Log.i("group selected", groupnames.get(position));
                }
            }
        });
    }
}

