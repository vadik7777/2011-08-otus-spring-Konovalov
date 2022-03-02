package ru.otus.project.rnis.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    private final String hystrixDashboardUrl;
    private final String swaggerUrl;

    public MainPageController(@Value("${rnis-service.hystrix-dashboard-url}") String hystrixDashboardUrl,
                              @Value("${springdoc.swagger-ui.path}") String swaggerUrl) {
        this.hystrixDashboardUrl = hystrixDashboardUrl;
        this.swaggerUrl = swaggerUrl;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("hystrixDashboardUrl", hystrixDashboardUrl);
        model.addAttribute("swaggerUrl", swaggerUrl);
        return "index";
    }
}