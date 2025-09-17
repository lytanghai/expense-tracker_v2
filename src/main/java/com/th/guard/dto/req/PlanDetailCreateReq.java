package com.th.guard.dto.req;

import java.math.BigDecimal;

public class PlanDetailCreateReq {
    private Integer planDetailId;
    private BigDecimal result;

    public Integer getPlanDetailId() {
        return planDetailId;
    }

    public void setProfitPlanId(Integer profitPlanId) {
        this.planDetailId = profitPlanId;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }
}
