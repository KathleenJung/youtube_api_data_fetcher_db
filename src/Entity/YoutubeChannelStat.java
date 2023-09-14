package Entity;

import lombok.Data;

@Data
public class YoutubeChannelStat {
    private String channelId;
    private Long subscriberCount;
    private Long viewCount;
    private Long videoCount;
}
