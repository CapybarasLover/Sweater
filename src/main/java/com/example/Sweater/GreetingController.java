package com.example.Sweater;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", defaultValue="World", required = false)
            String name,
            Map<String, Object> model
    ){
        model.put("name", name);
        return "greeting";
    }
}
