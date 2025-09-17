package com.th.guard.repository;

import com.th.guard.entity.ProfitPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitPlanRepo extends JpaRepository<ProfitPlan, Integer>, JpaSpecificationExecutor<ProfitPlan> {
}
