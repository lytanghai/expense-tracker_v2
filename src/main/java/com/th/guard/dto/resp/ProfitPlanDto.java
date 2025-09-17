package com.th.guard.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.th.guard.entity.ProfitPlan;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProfitPlanDto {
    private Integer id;
    private int year;
    private int month;
    private BigDecimal target;
    private BigDecimal result;
    private String type;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private List<PlanDetailDto> details;

    public ProfitPlanDto() {}

    public ProfitPlanDto(ProfitPlan profitPlan, List<PlanDetailDto> details) {
        this.id = profitPlan.getId();
        this.year = profitPlan.getYear();
        this.month = profitPlan.getMonth();
        this.target = profitPlan.getTarget();
        this.result = profitPlan.getResult();
        this.type = profitPlan.getType();
        this.status = profitPlan.getStatus();
        this.createdAt = profitPlan.getCreatedAt();
        this.details = details;
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }

    public BigDecimal getTarget() { return target; }
    public void setTarget(BigDecimal target) { this.target = target; }

    public BigDecimal getResult() { return result; }
    public void setResult(BigDecimal result) { this.result = result; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<PlanDetailDto> getDetails() { return details; }
    public void setDetails(List<PlanDetailDto> details) { this.details = details; }
}