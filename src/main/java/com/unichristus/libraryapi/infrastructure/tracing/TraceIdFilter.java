package com.unichristus.libraryapi.infrastructure.tracing;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * Filtro que intercepta TODOS os requests HTTP e adiciona um traceId único ao MDC.
 * O MDC (Mapped Diagnostic Context) propaga o traceId para todos os logs dentro do contexto do request.
 */
@Component
@Order(1) // Garante que seja o primeiro filtro
public class TraceIdFilter implements Filter {

    public static final String TRACE_ID_KEY = "traceId";
    private static final String TRACE_ID_HEADER = "X-Trace-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // Gera um UUID único para este request
            String traceId = UUID.randomUUID().toString();

            // Adiciona o traceId ao MDC (contexto de logging)
            MDC.put(TRACE_ID_KEY, traceId);

            // Opcionalmente, adiciona o traceId no header de resposta para rastreamento
            ((HttpServletResponse) response).setHeader(TRACE_ID_HEADER, traceId);

            // Continua a cadeia de filtros
            chain.doFilter(request, response);
        } finally {
            // CRÍTICO: Remove o traceId do MDC após o request finalizar
            // Isso garante que logs fora do contexto HTTP não terão traceId
            MDC.remove(TRACE_ID_KEY);
        }
    }
}
