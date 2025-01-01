package org.okten.may2024.demo.controller;

import org.okten.may2024.demo.config.properties.Office;
import org.okten.may2024.demo.config.properties.ReferenceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reference-data")
public class ReferenceDataController {

    // 2-nd variant of Dependency Injection
    @Autowired
    @Qualifier("demoBean1")
    private String demoBean;

    @Value("${reference-data.categories:cat1,cat2}")
    private List<String> categories;

    private ReferenceData referenceData;

    @GetMapping("/categories")
    public List<String> categories() {
        return categories;
    }

    @GetMapping("/demo-bean")
    public String demoBean() {
        return demoBean;
    }

    @GetMapping("/offices")
    private List<Office> offices() {
        return referenceData.getOffices();
    }

    // 3-rd variant of Dependency Injection
    @Autowired
    public void setReferenceData(ReferenceData referenceData) {
        this.referenceData = referenceData;
    }
}
