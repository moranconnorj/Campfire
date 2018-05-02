package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.parse.ParseUser;

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
    }
}
