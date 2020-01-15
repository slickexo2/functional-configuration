package org.exoplatform.termsconditions;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TermsAndConditionsActionServlet extends HttpServlet {

//    private static Log logger = ExoLogger.getLogger(TermsAndConditionsActionServlet.class);
    private TermsAndConditionsService termsAndConditionsService;

    public TermsAndConditionsService getTermsAndConditionsService()
    {
        if (this.termsAndConditionsService == null) {
            this.termsAndConditionsService = ((TermsAndConditionsService) PortalContainer.getInstance().getComponentInstanceOfType(TermsAndConditionsService.class));
        }
        return this.termsAndConditionsService;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        termsAndConditionsService.accept();

        String redirectURI = "/portal/";
        response.sendRedirect(redirectURI);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
