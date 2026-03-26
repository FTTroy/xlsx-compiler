package com.github.fttroy.xlsx_compiler.controller;

import com.github.fttroy.xlsx_compiler.model.FormData;
import com.github.fttroy.xlsx_compiler.service.XlsxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/xlsx")
public class XlsxController {

    @Autowired
    XlsxService service;

    @PostMapping("/")
    public ResponseEntity<byte[]> createXlsx(@RequestBody FormData formData) throws IOException {
        HttpHeaders headersHttp = new HttpHeaders();
        headersHttp.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headersHttp.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("timesheet.xlsx")
                .build());
        return new ResponseEntity<>(service.createXlsx(formData), headersHttp, HttpStatus.OK);
    }
}
