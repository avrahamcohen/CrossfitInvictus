package app.invictus.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import app.invictus.R;
import app.invictus.invictus.InvictusApplication;
import app.invictus.invictus.InvictusConstants;

public class HttpHandler {

    private String[] workoutDate;
    private String[] workoutDescription;

    private String[] workoutComments;
    private String[] workoutCommentsUserName;
    private Integer[] workoutCommentsUserPhoto;

    public String[] getWorkoutDate() {

        workoutDate = new String[10];
        for (int item = 9; item > (-1); item--)
            workoutDate[item] = "June " + (item+1) + ", 2015 – Competition";
        return workoutDate;
    }

    public String[] getWorkoutDescription() {
        workoutDescription = new String[10];
        for (int item = 9; item > (-1); item--)
            workoutDescription[item] = "Workout of the Day\n" + (item+1);
        return workoutDescription;
    }

    public String[] getWorkoutComments() {
        workoutComments = new String[10];
        for (int item = 9; item > (-1); item--)
            workoutComments[item] = "Good Workout ! " + (item+1);
        return workoutComments;
    }

    public String[] getWorkoutCommentsUserName() {
        workoutCommentsUserName = new String[10];
        for (int item = 9; item > (-1); item--)
            workoutCommentsUserName[item] = "Avraham Cohen " + (item+1);
        return workoutCommentsUserName;
    }

    public Integer[] getWorkoutCommentsUserPhoto() {
        workoutCommentsUserPhoto = new Integer[10];
        for (int item = 9; item > (-1); item--)
            workoutCommentsUserPhoto[item] = R.drawable.comment;
        return workoutCommentsUserPhoto;
    }

    public void makeHttpRequest(Context context, String date) {

        // Workout is in - http://www.crossfitinvictus.com/feed/
        // Comments for workout are in - http://www.crossfitinvictus.com/wod/june-13-2015-competition/feed/

        // Tag used to cancel the request
        String tag_string_req = "string_req";

        String url = "http://www.crossfitinvictus.com/feed/";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(InvictusConstants.INVICTUS_DEBUG_MSG, response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(InvictusConstants.INVICTUS_ERROR_MSG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        InvictusApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
