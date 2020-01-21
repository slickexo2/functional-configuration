package org.exoplatform.utils;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        return scheme + serverName + serverPort;
    }
}
