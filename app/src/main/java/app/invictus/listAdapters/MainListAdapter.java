package app.invictus.listAdapters;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import app.invictus.invictus.InvictusComment;
import app.invictus.invictus.InvictusNotes;
import app.invictus.R;

public class MainListAdapter extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] date;
    private final String[] workout;

    public MainListAdapter(Activity context, String[] date, String[] workout) {
        super(context, R.layout.main_list_item, date);

        this.context = context;
        this.date = date;
        this.workout = workout;
    }

    @Override
    public View getView(final int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.main_list_item, null,true);

        final TextView txtTitle = (TextView) rowView.findViewById(R.id.userName);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.userPhoto);
        final TextView extratxt = (TextView) rowView.findViewById(R.id.userComment);

        ImageButton commentButton = (ImageButton)rowView.findViewById(R.id.comment);
        ImageButton noteButton = (ImageButton)rowView.findViewById(R.id.note);
        ImageButton shareButton = (ImageButton)rowView.findViewById(R.id.share);

        commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                commentButtonHandler(txtTitle);
            }
        });

        noteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                noteButtonHandler(txtTitle, extratxt);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareButtonHandler(txtTitle, extratxt);
            }
        });

        txtTitle.setText(date[position]);
        imageView.setImageResource(R.drawable.logo);
        extratxt.setText(workout[position]);

        return rowView;
    };

    // TODO: fix this !
    private void shareButtonHandler(TextView txtTitle, TextView extratxt) {

        String title = "<b>" + "Crossfit Invictus" + "</b>";
        String date  = "<i>" + txtTitle.getText() + "</i>";

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                Html.fromHtml(title) + "\n\n" + Html.fromHtml(date) + "\n\n" + extratxt.getText());
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    private void noteButtonHandler(TextView txtTitle, TextView extratxt) {
        Intent intent = new Intent(context.getApplicationContext(), InvictusNotes.class);
        intent.putExtra("DATE", txtTitle.getText());
        intent.putExtra("WOD", extratxt.getText());
        context.startActivity(intent);
    }

    private void commentButtonHandler(TextView txtTitle) {
        Intent intent = new Intent(context.getApplicationContext(), InvictusComment.class);
        intent.putExtra("DATE", txtTitle.getText());
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }
}
