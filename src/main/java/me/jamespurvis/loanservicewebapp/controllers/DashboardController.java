package me.jamespurvis.loanservicewebapp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class DashboardController {


    @PreAuthorize("isAuthenticated()")
    
    @RequestMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }
}
