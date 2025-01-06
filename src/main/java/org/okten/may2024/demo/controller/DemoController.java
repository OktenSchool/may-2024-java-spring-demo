package org.okten.may2024.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    public final Integer demoBean;

    // 1-st variant of Dependency Injection
    public DemoController(Integer demoBean) {
        this.demoBean = demoBean;
    }

    @GetMapping("/bean")
    public Integer bean() {
        return demoBean;
    }
}
