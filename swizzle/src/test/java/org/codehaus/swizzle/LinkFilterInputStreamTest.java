package org.codehaus.swizzle;
/**
 * @version $Revision$ $Date$
 */

import junit.framework.TestCase;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LinkFilterInputStreamTest extends TestCase {

    public void testLinkFilterInputStream1() throws Exception {
        URL url = new URL("http://swizzle.codehaus.org/stuff/");

        String original = "<tr><td align=\"left\">\n<a href=\"/section1/orange.html\"><img src=\"/images/icon.gif\"\n border=\"0\"> Link Text</a></td>";
        String exptected = "<tr><td align=\"left\">\n<a href=\"http://swizzle.codehaus.org/section1/orange.html\"><img src=\"http://swizzle.codehaus.org/images/icon.gif\"\n border=\"0\"> Link Text</a></td>";

        String actual = resolveURLs(original, url);

        assertEquals(exptected, actual);
    }

    public void testLinkFilterInputStream2() throws Exception {
        URL url = new URL("http://swizzle.codehaus.org/stuff/");

        String original = "<td align=\"left\"><a href=\"apple.html\"><img src=\"images/picture.gif\" border=\"0\"> Link Text</a></td>";
        String exptected = "<td align=\"left\"><a href=\"http://swizzle.codehaus.org/stuff/apple.html\"><img src=\"http://swizzle.codehaus.org/stuff/images/picture.gif\" border=\"0\"> Link Text</a></td>";

        String actual = resolveURLs(original, url);

        assertEquals(exptected, actual);
    }

    public void testLinkFilterInputStream3() throws Exception {
        URL url = new URL("http://swizzle.codehaus.org/stuff/");

        String original = "<td align=\"left\">\n<a href=\"subsection/mango.html\"><img src=\"http://superimages.org/images/mango.jpg\" border=\"0\"> Link Text</a>\n</td>";
        String exptected = "<td align=\"left\">\n<a href=\"http://swizzle.codehaus.org/stuff/subsection/mango.html\"><img src=\"http://superimages.org/images/mango.jpg\" border=\"0\"> Link Text</a>\n</td>";

        String actual = resolveURLs(original, url);

        assertEquals(exptected, actual);
    }


    private String resolveURLs(String original, URL url) throws IOException {
        InputStream in = TestUtil.stringToStream(original);
        in = new LinkFilterInputStream(in, "<A HREF=", ">", url);
        in = new LinkFilterInputStream(in, "SRC=\"", "\"", url);

        return TestUtil.streamToString(in);
    }

}