package com.unichristus.libraryapi.application.mapper;

import com.unichristus.libraryapi.application.dto.response.FavoriteResponse;
import com.unichristus.libraryapi.domain.favorite.Favorite;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FavoriteResponseMapper {

    public static FavoriteResponse toFavoriteResponse(Favorite favorite) {
        return new FavoriteResponse(
                favorite.getBook() != null ? favorite.getBook().getId() : null,
                favorite.getBook() != null ? favorite.getBook().getTitle() : null,
                favorite.getBook() != null ? favorite.getBook().getIsbn() : null,
                favorite.getCreatedAt()
        );
    }
}
