package com.pos.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping(value={
            "",
            "/",
            "/login",
            "/products",
            "/quotations",
            "/invoice",
            "/quotation",
            "/invoices",
            "/invoice/noStock"
    })
    public String getBranches() {
        return "index.html";
    }
}
