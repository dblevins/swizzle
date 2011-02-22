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

/**
 * @version $Revision$ $Date$
 */
public class CsvUtil {
    public static String escape(Object object) {
        return (object == null) ? "" : escape(object.toString());
    }

    public static String escape(String text) {
        text = text + "";

        StringBuffer escaped = new StringBuffer(text.length());
        escaped.append('"');
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '"') {
                // escaped.append("\\\"");
            } else {
                escaped.append(ch);
            }
        }
        escaped.append('"');
        return escaped.toString();
    }
}
