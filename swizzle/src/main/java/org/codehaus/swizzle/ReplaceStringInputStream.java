/* =====================================================================
 *
 * Copyright (c) 2003 David Blevins.  All rights reserved.
 *
 * =====================================================================
 */
package org.codehaus.swizzle;

import java.io.InputStream;
import java.io.ByteArrayInputStream;

/**
 * @version $Revision$ $Date$
 */
public class ReplaceStringInputStream extends FixedTokenReplacementInputStream {

    public ReplaceStringInputStream(InputStream in, String token, String fixedValue) {
        super(in, token, new FixedStringValueTokenHandler(fixedValue));
    }

    public static class FixedStringValueTokenHandler implements StreamTokenHandler {

        private final String value;

        public FixedStringValueTokenHandler(String value) {
            this.value = value;
        }

        public InputStream processToken(String token) {
            return new ByteArrayInputStream(value.getBytes());
        }
    }

}
