package spring.boot.batch.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.boot.batch.service.ImportService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ImportService importService;

    @GetMapping("start")
    public String startExport( ) {
        log.info("Export has been started... " + LocalDateTime.now());
        importService.start();
        log.info("Service has been started");
        return "OK";
    }
}
