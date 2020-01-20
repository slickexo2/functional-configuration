package org.exoplatform.termsconditions;

import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.web.filter.Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TermsAndConditionsFilter implements Filter {

    private static final String ATISNETWORK_EXTENSION_WAR = "/functional-configuration";
    private static final String CHARTER_SERVLET_URL = "/terms-and-conditions";
    private TermsAndConditionsService termsAndConditionsService;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String remoteUser = httpServletRequest.getRemoteUser();
        if (remoteUser != null) {
            String requestUri = httpServletRequest.getRequestURI();

            TermsAndConditionsService termsAndConditionsService = getTermsAndConditionsService();

//            if (termsAndConditionsService.hasToValidateTermsAndConditions(remoteUser)) {
//                ServletContext atisnetworkExtensionContext = httpServletRequest.getSession().getServletContext().getContext(ATISNETWORK_EXTENSION_WAR);
//                atisnetworkExtensionContext.getRequestDispatcher(CHARTER_SERVLET_URL).forward(httpServletRequest, response);
//                return;
//            }
        }
        chain.doFilter(request, response);
    }

    private TermsAndConditionsService getTermsAndConditionsService() {
        if (termsAndConditionsService == null) {
            termsAndConditionsService = CommonsUtils.getService(TermsAndConditionsService.class);
        }
        return termsAndConditionsService;
    }
}
