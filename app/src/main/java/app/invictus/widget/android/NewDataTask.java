package app.invictus.widget.android;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import app.invictus.InvictusConstants;

public class NewDataTask extends AsyncTask<Void, Void, String> {

    private RefreshableListView mListView;

    public NewDataTask(RefreshableListView mListView) {
        this.mListView = mListView;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}
        return "Done";
    }

    @Override
    protected void onPostExecute(String result) {
        // This should be called after refreshing finished
        super.onPostExecute(result);
        Log.d(InvictusConstants.INVICTUS_DEBUG_MSG, "Done Refreshing !");
        mListView.completeRefreshing();
    }
}
