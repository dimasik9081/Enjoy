package ru.enjoyflowers.shop.server;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * BackupInputStream считывает данные из другого InputStream (origin)
 * и при этом копию записывает в OutputStream (backup)
 * То есть является сплитером входного потока origin на 2 копии.
 * Первую отдает вызвавшему его методу так, как если бы тот напрямую читал origin,
 * а вторую пишет в выходной поток backup.
 * Чтение origin при этом делается только 1 раз и никаких лишних копирований блоков
 * памяти не происходит.
 * Created by dm on 22.05.16.
 */
public class BackupInputStream extends FilterInputStream {

    private static final int LOCAL_BUFFER_SIZE = 1024;

    private OutputStream backup;
    private byte[] localBuffer;

    // этот конструктор прячем, так как использоваться будет другой (с потоками origin и backup)
    private BackupInputStream(InputStream origin) {
        super(origin);
    }

    public BackupInputStream(InputStream origin, OutputStream backup) {
        this(origin);
        this.backup = backup;
    }

    @Override
    public int read() throws IOException {
        int _byte = super.read();
        if (_byte != -1)
            backup.write(_byte);
        return _byte;
    }

    @Override
    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        int count = super.read(buffer, byteOffset, byteCount);
        backup.write(buffer, byteOffset, byteCount);
        return count;
    }

    @Override
    public void close() throws IOException {
        super.close();
        backup.close();
    }

    @Override
    public boolean markSupported() {
        // Не усложняем себе жизнь, говорим что так не умеем
        return false;
    }

    @Override
    public long skip(long byteCount) throws IOException {
        // хоть и вызвавший нас метод решил проигнорировать byteCount байт потока,
        // но это не повод оставить в backup белое пятно. Поэтому прочитаем
        // эти byteCount байт и просто запишем их в backup.
        if (localBuffer == null)
            localBuffer = new byte[LOCAL_BUFFER_SIZE];
        long decCnt = byteCount;
        while (byteCount > 0) {
            int cnt = super.read(localBuffer, 0, (decCnt > LOCAL_BUFFER_SIZE) ? LOCAL_BUFFER_SIZE : (int) decCnt);
            if (cnt == -1)
                return byteCount - decCnt;
            backup.write(localBuffer, 0, cnt);
            decCnt -= cnt;
        }
        return byteCount;
    }
}
