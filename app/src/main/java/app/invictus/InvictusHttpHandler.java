package app.invictus;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

public class InvictusHttpHandler {

    private String[] workoutDate;
    private String[] workoutDescription;

    InvictusHttpHandler() {}

    public String[] getWorkoutDate() {
        return workoutDate;
    }

    public String[] getworkoutDescription() {
        return workoutDescription;
    }

    public void makeHttpRequest(Context context, String date) {
//
//        // Workout is in - http://www.crossfitinvictus.com/feed/
//        // Comments for workout are in - http://www.crossfitinvictus.com/wod/june-13-2015-competition/feed/
//
//        // Tag used to cancel the request
//        String tag_string_req = "string_req";
//
//        String url = "http://www.crossfitinvictus.com/feed/";
//
//        StringRequest strReq = new StringRequest(Request.Method.GET,
//                url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d(InvictusConstants.INVICTUS_DEBUG_MSG, response.toString());
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(InvictusConstants.INVICTUS_ERROR_MSG, "Error: " + error.getMessage());
//            }
//        });
//
//        // Adding request to request queue
//        InvictusApplication.getInstance().addToRequestQueue(strReq, tag_string_req);

        workoutDate = new String[10];
        for(int item = 9; item > (-1); item--)
            workoutDate[item] = "June " + (item+1) +", 2015 – Competition";

        workoutDescription = new String[10];
        for(int item = 0; item < 10; item++)
            workoutDescription[item] = "Workout of the Day\n" +
                    "A.\n" +
                    "Back Squat\n" +
                    "*Set 1 – 5 reps @ 65%\n" +
                    "*Set 2 – 4 reps @ 75%\n" +
                    "*Set 3 – 3 reps @ 85%\n" +
                    "*Set 4 – 2 reps @ 90%\n" +
                    "*Set 5 – 1 rep @ 95%\n" +
                    "*Set 6 – 2 reps @ 95%\n" +
                    "Rest 2 minutes between sets\n" +
                    "*Please remember, these percentages are based on last tested, successful 1-RM.\n" +
                    "B.\n" +
                    "Complete as many rounds and reps as possible in 15 minutes of:\n" +
                    "Run 200 Meters with Medicine Ball\n" +
                    "15 Wall Ball Shots (20/12 lbs)\n" +
                    "15 Kettlebell Swings (32/24 kg)";
    }
}
