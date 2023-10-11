package project.lincook.backend.common.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLDecoder;

@Slf4j
@Component
public class HttpLogInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;

    public HttpLogInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request instanceof ContentCachingRequestWrapper) {
            log.info("uri: " + request.getRequestURI());
            String req = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            // 특수문자들이 URL 인코딩되어 있기때문에 URL 디코더를 사용
            log.info("request - {}", URLDecoder.decode(req, request.getCharacterEncoding()));
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        InputStream tmpIs = ((CachingResponseWrapper) response).getContentInputStream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(tmpIs, writer, "UTF-8");
        String res = writer.toString();
        log.info("response - {}", res);
    }
}
