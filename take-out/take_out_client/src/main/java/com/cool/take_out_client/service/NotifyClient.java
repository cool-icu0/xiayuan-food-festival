package com.cool.take_out_client.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "take-out-notify",
        contextId = "take-out-notify"
)
public interface NotifyClient {
    @GetMapping("/notify/paySuccess")
    boolean validateToken(String outTradeNo);
}
