package org.exoplatform.highlight.spaces;

import org.exoplatform.social.core.space.model.Space;

public class HighlightSpace {

    private Space space;
    private Integer order;
    private String groupIdentifier;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public String getGroupIdentifier() {
        return groupIdentifier;
    }

    public void setGroupIdentifier(String groupIdentifier) {
        this.groupIdentifier = groupIdentifier;
    }
}
