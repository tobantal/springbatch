package spring.boot.batch.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("start")
    public String startExport( ) {
        return "Export has been started... " + LocalDateTime.now();
    }
}
