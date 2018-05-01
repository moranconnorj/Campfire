/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity {

  public void signUpClicked(View view) {
      EditText usernameEditText = findViewById(R.id.usernameEditText);
      EditText passwordEditText = findViewById(R.id.passwordEditText);

      if (usernameEditText.getText().toString() == "" || passwordEditText.getText().toString() == "") {
          Toast.makeText(this, "A username and password are required", Toast.LENGTH_SHORT).show();
      } else {
          ParseUser user = new ParseUser();
          user.setUsername(usernameEditText.getText().toString());
          user.setPassword(passwordEditText.getText().toString());

          user.signUpInBackground(new SignUpCallback() {
              @Override
              public void done(ParseException e) {
                  if (e == null) {
                      // OK
                      Log.i("Sign Up", "OK");
                  } else {
                      Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                      e.printStackTrace();
                  }
              }
          });
      }
  }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

//    ParseObject score = new ParseObject("Score");
//    score.put("username", "Sean");
//    score.put("score", "65");
//    score.saveInBackground(new SaveCallback() {
//        @Override
//        public void done(ParseException e) {
//            if (e == null) {
//                // OK
//                Log.i("Success", "We Saved the Score");
//            } else {
//                e.printStackTrace();
//            }
//        }
//    });

//      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//      query.getInBackground("I1Jo18FEQx", new GetCallback<ParseObject>() {
//          @Override
//          public void done(ParseObject object, ParseException e) {
//              if (e == null && object != null) {
//
//                  object.put("score2", 45);
//                  object.saveInBackground();
//
//                  Log.i("username ", object.getString("username"));
//                  Log.i("score ", object.getString("score"));
//              }
//          }
//      });

//      ParseObject tweet = new ParseObject("Tweet");
//      tweet.put("username", "Donald");
//      tweet.put("tweet", "#MAGA");
//      tweet.saveInBackground(new SaveCallback() {
//          @Override
//          public void done(ParseException e) {
//              if (e == null) {
//                  Log.i("Success", "we saved the tweet");
//              } else {
//                  e.printStackTrace();
//              }
//          }
//      });

//      ParseQuery<ParseObject> tweetQuery = ParseQuery.getQuery("Tweet");
//
//      tweetQuery.getInBackground("jH31QFRTzI", new GetCallback<ParseObject>() {
//          @Override
//          public void done(ParseObject object, ParseException e) {
//              if (e == null && object != null) {
//                  Log.i("username", object.getString("username"));
//                  Log.i("tweet", object.getString("tweet"));
//
//              }
//          }
//      });

//      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//      query.whereEqualTo("username", "Sean");
//
//      query.findInBackground(new FindCallback<ParseObject>() {
//          @Override
//          public void done(List<ParseObject> objects, ParseException e) {
//              if (e == null) {
//                  if (objects.size() > 0 ) {
//                      for (ParseObject object: objects) {
//                          Log.i("username", object.getString("username"));
//                          Log.i("score", object.getString("score"));
//                      }
//                  }
//              }
//          }
//      });

//      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//      query.whereGreaterThan("score2", 50);
//
//      query.findInBackground(new FindCallback<ParseObject>() {
//          @Override
//          public void done(List<ParseObject> objects, ParseException e) {
//              if (e == null && objects != null) {
//                  if (objects.size() > 0) {
//                      for (ParseObject object: objects) {
//                          object.put("score2", (object.getInt("score2") +20));
//                          object.saveInBackground();
//
//                          Log.i("username", object.getString("username"));
//                          Log.i("score2", Integer.toString(object.getInt("score2")));
//                      }
//
//                  }
//              }
//          }
//      });
//
//
//                          SIGN UP CODE
//    ParseUser user = new ParseUser();
//
//    user.setUsername("Connor");
//    user.setPassword("myPass");
//
//    user.signUpInBackground(new SignUpCallback() {
//        @Override
//        public void done(ParseException e) {
//            if (e == null) {
//                // OK
//                Log.i("Sign Up", "OK");
//            } else {
//                e.printStackTrace();
//            }
//        }
//    });

//      ParseUser.logInInBackground("Connor", "myPass", new LogInCallback() {
//          @Override
//          public void done(ParseUser user, ParseException e) {
//              if (user != null) {
//                  Log.i("Success", "We Logged In");
//              } else {
//                  e.printStackTrace();
//              }
//          }
//      });

//      ParseUser.logOut();
//
//      if (ParseUser.getCurrentUser() != null) {
//          Log.i("Signed In", ParseUser.getCurrentUser().getUsername());
//      } else {
//          Log.i("Not Signed In", "Nope");
//      }






    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}