package Entity;

import lombok.Data;

@Data
public class YoutubeVideoStat {
    private String videoId;
    private Long viewCount;
    private Long likeCount;
    private Long commentCount;
}
