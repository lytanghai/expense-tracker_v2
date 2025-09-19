package com.th.guard.repository;

import com.th.guard.entity.PlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PlanDetailRepo extends JpaRepository<PlanDetail, Integer> {

    @Query(value = "SELECT * FROM plan_detail WHERE profit_plan_id = :profitPlanId", nativeQuery = true)
    List<PlanDetail> findAllByProfitPlanId(Integer profitPlanId);

    @Query(value = "select SUM(pd.\"result\") as total " +
            "from plan_detail pd " +
            "where pd.profit_plan_id = :profitPlanId", nativeQuery = true)
    BigDecimal total(int profitPlanId);
}
