package com.th.guard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
    name = "profit_plan",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"year", "month", "type"})
    }
)
public class ProfitPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int year;

    private int month;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal target;

    @Column(precision = 12, scale = 2)
    private BigDecimal result = BigDecimal.ZERO;

    @Column(name = "currency")
    private String currency;

    @Column(nullable = false)
    private String type;  // crypto or forex

    @Column(nullable = false)
    private String status = "Pending";  // Pending, Success, Failed

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // One-to-many relationship with plan_detail
    @OneToMany(mappedBy = "profitPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PlanDetail> details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<PlanDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PlanDetail> details) {
        this.details = details;
    }

}
