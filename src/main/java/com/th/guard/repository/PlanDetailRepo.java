package com.th.guard.repository;

import com.th.guard.entity.PlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanDetailRepo extends JpaRepository<PlanDetail, Integer> {

    @Query(value = "SELECT * FROM plan_detail WHERE profit_plan_id = :profitPlanId", nativeQuery = true)
    List<PlanDetail> findAllByProfitPlanId(Integer profitPlanId);
}
