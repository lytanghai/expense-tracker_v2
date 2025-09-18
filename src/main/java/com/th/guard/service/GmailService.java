package com.th.guard.service;

import com.th.guard.entity.PlanDetail;
import com.th.guard.entity.ProfitPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class GmailService {

    private static final Logger log = LoggerFactory.getLogger(GmailService.class);
    private final JavaMailSender javaMailSender;

    public GmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendProfitPlanEmail(ProfitPlan plan, List<PlanDetail> planDetails) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo("tanghaicrypto@gmail.com");
        helper.setSubject("📈 Profit Plan Report - " + plan.getMonth() + "/" + plan.getYear());

        // Build HTML table
        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family: Arial, sans-serif;'>");
        html.append("<h2 style='color:#4F46E5;'>📋 Profit Plan Checklist</h2>");
        html.append("<p><b>Type:</b> ").append(plan.getType()).append("<br/>")
                .append("<b>Month/Year:</b> ").append(plan.getMonth()).append("/").append(plan.getYear()).append("</p>");

        html.append("<table style='border-collapse:collapse;width:100%;'>");
        html.append("<thead><tr style='background:#4F46E5;color:white;'>")
                .append("<th style='padding:8px;border:1px solid #ddd;'>Day</th>")
                .append("<th style='padding:8px;border:1px solid #ddd;'>Target</th>")
                .append("<th style='padding:8px;border:1px solid #ddd;'>Result</th>")
                .append("<th style='padding:8px;border:1px solid #ddd;'>Remark</th>")
                .append("</tr></thead><tbody>");

        for (PlanDetail d : planDetails) {
            String rowColor = d.getDay() == java.time.LocalDate.now().getDayOfMonth() ? "#D1FAE5" : "#F9FAFB";
            String remark;
            if (d.getDay() < java.time.LocalDate.now().getDayOfMonth()) {
                if (d.getResult().compareTo(d.getTarget()) < 0) {
                    remark = "Incomplete (" + (d.getTarget().subtract(d.getResult())) + " $ left)";
                } else if (d.getResult().equals(d.getTarget())) {
                    remark = "✔️";
                } else {
                    remark = "🔥";
                }
            } else {
                remark = "-";
            }

            html.append("<tr style='background:").append(rowColor).append(";'>")
                    .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(d.getDay()).append("</td>")
                    .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(d.getTarget()).append("</td>")
                    .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(d.getResult()).append("</td>")
                    .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(remark).append("</td>")
                    .append("</tr>");
        }

        html.append("</tbody></table>");
        html.append("<p style='margin-top:10px;color:gray;'>Sent on ").append(java.time.LocalDate.now()).append("</p>");
        html.append("</body></html>");

        helper.setText(html.toString(), true);

        log.info("message sent successfully");
        javaMailSender.send(message);
    }
}
