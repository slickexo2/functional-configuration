package org.exoplatform.rest.response;

public class HighlightSpaceConfiguration {

    private boolean highlight;
    private int order;

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}