package org.exoplatform.utils;

import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.service.exception.FunctionalConfigurationRuntimeException;
import org.exoplatform.services.wcm.core.NodeLocation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Objects;

public class NodeUtils {

    private final static String NODE_REPOSITORY = "repository";
    private final static String NODE_WORKSPACE = "collaboration";

    public static Node findCollaborationFile(String webContentUrl) {
        Node nodeByExpression = NodeLocation.getNodeByLocation(new NodeLocation(NODE_REPOSITORY, NODE_WORKSPACE, webContentUrl, null,true));
        if (Objects.isNull(nodeByExpression)) {
            throw new FunctionalConfigurationRuntimeException("functionalConfiguration.termsAndConditions.fileNotFound");
        }
        return nodeByExpression;
    }

    public static String getNodePath(String webContentUrl) {
        Node node = findCollaborationFile(webContentUrl);

        try {
            String encodedPath =  node.getPath();
            return "/" + CommonsUtils.getRestContextName() + "/private/jcr/repository/collaboration" + encodedPath;
        } catch (Exception e) {
            throw new FunctionalConfigurationRuntimeException("termsAndConditions.fileNotFound");
        }
    }

    public static String getWebContentContentFromUrl(String webContentUrl) {
        try {
            return NodeUtils.findCollaborationFile(webContentUrl).getNode("default.html/jcr:content").getProperty("jcr:data").getString();
        } catch (RepositoryException e) {
            throw new FunctionalConfigurationRuntimeException(e.getMessage());
        }
    }
}
