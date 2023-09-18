package com.youtubeapi.datafetcher.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class YoutubeVideoStat {
    @Id
    private String videoId;

    private Long viewCount;

    private Long likeCount;

    private Long commentCount;
}
