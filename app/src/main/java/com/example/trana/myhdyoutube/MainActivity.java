package com.example.trana.myhdyoutube;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.trana.myhdyoutube.domain.Channel;
import com.example.trana.myhdyoutube.domain.VideoMetadata;
import com.example.trana.myhdyoutube.services.ChannelService;
import com.example.trana.myhdyoutube.services.ChannelServiceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by trana on 4/30/2016.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView contentListView;
    private RecyclerView.Adapter contentListViewAdapter;
    private RecyclerView.LayoutManager contentListViewLayoutManager;
    List<VideoMetadata> mDataSet = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        contentListView = (RecyclerView)findViewById(R.id.content_list);
        contentListView.setHasFixedSize(true);
        contentListViewLayoutManager = new LinearLayoutManager(this);
        contentListView.setLayoutManager(contentListViewLayoutManager);

       /* for(int i=0; i<10; i++){
            VideoMetadata data = new VideoMetadata();
            data.setTitle("This is the title of the most wanted movies ==" + i );
            data.setDuration("1:30:30");
            data.setNumberOfViews("400K");
            mDataSet.add(data);
        }
*/
        /*ChannelServiceManager channelService = new ChannelServiceManager();
        //mDataSet = channelService.getChannels().get(0).getVideos();
        channelService.execute("Hello");*/
        //new FechingVideoTask().execute("UCI2OiZs5aVcyBUBVsgovzng");
        new FechingVideoTask().execute("UCI2OiZs5aVcyBUBVsgovzng");
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        contentListView.addItemDecoration(itemDecoration);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_today:
                //Yeah 1 Music
                new FechingVideoTask().execute("UCI2OiZs5aVcyBUBVsgovzng");
                break;
            case R.id.btn_last_week:
                // Red Original
                new FechingVideoTask().execute("UCk9ft9Cy-uh0I2goL48KASg");
                break;
            case R.id.btn_hot:
                new FechingVideoTask().execute("UCF0pVplsI8R5kcAqgtoRqoA");
                break;
        }

    }

    private class FechingVideoTask extends AsyncTask<String, Void, List<Channel>> {
        @Override
        protected List<Channel> doInBackground(String... strings) {
            ChannelService serviceManager = new ChannelServiceManager();
            List<Channel> channles = serviceManager.getChannels(strings[0]);
            return channles;
        }

        @Override
        protected void onPostExecute(List<Channel> channels) {
            mDataSet = channels.get(0).getVideos();
            contentListViewAdapter = new ContentListAdapter(mDataSet);
            contentListViewAdapter.notifyDataSetChanged();
            contentListView.setAdapter(contentListViewAdapter);
        }
    }

    public void myClickHandler(View view) {
        // Gets the URL from the UI's text field.

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new FechingVideoTask().execute("Hello");
        } else {
            //textView.setText("No network connection available.");
        }
    }
}
