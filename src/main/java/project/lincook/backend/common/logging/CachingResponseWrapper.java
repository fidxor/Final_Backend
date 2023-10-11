package project.lincook.backend.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.TeeOutputStream;
import org.springframework.util.FastByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class CachingResponseWrapper extends HttpServletResponseWrapper {
    private final FastByteArrayOutputStream content = new FastByteArrayOutputStream(1024);
    private ServletOutputStream outputStream;
    public CachingResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.outputStream == null) {
            this.outputStream = new CachingResponseWrapper.CashedServletOutputStream(getResponse().getOutputStream(), this.content);
        }

        return this.outputStream;
    }

    public InputStream getContentInputStream() {
        return this.content.getInputStream();
    }

    private class CashedServletOutputStream extends ServletOutputStream {

        private final TeeOutputStream targetStream;
        public CashedServletOutputStream(OutputStream one, OutputStream two) {
            targetStream = new TeeOutputStream(one, two);
        }

        @Override
        public void write(int arg) throws IOException {
            this.targetStream.write(arg);
        }

        @Override
        public void write(byte[] buf, int off, int len) throws IOException {
            this.targetStream.write(buf, off, len);
        }

        @Override
        public void flush() throws IOException {
            super.flush();
            this.targetStream.flush();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.targetStream.close();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            throw new UnsupportedOperationException("not support");
        }
    }
}
