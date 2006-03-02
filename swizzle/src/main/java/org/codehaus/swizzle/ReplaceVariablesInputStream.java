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

}
