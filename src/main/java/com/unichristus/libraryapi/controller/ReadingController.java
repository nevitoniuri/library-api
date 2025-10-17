package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.dto.response.ReadingResponseDTO;
import com.unichristus.libraryapi.model.Reading;
import com.unichristus.libraryapi.service.ReadingService;
import com.unichristus.libraryapi.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("readings")
public class ReadingController {

    private final ReadingService readingService;

    @PostMapping
    public ReadingResponseDTO startReading(@RequestParam UUID bookId, @RequestParam UUID userId) {
        Reading reading = readingService.startReading(bookId, userId);
        return MapperUtil.parse(reading, ReadingResponseDTO.class);
    }
}
