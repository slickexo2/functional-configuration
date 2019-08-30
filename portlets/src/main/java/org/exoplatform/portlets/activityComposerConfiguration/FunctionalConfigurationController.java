package org.exoplatform.portlets.activityComposerConfiguration;

import java.util.List;

import javax.inject.Inject;

import juzu.Path;
import juzu.Resource;
import juzu.Response;
import juzu.View;
import juzu.plugin.ajax.Ajax;
import juzu.template.Template;

import org.json.JSONArray;
import org.json.JSONObject;

import org.exoplatform.service.FunctionalConfigurationService;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.social.core.space.model.Space;


public class FunctionalConfigurationController {

    private static Log log = ExoLogger.getLogger(FunctionalConfigurationController.class);
    private static final String NAME = "name";
    private static final String LABEL = "label";
    private static final String SPACES_WITH_ACTIVITY_COMPOSER = "spacesWithActivityComposer";
    
    @Inject
    @Path("index.gtmpl")
    Template index;
    
    @Inject
    FunctionalConfigurationService functionalConfigurationService;
    
    @View
    public Response.Content index() throws Exception {
      return index.ok();
    }

    @Ajax
    @Resource
    public Response getSpacesWithoutActivityComposer() {
      JSONObject jsonGlobal = new JSONObject();
      try {
        List<Space> listSpacesWithoutActivityComposer = functionalConfigurationService.getListSpacesWithoutActivityComposer();
        JSONArray jsonArray = new JSONArray();
        for(Space spaceWithoutActivityComposer : listSpacesWithoutActivityComposer) {
          JSONObject json = new JSONObject();
          json.put(NAME, spaceWithoutActivityComposer.getPrettyName());
          json.put(LABEL, spaceWithoutActivityComposer.getDisplayName());
          jsonArray.put(json);
        }
        jsonGlobal.put(FunctionalConfigurationService.SPACES_WITHOUT_ACTIVITY_COMPOSER, jsonArray);
      } catch (Exception e) {
        log.error("Cannot get spaces without activity composer", e);
      }
      return Response.ok(jsonGlobal.toString());
    }

    @Ajax
    @Resource
    public Response getSpacesWithActivityComposer() {
      JSONObject jsonGlobal = new JSONObject();
      try {
        List<Space> listSpacesWithActivityComposer = functionalConfigurationService.getListSpacesWithActivityComposer();
        JSONArray jsonArray = new JSONArray();
        for(Space spaceWithActivityComposer : listSpacesWithActivityComposer) {
          JSONObject json = new JSONObject();
          json.put(NAME, spaceWithActivityComposer.getPrettyName());
          json.put(LABEL, spaceWithActivityComposer.getDisplayName());
          jsonArray.put(json);
        }
        jsonGlobal.put(SPACES_WITH_ACTIVITY_COMPOSER, jsonArray);
      } catch (Exception e) {
        log.error("Cannot get spaces with activity composer", e);
      }
      return Response.ok(jsonGlobal.toString());
    }


    @Ajax
    @Resource
    public Response getUserActivityComposerState() {
      JSONObject jsonGlobal = new JSONObject();
      try {
        String userActivityComposerState = functionalConfigurationService.getUserActivityComposerState();
        jsonGlobal.put(FunctionalConfigurationService.HIDE_USER_ACTIVITY_COMPOSER, userActivityComposerState);
      } catch (Exception e) {
        log.error("Cannot get user activity composer state", e);
      }
      return Response.ok(jsonGlobal.toString());
    }

    @Ajax
    @Resource
    public Response hideSpaceActivityComposer(String spaces) {
      functionalConfigurationService.hideSpaceActivityComposer(spaces);
      return Response.ok();
    }

    @Ajax
    @Resource
    public Response showSpaceActivityComposer(String spaces) {
      functionalConfigurationService.showSpaceActivityComposer(spaces);
      return Response.ok();
    }

    @Ajax
    @Resource
    public Response configureUserActivityComposer(String hideUserActivityComposer) {
      functionalConfigurationService.configureActivityComposer(hideUserActivityComposer);
      return Response.ok();
    }
}
