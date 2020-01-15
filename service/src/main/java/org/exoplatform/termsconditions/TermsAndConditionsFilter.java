package org.exoplatform.termsconditions;

import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.commons.utils.PropertyManager;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.web.filter.Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TermsAndConditionsFilter implements Filter {

    private static final String ATISNETWORK_EXTENSION_WAR = "/functional-configuration";
    private static final String CHARTER_SERVLET_URL = "/terms-and-conditions";
//    private static final String DO_LOGIN = "/doLogin";
//    private static final String LOGIN = "/login";
    private TermsAndConditionsService termsAndConditionsService;

//    private static String restUri = ExoContainerContext.getCurrentContainer().getContext().getRestContextName();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String remoteUser = httpServletRequest.getRemoteUser();
        if (remoteUser != null) {
            String requestUri = httpServletRequest.getRequestURI();

            if (!getTermsAndConditionsService().isAccepted(remoteUser)) {
                ServletContext atisnetworkExtensionContext = httpServletRequest.getSession().getServletContext().getContext(ATISNETWORK_EXTENSION_WAR);
                String queryString = httpServletRequest.getQueryString();
                if (queryString != null) {
                    requestUri = requestUri + "?" + queryString;
                }
//                atisnetworkExtensionContext.getRequestDispatcher(CHARTER_SERVLET_URL).forward(httpServletRequest, response);
//                return;
            }
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
