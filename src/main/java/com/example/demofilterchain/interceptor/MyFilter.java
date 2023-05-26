package com.example.demofilterchain.interceptor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // This method is called for each incoming request, just like an interceptor.
        // Implement your custom logic here.
        System.out.println("MyFilter: Processing request");
        log.info("request before filter: {}", IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8));
        log.info("header before filter: {}", response);
        // Call the next filter in the chain or the controller
        filterChain.doFilter(request, response);
        log.info("request after filter: {}", IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8));
        log.info("header after filter: {}", response.getHeader("header1").toString());
    }
    // filter rồi vào controller, rồi trả ra luôn, trừ khi có thêm interceptor nào đó nữa xử lý đầu ra.
    private static class ResponseWrapper extends HttpServletResponseWrapper {
        private final ByteArrayOutputStream outputStream;
        private final PrintWriter writer;

        public ResponseWrapper(HttpServletResponse response) throws IOException {
            super(response);
            outputStream = new ByteArrayOutputStream();
            writer = new PrintWriter(outputStream, true);
        }

        @Override
        public PrintWriter getWriter() {
            return writer;
        }

        @Override
        public ServletOutputStream getOutputStream() {
            return new ServletOutputStream() {
                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setWriteListener(WriteListener listener) {

                }

                @Override
                public void write(int b) throws IOException {
                    outputStream.write(b);
                }
            };
        }

        public String getBody() {
            writer.flush();
            return outputStream.toString();
        }
    }
}

