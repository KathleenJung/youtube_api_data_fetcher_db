package com.youtubeapi.datafetcher.services;

import com.youtubeapi.datafetcher.models.YoutubeChannelStat;
import com.youtubeapi.datafetcher.models.YoutubeVideo;
import com.youtubeapi.datafetcher.models.YoutubeVideoKey;
import com.youtubeapi.datafetcher.models.YoutubeVideoStat;
import com.youtubeapi.datafetcher.repositories.YoutubeChannelStatRepository;
import com.youtubeapi.datafetcher.repositories.YoutubeVideoRepository;
import com.youtubeapi.datafetcher.repositories.YoutubeVideoStatRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class DataService {
    @Autowired
    YoutubeChannelStatRepository youtubeChannelStatRepository;
    @Autowired
    YoutubeVideoRepository youtubeVideoRepository;
    @Autowired
    YoutubeVideoStatRepository youtubeVideoStatRepository;

    private static final String PROPERTIES_FILE_PATH = "src/main/resources/application.properties";
    private static final String CONFIG_FOLDER_PATH = "src/main/resources/config";
    private static final List<String> apiKeys = new ArrayList<>();
    private static int currentApiKeyIndex = 0;
    private static String fileName;

    static {
        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {
            Properties properties = new Properties();
            properties.load(input);

            int apiKeyIndex = 1;
            String apiKey;
            while ((apiKey = properties.getProperty("api_key" + apiKeyIndex)) != null) {
                apiKeys.add(apiKey);
                apiKeyIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("API 키를 가져올 수 없습니다.", e);
        }
    }

    public void FetchData() {
        File configFolder = new File(CONFIG_FOLDER_PATH);
        File[] files = configFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    fileName = file.getName();
                    List<String> keywords = readKeywordsFromFile(CONFIG_FOLDER_PATH + "/" + fileName);

                    for (String s : keywords) {
                        String response = getConnection("search", s);
                        parseData(s, response);
                    }
                }
            }
        }
    }

    private void parseData(String keyword, String jsonData) throws ClassCastException {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
            JSONArray dataArray = (JSONArray) jsonObject.get("items");

            YoutubeVideo youtubeVideo = new YoutubeVideo();
            YoutubeChannelStat youtubeChannelStat;
            YoutubeVideoStat youtubeVideoStat;
            YoutubeVideoKey youtubeVideoKey = new YoutubeVideoKey();

            youtubeVideoKey.setClient(fileName.substring(0, fileName.lastIndexOf('.')));
            youtubeVideoKey.setKeyword(keyword);

            for (Object obj : dataArray) {
                JSONObject item = (JSONObject) obj;
                JSONObject ids = (JSONObject) item.get("id");

                if (ids.get("videoId") == null) {
                    break;
                }

                youtubeVideoKey.setVideoId((String) ids.get("videoId"));
                youtubeVideo.setId(youtubeVideoKey);

                JSONObject snippet = (JSONObject) item.get("snippet");
                JSONObject thumbnails = (JSONObject) snippet.get("thumbnails");
                JSONObject defaultThumbnail = (JSONObject) thumbnails.get("default");

                youtubeVideo.setTitle((String) snippet.get("title"));
                youtubeVideo.setThumbnailUrl((String) defaultThumbnail.get("url"));
                youtubeVideo.setChannelId((String) snippet.get("channelId"));
                youtubeVideo.setChannel((String) snippet.get("channelTitle"));
                youtubeVideo.setPublishedAt((String) snippet.get("publishTime"));

                youtubeVideoStat = parseVideoData((String) ids.get("videoId"));
                youtubeVideoStat.setVideoId((String) ids.get("videoId"));

                youtubeChannelStat = parseChannelData((String) snippet.get("channelId"));
                youtubeChannelStat.setChannelId((String) snippet.get("channelId"));

                System.out.println(youtubeVideo);
                System.out.println(youtubeChannelStat);
                System.out.println(youtubeVideoStat);

                youtubeChannelStatRepository.save(youtubeChannelStat);
                youtubeVideoRepository.save(youtubeVideo);
                youtubeVideoStatRepository.save(youtubeVideoStat);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private YoutubeChannelStat parseChannelData(String channelId) throws ParseException {
        YoutubeChannelStat channel = new YoutubeChannelStat();

        JSONParser parser = new JSONParser();
        String channelInfo = getConnection("channels", channelId);

        JSONObject jsonObject = (JSONObject) parser.parse(channelInfo);
        JSONArray jsonArray = (JSONArray) jsonObject.get("items");
        JSONObject value = (JSONObject) jsonArray.get(0);
        JSONObject statistics = (JSONObject) value.get("statistics");

        if (statistics.get("viewCount") != null) {
            channel.setViewCount(Long.parseLong((String) statistics.get("viewCount")));
        }
        if (statistics.get("subscriberCount") != null) {
            channel.setSubscriberCount(Long.parseLong((String) statistics.get("subscriberCount")));
        }
        if (statistics.get("videoCount") != null) {
            channel.setVideoCount(Long.parseLong((String) statistics.get("videoCount")));
        }

        return channel;
    }

    private YoutubeVideoStat parseVideoData(String videoId) throws ParseException {
        YoutubeVideoStat video = new YoutubeVideoStat();

        JSONParser parser = new JSONParser();
        String videoInfo = getConnection("videos", videoId);

        JSONObject jsonObject = (JSONObject) parser.parse(videoInfo);
        JSONArray dataArray = (JSONArray) jsonObject.get("items");
        JSONObject value = (JSONObject) dataArray.get(0);
        JSONObject statistics = (JSONObject) value.get("statistics");

        if (statistics.get("viewCount") != null) {
            video.setViewCount(Long.parseLong((String) statistics.get("viewCount")));
        }
        if (statistics.get("likeCount") != null) {
            video.setLikeCount(Long.parseLong((String) statistics.get("likeCount")));
        }
        if (statistics.get("commentCount") != null) {
            video.setCommentCount(Long.parseLong((String) statistics.get("commentCount")));
        }

        return video;
    }

    public String getNextApiKey() {
        if (currentApiKeyIndex < apiKeys.size()) {
            return apiKeys.get(currentApiKeyIndex++);
        } else {
            throw new RuntimeException("더 이상 사용 가능한 API 키가 없습니다.");
        }
    }

    public List<String> readKeywordsFromFile(String filePath) {
        try {
            List<String> keywords = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                keywords.add(line);
            }
            br.close();
            return keywords;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(fileName + "파일 읽기 오류: " + e.getMessage(), e);
        }
    }

    public String getConnection(String type, String param) {
        try {
            URI uri;
            String queryString = "key=" + apiKeys.get(currentApiKeyIndex);

            switch (type) {
                case "search" -> queryString += "&part=snippet&maxResults=50&order=viewCount" + "&q=" + param;
                case "videos", "channels" -> queryString += "&part=snippet,statistics" + "&id=" + param;
                default -> throw new IllegalArgumentException("타입 재지정 필요");
            }

            uri = new URI("https", "www.googleapis.com", "/youtube/v3/" + type, queryString, null);

            String urlString = uri.toASCIIString();

            System.out.println(urlString);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            if (responseCode == 200) {
                StringBuilder sb = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                }

                conn.disconnect();
                System.out.println(sb);
                return sb.toString();
            } else if (responseCode == 403) {
                System.out.println("[Failed] API Key 일일 호출 횟수 초과 - Response Code : " + responseCode);
                getNextApiKey();
                return getConnection(type, param);
            } else {
                conn.disconnect();
                throw new RuntimeException("[Failed] 요청 실패 - Response Code : " + responseCode);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("HTTP 요청 중 오류 발생: " + e.getMessage(), e);
        }
    }

}
