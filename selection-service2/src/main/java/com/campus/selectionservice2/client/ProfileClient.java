package com.campus.selectionservice2.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "profile-service2")
public interface ProfileClient {

    @PostMapping("/api/profile/blacklist/{email}")
    void blacklistStudentByEmail(@PathVariable String email);
}

