package com.example.sweater;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greetingPage(
            @RequestParam(name="name", defaultValue="World", required = false)
            String name,
            Map<String, Object> model
    ){
        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/main")
    public String mainPage(
            Map<String, Object> model
    ){
        model.put("main", "main");
        return "main";
    }
}
