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
import java.net.URL;

public class LinkFilterInputStream extends FilterInputStream {

    private final URL url;
    private final ScanBuffer beginBuffer;
    private final ScanBuffer endBuffer;
    private ScanBuffer urlBuffer;

    public LinkFilterInputStream(InputStream in, String begin, String end, URL url) {
        super(in);
        this.url = url;

        beginBuffer = new ScanBuffer(begin);
        endBuffer = new ScanBuffer(end);
        urlBuffer = new ScanBuffer(1000);

        strategy = lookingForURL;
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

    private final StreamReadingStrategy readingURL = new StreamReadingStrategy() {
        public int _read() throws IOException {
            endBuffer.flush();
            StringBuffer link = new StringBuffer();

            while (true) {
                int stream = superRead();
                int buffer = endBuffer.append(stream);
                char s = (char) stream, b = (char) buffer;

                if (buffer == -1 && stream != -1) {
                    // Have we just started?
                    continue;
                } else if (buffer == -1 && stream == -1) {
                    endBuffer.resetPosition();
                    strategy = flushingURL;
                    return strategy._read();
                }
                link.append((char) buffer);
                if (endBuffer.match()) {
                    break;
                }
            }

            URL newURL =
                    new URL(url, link.toString().replaceAll("[ \"\']", ""));

            link.setLength(0);
            link.append(beginBuffer.getScanString().toLowerCase());
            if (!beginBuffer.getScanString().endsWith("\"")) {
                link.append('\"');
            }

            link.append(newURL.toExternalForm());

            if (!endBuffer.getScanString().startsWith("\"")) {
                link.append('\"');
            }
            link.append(endBuffer.getScanString());

            urlBuffer.flush();
            for (int i = 0; i < link.length(); i++) {
                urlBuffer.append(link.charAt(i));
            }
            urlBuffer.resetPosition();

            strategy = flushingURL;
            return strategy._read();
        }
    };

    private final StreamReadingStrategy flushingURL = new StreamReadingStrategy() {
        public int _read() throws IOException {
            int i = urlBuffer.append(-1);
            if (i == -1) {
                strategy = lookingForURL;
                i = strategy._read();
            }
            return i;
        }
    };

    private final StreamReadingStrategy lookingForURL = new StreamReadingStrategy() {
        public int _read() throws IOException {
            int stream = superRead();
            int buffer = beginBuffer.append(stream);

            if (beginBuffer.match()) {
                beginBuffer.flush();

                strategy = readingURL;
                //return reader._read();
                return buffer;
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
