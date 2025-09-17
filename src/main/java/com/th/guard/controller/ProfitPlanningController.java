package com.th.guard.controller;

import com.th.guard.dto.req.FilterRequest;
import com.th.guard.dto.req.ProfitPlanReq;
import com.th.guard.dto.resp.CommonResp;
import com.th.guard.dto.resp.ProfitPlanDto;
import com.th.guard.entity.ProfitPlan;
import com.th.guard.service.ProfitPlanService;
import com.th.guard.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profit-plan")
public class ProfitPlanningController {

    @Autowired private ProfitPlanService profitPlanService;

    @PostMapping("/create")
    public ResponseBuilder<CommonResp> createProfitPlan(@RequestBody ProfitPlanReq profitPlanReq) {
        return profitPlanService.createProfitPlan(profitPlanReq);
    }

    @PatchMapping("/update")
    public void updateProfitPlan(@RequestBody ProfitPlanReq profitPlanReq) {
        profitPlanService.updateProfitPlan(profitPlanReq);
    }

    @PostMapping("/get-all")
    public ResponseBuilder<Page<ProfitPlan>> getProfitPlan(@RequestBody FilterRequest filterRequest) {
        return profitPlanService.getFilteredExpenses(filterRequest);
    }

    @GetMapping("/get")
    public ResponseBuilder<ProfitPlanDto> getProfitPlanById(@RequestParam Integer id) {
        return profitPlanService.getById(id);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Integer id) {
        profitPlanService.deleteProfitPlan(id);
    }

    @GetMapping("/send-msg")
    public void sendMessage(@RequestParam("type") String type, @RequestParam("plan_id") Integer planId) {
        System.out.println("here");
    }
}
