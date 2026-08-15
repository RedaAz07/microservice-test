package com.product.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.product.dto.requestdto.userDto;

@FeignClient("USER-SERVICE")
public interface UserClientInterface {

    @GetMapping("/api/users/{username}")
    userDto getUserByUsername(@PathVariable("username") String username);

}