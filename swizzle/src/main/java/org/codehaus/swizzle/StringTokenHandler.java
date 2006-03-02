/* =====================================================================
 *
 * Copyright (c) 2003 David Blevins.  All rights reserved.
 *
 * =====================================================================
 */
package org.codehaus.swizzle;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @version $Revision$ $Date$
 */
public abstract class StringTokenHandler implements StreamTokenHandler {

    public abstract String handleToken(String token) throws IOException;

    public final InputStream processToken(String token) throws IOException {
        String result = handleToken(token);
        result = (result != null)? result: "null";
        return new ByteArrayInputStream(result.getBytes());
    }

}
