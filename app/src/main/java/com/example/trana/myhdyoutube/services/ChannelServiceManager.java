package com.example.trana.myhdyoutube.services;

import com.example.trana.myhdyoutube.YoutubeConfig;
import com.example.trana.myhdyoutube.domain.Channel;
import com.example.trana.myhdyoutube.domain.VideoMetadata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;

/**
 * Created by trana on 5/12/2016.
 */
public class ChannelServiceManager implements ChannelService {
    private List<Channel> popularChannels;

    public ChannelServiceManager() {
        popularChannels = new ArrayList<Channel>();
    }


    @Override
    public List<Channel> getChannels() {
        RestTemplate restTemplate = new RestTemplate();
        boolean success = restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String channelURL = "https://www.googleapis.com/youtube/v3/channels?part=snippet&forUsername=TechGuyWeb&key=" + YoutubeConfig.YOUTUBE_API_KEY;
        String videoListURL = "https://www.googleapis.com/youtube/v3/search?key="+ YoutubeConfig.YOUTUBE_API_KEY + "&channelId=UCI2OiZs5aVcyBUBVsgovzng&part=snippet,id&order=date&maxResults=50";
        //String videoDetailURL = "https://www.googleapis.com/youtube/v3/videos?part=contentDetails&id=" + KEY_API + "&key="+ KEY_API;
        //RgKAFK5djSk%2Cw1oM3kQpXRo&key=AIzaSyBMfo5HKb5CDKugVphxLv1zLWxvX7TRLXk"


        RestTemplate r = new RestTemplate();
        success = r.getMessageConverters().add(new StringHttpMessageConverter());

        String result = restTemplate.getForObject(videoListURL, String.class, "Android");
        try {
            JSONObject reader = new JSONObject(result);
            JSONArray objList = reader.optJSONArray("items");
            Channel channel = new Channel();
            channel.setChannelID("UC29ju8bIPH5as8OGnQzwJyA");
            channel.setChannelName("TechGuyWeb");
            for(int i=0; i< objList.length(); i++){
                JSONObject obj = objList.getJSONObject(i);
                JSONObject snippetObj = obj.getJSONObject("snippet");
                JSONObject thumbnailObj = snippetObj.getJSONObject("thumbnails");
                JSONObject defaultObj = thumbnailObj.getJSONObject("medium");
                VideoMetadata video = new VideoMetadata();
                video.setTitle(snippetObj.optString("title"));
                video.setThumbnailURL(defaultObj.optString("url"));
                channel.getVideos().add(video);
            }
            this.popularChannels.add(channel);
            return this.popularChannels;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    @Override
    public List<Channel> getChannels(String channelID) {
        RestTemplate restTemplate = new RestTemplate();
        boolean success = restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String videoListURL = "https://www.googleapis.com/youtube/v3/search?key="+ YoutubeConfig.YOUTUBE_API_KEY + "&channelId="+ channelID + "&part=snippet,id&order=viewCount&maxResults=20";
        String result = restTemplate.getForObject(videoListURL, String.class, "Android");
        try {
            JSONObject reader = new JSONObject(result);
            JSONArray objList = reader.optJSONArray("items");
            Channel channel = new Channel();
            for(int i=0; i< objList.length(); i++){
                JSONObject obj = objList.getJSONObject(i);
                JSONObject snippetObj = obj.getJSONObject("snippet");
                JSONObject thumbnailObj = snippetObj.getJSONObject("thumbnails");
                JSONObject defaultObj = thumbnailObj.getJSONObject("medium");

                VideoMetadata video = new VideoMetadata();
                video.setTitle(snippetObj.optString("title"));
                video.setThumbnailURL(defaultObj.optString("url"));
                String videoId = obj.optJSONObject("id").optString("videoId");
                getVideoMetadata(restTemplate, videoId, video);
                channel.getVideos().add(video);
            }
            this.popularChannels.add(channel);
            return this.popularChannels;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    private void getVideoMetadata(RestTemplate restTemplate, String videoId,VideoMetadata videoObj) {

        String videoMetadataURL = "https://www.googleapis.com/youtube/v3/videos?part=contentDetails,statistics&id=" + videoId + "&key=" + YoutubeConfig.YOUTUBE_API_KEY;
        String videoMetadata = restTemplate.getForObject(videoMetadataURL, String.class);
        try {
            JSONObject reader = new JSONObject(videoMetadata);
            JSONArray videoMetadataList = reader.getJSONArray("items");
            if(videoMetadataList != null && videoMetadataList.length() !=0){
                JSONObject oneItem = videoMetadataList.getJSONObject(0);
                JSONObject contentDetailsObj = oneItem.getJSONObject("contentDetails");
                JSONObject statisticObj = oneItem.getJSONObject("statistics");

                videoObj.setDuration(contentDetailsObj.optString("duration"));
                videoObj.setNumberOfViews(statisticObj.optString("viewCount"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<Channel> getPopularChannels() {
        return this.popularChannels;
    }
}
