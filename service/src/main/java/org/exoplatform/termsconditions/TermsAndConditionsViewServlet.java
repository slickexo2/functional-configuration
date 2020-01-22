package org.exoplatform.termsconditions;

import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.rest.response.TermsAndConditions;
import org.exoplatform.service.FunctionalConfigurationService;
import org.exoplatform.utils.NodeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.exoplatform.utils.ServletUtils.getBaseUrl;

public class TermsAndConditionsViewServlet extends HttpServlet {

    private static final String JSP_PATH = "/WEB-INF/jsp/charte-utilisation/charte.jsp";
    private static final String REQUEST_ATTRIBUTE_TERMS_URL = "chartUrl";

    private FunctionalConfigurationService getTermsAndConditionsService() {
        return CommonsUtils.getService(FunctionalConfigurationService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TermsAndConditions termsAndConditions = getTermsAndConditionsService().getTermsAndConditions();
        String nodePath = getBaseUrl(request) + NodeUtils.getNodePath(termsAndConditions.getWebContentUrl());

        request.setAttribute(REQUEST_ATTRIBUTE_TERMS_URL, nodePath);

        getServletContext().getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    @Override    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
