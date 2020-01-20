package org.exoplatform.termsconditions;

import com.google.javascript.jscomp.NodeUtil;
import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.rest.response.TermsAndConditions;
import org.exoplatform.service.FunctionalConfigurationService;
import org.exoplatform.utils.NodeUtils;

import javax.jcr.Node;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TermsAndConditionsViewServlet extends HttpServlet {

    public FunctionalConfigurationService getTermsAndConditionsService() {
        return CommonsUtils.getService(FunctionalConfigurationService.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TermsAndConditions termsAndConditions = getTermsAndConditionsService().getTermsAndConditions();
//        Node collaborationFile = NodeUtils.findCollaborationFile();
        String nodePath = getBaseUrl(request) + NodeUtils.getNodePath(termsAndConditions.getWebContentUrl());

        request.setAttribute("chartUrl", nodePath);
//        request.getSession().setAttribute("chartUrl", nodePath);

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/charte-utilisation/charte.jsp").forward(request, response);
    }

    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
//        String contextPath = request.getContextPath();
        return scheme + serverName + serverPort;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
