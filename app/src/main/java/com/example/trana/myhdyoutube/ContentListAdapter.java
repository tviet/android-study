package com.example.trana.myhdyoutube;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trana.myhdyoutube.domain.VideoMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by trana on 4/30/2016.
 */
public class ContentListAdapter extends RecyclerView.Adapter<ContentListAdapter.VideoObjectHolder> {

    private ArrayList<VideoMetadata> mDataSet;

    public ContentListAdapter(List<VideoMetadata> myDataSet){
        mDataSet = (ArrayList)myDataSet;
    }

    @Override
    public ContentListAdapter.VideoObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_linear_layout, parent, false);
        return new VideoObjectHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoObjectHolder holder, int position) {
        holder.txtTitle.setText(mDataSet.get(position).getTitle());
        holder.txtNumberOfViews.setText(mDataSet.get(position).getNumberOfViews());
        holder.txtDuration.setText(mDataSet.get(position).getDuration());
        String imageURL = mDataSet.get(position).getThumbnailURL();
        new DownloadImageTask(holder.imgKeyFrame).execute(imageURL);
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class VideoObjectHolder extends RecyclerView.ViewHolder {
        protected TextView txtDuration;
        protected ImageView imgKeyFrame;
        protected TextView txtTitle;
        protected TextView txtNumberOfViews;

        public VideoObjectHolder(View videoItemView) {
            super(videoItemView);
            if(txtDuration!=null && txtDuration.getParent() != null){

            }
            txtDuration = (TextView)videoItemView.findViewById(R.id.duration);

            imgKeyFrame = (ImageView) videoItemView.findViewById(R.id.video_keyframe);
            txtTitle = (TextView) videoItemView.findViewById(R.id.video_title);
            txtNumberOfViews = (TextView) videoItemView.findViewById(R.id.number_of_views);


        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imgView;

        public DownloadImageTask(ImageView imgView) {
            this.imgView = imgView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            InputStream in = null;
            try {
                in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
               // Log.e("Error", e.getMessage());
                e.printStackTrace();


            }finally {
                if(in != null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imgView.setImageBitmap(result);
        }
    }
}
