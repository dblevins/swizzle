package org.codehaus.swizzle.jira;
/**
 * @version $Revision$ $Date$
 */

import junit.framework.*;
import org.codehaus.swizzle.jira.JiraRss;

import java.net.URL;
import java.util.Hashtable;

public class JiraRssTest extends TestCase {

    public void testJiraRss() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource("jirarss.xml");
        JiraRss jiraRss = new JiraRss(resource);
        Issue issue = jiraRss.getIssue("SWIZZLE-1");
        assertEquals("Issue.getCreated()", "Fri Aug 04 20:05:13 PDT 2006", issue.getCreated().toString());
        assertEquals("Issue.getSummary()", "Unit Test Summary", issue.getSummary());
        assertEquals("Issue.getType()", 2, issue.getType().getId());
        assertEquals("Issue.getEnvironment()", "Unit Test Environment", issue.getEnvironment());
        assertEquals("Issue.getStatus()", 6, issue.getStatus().getId());
        assertEquals("Issue.getUpdated()", "Fri Aug 04 21:33:48 PDT 2006", issue.getUpdated().toString());
        assertEquals("Issue.getId()", 40099, issue.getId());
        assertEquals("Issue.getKey()", "SWIZZLE-1", issue.getKey());
        assertEquals("Issue.getDescription()", "Unit Test Description", issue.getDescription());
        assertEquals("Issue.getDuedate()", "Sun Aug 06 00:00:00 PDT 2006", issue.getDuedate().toString());
        assertEquals("Issue.getReporter()", "dblevins", issue.getReporter().getName());
        assertEquals("Issue.getProject()", "SWIZZLE", issue.getProject().getKey());
        assertEquals("Issue.getResolution()", 1, issue.getResolution().getId());
        assertEquals("Issue.getVotes()", 1, issue.getVotes());
        assertEquals("Issue.getAssignee()", "dblevins", issue.getAssignee().getName());
        assertEquals("Issue.getPriority()", 1, issue.getPriority().getId());
        assertEquals("Issue.getLink()", "http://jira.codehaus.org/browse/SWIZZLE-1", issue.getLink());

        assertEquals("Issue.getFixVersions().size()", 1, issue.getFixVersions().size());
        assertTrue("FixVersion instance of Version", issue.getFixVersions().get(0) instanceof Version);
        Version version = (Version) issue.getFixVersions().get(0);
        assertEquals("Version.getName()", "Test Version", version.getName());

        assertEquals("Issue.getComponents().size()", 1, issue.getComponents().size());
        assertTrue("Issue.getComponents instance of Component", issue.getComponents().get(0) instanceof Component);
        Component component = (Component) issue.getComponents().get(0);
        assertEquals("Component.getName()", "jira client", component.getName());


        Hashtable data = issue.toHashtable();

        assertEquals("issue.Created", "Fri, 4 Aug 2006 20:05:13 -0700 (PDT)", data.get("created"));
        assertEquals("issue.Summary", "Unit Test Summary", data.get("summary"));
        assertEquals("issue.Type", "2", data.get("type"));
        assertEquals("issue.Environment", "Unit Test Environment", data.get("environment"));
        assertEquals("issue.Status", "6", data.get("status"));
        assertEquals("issue.Updated", "Fri, 4 Aug 2006 21:33:48 -0700 (PDT)", data.get("updated"));
        assertEquals("issue.Id", "40099", data.get("id"));
        assertEquals("issue.Key", "SWIZZLE-1", data.get("key"));
        assertEquals("issue.Description", "Unit Test Description", data.get("description"));
        assertEquals("issue.Duedate", "Sun, 6 Aug 2006 00:00:00 -0700 (PDT)", data.get("duedate"));
        assertEquals("issue.Reporter", "dblevins", data.get("reporter"));
        assertEquals("issue.Project", "SWIZZLE", data.get("project"));
        assertEquals("issue.Resolution", "1", data.get("resolution"));
        assertEquals("issue.Votes", "1", data.get("votes"));
        assertEquals("issue.Assignee", "dblevins", data.get("assignee"));
        assertEquals("issue.Priority", "1", data.get("priority"));
        assertEquals("issue.Link", null, data.get("link"));
    }

}