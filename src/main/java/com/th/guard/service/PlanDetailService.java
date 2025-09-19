package com.th.guard.service;

import com.th.guard.dto.req.PlanDetailCreateReq;
import com.th.guard.dto.resp.CommonResp;
import com.th.guard.entity.PlanDetail;
import com.th.guard.entity.ProfitPlan;
import com.th.guard.repository.PlanDetailRepo;
import com.th.guard.repository.ProfitPlanRepo;
import com.th.guard.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class PlanDetailService {

    @Autowired private PlanDetailRepo planDetailRepo;
    @Autowired private ProfitPlanRepo profitPlanRepo;

    public void initChecklist(ProfitPlan profitPlan) {
        int month = profitPlan.getMonth();
        int year = profitPlan.getYear();
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        BigDecimal monthlyTarget = profitPlan.getTarget();

        // Count weekdays (Mon–Fri) in this month
        long weekdaysCount = IntStream.rangeClosed(1, daysInMonth)
                .mapToObj(d -> LocalDate.of(year, month, d))
                .filter(date -> !(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY))
                .count();

        BigDecimal dailyTarget = monthlyTarget.divide(BigDecimal.valueOf(weekdaysCount), 2, RoundingMode.DOWN);

        List<PlanDetail> details = new ArrayList<>();
        BigDecimal totalAssigned = BigDecimal.ZERO;

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(year, month, day);

            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                continue; // skip weekends
            }

            PlanDetail detail = new PlanDetail();
            detail.setProfitPlan(profitPlan);
            detail.setDay(day);
            detail.setCurrency(profitPlan.getCurrency());

            // For the last weekday, adjust so the total = monthlyTarget
            if (day == daysInMonth || totalAssigned.add(dailyTarget).compareTo(monthlyTarget) > 0) {
                detail.setTarget(monthlyTarget.subtract(totalAssigned));
            } else {
                detail.setTarget(dailyTarget);
                totalAssigned = totalAssigned.add(dailyTarget);
            }

            details.add(detail);
        }

        planDetailRepo.saveAll(details);
    }


    public ResponseBuilder<CommonResp> fillOut(PlanDetailCreateReq fillOutReq) {
        CommonResp commonResp = new CommonResp();

        PlanDetail planDetail = planDetailRepo.findById(fillOutReq.getPlanDetailId()).orElse(null);
        if (planDetail != null) {
            planDetail.setResult(fillOutReq.getResult());
            planDetailRepo.save(planDetail);

            BigDecimal total = planDetailRepo.total(planDetail.getProfitPlan().getId());
            Optional<ProfitPlan> profitPlan = profitPlanRepo.findById(planDetail.getProfitPlan().getId());
            if(profitPlan.isPresent() && total.compareTo(profitPlan.get().getTarget()) >= 0) {
                profitPlan.get().setStatus("Success");
                profitPlanRepo.save(profitPlan.get());
            }
            String detail;
            int cmp = planDetail.getResult().compareTo(planDetail.getTarget());

            if (cmp > 0) {
                detail = "Congratulations! You've gained more than your target.";
            } else if (cmp == 0) {
                detail = "Great! You've achieved your target exactly.";
            } else {
                BigDecimal diff = planDetail.getTarget().subtract(planDetail.getResult());
                detail = "Keep going! You are " + diff + " " + planDetail.getCurrency() +" below your target.";
            }

            commonResp.setDetail(detail);
        } else {
            commonResp.setResult("FAILED");
        }
        return ResponseBuilder.success(commonResp);
    }
}
