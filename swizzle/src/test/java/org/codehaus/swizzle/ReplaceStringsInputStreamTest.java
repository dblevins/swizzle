/* =====================================================================
 *
 * Copyright (c) 2003 David Blevins.  All rights reserved.
 *
 * =====================================================================
 */
package org.codehaus.swizzle;

import junit.framework.TestCase;

import java.io.InputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class ReplaceStringsInputStreamTest extends TestCase {

    public void testTokenFilterInputStream() throws Exception {
        String original = "";
        String expected = "";

        original = "some FOO text";
        expected = "some apple text";
        swizzleAndAssert(original, expected);

        original = "FOO text";
        expected = "apple text";
        swizzleAndAssert(original, expected);

        original = ".FOO text";
        expected = ".apple text";
        swizzleAndAssert(original, expected);

        original = "some text FOO";
        expected = "some text apple";
        swizzleAndAssert(original, expected);

        original = "some text FOO.";
        expected = "some text apple.";
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
        swizzleAndAssert(original, expected);

        original = "BARFOOFOOBAR text";
        expected = "orangeappleappleorange text";
        swizzleAndAssert(original, expected);

        original = "BAR some FOO BAR test FOO";
        expected = "orange some apple orange test apple";
        swizzleAndAssert(original, expected);

        original = "package org.apache.maven.archetype;";
        expected = "package ${package};";

        InputStream in = TestUtil.stringToStream(original);
        in = new ReplaceStringInputStream(in, "org.apache.maven.archetype", "${package}");
        String actual = TestUtil.streamToString(in);

        assertEquals(expected, actual);
    }

    private void swizzleAndAssert(String original, String expected) throws IOException {
        InputStream in = TestUtil.stringToStream(original);

        Map strings = new HashMap();
        strings.put("FOO", "apple");
        strings.put("BAR", "orange");
        in = new ReplaceStringsInputStream(in, strings);

        String actual = TestUtil.streamToString(in);

        assertEquals(expected, actual);
    }

}
