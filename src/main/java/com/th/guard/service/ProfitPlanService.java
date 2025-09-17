package com.th.guard.service;

import com.th.guard.dto.req.FilterRequest;
import com.th.guard.dto.req.ProfitPlanReq;
import com.th.guard.dto.resp.CommonResp;
import com.th.guard.dto.resp.PlanDetailDto;
import com.th.guard.dto.resp.ProfitPlanDto;
import com.th.guard.dto.resp.ProfitPlanResp;
import com.th.guard.entity.PlanDetail;
import com.th.guard.entity.ProfitPlan;
import com.th.guard.repository.PlanDetailRepo;
import com.th.guard.repository.ProfitPlanRepo;
import com.th.guard.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfitPlanService {

    @Autowired private ProfitPlanRepo  profitPlanRepo;

    @Autowired private PlanDetailRepo planDetailRepo;

    @Autowired private PlanDetailService planDetailService;

    public ResponseBuilder<CommonResp> createProfitPlan(ProfitPlanReq profitPlanReq) {
        ProfitPlan profitPlanEntity = new ProfitPlan();
        profitPlanEntity.setMonth(profitPlanReq.getMonth());
        profitPlanEntity.setYear(profitPlanReq.getYear());
        profitPlanEntity.setStatus("Pending");
        profitPlanEntity.setTarget(profitPlanReq.getTargetAmount());
        profitPlanEntity.setType(profitPlanReq.getType());

        ProfitPlan result = profitPlanRepo.save(profitPlanEntity);

        planDetailService.initChecklist(result);

        CommonResp commonResp = new CommonResp();
        commonResp.setResult("SUCCESS");

        return ResponseBuilder.success(commonResp);
    }

    public void deleteProfitPlan(Integer id) {
        profitPlanRepo.deleteById(id);
    }

    public ResponseBuilder<ProfitPlanDto> getById(Integer id) {
        ProfitPlan profitPlan = profitPlanRepo.findById(id).orElse(null);

        if (profitPlan != null) {
            List<PlanDetailDto> detailDtos = planDetailRepo
                    .findAllByProfitPlanId(id)
                    .stream()
                    .map(PlanDetailDto::new)
                    .collect(Collectors.toList());

            ProfitPlanDto dto = new ProfitPlanDto(profitPlan, detailDtos);
            return ResponseBuilder.success(dto);
        }

        return ResponseBuilder.success(null);
    }

    public void updateProfitPlan(ProfitPlanReq profitPlanReq) {
        Optional<ProfitPlan> optionalProfitPlan = profitPlanRepo.findById(profitPlanReq.getId() != null ? profitPlanReq.getId() : null);

        if(optionalProfitPlan.isPresent()) {
            ProfitPlan profitPlan = optionalProfitPlan.get();

            // Update fields only if present in the request
            if(profitPlanReq.getType() != null && !profitPlanReq.getType().isEmpty()) {
                profitPlan.setType(profitPlanReq.getType().toLowerCase()); // normalize to lowercase
            }

            if(profitPlanReq.getTargetAmount() != null) {
                profitPlan.setTarget(profitPlanReq.getTargetAmount());
            }

            if(profitPlanReq.getYear() != null) {
                profitPlan.setYear(profitPlanReq.getYear());
            }

            if(profitPlanReq.getMonth() != null) {
                profitPlan.setMonth(profitPlanReq.getMonth());
            }

            if(profitPlanReq.getStatus() != null && !profitPlanReq.getStatus().isEmpty()) {
                profitPlan.setStatus(profitPlanReq.getStatus());
            }

            if(profitPlanReq.getActualResult() != null) {
                profitPlan.setResult(profitPlanReq.getActualResult());
            }

            // Save updated entity
            profitPlanRepo.save(profitPlan);
        }
    }


    public ResponseBuilder<Page<ProfitPlan>> getFilteredExpenses(FilterRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("id").descending());

        return ResponseBuilder.success(profitPlanRepo.findAll(this.filter(request), pageable));
    }

    public static Specification<ProfitPlan> filter(FilterRequest req) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by id
            if (req.getId() != null) {
                predicates.add(cb.equal(root.get("id"), req.getId()));
            }

            // Filter by month
            if (req.getMonth() != null) {
                predicates.add(cb.equal(root.get("month"), req.getMonth()));
            }

            // Filter by year
            if (req.getYear() != null) {
                predicates.add(cb.equal(root.get("year"), req.getYear()));
            }

            // Filter by type (crypto/forex)
            if (req.getType() != null && !req.getType().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("type")), req.getType().toLowerCase()));
            }

            // Filter by status (Pending/Success/Failed)
            if (req.getStatus() != null && !req.getStatus().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("status")), req.getStatus().toLowerCase()));
            }

            // Filter by createdDate (exact date, format: "yyyy-MM-dd")
            if (req.getCreatedDate() != null && !req.getCreatedDate().isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDateTime startOfDay = LocalDateTime.parse(req.getCreatedDate() + "T00:00:00");
                    LocalDateTime endOfDay = LocalDateTime.parse(req.getCreatedDate() + "T23:59:59");

                    predicates.add(cb.between(root.get("createdAt"), startOfDay, endOfDay));
                } catch (Exception e) {
                    // ignore parse errors or handle accordingly
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
