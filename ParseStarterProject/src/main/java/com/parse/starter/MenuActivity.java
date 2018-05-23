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
    ArrayAdapter arrayAdapter;
    ArrayList<String> groupnames;

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void onClick(View view) {
        hideKeyboard();
    }

    public void groupActivity(String groupname) {
        Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
        intent.putExtra("groupname", groupname);
        startActivity(intent);
    }


    public void logout(View view) {
        ParseUser.logOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void createGroupClicked(String groupName) {
        if (groupName.length() > 0) { // Group Name Not Blank
            if (groupExists(groupName)) { // Group/User combo already exists in Parse
                Toast.makeText(MenuActivity.this, "Group Already Exists", Toast.LENGTH_SHORT).show();
            } else { // Group/User combo DOES NOT exist in parse; create group
                createGroup();
            }
        } else { //Groupname length < 0
            Toast.makeText(MenuActivity.this, "Group Name Required", Toast.LENGTH_SHORT).show();
        }
    }

    public void existingGroupClicked(int position) {
        Log.i("group selected", groupnames.get(position));
        String groupname = groupnames.get(position);
        groupActivity(groupname);
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


    public List<ParseObject> lookForExistingGroupParseQuery(String groupName) {
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");

            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.whereEqualTo("groupname", groupName);

            return query.find();

        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<ParseObject>();
        }
    }

    public List<ParseObject> groupsUserBelongsToParseQuery() {
        try {
            ParseQuery<ParseObject> userGroupsQuery = ParseQuery.getQuery("Group");

            userGroupsQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            return userGroupsQuery.find();

        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<ParseObject>();
        }
    }

    public boolean groupExists(String groupName) {
        if (lookForExistingGroupParseQuery(groupName).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void populateList() {
        groupnames.clear();
        groupnames.add("Add new Group");
        for (ParseObject object:groupsUserBelongsToParseQuery()) {
            groupnames.add(object.getString("groupname"));
            Log.i("Groupname", object.getString("groupname"));
        }
        arrayAdapter.notifyDataSetChanged();
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

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, groupnames);
        listView.setAdapter(arrayAdapter);

        populateList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String groupName = addGroupEditText.getText().toString();
                hideKeyboard();
                if (position == 0) { // Create Group Clicked
                    createGroupClicked(groupName);
                    populateList();
                } else {
                    existingGroupClicked(position);
                }
            }
        });
    }
}


