package app.invictus;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

public class InvictusNotes extends Activity {

    private EditText editText;
    private String wod;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invictus_notes);

        editText = (EditText)findViewById(R.id.wodWithNotes);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            wod = extras.getString("WOD");
            date = extras.getString("DATE");
        }

        editText.setText("Crossfit Invictus\n\n" + date + "\n\n" + wod);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO save comment.
    }
}
