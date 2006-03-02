/* =====================================================================
 *
 * Copyright (c) 2003 David Blevins.  All rights reserved.
 *
 * =====================================================================
 */
package org.codehaus.swizzle;

import java.io.InputStream;
import java.util.Map;

/**
 * @version $Revision$ $Date$
 */
public class ReplaceVariablesInputStream extends DelimitedTokenReplacementInputStream {

    public ReplaceVariablesInputStream(InputStream in, String begin, String end, Map variables) {
        super(in, begin, end, new MappedTokenHandler(variables));
    }

    public static class MappedTokenHandler extends StringTokenHandler {

        private final Map entries;

        public MappedTokenHandler(Map entries) {
            this.entries = entries;
        }

        public String handleToken(String token) {
            Object object = entries.get(token);
            if (object != null){
                return object.toString();
            }
            return token;
        }
    }

}
