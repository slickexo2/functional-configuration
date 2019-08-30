package org.exoplatform.rest.response;

public class SpaceConfiguration {

    private String id;
    private String displayName;
    private String description;
    private boolean hideActivityComposer;

    private HighlightSpaceConfiguration highlightConfiguration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHideActivityComposer() {
        return hideActivityComposer;
    }

    public void setHideActivityComposer(boolean hideActivityComposer) {
        this.hideActivityComposer = hideActivityComposer;
    }

    public HighlightSpaceConfiguration getHighlightConfiguration() {
        return highlightConfiguration;
    }

    public void setHighlightConfiguration(HighlightSpaceConfiguration highlightConfiguration) {
        this.highlightConfiguration = highlightConfiguration;
    }
}