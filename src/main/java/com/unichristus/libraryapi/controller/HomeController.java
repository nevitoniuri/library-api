package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.dto.response.HomeResponseDTO;
import com.unichristus.libraryapi.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("resume")
    public HomeResponseDTO resume(@RequestParam UUID userId) {
         return homeService.resume(userId);
    }
}
