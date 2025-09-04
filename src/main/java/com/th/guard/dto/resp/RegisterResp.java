package com.th.guard.dto.resp;

import com.th.guard.dto.req.RegisterReq;

public class RegisterResp extends RegisterReq {
    private String result;
    private String detail;
    private String createdAtStr;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreatedAtStr() {
        return createdAtStr;
    }

    public void setCreatedAtStr(String createdAtStr) {
        this.createdAtStr = createdAtStr;
    }
}
