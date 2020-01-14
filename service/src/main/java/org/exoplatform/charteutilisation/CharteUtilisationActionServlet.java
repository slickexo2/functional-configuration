package org.exoplatform.charteutilisation;

/**
 * Created by gregorysebert on 16/03/15.
 */

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CharteUtilisationActionServlet extends HttpServlet
{
    private static Log logger = ExoLogger.getLogger(CharteUtilisationActionServlet.class);
//    private CharteUtilisationService charteUtilisationService;
//
//    public CharteUtilisationService getCharteUtilisationService()
//    {
//        if (this.charteUtilisationService == null) {
//            this.charteUtilisationService = ((CharteUtilisationService)PortalContainer.getInstance().getComponentInstanceOfType(CharteUtilisationService.class));
//        }
//        return this.charteUtilisationService;
//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Boolean checkCharte = Boolean.valueOf(false);
        try {
            checkCharte = Boolean.valueOf(request.getParameter("checkcharte"));
        }
        catch (Exception e) {
            logger.error("Charte d'Utilisation: impossible to get parameter checkcharte", e);
        }
//
//        if (checkCharte.booleanValue()) {
//            getCharteUtilisationService().checkCharteUtilisation(request.getRemoteUser());
//        }

        String redirectURI = "/portal/";
        response.sendRedirect(redirectURI);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
