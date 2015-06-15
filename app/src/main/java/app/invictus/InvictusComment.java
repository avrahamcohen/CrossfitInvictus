    package app.invictus;

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

import app.invictus.listAdapters.CommentListAdapter;
import app.invictus.listAdapters.MainListAdapter;


public class InvictusComment extends Activity {

    private ListView listView;
    private EditText editText;

    private Button commentButton;

    private final String[] userName = {"Avraham Cohen", "Omer Munin", "Renata Perl", "Tomy Sela Meron", "Yaki Shoshani", "Eliran Hershko"};
    private final String[] userComment = {"Any serious issues if we push active recovery swimming to Sundays and take Thursday off instead?", "Any serious issues if we push active recovery swimming to Sundays and take Thursday off instead?" , "Any serious issues if we push active recovery swimming to Sundays and take Thursday off instead?"};
    private final String[] userDate = {"June 14, 2015", "June 14, 2015", "June 14, 2015"};
    private final Integer[] userImage = {R.drawable.logo, R.drawable.logo, R.drawable.logo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invictus_comment);

        editText = (EditText)findViewById(R.id.userCommentText);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);

        commentButton = (Button)findViewById(R.id.commentButton);

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
        listView = (ListView) findViewById(R.id.list);
        CommentListAdapter listAdapter = new CommentListAdapter(
                this, userName, userComment, userDate, userImage);
        listView.setClickable(false);
        listView.setAdapter(listAdapter);
    }
}
