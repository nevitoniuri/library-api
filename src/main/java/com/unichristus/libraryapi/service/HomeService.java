package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.mapper.ReadingResponseMapper;
import com.unichristus.libraryapi.dto.response.HomeResponse;
import com.unichristus.libraryapi.dto.response.ReadingResponse;
import com.unichristus.libraryapi.dto.response.UserSummaryResponse;
import com.unichristus.libraryapi.enums.ReadingStatus;
import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.model.Favorite;
import com.unichristus.libraryapi.model.Reading;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final ReadingService readingService;
    private final FavoriteService favoriteService;

    public HomeResponse resume(UUID userId) {
        List<Reading> readings = readingService.findReadingsByUser(userId);
        List<Book> favoriteBooks = favoriteService.findFavoritesByUser(userId)
                .stream()
                .map(Favorite::getBook)
                .toList();

        int totalPagesRead = 0;
        int totalFinished = 0;

        List<ReadingResponse> inProgress = new ArrayList<>();

        for (Reading reading : readings) {
            if (reading.getStatus() == ReadingStatus.IN_PROGRESS) {
                boolean isFavorite = favoriteBooks.contains(reading.getBook());
                inProgress.add(ReadingResponseMapper.toReadingResponse(reading, isFavorite));
            } else if (reading.getStatus() == ReadingStatus.FINISHED) {
                totalFinished++;
                totalPagesRead += reading.getCurrentPage();
            }
        }

        UserSummaryResponse summary = new UserSummaryResponse(inProgress.size(), totalFinished, totalPagesRead);
        return new HomeResponse(inProgress, summary);
    }

}
