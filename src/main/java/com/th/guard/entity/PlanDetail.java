package com.th.guard.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "plan_detail")
public class PlanDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profit_plan_id", nullable = false)
    private ProfitPlan profitPlan;

    @Column(nullable = false)
    private int day;  // 1–31

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal target;

    @Column(precision = 12, scale = 2)
    private BigDecimal result = BigDecimal.ZERO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProfitPlan getProfitPlan() {
        return profitPlan;
    }

    public void setProfitPlan(ProfitPlan profitPlan) {
        this.profitPlan = profitPlan;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public BigDecimal getTarget() {
        return target;
    }

    public void setTarget(BigDecimal target) {
        this.target = target;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

}
