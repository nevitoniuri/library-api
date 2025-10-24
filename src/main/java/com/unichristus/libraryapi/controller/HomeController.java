package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.annotation.LoggedUser;
import com.unichristus.libraryapi.dto.response.HomeResponse;
import com.unichristus.libraryapi.security.CustomUserDetails;
import com.unichristus.libraryapi.service.HomeService;
import com.unichristus.libraryapi.util.ServiceURIs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.HOME_RESOURCE)
public class HomeController {

    private final HomeService homeService;

    @GetMapping("resume")
    public HomeResponse resume(@LoggedUser CustomUserDetails userDetails) {
         return homeService.resume(userDetails.toEntityReference());
    }
}
