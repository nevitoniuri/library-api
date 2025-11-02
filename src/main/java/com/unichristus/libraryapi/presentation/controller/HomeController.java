package com.unichristus.libraryapi.presentation.controller;

import com.unichristus.libraryapi.application.dto.response.HomeResponse;
import com.unichristus.libraryapi.application.usecase.home.HomeResumeUseCase;
import com.unichristus.libraryapi.infrastructure.security.LoggedUser;
import com.unichristus.libraryapi.presentation.common.ServiceURIs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.HOME_RESOURCE)
public class HomeController {

    private final HomeResumeUseCase homeResumeUseCase;

    @GetMapping("resume")
    public HomeResponse resume(@LoggedUser UUID userId) {
        return homeResumeUseCase.resume(userId);
    }
}
