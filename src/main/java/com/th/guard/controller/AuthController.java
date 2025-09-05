package com.th.guard.controller;

import com.th.guard.dto.req.ChangePasswordReq;
import com.th.guard.dto.req.LoginReq;
import com.th.guard.dto.req.RegisterReq;
import com.th.guard.dto.resp.CommonResp;
import com.th.guard.dto.resp.LoginResp;
import com.th.guard.dto.resp.RegisterResp;
import com.th.guard.service.AuthenticationService;
import com.th.guard.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://expense-tracker-v2-web.onrender.com", allowedHeaders = "*")
public class AuthController {

    @Autowired private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseBuilder<RegisterResp> register(@RequestBody RegisterReq req) {
        return authenticationService.register(req);
    }

    @PostMapping("/login")
    public ResponseBuilder<LoginResp> login(@RequestBody LoginReq login) throws InterruptedException {
        return authenticationService.login(login);
    }

    @PostMapping("/change-password")
    public ResponseBuilder<CommonResp> changePassword(@RequestBody ChangePasswordReq req) {
        return authenticationService.changePassword(req);
    }

}