package com.hmack101.screener.service;

import com.hmack101.screener.dto.ExternalStockData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Map;

@Service
public class PolygonClient {

    @Value("${polygon.api.key}")
    private String apiKey;

    @Value("${polygon.base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public ExternalStockData fetchStockDetails(String ticker) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/v3/reference/tickers/" + ticker)
                .queryParam("apiKey", apiKey)
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        Map<String, Object> results = (Map<String, Object>) response.get("results");

        ExternalStockData data = new ExternalStockData();
        data.setTicker(ticker);

        try {
            if (results != null) {
                Map<String, Object> shareClass = (Map<String, Object>) results.get("share_class_shares_outstanding");
                data.setFloatShares(results.get("share_class_shares_outstanding") != null
                        ? ((Number) results.get("share_class_shares_outstanding")).floatValue()
                        : 0d);
                data.setAvgVolume(results.get("volume_avg") != null
                        ? ((Number) results.get("volume_avg")).floatValue()
                        : 0d);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse stock data from Polygon", e);
        }

        return data;
    }
}
