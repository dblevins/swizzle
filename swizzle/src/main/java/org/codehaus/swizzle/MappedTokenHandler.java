/* =====================================================================
 *
 * Copyright (c) 2003 David Blevins.  All rights reserved.
 *
 * =====================================================================
 */
package org.codehaus.swizzle;

import java.util.Map;

/**
 * @version $Revision$ $Date$
 */
public class MappedTokenHandler extends StringTokenHandler {

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
