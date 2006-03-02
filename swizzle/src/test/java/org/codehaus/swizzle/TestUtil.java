/* =====================================================================
 *
 * Copyright (c) 2003 David Blevins.  All rights reserved.
 *
 * =====================================================================
 */
package org.codehaus.swizzle;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

/**
 * @version $Revision$ $Date$
 */
public class TestUtil {
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

    public static ByteArrayInputStream stringToStream(String original) {
        return new ByteArrayInputStream(original.getBytes());
    }
}
