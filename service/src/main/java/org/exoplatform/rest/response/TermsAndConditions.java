package org.exoplatform.rest.request;

public class TermsAndConditions {

    private boolean active;
    private String webContentUrl;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getWebContentUrl() {
        return webContentUrl;
    }

    public void setWebContentUrl(String webContentUrl) {
        this.webContentUrl = webContentUrl;
    }
}
