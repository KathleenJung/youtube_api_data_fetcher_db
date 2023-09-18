package com.youtubeapi.datafetcher.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class YoutubeVideo {
    @EmbeddedId
    private YoutubeVideoKey id;

    private String title;
    private String thumbnailUrl;
    private String channel;
    private String channelId;
    private String publishedAt;
}
