package com.example.trana.myhdyoutube.services;

import com.example.trana.myhdyoutube.domain.Channel;

import java.util.List;

/**
 * Created by trana on 5/12/2016.
 */
public interface ChannelService {
    List<Channel> getChannels();
    List<Channel> getChannels(String channelID);
    List<Channel> getPopularChannels();
}
