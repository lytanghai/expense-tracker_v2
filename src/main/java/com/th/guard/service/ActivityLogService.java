package com.th.guard.service;

import com.th.guard.component.SettingProperties;
import com.th.guard.entity.Logs;
import com.th.guard.repository.LogsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActivityLogService {

    @Autowired private LogsRepo repo;
    @Autowired private SettingProperties settingProperties;

    public void insert(String operation, Long userId, String result, String resultDetail) {
        if(settingProperties.getEnableSaveLog()) {
            Logs newLog = new Logs();
            newLog.setCreatedAt(LocalDateTime.now());
            newLog.setOperation(operation);
            newLog.setUserId(userId);
            newLog.setResult(result);
            newLog.setResultDetail(resultDetail);

            repo.save(newLog);
        }
    }
}
