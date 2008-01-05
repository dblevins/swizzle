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
package org.codehaus.swizzle.depreport;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

/**
 * @version $Revision$ $Date$
 */
public class PlainTextFormatter extends Formatter {

    public PlainTextFormatter(PrintWriter out) {
        super(out);
    }

    public void format(Dependency root) throws IOException {
        print(root.getChildern(), "");
    }

    private void print(List childern, String s) {

        for (int i = 0; i < childern.size(); i++) {
            Dependency dep = (Dependency) childern.get(i);
            out.print(s);
            out.print(dep.getArtifact().getFile().getName());
            if (dep.getArtifact().isOptional()) {
                out.print(" (optional)");
            }
            out.println();
            print(dep.getChildern(), s + " ");
        }
    }
}
