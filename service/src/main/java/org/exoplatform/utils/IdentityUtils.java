package org.exoplatform.utils;

import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.social.core.identity.model.Profile;
import org.exoplatform.social.core.identity.provider.OrganizationIdentityProvider;
import org.exoplatform.social.core.manager.IdentityManager;

public class IdentityUtils {

    public static Profile findUserProfileByUserName(String userName) {
        return CommonsUtils.getService(IdentityManager.class)
                    .getOrCreateIdentity(OrganizationIdentityProvider.NAME, userName, true).getProfile();
    }
}
