package app.invictus;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

public class InvictusSettings extends Activity {

    private EditText date;
    private String userToast = "Earliest date is 01-02-2012 !";

//    private TextView facebookUser;
//    private ProfilePictureView profilePicture;

    private LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invictus_settings);

        date = (EditText)findViewById(R.id.userName);
        date.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("date", ""));

//        facebookUser = (TextView)findViewById(R.id.facebookUserName);
//        profilePicture = (ProfilePictureView)findViewById(R.id.profilePicture);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(InvictusConstants.FACEBOOK_PERMISSIONS);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    String name = me.optString("name");
                                    String id = me.optString("id");

                                    Log.d("Facebook", "ID:" + id);
                                    Log.d("Facebook", "Username:" + name);

//                                    facebookUser.setText("Hello " + name + " !");
//                                    profilePicture.setProfileId(id);
                                }
                            }
                        }).executeAsync();
            }

            @Override
            public void onCancel() {
//                facebookUser.setText("Hello !");
//                profilePicture.setProfileId(null);
            }

            @Override
            public void onError(FacebookException exception) {
//                facebookUser.setText("Hello !");
//                profilePicture.setProfileId(null);
            }
        });
    }

    private void checkUserDate() {
        int eday=2, eyear=2012;
        int day, year;

        if (date.getText().length() != 0) {
            String userDate = date.getText().toString();
            String[] parts = userDate.split("-");

            day = Integer.parseInt(parts[1]);
            year = Integer.parseInt(parts[2]);

        /* Means that the earlier date is 2012 and the 2nd day */
            if (year >= eyear && day >= eday) {

                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                editor.putString("date", date.getText().toString());
                editor.commit();


            } else {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                editor.putString("date", "01-02-2012");
                editor.commit();

                Toast.makeText(getApplicationContext(), userToast,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        checkUserDate();
    }
}
