package com.th.guard.dto.req;

import java.math.BigDecimal;
import java.util.Date;

public class ProfitPlanDetailReq {
    private Integer profitPlanId;
    private Integer day;
    private BigDecimal actualAmount;
    private BigDecimal dailyTarget;
    private Date createdAt;

    public Integer getProfitPlanId() {
        return profitPlanId;
    }

    public void setProfitPlanId(Integer profitPlanId) {
        this.profitPlanId = profitPlanId;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getDailyTarget() {
        return dailyTarget;
    }

    public void setDailyTarget(BigDecimal dailyTarget) {
        this.dailyTarget = dailyTarget;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
