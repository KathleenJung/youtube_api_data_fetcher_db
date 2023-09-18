package com.youtubeapi.datafetcher.runners;

import com.youtubeapi.datafetcher.services.DataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FetcherRunner implements CommandLineRunner {
    private final DataService dataService;

    public FetcherRunner(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void run(String... args) throws Exception {
        dataService.FetchData();
        System.out.println("[데이터 수집 종료]");
    }
}
