package org.exoplatform.termsconditions;

import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.rest.response.TermsAndConditions;
import org.exoplatform.service.FunctionalConfigurationService;
import org.exoplatform.service.exception.FunctionalConfigurationRuntimeException;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.social.core.identity.model.Profile;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exoplatform.utils.NodeUtils;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.version.Version;

public class TermsAndConditionsService {

    private static final String                 TERMS_AND_CONDITONS_PROPERTY      = "acceptedTermsAndConditions";

    private static Log log = ExoLogger.getLogger(TermsAndConditionsService.class);
    private final FunctionalConfigurationService functionalConfigurationService;


    public TermsAndConditionsService(FunctionalConfigurationService functionalConfigurationService) {
        this.functionalConfigurationService = functionalConfigurationService;
    }

    private boolean hasAccepted(String userName){
        Profile socialProfile = findUserProfileByUserName(userName);

        try {
            String acceptedTermsVersion = "" + socialProfile.getProperty(TERMS_AND_CONDITONS_PROPERTY);
            String currentVersionUid = getCurrentTermsAndConditionsVersionUUID();

            return currentVersionUid.equals(acceptedTermsVersion);
        } catch (Exception e) {
            log.warn("Terms and conditions still not accepted by OBF user: " + userName, e);
            return false;
        }
    }

    private String getCurrentTermsAndConditionsVersionUUID() {

        TermsAndConditions termsAndConditions = functionalConfigurationService.getTermsAndConditions();
        Node termsAndConditionsNode = NodeUtils.findCollaborationFile(termsAndConditions.getWebContentUrl());

        try {
            Version baseVersion = termsAndConditionsNode.getBaseVersion();
            return baseVersion.getUUID();
        } catch (RepositoryException e) {
            // TODO log
            throw new FunctionalConfigurationRuntimeException("tototototo");
        }

    }

    private Profile findUserProfileByUserName(String userName) {
        return CommonsUtils.getService(IdentityManager.class).getOrCreateIdentity("organization", userName).getProfile();
    }

    public void accept(String userName){
        hasAccepted(userName);
        String currentTermsAndConditionsVersionUUID = getCurrentTermsAndConditionsVersionUUID();
        Profile userProfileByUserName = findUserProfileByUserName(userName);

        userProfileByUserName.setProperty(TERMS_AND_CONDITONS_PROPERTY, currentTermsAndConditionsVersionUUID);
    }

    boolean hasToValidateTermsAndConditions(String remoteUser) {
         return !(hasAccepted(remoteUser) && functionalConfigurationService.isTermsAndConditionsActive());
    }
}
