package com.th.guard.dto.resp;

import com.th.guard.entity.PlanDetail;

import java.math.BigDecimal;

public class PlanDetailDto {
    private Integer id;
    private int day;
    private BigDecimal target;
    private BigDecimal result;

    public PlanDetailDto(PlanDetail detail) {
        this.id = detail.getId();
        this.day = detail.getDay();
        this.target = detail.getTarget();
        this.result = detail.getResult();
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }

    public BigDecimal getTarget() { return target; }
    public void setTarget(BigDecimal target) { this.target = target; }

    public BigDecimal getResult() { return result; }
    public void setResult(BigDecimal result) { this.result = result; }
}