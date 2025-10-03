package com.th.guard.component;

import com.th.guard.dto.resp.GoldApiResp;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KeepAlivePing {

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10 * 60 * 1000) // every 10 min
    public void goldApiResp() {
        restTemplate.getForObject("https://api.gold-api.com/price/XAU", GoldApiResp.class).getPrice().toString();
        System.out.println("Pong");

    }
}
