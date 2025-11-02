package com.unichristus.libraryapi.application.usecase.home;

import com.unichristus.libraryapi.application.mapper.ReadingResponseMapper;
import com.unichristus.libraryapi.application.dto.response.HomeResponse;
import com.unichristus.libraryapi.application.dto.response.ReadingHomeResponse;
import com.unichristus.libraryapi.application.dto.response.UserSummaryResponse;
import com.unichristus.libraryapi.domain.favorite.FavoriteService;
import com.unichristus.libraryapi.domain.reading.ReadingService;
import com.unichristus.libraryapi.domain.reading.ReadingStatus;
import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.favorite.Favorite;
import com.unichristus.libraryapi.domain.reading.Reading;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeResumeUseCase {

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

        List<ReadingHomeResponse> inProgress = new ArrayList<>();

        for (Reading reading : readings) {
            if (reading.getStatus() == ReadingStatus.IN_PROGRESS) {
                boolean isFavorite = favoriteBooks.contains(reading.getBook());
                inProgress.add(ReadingResponseMapper.toReadingHomeResponse(reading, isFavorite));
            } else if (reading.getStatus() == ReadingStatus.FINISHED) {
                totalFinished++;
                totalPagesRead += reading.getCurrentPage();
            }
        }

        UserSummaryResponse summary = new UserSummaryResponse(inProgress.size(), totalFinished, totalPagesRead);
        return new HomeResponse(inProgress, summary);
    }

}
