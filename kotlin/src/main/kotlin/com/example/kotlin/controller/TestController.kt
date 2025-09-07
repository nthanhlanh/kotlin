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
        backgroundJob() // launch sẽ không block coroutine chính, nó sẽ chạy song song
        //backgroundJob().join() // sẽ block coroutine chính cho đến khi job này hoàn thành
        println("after launch");
        delay(5000);
        println("after launch delay");
        return "aaaaaa"
    }

    private fun backgroundJob(): Job {
        return GlobalScope.launch {
            delay(3000)
            println("Background job completed")
        }
    }
}
