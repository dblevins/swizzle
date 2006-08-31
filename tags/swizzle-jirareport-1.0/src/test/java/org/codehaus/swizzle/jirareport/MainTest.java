/**
 *
 * Copyright 2006 David Blevins
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
package org.codehaus.swizzle.jirareport;

import junit.framework.TestCase;

import java.net.URL;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * @version $Revision$ $Date$
 */
public class MainTest extends TestCase {

    public void testRssTemplate() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource("jirarss.vm");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);

        Main.generate(resource.getFile(), printStream);

        printStream.close();

        resource = classLoader.getResource("jirarss-report.txt");
        String expected = streamToString(resource.openStream());

        String actual = new String(baos.toByteArray());

        assertEquals(expected,actual);
    }

    public void testXmlRpcTemplate() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = this.getClass().getClassLoader().getResource("jiraxmlrpc.vm");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);

        Main.generate(resource.getFile(), printStream);

        printStream.close();

        resource = classLoader.getResource("jiraxmlrpc-report.txt");
        String expected = streamToString(resource.openStream());

        String actual = new String(baos.toByteArray());

        assertEquals(expected,actual);
    }

    public static String streamToString(InputStream in) throws IOException {
        StringBuffer text = new StringBuffer();
        try {
            int b;
            while ((b = in.read()) != -1) {
                text.append((char) b);
            }
        } finally {
            in.close();
        }
        return text.toString();
    }

}