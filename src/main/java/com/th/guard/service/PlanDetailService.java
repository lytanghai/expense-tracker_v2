package com.th.guard.service;

import com.th.guard.dto.req.PlanDetailCreateReq;
import com.th.guard.dto.resp.CommonResp;
import com.th.guard.entity.PlanDetail;
import com.th.guard.entity.ProfitPlan;
import com.th.guard.repository.PlanDetailRepo;
import com.th.guard.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanDetailService {

    @Autowired private PlanDetailRepo planDetailRepo;

    public void initChecklist(ProfitPlan profitPlan) {
        int month = profitPlan.getMonth();
        int year = profitPlan.getYear();
        int days = YearMonth.of(year, month).lengthOfMonth();
        BigDecimal monthlyTarget = profitPlan.getTarget();

        BigDecimal dailyTarget = monthlyTarget.divide(BigDecimal.valueOf(days), 2, RoundingMode.DOWN);

        // Prepare PlanDetail records
        List<PlanDetail> details = new ArrayList<>();
        BigDecimal totalAssigned = BigDecimal.ZERO;

        for (int day = 1; day <= days; day++) {
            PlanDetail detail = new PlanDetail();
            detail.setProfitPlan(profitPlan);
            detail.setDay(day);

            // For last day, adjust to ensure sum = monthlyTarget
            if (day == days) {
                detail.setTarget(monthlyTarget.subtract(totalAssigned));
            } else {
                detail.setTarget(dailyTarget);
                totalAssigned = totalAssigned.add(dailyTarget);
            }

            details.add(detail);
        }

        // Save all details in repository
        planDetailRepo.saveAll(details);

    }

    public ResponseBuilder<CommonResp> fillOut(PlanDetailCreateReq fillOutReq) {
        CommonResp commonResp = new CommonResp();

        PlanDetail planDetail = planDetailRepo.findById(fillOutReq.getPlanDetailId()).orElse(null);
        if (planDetail != null) {
            planDetail.setResult(fillOutReq.getResult());
            planDetailRepo.save(planDetail);

            String detail;
            int cmp = planDetail.getResult().compareTo(planDetail.getTarget());

            if (cmp > 0) {
                detail = "Congratulations! You've gained more than your target.";
            } else if (cmp == 0) {
                detail = "Great! You've achieved your target exactly.";
            } else {
                BigDecimal diff = planDetail.getTarget().subtract(planDetail.getResult());
                detail = "Keep going! You are " + diff + " USD below your target.";
            }

            commonResp.setDetail(detail);
        } else {
            commonResp.setResult("FAILED");
        }
        return ResponseBuilder.success(commonResp);
    }
}
