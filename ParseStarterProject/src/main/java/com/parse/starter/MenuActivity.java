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

    public void groupActivity(String groupID) {
        Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
        intent.putExtra("groupID", groupID);
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
        List<ParseObject> groups = groupsUserBelongsToParseQuery();
        ParseObject group = groups.get(position-1);

        Log.i("ParseID", group.get("groupID").toString());
        String groupID = group.get("groupID").toString();
        groupActivity(groupID);
    }

    public void createGroup() {
        ParseObject groupData = new ParseObject("GroupData");
        groupData.put("admin", ParseUser.getCurrentUser().getUsername());

        try {
            groupData.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ParseObject groupMembership = new ParseObject("GroupMembership");
        groupMembership.put("groupname", addGroupEditText.getText().toString());
        groupMembership.put("username", ParseUser.getCurrentUser().getUsername());
        groupMembership.put("groupID", groupData.getObjectId());

        groupMembership.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // OK
                    Log.i("Success", "We Saved the Group");
                    populateList(); // Waits for Async save to finish to populate list with current groups

                } else {
                    // Group Not Saved
                    e.printStackTrace();
                }
            }
        });
    }


    public List<ParseObject> lookForExistingGroupParseQuery(String groupName) {
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("GroupMembership");

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
            ParseQuery<ParseObject> userGroupsQuery = ParseQuery.getQuery("GroupMembership");

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
                } else {
                    existingGroupClicked(position);
                }
            }
        });
    }
}


