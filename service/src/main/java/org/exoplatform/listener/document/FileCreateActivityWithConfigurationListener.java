package org.exoplatform.listener.document;

import javax.jcr.Node;

import org.exoplatform.service.FunctionalConfigurationService;
import org.exoplatform.services.listener.Event;
import org.exoplatform.wcm.ext.component.activity.listener.FileCreateActivityListener;

public class FileCreateActivityWithConfigurationListener extends FileCreateActivityListener {

  private FunctionalConfigurationService functionalConfigurationService;

  public FileCreateActivityWithConfigurationListener(FunctionalConfigurationService functionalConfigurationService) {
    this.functionalConfigurationService = functionalConfigurationService;
  }

  @Override
  public void onEvent(Event<Object, Node> event) throws Exception {

      if(!functionalConfigurationService.isDocumentActionActivityHidden()) {
        super.onEvent(event);
    }
  }
}