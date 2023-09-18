package com.youtubeapi.datafetcher.repositories;

import com.youtubeapi.datafetcher.models.YoutubeVideoStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeVideoStatRepository extends JpaRepository<YoutubeVideoStat, String> {
}
