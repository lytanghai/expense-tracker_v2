package com.th.guard.service;

import com.th.guard.component.TelegramProperties;
import com.th.guard.constant.Static;
import com.th.guard.entity.PlanDetail;
import com.th.guard.entity.ProfitPlan;
import com.th.guard.util.DateUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TelegramService {

    private final TelegramProperties telegramProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    public TelegramService(TelegramProperties telegramProperties) {
        this.telegramProperties = telegramProperties;
    }

    public void sendMessage(String message) {

        Map<String, String> request = new HashMap<>();
        request.put(Static.CHAT_ID, telegramProperties.getChatId());
        request.put(Static.TEXT, message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        if(telegramProperties.isEnable()) {
            restTemplate.postForEntity(
                    Static.TELEGRAM_BOT_URL + telegramProperties.getBotToken() + Static.SEND_MESSAGE,
                    entity,
                    String.class);
        }
    }

    public void sendProfitPlanTelegram(ProfitPlan plan, List<PlanDetail> planDetails) {
        StringBuilder text = new StringBuilder();
        text.append("📋 <b>Profit Plan Checklist</b>\n\n");
        text.append("<b>Type:</b> ").append(plan.getType()).append("\n");
        text.append("<b>Date:</b> ").append(plan.getMonth()).append("/").append(plan.getYear()).append("\n\n");
        text.append("<b>Sent on:<b> ").append(DateUtil.format(new Date()));

        text.append("<b>Day | Target | Result | Remark</b>\n");

        for (PlanDetail d : planDetails) {
            String remark;
            if (d.getDay() < java.time.LocalDate.now().getDayOfMonth()) {
                if (d.getResult().compareTo(d.getTarget()) < 0) {
                    remark = "❌ (" + (d.getTarget().subtract(d.getResult())) + " $ left)";
                } else if (d.getResult().equals(d.getTarget())) {
                    remark = "✔️";
                } else {
                    remark = "🔥";
                }
            } else if (d.getDay() == java.time.LocalDate.now().getDayOfMonth()) {
                remark = "📍 Today";
            } else {
                remark = "-";
            }

            text.append(d.getDay())
                    .append(" | ")
                    .append(d.getTarget())
                    .append(" | ")
                    .append(d.getResult())
                    .append(" | ")
                    .append(remark)
                    .append("\n");
        }


        Map<String, Object> request = new HashMap<>();
        request.put("chat_id", telegramProperties.getChatId());
        request.put("text", text.toString());
        request.put("parse_mode", "HTML"); // allows <b>, <i>, etc.

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        if (telegramProperties.isEnable()) {
            restTemplate.postForEntity(
                    Static.TELEGRAM_BOT_URL + telegramProperties.getBotToken() + Static.SEND_MESSAGE,
                    entity,
                    String.class
            );
        }
    }


}
