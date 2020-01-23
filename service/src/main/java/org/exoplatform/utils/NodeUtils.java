package org.exoplatform.utils;

import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.service.exception.FunctionalConfigurationRuntimeException;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.wcm.core.NodeLocation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Objects;

public class NodeUtils {

    private static final Log LOGGER = ExoLogger.getLogger(NodeUtils.class);

        private static final String COLLABORATION_FILE_PREFIX = "repository:collaboration:";

    public static Node findCollaborationFile(String webContentUrl) {
        Node nodeByExpression = NodeLocation.getNodeByLocation(new NodeLocation("repository", "collaboration", webContentUrl, null,true));
        if (Objects.isNull(nodeByExpression)) {
            LOGGER.error("File not found with URL : {}", webContentUrl);
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
            LOGGER.error("File not found with URL : {}", webContentUrl);
            throw new FunctionalConfigurationRuntimeException("termsAndConditions.fileNotFound");
        }
    }

    public static String getWebContentContentFromUrl(String webContentUrl) {
        try {
            return NodeUtils.findCollaborationFile(webContentUrl).getNode("default.html/jcr:content").getProperty("jcr:data").getString();
        } catch (RepositoryException e) {
            LOGGER.error("Cannot find WEBCONTENT content from URL : {}", webContentUrl);
            throw new FunctionalConfigurationRuntimeException(e.getMessage());
        }
    }
}
