package com.example.kotlin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kotlinx.coroutines.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @OptIn(DelicateCoroutinesApi::class)
    @GetMapping("/test")
    suspend fun testEndpoint(): String {
        println("start");
        GlobalScope.launch {
                delay(500);
                println("job done");
            }
        println("after launch");
        delay(2000);
        println("after launch delay");
        return "aaaaaa"
    }
}
