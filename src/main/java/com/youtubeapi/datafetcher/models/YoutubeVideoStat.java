package com.youtubeapi.datafetcher.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class YoutubeVideoStat {
    @Id
    private String videoId;

    private Long viewCount;

    private Long likeCount;

    private Long commentCount;

    private LocalDate wDate;
}
