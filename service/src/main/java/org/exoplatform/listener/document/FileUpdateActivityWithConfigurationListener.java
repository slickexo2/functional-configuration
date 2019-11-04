package org.exoplatform.listener.document;

import org.apache.commons.chain.Context;

import org.exoplatform.service.FunctionalConfigurationService;
import org.exoplatform.services.listener.Event;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.wcm.ext.component.activity.listener.FileUpdateActivityListener;

public class FileUpdateActivityWithConfigurationListener extends FileUpdateActivityListener {

  private FunctionalConfigurationService functionalConfigurationService;
  private static final Log LOGGER = ExoLogger.getLogger(FunctionalConfigurationService.class);

  public FileUpdateActivityWithConfigurationListener(FunctionalConfigurationService functionalConfigurationService) {
    this.functionalConfigurationService = functionalConfigurationService;
  }

  @Override
  public void onEvent(Event<Context, String> event) throws Exception {
    LOGGER.debug("FunctionalConfigurationService.isDocumentActionActivityHidden() : "+functionalConfigurationService.isDocumentActionActivityHidden());

    if (!functionalConfigurationService.isDocumentActionActivityHidden()) {
      super.onEvent(event);
    }
  }
}
