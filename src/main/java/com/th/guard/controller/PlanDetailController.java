package com.th.guard.controller;

import com.th.guard.dto.req.PlanDetailCreateReq;
import com.th.guard.dto.resp.CommonResp;
import com.th.guard.service.PlanDetailService;
import com.th.guard.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan-detail")
public class PlanDetailController {

    @Autowired private PlanDetailService planDetailService;

    @PostMapping("/fill-out")
    public ResponseBuilder<CommonResp> insert(@RequestBody PlanDetailCreateReq planDetailCreateReq) {
        return planDetailService.fillOut(planDetailCreateReq) ;
    }

}
