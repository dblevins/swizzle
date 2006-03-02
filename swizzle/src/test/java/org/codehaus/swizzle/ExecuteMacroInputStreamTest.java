package org.codehaus.swizzle;
/**
 * @version $Revision$ $Date$
 */

import junit.framework.*;
import org.codehaus.swizzle.ExecuteMacroInputStream;

import java.util.HashMap;
import java.io.InputStream;

public class ExecuteMacroInputStreamTest extends TestCase {

    public void testExecuteMacroInputStream() throws Exception {
        HashMap macros = new HashMap();
        macros.put("wget", new ExecuteMacroInputStream.IncludeUrlMacro());
        macros.put("file", new ExecuteMacroInputStream.IncludeFileMacro());

        String original = "Some template {date:tz=PST}.  With some web content {wget:url=file:target/test-classes/fuzzbucket/widget.txt} and \n{file:path=target/test-classes/fuzzbucket/DoHickey.java}";

        InputStream in = TestUtil.stringToStream(original);
        in = new ExecuteMacroInputStream(in, "{","}", macros);

        String actual = TestUtil.streamToString(in);
        String expected = "Some template {date:tz=PST}.  With some web content This content is from the widget.txt file and \n" +
                "public class DoHickey {\n" +
                "    public String whatIsIt(){\n" +
                "        return \"i don't know\";\n" +
                "    }\n" +
                "}";

        assertEquals(expected, actual);
    }
}