package com.th.guard.controller;

import com.th.guard.dto.req.ChangePasswordReq;
import com.th.guard.dto.req.ExpReq;
import com.th.guard.dto.req.LoginReq;
import com.th.guard.dto.req.RegisterReq;
import com.th.guard.dto.resp.CommonResp;
import com.th.guard.dto.resp.LoginResp;
import com.th.guard.dto.resp.RegisterResp;
import com.th.guard.service.AuthenticationService;
import com.th.guard.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/public/auth")
//@CrossOrigin(origins = "https://expense-tracker-v2-web.onrender.com", allowedHeaders = "*")
public class AuthController {

    @Autowired private AuthenticationService authenticationService;

    @GetMapping("/wakeup")
    public String wakeup() {
        System.out.println("Wake-up request received!");
        return "Server is awake!";
    }

    @PostMapping("/experimental")
    public ResponseEntity<String> experimental(@RequestBody ExpReq expReq) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        if (expReq.getHeaders() != null) {
            expReq.getHeaders().forEach(headers::add); // ✅ Correct use of lambda
        }

        HttpEntity<Object> requestEntity = new HttpEntity<>(expReq.getPayload(), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    expReq.getUrl(),
                    HttpMethod.valueOf(expReq.getMethod().toUpperCase()),
                    requestEntity,
                    String.class
            );
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

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