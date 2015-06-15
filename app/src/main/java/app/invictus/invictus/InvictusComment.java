    package app.invictus.invictus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import app.invictus.R;
import app.invictus.listAdapters.CommentListAdapter;
import app.invictus.listAdapters.MainListAdapter;
import app.invictus.volley.HttpHandler;
import app.invictus.widget.android.NewDataTask;
import app.invictus.widget.android.RefreshableListView;

    public class InvictusComment extends Activity {

    private RefreshableListView mListView;
    private EditText editText;

    private Button commentButton;

    private HttpHandler httpHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invictus_comment);

        editText = (EditText)findViewById(R.id.userCommentText);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);

        commentButton = (Button)findViewById(R.id.commentButton);

        httpHandler = new HttpHandler();
        listHandler();

        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* Upload comment and reload the list */

                final Drawable[] drawableToPressed = {getResources().getDrawable(R.drawable.button_shape_header_normal),
                        getResources().getDrawable(R.drawable.button_shape_header_pressed)};

                final Drawable[] drawableToNormal = {getResources().getDrawable(R.drawable.button_shape_header_pressed),
                        getResources().getDrawable(R.drawable.button_shape_header_normal)};

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        TransitionDrawable a = new TransitionDrawable(drawableToPressed);
                        commentButton.setBackgroundDrawable(a);
                        a.startTransition(2000);
                    }

                    public void onFinish() {
                        TransitionDrawable b = new TransitionDrawable(drawableToNormal);
                        commentButton.setBackgroundDrawable(b);
                        b.startTransition(2000);
                    }
                }.start();
            }
        });
    }

    private void listHandler() {
//        httpHandler.makeHttpRequest(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("date", ""));

        CommentListAdapter listAdapter = new CommentListAdapter(
                this,
                httpHandler.getWorkoutCommentsUserName(),
                httpHandler.getWorkoutComments(),
                httpHandler.getWorkoutDate(),
                httpHandler.getWorkoutCommentsUserPhoto());
        mListView = (RefreshableListView)findViewById(R.id.list);
        mListView.setAdapter(listAdapter);

        // Callback to refresh the list
        mListView.setOnRefreshListener(new RefreshableListView.OnRefreshListener() {

            @Override
            public void onRefresh(RefreshableListView listView) {
                new NewDataTask(mListView).execute();
            }
        });
    }
}
