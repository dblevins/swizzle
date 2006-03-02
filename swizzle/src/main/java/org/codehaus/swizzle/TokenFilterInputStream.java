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
import java.io.IOException;
import java.io.InputStream;

public class TokenFilterInputStream extends FilterInputStream {

    private final ScanBuffer tokenBuffer;
    private final ScanBuffer valueBuffer;

    public TokenFilterInputStream(InputStream in, String key, String value) {
        super(in);
        tokenBuffer = new ScanBuffer(key);
        valueBuffer = new ScanBuffer(value);

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
            int i = valueBuffer.append(-1);
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
            int buffer = tokenBuffer.append(stream);

            if (tokenBuffer.match()) {
                tokenBuffer.flush();

                char[] value = valueBuffer.getScanString().toCharArray();
                for (int i = 0; i < value.length; i++) {
                    valueBuffer.append(value[i]);
                }

                strategy = flushingValue;
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
