package com.youtubeapi.datafetcher.repositories;

import com.youtubeapi.datafetcher.models.YoutubeVideo;
import com.youtubeapi.datafetcher.models.YoutubeVideoKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeVideoRepository extends JpaRepository<YoutubeVideo, YoutubeVideoKey> {
}
