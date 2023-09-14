package Entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class YoutubeVideo {
    private String client;
    private String keyword;
    private String videoId;
    private String title;
    private String thumbnailUrl;
    private String channel;
    private String channelId;
    private LocalDateTime publishedAt;
}
