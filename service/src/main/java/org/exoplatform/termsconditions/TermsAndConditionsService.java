package org.exoplatform.termsconditions;

import org.exoplatform.ecm.jcr.model.VersionNode;
import org.exoplatform.rest.response.TermsAndConditions;
import org.exoplatform.service.FunctionalConfigurationService;
import org.exoplatform.service.exception.FunctionalConfigurationRuntimeException;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.ext.app.SessionProviderService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.social.core.identity.model.Profile;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exoplatform.utils.NodeUtils;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.exoplatform.utils.IdentityUtils.findUserProfileByUserName;

public class TermsAndConditionsService {

    private static final String TERMS_AND_CONDITONS_PROPERTY = "acceptedTermsAndConditions";
    private static final Log LOGGER = ExoLogger.getLogger(TermsAndConditionsService.class);

    private final FunctionalConfigurationService functionalConfigurationService;

    private IdentityManager identityManager;


    public TermsAndConditionsService(FunctionalConfigurationService functionalConfigurationService, IdentityManager identityManager) {
        this.functionalConfigurationService = functionalConfigurationService;
        this.identityManager = identityManager;
    }

    boolean isTermsAndConditionsAcceptedBy(String userName){
        Profile socialProfile = findUserProfileByUserName(userName);

        try {
            String acceptedTermsVersion = socialProfile.getProperty(TERMS_AND_CONDITONS_PROPERTY) + "";
            String currentVersion = getCurrentTermsAndConditionsVersion();

            return currentVersion.equals(acceptedTermsVersion);
        } catch (Exception e) {
            LOGGER.warn("Terms and conditions still not accepted by user: " + userName, e);
            return false;
        }
    }

    private String getCurrentTermsAndConditionsVersion() {

        TermsAndConditions termsAndConditions = functionalConfigurationService.getTermsAndConditions();
        Node termsAndConditionsNode = NodeUtils.findCollaborationFile(termsAndConditions.getWebContentUrl());

        try {
            return termsAndConditionsNode.getBaseVersion().getName();
        } catch (Exception e) {
            throw new FunctionalConfigurationRuntimeException("invalid.termsAndConditions");
        }
    }

    public void accept(String userName){

        Profile userProfile = findUserProfileByUserName(userName);
        String currentVersionName = getCurrentTermsAndConditionsVersion();

        userProfile.setProperty(TERMS_AND_CONDITONS_PROPERTY, currentVersionName);

        try {
            identityManager.updateProfile(userProfile);
        } catch (Exception e) {
            LOGGER.error("Cannot update user profile to store terms and conditions acceptation", e);
        }
    }

    boolean isTermsAndConditionsActive()  {

        TermsAndConditions termsAndConditions = functionalConfigurationService.getTermsAndConditions();
        boolean isValidFile;
        try {
            isValidFile = Objects.nonNull(NodeUtils.findCollaborationFile(termsAndConditions.getWebContentUrl()));
        } catch (Exception e) {
            return false;
        }

        return termsAndConditions.isActive() && isValidFile;
    }
}
