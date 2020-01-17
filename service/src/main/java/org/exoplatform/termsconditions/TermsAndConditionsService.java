package org.exoplatform.termsconditions;

import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.rest.response.TermsAndConditions;
import org.exoplatform.service.FunctionalConfigurationService;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.social.core.identity.model.Profile;
import org.exoplatform.social.core.manager.IdentityManager;

import javax.jcr.Node;
import javax.jcr.version.Version;

public class TermsAndConditionsService {

    private static final String                 TERMS_AND_CONDITONS_PROPERTY      = "acceptedTermsAndConditions";

    private static Log log = ExoLogger.getLogger(TermsAndConditionsService.class);
    private final FunctionalConfigurationService functionalConfigurationService;


    public TermsAndConditionsService(FunctionalConfigurationService functionalConfigurationService) {
        this.functionalConfigurationService = functionalConfigurationService;
    }

    private boolean isAcceptedBy(String userName){
        Profile socialProfile = findUserProfileByUserName(userName);

        try {
            String acceptedTermsVersion = "" + socialProfile.getProperty(TERMS_AND_CONDITONS_PROPERTY);

            TermsAndConditions termsAndConditions = functionalConfigurationService.getTermsAndConditions();
            Node termsAndConditionsNode = functionalConfigurationService.findNodeFileByAbsoluteName(termsAndConditions.getWebContentUrl());
            Version baseVersion = termsAndConditionsNode.getBaseVersion(); // TODO regarder

            return false;
        } catch (Exception e) {
            log.warn("Terms and conditions still not accepted by OBF user: " + userName, e);
            return false;
        }
    }

    private Profile findUserProfileByUserName(String userName) {
        return CommonsUtils.getService(IdentityManager.class).getOrCreateIdentity("organization", userName).getProfile();
    }

    public void accept(String userId){

        TermsAndConditions termsAndConditions = functionalConfigurationService.getTermsAndConditions();
        Node termsAndConditionsNode = functionalConfigurationService.findNodeFileByAbsoluteName(termsAndConditions.getWebContentUrl());
//        Version baseVersion = termsAndConditionsNode.getBaseVersion();

        // versionning



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

    private boolean isActive() {
        return functionalConfigurationService.isTermsAndConditionsActive();
    }

    boolean isActiveFor(String remoteUser) {
         return !isAcceptedBy(remoteUser)
                 && isActive();
    }
}
