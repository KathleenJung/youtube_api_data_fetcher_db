package com.youtubeapi.datafetcher.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class YoutubeVideoKey implements Serializable {
    private String client;
    private String keyword;
    private String videoId;
}
