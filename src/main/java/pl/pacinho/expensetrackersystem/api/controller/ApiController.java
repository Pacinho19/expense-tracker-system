package pl.pacinho.expensetrackersystem.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiController {

    @GetMapping("/ping")
    String ping() {
        return "OK";
    }
}
