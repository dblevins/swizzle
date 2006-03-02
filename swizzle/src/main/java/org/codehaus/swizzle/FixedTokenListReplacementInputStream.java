/* =====================================================================
 *
 * Copyright (c) 2003 David Blevins.  All rights reserved.
 *
 * =====================================================================
 */
package org.codehaus.swizzle;

import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;

public class FixedTokenListReplacementInputStream extends FilterInputStream {

    private final StreamTokenHandler handler;
    private InputStream value;
    private final ScanBuffer[] tokenBuffers;
    private final ScanBuffer largestBuffer;

    public FixedTokenListReplacementInputStream(InputStream in, List tokens, StreamTokenHandler handler) {
        this(in, tokens, handler, true);
    }

    public FixedTokenListReplacementInputStream(InputStream in, List tokens, StreamTokenHandler handler, boolean caseSensitive) {
        super(in);
        ScanBuffer largestBuffer = new ScanBuffer("", caseSensitive);
        tokenBuffers = new ScanBuffer[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            String token = (String) tokens.get(i);
            ScanBuffer buffer = new ScanBuffer(token, caseSensitive);
            tokenBuffers[i] = buffer;
            largestBuffer = (buffer.size() > largestBuffer.size())? buffer: largestBuffer;
        }
        this.largestBuffer = largestBuffer;
        this.handler = handler;
        strategy = lookingForToken;
    }

    private StreamReadingStrategy strategy;

    public int read() throws IOException {
        return strategy._read();
    }

    // reading url (looking for end)
    // flushing url
    // regular read (looking for begin)
    interface StreamReadingStrategy {
        int _read() throws IOException;
    }

    private final StreamReadingStrategy flushingValue = new StreamReadingStrategy() {
        public int _read() throws IOException {
            int i = value.read();
            if (i == -1) {
                strategy = lookingForToken;
                i = strategy._read();
            }
            return i;
        }
    };

    private final StreamReadingStrategy lookingForToken = new StreamReadingStrategy() {
        public int _read() throws IOException {
            int stream = superRead();

            int buffer = -1;
            for (int i = 0; i < tokenBuffers.length; i++) {
                ScanBuffer tokenBuffer = tokenBuffers[i];
                buffer = tokenBuffer.append(stream);

                if (tokenBuffer.match()) {
                    clearAllBuffers();

                    String token = tokenBuffer.getScanString();
                    value = handler.processToken(token);
                    strategy = flushingValue;

                    return (buffer == -1 && stream != -1) ? read() : buffer;
                }
            }

            // Have we just started?

            // The buffer starts out in -1 state. If the
            // data coming from the stream is valid, we
            // need to just keep reading till the buffer
            // gives us good data.
            return (buffer == -1 && largestBuffer.hasData()) ? _read() : buffer;
        }
    };

    private void clearAllBuffers() {
        for (int i = 0; i < tokenBuffers.length; i++) {
            ScanBuffer tokenBuffer = tokenBuffers[i];
            tokenBuffer.flush();
        }
    }

    private int superRead() throws IOException {
        return super.read();
    }
}
