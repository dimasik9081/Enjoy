package ru.enjoyflowers.shop.server;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * InputStream - прокси. Все вызовы
 * Created by dm on 22.05.16.
 */
public class BackupInputStream extends FilterInputStream {

    private BackupInputStream(InputStream in) {
        super(in);
    }
    public BackupInputStream(InputStream in, OutputStream backup){
        this(in);
    }

    @Override
    public int read() throws IOException {
        return super.read();
    }

    @Override
    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        return super.read(buffer, byteOffset, byteCount);
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    @Override
    public synchronized void reset() throws IOException {
        super.reset();
    }

    @Override
    public long skip(long byteCount) throws IOException {
        return super.skip(byteCount);
    }
}
