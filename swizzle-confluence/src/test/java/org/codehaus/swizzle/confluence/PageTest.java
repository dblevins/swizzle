/**
 *
f * Copyright 2006 David Blevins
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
package org.codehaus.swizzle.confluence;

import java.util.List;

import junit.framework.TestCase;

/**
 * @version $Revision$ $Date$
 */
public class PageTest extends SwizzleConfluenceTestCase {

    public void test() throws Exception {
        Confluence confluence = getConfluence();
        Page page = getTestPage();

        assertNotNull("page", page);
        assertEquals("Page.getId()", "62967", page.getId());
        assertEquals("Page.getTitle()", "UnitTest Page", page.getTitle());
        assertEquals("Page.getSpace()", "SWIZZLE", page.getSpace());

        //String actual = confluence.renderContent(page.getSpace(), page.getId(), "");

        //assertTrue("html", actual.indexOf("<b>hello</b> <em>world</em>") != -1);

    }        

    public void _testGetActiveUsers() throws Exception {
        Confluence confluence = getConfluence();

        List activeUsers = confluence.getActiveUsers(true);
        for (int i = 0; i < activeUsers.size() && i < 10; i++) {
            String username = (String) activeUsers.get(i);

            assertTrue("hasUser", confluence.hasUser(username));

            User user = confluence.getUser(username);
            assertNotNull("user", user);
            assertEquals("User.getName()", username, user.getName());

            UserInformation userInfo = confluence.getUserInformation(username);
            assertNotNull("userInformation", userInfo);
            assertEquals("UserInfo.getUsername()", username, userInfo.getUsername());

            List userGroups = confluence.getUserGroups(username);
            for (int j = 0; j < userGroups.size(); j++) {
                Object group = userGroups.get(j);
                assertNotNull("group not null", group);
                assertTrue("group is string", group instanceof String);
            }
        }
    }

}
