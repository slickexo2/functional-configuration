package org.exoplatform.rest.response;

import java.io.Serializable;
import java.util.List;

public class FunctionalConfiguration implements Serializable {

    private boolean hideDocumentActionActivities;

    private boolean hideComposerActivities;

    private String termsAndConditionsWebContentUrl;

    private List<SpaceConfiguration> spaceConfigurations;

    public FunctionalConfiguration() {}

    public boolean isHideDocumentActionActivities() {
        return hideDocumentActionActivities;
    }

    public void setHideDocumentActionActivities(boolean hideDocumentActionActivities) {
        this.hideDocumentActionActivities = hideDocumentActionActivities;
    }

    public boolean isHideComposerActivities() {
        return hideComposerActivities;
    }

    public void setHideComposerActivities(boolean hideComposerActivities) {
        this.hideComposerActivities = hideComposerActivities;
    }

    public List<SpaceConfiguration> getSpaceConfigurations() {
        return spaceConfigurations;
    }

    public void setSpaceConfigurations(List<SpaceConfiguration> spaceConfigurations) {
        this.spaceConfigurations = spaceConfigurations;
    }

    public String getTermsAndConditionsWebContentUrl() {
        return termsAndConditionsWebContentUrl;
    }

    public void setTermsAndConditionsWebContentUrl(String termsAndConditionsWebContentUrl) {
        this.termsAndConditionsWebContentUrl = termsAndConditionsWebContentUrl;
    }
}
