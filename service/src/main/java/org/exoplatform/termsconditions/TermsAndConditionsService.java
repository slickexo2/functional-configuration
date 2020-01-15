package org.exoplatform.termsconditions;

import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

public class TermsAndConditionsService {

    public final static String                 TERMS_AND_CONDITONS_PROPERTY      = "acceptedTermsAndConditions";

    private static Log log = ExoLogger.getLogger(TermsAndConditionsService.class);

    public boolean isAccepted(String userId){
//        PortalContainer container = PortalContainer.getInstance();
//        IdentityManager identityManager = (IdentityManager) container.getComponentInstanceOfType(IdentityManager.class);
//        try {
//            Identity currentIdentity = identityManager.getOrCreateIdentity(OrganizationIdentityProvider.NAME, userId, false);
//            Profile currentProfile = currentIdentity.getProfile();
//            Boolean obfChartChecked = Boolean.parseBoolean((String) currentProfile.getProperty(TERMS_AND_CONDITONS_PROPERTY));
//            return obfChartChecked == null ? false : obfChartChecked;
//        } catch (Exception e) {
//            log.warn("Terms and conditions still not accepted by OBF user: " + userId, e);
//            return false;
//        }
        return false;
    }

    public void accept(String userId){
//        PortalContainer container = PortalContainer.getInstance();
//        IdentityManager identityManager = (IdentityManager) container.getComponentInstanceOfType(IdentityManager.class);
//        try {
//            Identity currentIdentity = identityManager.getOrCreateIdentity(OrganizationIdentityProvider.NAME, userId, false);
//            Profile currentProfile = currentIdentity.getProfile();
//            currentProfile.setProperty(TERMS_AND_CONDITONS_PROPERTY, true);
//            identityManager.updateProfile(currentProfile);
//        } catch (Exception e) {
//            log.warn("Failed to set TERMS_AND_CONDITONS_PROPERTY for OBF user: " + userId, e);
//        }
    }
}
