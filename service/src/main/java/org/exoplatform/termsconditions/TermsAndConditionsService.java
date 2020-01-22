package org.exoplatform.termsconditions;

import org.exoplatform.rest.response.TermsAndConditions;
import org.exoplatform.service.FunctionalConfigurationService;
import org.exoplatform.service.exception.FunctionalConfigurationRuntimeException;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.social.core.identity.model.Profile;
import org.exoplatform.utils.NodeUtils;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.version.Version;

import static org.exoplatform.utils.IdentityUtils.findUserProfileByUserName;

public class TermsAndConditionsService {

    private static final String TERMS_AND_CONDITONS_PROPERTY = "acceptedTermsAndConditions";
    private static final Log LOGGER = ExoLogger.getLogger(TermsAndConditionsService.class);

    private final FunctionalConfigurationService functionalConfigurationService;

    public TermsAndConditionsService(FunctionalConfigurationService functionalConfigurationService) {
        this.functionalConfigurationService = functionalConfigurationService;
    }

    public boolean isTermsAndConditionsAcceptedBy(String userName){
        Profile socialProfile = findUserProfileByUserName(userName);

        try {
            String acceptedTermsVersion = socialProfile.getProperty(TERMS_AND_CONDITONS_PROPERTY) + "";
            String currentVersionUid = getCurrentTermsAndConditionsVersionUUID();

            return currentVersionUid.equals(acceptedTermsVersion);
        } catch (Exception e) {
            LOGGER.warn("Terms and conditions still not accepted by user: " + userName, e);
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
            LOGGER.error("Could not get actual terms and conditions version");
            throw new FunctionalConfigurationRuntimeException("invalid.termsAndConditions");
        }
    }

    public void accept(String userName){

        Profile userProfile = findUserProfileByUserName(userName);
        String currentTermsAndConditionsVersionUUID = getCurrentTermsAndConditionsVersionUUID();

        userProfile.setProperty(TERMS_AND_CONDITONS_PROPERTY, currentTermsAndConditionsVersionUUID);
    }

    public boolean isTermsAndConditionsActive()  {
        return functionalConfigurationService.isTermsAndConditionsActive();
    }

}
