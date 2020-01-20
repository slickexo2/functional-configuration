package org.exoplatform.utils;

import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.service.exception.FunctionalConfigurationRuntimeException;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.wcm.core.NodeLocation;

import javax.jcr.Node;
import java.util.Objects;

public class NodeUtils {

    private static final Log LOGGER = ExoLogger.getLogger(NodeUtils.class);

    private static final String COLLABORATION_FILE_PREFIX = "repository:collaboration:";

    public static Node findCollaborationFile(String webContentUrl) {

        Node nodeByExpression = NodeLocation.getNodeByExpression(COLLABORATION_FILE_PREFIX + webContentUrl);
        if (Objects.isNull(nodeByExpression)) {
            throw new FunctionalConfigurationRuntimeException("termsAndConditions.fileNotFound");
        }
        return nodeByExpression;
    }

    public static String getNodePath(String webContentUrl) {
        Node node = findCollaborationFile(webContentUrl);

        try {
            String encodedPath =  node.getPath();
            return "/" + CommonsUtils.getRestContextName() + "/private/jcr/" + "repository/collaboration" + encodedPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
