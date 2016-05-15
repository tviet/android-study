package com.example.trana.myhdyoutube.domain;

import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trana on 5/12/2016.
 */
public class Channel {
    private String channelName;
    private String channelID;

    private List<VideoMetadata> videos;

    public Channel() {
       this.videos = new ArrayList<>();
    }

    public Channel(String channelName, String channelID) {
        this.channelName = channelName;
        this.channelID = channelID;
        this.videos = new ArrayList<>();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public List<VideoMetadata> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoMetadata> videos) {
        this.videos = videos;
    }
}
