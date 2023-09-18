package com.youtubeapi.datafetcher.repositories;

import com.youtubeapi.datafetcher.models.YoutubeChannelStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeChannelStatRepository extends JpaRepository<YoutubeChannelStat, String> {
}
