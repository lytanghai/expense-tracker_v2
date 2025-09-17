package com.th.guard.dto.req;

import java.math.BigDecimal;

public class PlanDetailFillOutReq {
    private Integer planDetailId;
    private BigDecimal result;

    public Integer getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(Integer planDetailId) {
        this.planDetailId = planDetailId;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }
}
