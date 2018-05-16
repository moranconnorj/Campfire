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
    List<ParseObject> results;

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

    public void parseQuery(String groupName) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");

        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.whereEqualTo("groupname", groupName);

        results = query.find();

        for (ParseObject object: results) {
            Log.i("Username", object.getString("username"));
            Log.i("Groupname", object.getString("groupname"));
        }

    }

    public boolean groupExists() {
        if (results.isEmpty()) {
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
        final ArrayList<String> groupnames = new ArrayList<String>();
        groupnames.add("Add new group");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, groupnames);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String groupName = addGroupEditText.getText().toString();
                hideKeyboard();
                if (position == 0) { // Create Group Clicked
                    try {
                        parseQuery(groupName);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (groupName.length() > 0) { // Group Name Not Blank
                        if (groupExists()) { // Group/User combo already exists in Parse
                            Toast.makeText(MenuActivity.this, "Group Already Exists", Toast.LENGTH_SHORT).show();
                        } else { // Group/User combo DOES NOT exist in parse; create group
                            createGroup();
                        }
                    } else { //Groupname length < 0
                        Toast.makeText(MenuActivity.this, "Group Name Required", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("group selected", groupnames.get(position));
                }
            }
        });
    }
}

//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    if (objects.size() > 0) {
//                        for (ParseObject object: objects) {
//                            Log.i("Username", object.getString("username"));
//                            Log.i("Groupname", object.getString("groupname"));
//                        }
//                    } else {
//                        Log.i("Group", "Doesnt Exist");
//                    }
//                }
//            }
//        });
