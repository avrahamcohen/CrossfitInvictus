package app.invictus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import app.invictus.listAdapters.MainListAdapter;

public class InvictusMain extends Activity {

    private ListView listView;

    private Button performanceButton;
    private Button fitnessButton;
    private Button competitionButton;

    private InvictusHttpHandler invictusHttpHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.app_name);
        getActionBar().setIcon(R.drawable.ic_launcher);

        setContentView(R.layout.activity_main);

        invictusHttpHandler = new InvictusHttpHandler();

        listHandler();
        buttonsHandlers();
        actionBarHandler();
    }

    private void actionBarHandler() {
        final int abTitleId = getResources().getIdentifier("action_bar_title", "id", "android");
        findViewById(abTitleId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InvictusSettings.class);
                startActivity(intent);
            }
        });
    }

    private void listHandler() {
        listView = (ListView) findViewById(R.id.list);
        invictusHttpHandler.makeHttpRequest(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("date", ""));
        MainListAdapter listAdapter = new MainListAdapter(
                this, invictusHttpHandler.getWorkoutDate(), invictusHttpHandler.getworkoutDescription());
        listView.setClickable(true);
        listView.setAdapter(listAdapter);
    }

    private void buttonsHandlers() {

        performanceButton = (Button)findViewById(R.id.performance);
        fitnessButton = (Button)findViewById(R.id.fitness);
        competitionButton = (Button)findViewById(R.id.competition);

        performanceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                buttonColor(performanceButton, fitnessButton, competitionButton);

                /* Reload list with Performance items */
            }
        });

        fitnessButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            buttonColor(fitnessButton, performanceButton, competitionButton);

            /* Reload list with Fitness items */
            }
        });

        competitionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                buttonColor(competitionButton, performanceButton, fitnessButton);

                /* Reload list with Competition items */
            }
        });
    }

    private void buttonColor(Button toPressed, Button toNormal_a, Button toNormal_b) {

        final Drawable[] drawableToPressed = {getResources().getDrawable(R.drawable.button_shape_header_normal),
                getResources().getDrawable(R.drawable.button_shape_header_pressed)};

        final Drawable[] drawableToNormal = {getResources().getDrawable(R.drawable.button_shape_header_normal),
                getResources().getDrawable(R.drawable.button_shape_header_normal)};

        TransitionDrawable a = new TransitionDrawable(drawableToPressed);
        toPressed.setBackgroundDrawable(a);
        a.startTransition(2000);

        TransitionDrawable b = new TransitionDrawable(drawableToNormal);
        toNormal_a.setBackgroundDrawable(b);
        b.startTransition(500);

        TransitionDrawable c = new TransitionDrawable(drawableToNormal);
        toNormal_b.setBackgroundDrawable(c);
        c.startTransition(500);
    }
}
