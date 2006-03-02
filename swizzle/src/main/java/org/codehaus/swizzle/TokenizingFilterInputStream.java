/**
 *
 * Copyright 2003 David Blevins
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.codehaus.swizzle;

import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.IOException;

public class TokenizingFilterInputStream extends FilterInputStream {

    private final ScanBuffer beginBuffer;
    private final ScanBuffer endBuffer;
    private ScanBuffer valueBuffer;
    private final TokenizedStreamHandler tokenHandler;

    public TokenizingFilterInputStream(InputStream in, String begin, String end, TokenizedStreamHandler tokenHandler) {
        super(in);
        this.tokenHandler = tokenHandler;

        beginBuffer = new ScanBuffer(begin);
        endBuffer = new ScanBuffer(end);
        valueBuffer = new ScanBuffer(1000);

        strategy = lookingForToken;
    }

    private TokenizingFilterInputStream.StreamReadingStrategy strategy;

    public int read() throws IOException {
        return strategy._read();
    }

    // reading url (looking for end)
    // flushing url
    // regular read (looking for begin)
    interface StreamReadingStrategy {
        int _read() throws IOException;
    }

    private final TokenizingFilterInputStream.StreamReadingStrategy readingToken = new TokenizingFilterInputStream.StreamReadingStrategy() {
        public int _read() throws IOException {
            endBuffer.flush();
            StringBuffer token = new StringBuffer();

            while (true) {
                int stream = superRead();
                int buffer = endBuffer.append(stream);
                char s = (char) stream, b = (char) buffer;

                if (buffer == -1 && stream != -1) {
                    // Have we just started?
                    continue;
                } else if (buffer == -1 && stream == -1) {
                    endBuffer.resetPosition();
                    strategy = flushingValue;
                    return strategy._read();
                }

                token.append((char) buffer);

                if (endBuffer.match()) {
                    break;
                }
            }

            String value = tokenHandler.tokenFound(token.toString());

            valueBuffer = new ScanBuffer(value.length());
            char[] chars = value.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                valueBuffer.append(chars[i]);
            }

            strategy = flushingValue;
            return strategy._read();
        }
    };

    private final TokenizingFilterInputStream.StreamReadingStrategy flushingValue = new TokenizingFilterInputStream.StreamReadingStrategy() {
        public int _read() throws IOException {
            int i = valueBuffer.append(-1);
            if (i == -1) {
                strategy = lookingForToken;
                i = strategy._read();
            }
            return i;
        }
    };

    private final TokenizingFilterInputStream.StreamReadingStrategy lookingForToken = new TokenizingFilterInputStream.StreamReadingStrategy() {
        public int _read() throws IOException {
            int stream = superRead();
            int buffer = beginBuffer.append(stream);

            if (beginBuffer.match()) {
                beginBuffer.flush();

                strategy = readingToken;
                //return reader._read();
                return (buffer == -1 && stream != -1) ? read() : buffer;
            }

            // Have we just started?

            // The buffer starts out in -1 state. If the
            // data coming from the stream is valid, we
            // need to just keep reading till the buffer
            // gives us good data.
            return (buffer == -1 && stream != -1) ? _read() : buffer;
        }
    };

    private int superRead() throws IOException {
        return super.read();
    }
}
