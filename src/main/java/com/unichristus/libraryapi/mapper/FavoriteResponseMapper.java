package com.unichristus.libraryapi.mapper;

import com.unichristus.libraryapi.dto.response.FavoriteResponse;
import com.unichristus.libraryapi.model.Favorite;
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
