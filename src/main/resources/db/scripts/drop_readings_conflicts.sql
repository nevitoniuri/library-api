-- Script para dropar objetos conflitantes da migration readings (safe em dev).
-- Execute antes de rodar a aplicação para permitir que a migration crie os objetos.

-- 1) Remover índices que causam conflito
DROP INDEX IF EXISTS idx_readings_user_id;
DROP INDEX IF EXISTS idx_recent_readings_user;
DROP INDEX IF EXISTS idx_readings_book_id;
DROP INDEX IF EXISTS idx_readings_status;
DROP INDEX IF EXISTS idx_readings_user_book_status;
DROP INDEX IF EXISTS idx_readings_book_history;
DROP INDEX IF EXISTS uk_readings_user_book_active;

-- 2) Remover trigger (assume tabela readings existe)
DROP TRIGGER IF EXISTS trigger_updated_at ON readings;

-- 3) Opcional: remover a tabela (perde dados) — descomente se quiser
-- DROP TABLE IF EXISTS readings;

-- 4) Alternativa (opcional): marcar o changeset como já executado no Liquibase
-- Use esta opção se você NÃO quer que a migration rode (mas quer que Liquibase a considere aplicada).
-- Faça backup antes. Ajuste id/author/filename se necessário.
-- INSERT INTO public.databasechangelog
--   (id, author, filename, dateexecuted, orderexecuted, exectype)
-- VALUES
--   ('1761049764925', 'iuri', 'db/migrations/1761049764925-create-table-readings.sql', now(),
--    (SELECT COALESCE(MAX(orderexecuted),0)+1 FROM public.databasechangelog), 'EXECUTED');

-- Instrução de uso (exemplo psql):
-- Windows PowerShell:
-- $env:PGPASSWORD='SUA_SENHA'; psql -h localhost -U SEU_USUARIO -d SEU_BANCO -f "C:\Users\TIALA\Downloads\library-api-main\library-api\src\main\resources\db\scripts\drop_readings_conflicts.sql"

-- Garantir que a função trigger_updated_at exista (cria apenas se não existir)
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_proc WHERE proname = 'trigger_updated_at') THEN
    EXECUTE $fn$
      CREATE FUNCTION trigger_updated_at()
      RETURNS trigger
      LANGUAGE plpgsql
      AS $body$
      BEGIN
        NEW.updated_at := now();
        RETURN NEW;
      END;
      $body$;
    $fn$;
  END IF;
END
$$;
