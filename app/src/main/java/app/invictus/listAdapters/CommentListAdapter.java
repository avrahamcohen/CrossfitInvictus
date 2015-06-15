package app.invictus.listAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import app.invictus.R;

public class CommentListAdapter extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] userName;
    private final String[] userComment;
    private final String[] userDate;
    private final Integer[] userImage;

    public CommentListAdapter(
            Activity context, String[] userName, String[] userComment, String[] userDate, Integer[] userImage) {
        super(context, R.layout.comment_list_item, userName);

        this.context = context;
        this.userName = userName;
        this.userComment = userComment;
        this.userDate = userDate;
        this.userImage = userImage;
    }

    @Override
    public View getView(final int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.comment_list_item, null,true);

        final TextView userDateTextView = (TextView) rowView.findViewById(R.id.applicationTitle);
        final TextView userNameTextView = (TextView) rowView.findViewById(R.id.userName);
        final ImageView userImageView = (ImageView) rowView.findViewById(R.id.userPhoto);
        final TextView userCommentTextView = (TextView) rowView.findViewById(R.id.userComment);

        userDateTextView.setText(userDate[position]);
        userNameTextView.setText(userName[position]);
        userImageView.setImageResource(R.drawable.logo);
        userCommentTextView.setText(userComment[position]);

        return rowView;
    };

}
