/* =====================================================================
 *
 * Copyright (c) 2003 David Blevins.  All rights reserved.
 *
 * =====================================================================
 */
package org.codehaus.swizzle.confluence;

import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

/**
 * @version $Revision$ $Date$
 */
public class Confluence {
    private String loginToken;
    private String space;
    private XmlRpcClient rpcClient;


//    public Confluence() {
//        try {
//            // Initialise RPC Client
//            rpcClient = new XmlRpcClient("http://docs.codehaus.org/rpc/xmlrpc");
//
//            // Login and retrieve logon token
//            loginToken = (String) rpcClient.execute("confluence1.login", args("admin","admin"));
//
//            space = (String) rpcClient.execute("confluence1.getSpace", args(loginToken, "OPENEJB"));
//        } catch (XmlRpcException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public String getRenderedPage(String pageId) throws Exception {
//        rpcClient.execute("confluence1.renderContent", args(space,"spaceKey"));
//    }
//
//    private List args(String arg0, String arg1, String arg2, String arg3, String arg4){
//        String[] args = {arg0, arg1, arg2, arg3, arg4};
//        return Arrays.asList(args);
//    }
//    private List args(String arg0, String arg1, String arg2, String arg3){
//        String[] args = {arg0, arg1, arg2, arg3};
//        return Arrays.asList(args);
//    }
//    private List args(String arg0, String arg1, String arg2){
//        String[] args = {arg0, arg1, arg2};
//        return Arrays.asList(args);
//    }
//    private List args(String arg0, String arg1){
//        String[] args = {arg0, arg1};
//        return Arrays.asList(args);
//    }
//    private List args(String arg0){
//        String[] args = {arg0};
//        return Arrays.asList(args);
//    }
}
