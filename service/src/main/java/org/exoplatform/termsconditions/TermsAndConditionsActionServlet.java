package org.exoplatform.termsconditions;

import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.rest.response.TermsAndConditions;
import org.exoplatform.service.FunctionalConfigurationService;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.utils.NodeUtils;

import javax.jcr.Node;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TermsAndConditionsActionServlet extends HttpServlet {

    public TermsAndConditionsService getTermsAndConditionsService() {
            return CommonsUtils.getService(TermsAndConditionsService.class);
    }

    public FunctionalConfigurationService getFunctionalConfigurationService() {
        return CommonsUtils.getService(FunctionalConfigurationService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        TermsAndConditions termsAndConditions = getFunctionalConfigurationService().getTermsAndConditions();

//        Node collaborationFile = NodeUtils.findCollaborationFile(termsAndConditions.getWebContentUrl());

        getTermsAndConditionsService().accept(request.getRemoteUser());

        String redirectURI = "/portal/";
        response.sendRedirect(redirectURI);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
