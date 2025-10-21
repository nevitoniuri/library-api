--liquibase formatted sql
--changeset author:1760917088900
-- Criação da tabela de favoritos
CREATE TABLE IF NOT EXISTS favorites (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    book_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    CONSTRAINT unique_user_book_favorite UNIQUE (user_id, book_id)
);

-- Índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_favorites_user_id ON favorites(user_id);
CREATE INDEX IF NOT EXISTS idx_favorites_book_id ON favorites(book_id);

-- Comentários para documentação
COMMENT ON TABLE favorites IS 'Tabela que armazena os livros favoritos dos usuários';
COMMENT ON COLUMN favorites.id IS 'Identificador único do favorito (UUID)';
COMMENT ON COLUMN favorites.user_id IS 'Referência ao usuário que favoritou o livro';
COMMENT ON COLUMN favorites.book_id IS 'Referência ao livro favoritado';
COMMENT ON COLUMN favorites.created_at IS 'Data e hora em que o livro foi adicionado aos favoritos';
