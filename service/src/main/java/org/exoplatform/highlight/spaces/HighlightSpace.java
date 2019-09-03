package org.exoplatform.highlight.spaces;

import org.exoplatform.social.core.space.model.Space;

public class HighlightSpace {

    private Space space;
    private Integer order;

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
}
