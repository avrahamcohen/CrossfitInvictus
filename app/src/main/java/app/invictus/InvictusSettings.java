package app.invictus;

import android.app.Activity;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.io.InputStream;

public class InvictusSettings extends Activity
        implements
            GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener,
            View.OnClickListener {

    private EditText date;
    private String userToast = "Earliest date is 01-02-2012 !";

    /* Facebook Variables */
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String facebookUserID;
    private String facebookUserName;

    /* Google+ Variables */
    private Bitmap GoogleProfilePicture;
    private String GoogleProfilePictureURL;
    private String GoogleUserName;
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient = null;
    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;

    /* Facebook Getters */
    public String getFacebookUserName() { return facebookUserName;}
    public String getfacebookUserID() { return facebookUserID;}
    /* Google+ Getters */
    public Bitmap getGoogleProfilePicture() {return GoogleProfilePicture;}
    public String getGoogleProfilePictureURL() {return GoogleProfilePictureURL;}
    public String getGoogleUserName() {return GoogleUserName;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invictus_settings);

        date = (EditText)findViewById(R.id.userName);
        date.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("date", ""));

        /* Facebook Login */
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(InvictusConstants.FACEBOOK_PERMISSIONS);

        /* Google+ Login */
        findViewById(R.id.sign_in_button).setOnClickListener(this);

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
                                    facebookUserName = me.optString("name");
                                    facebookUserID = me.optString("id");

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkUserDate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
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

    /* Google+ Functions */
    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Google+ Connected !",
                Toast.LENGTH_SHORT).show();
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            GoogleUserName = currentPerson.getDisplayName();
            GoogleProfilePictureURL = currentPerson.getImage().getUrl();
//            new GetProfileImage().execute(GoogleProfilePictureURL);
        }
    }

    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress && result.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(result.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onClick(View view) {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API)
                    .addScope(Plus.SCOPE_PLUS_LOGIN)
                    .build();

            mGoogleApiClient.connect();
        }

        else {
            Toast.makeText(this, "Google+ Disconnected !",
                    Toast.LENGTH_SHORT).show();
            mGoogleApiClient.disconnect();
            return;
        }


    }

    private class GetProfileImage extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {

                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            GoogleProfilePicture = result;
            //<image>.setImageBitmap(GoogleProfilePicture);
        }
    }
}
