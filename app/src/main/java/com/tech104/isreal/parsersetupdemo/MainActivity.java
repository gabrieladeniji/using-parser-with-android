package com.tech104.isreal.parsersetupdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void checkForSignedIn() {
        if(ParseUser.getCurrentUser() != null) {
            Log.i("Signed In", "User is signed in " + ParseUser.getCurrentUser().getString("username"));
        } else {
            Log.i("Signed In", "No active session");
        }
    }

    public void signUserOut() {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Log.i("Signed Out", "User has been successfully signed out!");
                } else {
                    Log.i("Signed Out", "User was not signed out!");
                }
            }
        });
    }

    public void signUserIn() {
        ParseUser.logInInBackground("gab1995", "adeniji1234", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user != null) {
                    Log.i("Sign In", "Successful");
                } else {
                    Log.i("Sign In", "Failed");
                }
            }
        });
    }

    public void signUserUp() {
        ParseUser user = new ParseUser();
        user.setUsername("gab1995");
        user.setPassword("adeniji123");
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Log.i("Sign Up", "Successful");
                } else {
                    Log.i("Sign Up", "Failed");
                }
            }
        });
    }


    public void search() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
        query.whereGreaterThan("point", 100);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects != null && e == null) {
                    Log.i("FindInBackground", Integer.toString(objects.size()) );
                    for (ParseObject object : objects) {
                        Log.i("FindInBackgroundResult", object.getString("username"));
                    }
                }
            }
        });
    }

    public void extract() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
        query.getInBackground("Wca1f5NotC", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(object != null && e == null) {
                    //
                    object.put("tweet", "Hi my people, how is family over there");
                    object.saveInBackground();
                    //
                    Log.i("getSavedTweet", "USERNAME: " + object.getString("username"));
                    Log.i("getSavedTweet", "TWEET: " + object.getString("tweet"));
                }
            }
        });
    }

    public void createAndInsert() {
        ParseObject parseObject = new ParseObject("Tweet");
        parseObject.put("username", "django");
        parseObject.put("tweet", "Hello my people, how is family over there");
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Log.i("SaveInBackground", "Tweet saved!");
                }
            }
        });
    }
}
