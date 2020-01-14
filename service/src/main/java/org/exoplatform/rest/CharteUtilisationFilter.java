package org.exoplatform.rest;

import org.exoplatform.commons.utils.PropertyManager;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.web.filter.Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CharteUtilisationFilter implements Filter {
    private static Log logger = ExoLogger.getLogger(CharteUtilisationFilter.class);
//    private CharteUtilisationService charteUtilisationService;
    private static final String CHARTE_SERVLET_URL = "/charte-utilisation";
    private static String REST_URI;

    public CharteUtilisationFilter()
    {
        REST_URI = ExoContainerContext.getCurrentContainer().getContext().getRestContextName();
    }

//    public CharteUtilisationService getCharteUtilisationService() {
//        if (this.charteUtilisationService == null) {
//            this.charteUtilisationService = ((CharteUtilisationService) PortalContainer.getInstance().getComponentInstanceOfType(CharteUtilisationService.class));
//        }
//        return this.charteUtilisationService;
//    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        ServletContext gendarmerieContext = httpServletRequest.getSession().getServletContext().getContext("/functional-configuration-webapp");

//        String loginRequestUri = httpServletRequest.getContextPath() + "/login";
//        String dologinRequestUri = httpServletRequest.getContextPath() + "/dologin";
        String requestUri = httpServletRequest.getRequestURI();
//        boolean isLoginUri = (requestUri.contains(loginRequestUri)) || (requestUri.contains(dologinRequestUri));
//        boolean isRestUri = requestUri.contains(REST_URI);
//        boolean isDevMod = PropertyManager.isDevelopping();

//        if (httpServletRequest.getRemoteUser()==null) {
//            gendarmerieContext.getRequestDispatcher("/WEB-INF/jsp/gendarmerie/connexion.jsp").forward(httpServletRequest, httpServletResponse);
//            return;
//        }


//        if (httpServletRequest.getRemoteUser()!=null) {
////            boolean charteUtilisationChecked = getCharteUtilisationService().isCharteUtilisationChecked(httpServletRequest.getRemoteUser());
////            if ((!isLoginUri) && (!isRestUri) && (!charteUtilisationChecked)) {
////                String reqUri = httpServletRequest.getRequestURI().toString();
////                String queryString = httpServletRequest.getQueryString();
////                if (queryString != null) {
////                    reqUri = reqUri + "?" + queryString;
////                }
////                String uriTarget = CHARTE_SERVLET_URL + "?initialURI=" + reqUri;
//                gendarmerieContext.getRequestDispatcher(CHARTE_SERVLET_URL).forward(httpServletRequest, httpServletResponse);
//                return;
////            }
//        }

        chain.doFilter(request, response);
    }
}
