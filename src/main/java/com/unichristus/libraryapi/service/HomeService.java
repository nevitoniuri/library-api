package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.mapper.ReadingResponseMapper;
import com.unichristus.libraryapi.dto.response.HomeResponse;
import com.unichristus.libraryapi.dto.response.ReadingResponse;
import com.unichristus.libraryapi.dto.response.UserSummaryResponse;
import com.unichristus.libraryapi.enums.ReadingStatus;
import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.model.Favorite;
import com.unichristus.libraryapi.model.Reading;
import com.unichristus.libraryapi.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final ReadingService readingService;
    private final FavoriteService favoriteService;

    public HomeResponse resume(User user) {
        List<Reading> readings = readingService.findReadingsByUser(user);
        List<Book> favoriteBooks = favoriteService.findFavoritesByUser(user)
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
