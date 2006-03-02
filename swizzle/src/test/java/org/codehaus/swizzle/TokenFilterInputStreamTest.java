package org.codehaus.swizzle;
/**
 * @version $Revision$ $Date$
 */

import junit.framework.TestCase;

import java.io.IOException;
import java.io.InputStream;

public class TokenFilterInputStreamTest extends TestCase {

    public void testTokenFilterInputStream() throws Exception {
        String original = "";
        String expected = "";

        original = "some FOO text";
        expected = "some apple text";
        swizzleAndAssert(original, expected);

        original = "FOO text";
        expected = "apple text";
        swizzleAndAssert(original, expected);

        original = "some text FOO";
        expected = "some text apple";
        swizzleAndAssert(original, expected);

        original = "FOO";
        expected = "apple";
        swizzleAndAssert(original, expected);

        original = "some FOOO text";
        expected = "some appleO text";
        swizzleAndAssert(original, expected);

        original = "some text";
        expected = "some text";
        swizzleAndAssert(original, expected);

        original = "";
        expected = "";
        swizzleAndAssert(original, expected);

        original = "some FO text";
        expected = "some FO text";
        swizzleAndAssert(original, expected);

        original = "FO some text";
        expected = "FO some text";
        swizzleAndAssert(original, expected);

        original = "some text FO";
        expected = "some text FO";
        swizzleAndAssert(original, expected);

        original = "some FOO FOO text";
        expected = "some apple apple text";
        swizzleAndAssert(original, expected);

        original = "FOO text FOO";
        expected = "apple text apple";
        swizzleAndAssert(original, expected);

        original = "FOOFOO";
        expected = "appleapple";
        swizzleAndAssert(original, expected);

        original = "BAsome FOOO text";
        expected = "BAsome appleO text";
        swizzleAndAssert2(original, expected);

        original = "BARFOOFOOBAR text";
        expected = "orangeappleappleorange text";
        swizzleAndAssert2(original, expected);

        original = "BAR some FOO BAR test FOO";
        expected = "orange some apple orange test apple";
        swizzleAndAssert2(original, expected);
    }

    private void swizzleAndAssert(String original, String expected) throws IOException {
        InputStream in = TestUtil.stringToStream(original);

        in = new TokenFilterInputStream(in, "FOO", "apple");

        String actual = TestUtil.streamToString(in);

        assertEquals(expected, actual);
    }

    private void swizzleAndAssert2(String original, String expected) throws IOException {
        InputStream in = TestUtil.stringToStream(original);

        in = new TokenFilterInputStream(in, "FOO", "apple");
        in = new TokenFilterInputStream(in, "BAR", "orange");

        String actual = TestUtil.streamToString(in);

        assertEquals(expected, actual);
    }

}