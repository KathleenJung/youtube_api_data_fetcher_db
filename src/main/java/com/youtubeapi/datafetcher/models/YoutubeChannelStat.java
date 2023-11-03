package com.youtubeapi.datafetcher.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class YoutubeChannelStat {
    @Id
    private String channelId;

    private Long subscriberCount;

    private Long viewCount;

    private Long videoCount;

    private LocalDate wDate;
}
