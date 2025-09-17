package com.th.guard.service;

import com.th.guard.repository.PlanDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class GmailService {

    @Autowired private JavaMailSender javaMailSender;
    @Autowired private PlanDetailRepo planDetailRepo;

//    public void sendProfitPlanEmail(String to, ProfitPlan plan) throws MessagingException {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to);
//        helper.setSubject("📈 Profit Plan Report - " + plan.getMonth() + "/" + plan.getYear());
//
//        // Build HTML table
//        StringBuilder html = new StringBuilder();
//        html.append("<html><body style='font-family: Arial, sans-serif;'>");
//        html.append("<h2 style='color:#4F46E5;'>📋 Profit Plan Checklist</h2>");
//        html.append("<p><b>Type:</b> ").append(plan.getType()).append("<br/>")
//                .append("<b>Month/Year:</b> ").append(plan.getMonth()).append("/").append(plan.getYear()).append("</p>");
//
//        html.append("<table style='border-collapse:collapse;width:100%;'>");
//        html.append("<thead><tr style='background:#4F46E5;color:white;'>")
//                .append("<th style='padding:8px;border:1px solid #ddd;'>Day</th>")
//                .append("<th style='padding:8px;border:1px solid #ddd;'>Target</th>")
//                .append("<th style='padding:8px;border:1px solid #ddd;'>Result</th>")
//                .append("<th style='padding:8px;border:1px solid #ddd;'>Remark</th>")
//                .append("</tr></thead><tbody>");
//
//        List<PlanDetailDto> planDetailDtos = planDetailRepo
//                .findAllByProfitPlanId(plan.getId())
//                .stream()
//                .map(PlanDetailDto::new)
//                .collect(Collectors.toList());
//
//        for (PlanDetailDto d : planDetailDtos) {
//            String rowColor = d.getDay() == java.time.LocalDate.now().getDayOfMonth() ? "#D1FAE5" : "#F9FAFB";
//            String remark;
//            if (d.getDay() < java.time.LocalDate.now().getDayOfMonth()) {
//                if (d.getResult().compareTo(d.getTarget()) < 0) {
//                    remark = "Incomplete (" + (d.getTarget().subtract(d.getResult())) + " $ left)";
//                } else if (d.getResult().equals(d.getTarget())) {
//                    remark = "✔️";
//                } else {
//                    remark = "🔥";
//                }
//            } else {
//                remark = "-";
//            }
//
//            html.append("<tr style='background:").append(rowColor).append(";'>")
//                    .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(d.getDay()).append("</td>")
//                    .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(d.getTarget()).append("</td>")
//                    .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(d.getResult()).append("</td>")
//                    .append("<td style='padding:8px;border:1px solid #ddd;text-align:center;'>").append(remark).append("</td>")
//                    .append("</tr>");
//        }
//
//        html.append("</tbody></table>");
//        html.append("<p style='margin-top:10px;color:gray;'>Sent on ").append(java.time.LocalDate.now()).append("</p>");
//        html.append("</body></html>");
//
//        helper.setText(html.toString(), true);
//
//        javaMailSender.send(message);
//    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tanghai1289@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
