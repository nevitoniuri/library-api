package com.unichristus.libraryapi.presentation.controller;

import com.unichristus.libraryapi.infra.security.LoggedUser;
import com.unichristus.libraryapi.application.dto.response.HomeResponse;
import com.unichristus.libraryapi.application.service.HomeService;
import com.unichristus.libraryapi.application.util.ServiceURIs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.HOME_RESOURCE)
public class HomeController {

    private final HomeService homeService;

    @GetMapping("resume")
    public HomeResponse resume(@LoggedUser UUID userId) {
         return homeService.resume(userId);
    }
}
