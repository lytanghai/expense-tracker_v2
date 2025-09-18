package com.th.guard.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.th.guard.component.SettingProperties;
import com.th.guard.entity.PlanDetail;
import com.th.guard.entity.ProfitPlan;
import com.th.guard.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class GmailService {

    private static final Logger log = LoggerFactory.getLogger(GmailService.class);
    @Autowired private SettingProperties settingProperties;

    public void sendProfitPlanEmail(ProfitPlan plan, List<PlanDetail> planDetails) throws ResendException {

        Resend reSend = new Resend(settingProperties.getResendToken());
        BigDecimal totalTarget = BigDecimal.ZERO;
        BigDecimal totalResult = BigDecimal.ZERO;
        String type = plan.getType().equals("crypto") ? "Crypto Currency" : "Forex (XAU/USD)";
        StringBuilder html = new StringBuilder();

        html
        .append("<p><b>Type:</b> ").append(type).append("<br/>")
        .append("<b>Month/Year:</b> ").append(plan.getMonth()).append("/").append(plan.getYear()).append("</p></br>")
        .append("<p style='margin-top:10px;color:gray;'>Sent at: ").append(DateUtil.format(new Date())).append("</p>");

        html.append("<table style='border-collapse:collapse;width:100%;font-family:Arial,sans-serif;font-size:14px;'>");
        html.append("<thead><tr style='background:#4F46E5;color:white;'>")
                .append("<th style='padding:8px;border:1px solid #ddd;'>Day</th>")
                .append("<th style='padding:8px;border:1px solid #ddd;'>Target</th>")
                .append("<th style='padding:8px;border:1px solid #ddd;'>Result</th>")
                .append("<th style='padding:8px;border:1px solid #ddd;'>Remark</th>")
                .append("</tr></thead><tbody>");

        for (PlanDetail d : planDetails) {
            totalTarget = totalTarget.add(d.getTarget());
            totalResult = totalResult.add(d.getResult());

            String rowColor = d.getDay() == java.time.LocalDate.now().getDayOfMonth() ? "#D1FAE5" : "#F9FAFB";
            String remark;
            if (d.getDay() < java.time.LocalDate.now().getDayOfMonth()) {
                if (d.getResult().compareTo(d.getTarget()) < 0) {
                    remark = "-" + (d.getTarget().subtract(d.getResult())) + " $ left";
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
                    .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(d.getTarget()).append("$</td>")
                    .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(d.getResult()).append("$</td>")
                    .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(remark).append("</td>")
                    .append("</tr>");
        }

        // Add Grand Total row
        html.append("<tr style='background:#E0E7FF;font-weight:bold;'>")
                .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>TOTAL</td>")
                .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(totalTarget).append("$</td>")
                .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>-</td>")
                .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(totalResult).append("$</td>")
                .append("</tr>");

        html.append("</tbody></table>");

        // Add performance message
        String message;
        if (totalResult.compareTo(totalTarget) < 0) {
            BigDecimal diff = totalTarget.subtract(totalResult);
            message = "Keep going! You are " + diff + " USD below your target!";
        } else if (totalResult.compareTo(totalTarget) == 0) {
            message = "Great! You've achieved your target exactly!!";
        } else {
            message = "🎉 Congratulations! You've gained more than your target!!!";
        }

        String color;
        if (totalResult.compareTo(totalTarget) < 0) {
            color = "red";
        } else if (totalResult.compareTo(totalTarget) == 0) {
            color = "#2563EB"; // blue
        } else {
            color = "green";
        }
        html.append("<p style='margin-top:15px;font-size:14px;font-weight:bold;color:").append(color).append(";'>")
                .append(message)
                .append("</p>");

        html.append("</body></html>");

        CreateEmailOptions createEmailOptions = new CreateEmailOptions.Builder()
                .from("ProfitGuard <onboarding@resend.dev>")
                .to("tanghaicrypto@gmail.com")
                .subject("Profit Plan Checklist")
                .html(html.toString())
                .build();
        reSend.emails().send(createEmailOptions);
        log.info("message sent successfully");
    }
}
