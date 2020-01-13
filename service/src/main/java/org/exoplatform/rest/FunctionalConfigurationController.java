package org.exoplatform.rest;

import static org.exoplatform.rest.utils.RestUtils.isValidBooleanParameter;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.exoplatform.rest.response.SpaceConfiguration;
import org.exoplatform.service.FunctionalConfigurationService;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;

@Path("/functional-configuration")
public class FunctionalConfigurationController implements ResourceContainer {

    private static final String DOCUMENT_ACTIVITY_ENDPOINT = "/document-activity";
    private static final String CONFIGURATION_ENDPOINT = "/configuration";
    private static final String COMPOSER_ACTIVITY_ENDPOINT = "/composer-activity";
    private static final String UPDATE_SPACE_CONFIGURATION_ENDPOINT = "/configuration/space";

    private FunctionalConfigurationService functionalConfigurationService;

    private static final Log LOGGER = ExoLogger.getLogger(FunctionalConfigurationService.class);


    public FunctionalConfigurationController(FunctionalConfigurationService functionalConfigurationService){
        this.functionalConfigurationService = functionalConfigurationService;
    }

    @GET
    @Path(CONFIGURATION_ENDPOINT)
    @RolesAllowed("administrators")
    public Response getConfiguration(){

        return Response
                .ok(functionalConfigurationService.getConfiguration(), MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Path(DOCUMENT_ACTIVITY_ENDPOINT)
    @RolesAllowed("administrators")
    public Response updateDocumentActionActivitiesVisibility(@QueryParam("hidden") String hidden) {

        if (!isValidBooleanParameter(hidden)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        LOGGER.debug("Update document action actitivites visibility, set value="+hidden);
        functionalConfigurationService.configureDocumentActionActivities(hidden);

        return Response.ok().build();
    }

    @PUT
    @Path(COMPOSER_ACTIVITY_ENDPOINT)
    @RolesAllowed("administrators")
    public Response updateComposerActivity(@QueryParam("hidden") String hidden) {

        if (!isValidBooleanParameter(hidden)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        functionalConfigurationService.configureActivityComposer(hidden);
        return Response.ok().build();
    }


    @PUT
    @Path(UPDATE_SPACE_CONFIGURATION_ENDPOINT)
    @RolesAllowed("administrators")
    public Response updateSpaceConfiguration(SpaceConfiguration space) {

        SpaceConfiguration spaceConfiguration = functionalConfigurationService.updateSpaceConfiguration(space);

        return Response
                .ok(spaceConfiguration, MediaType.APPLICATION_JSON)
                .build();
    }
}
