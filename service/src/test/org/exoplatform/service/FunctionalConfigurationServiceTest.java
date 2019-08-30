package org.exoplatform.service;

import org.exoplatform.commons.api.settings.SettingService;
import org.exoplatform.commons.api.settings.SettingValue;
import org.exoplatform.commons.api.settings.data.Context;
import org.exoplatform.commons.api.settings.data.Scope;
import org.exoplatform.rest.response.FunctionalConfigurationResponse;
import org.exoplatform.social.core.space.spi.SpaceService;
import org.exoplatform.test.matchers.SettingValueMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.exoplatform.service.FunctionalConfigurationService.HIDE_DOCUMENT_ACTION_ACTIVITIES;
import static org.exoplatform.service.FunctionalConfigurationService.HIDE_USER_ACTIVITY_COMPOSER;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FunctionalConfigurationServiceTest {

    @Mock
    private SettingService settingService;

    @Mock
    private SpaceService spaceService;

    private FunctionalConfigurationService functionalConfigurationService;

    @Before
    public void setUp() {
        functionalConfigurationService = new FunctionalConfigurationService(settingService, spaceService);
    }

    @Test
    public void should_hide_document_action_activity() {
        String hide = "true";

        doNothing().when(settingService).set(any(), any(), any(), any());

        functionalConfigurationService.configureActivityComposer(hide);

        verify(settingService).set(eq(Context.GLOBAL), eq(Scope.GLOBAL), eq(HIDE_DOCUMENT_ACTION_ACTIVITIES), argThat(new SettingValueMatcher(SettingValue.create(hide))));
    }

    @Test
    public void should_show_document_action_activity() {
        String hide = "false";

        doNothing().when(settingService).set(any(), any(), any(), any());

        functionalConfigurationService.configureActivityComposer(hide);

        verify(settingService).set(eq(Context.GLOBAL), eq(Scope.GLOBAL), eq(HIDE_DOCUMENT_ACTION_ACTIVITIES), argThat(new SettingValueMatcher(SettingValue.create(hide))));
    }

    @Test
    public void should_hide_activity_composer() {
        String hide = "true";

        doNothing().when(settingService).set(any(), any(), any(), any());

        functionalConfigurationService.configureActivityComposer(hide);

        verify(settingService).set(eq(Context.GLOBAL), eq(Scope.GLOBAL), eq(HIDE_USER_ACTIVITY_COMPOSER), argThat(new SettingValueMatcher(SettingValue.create(hide))));
    }

    @Test
    public void should_show_activity_composer() {
        String hide = "false";

        doNothing().when(settingService).set(any(), any(), any(), any());

        functionalConfigurationService.configureActivityComposer(hide);

        verify(settingService).set(eq(Context.GLOBAL), eq(Scope.GLOBAL), eq(HIDE_USER_ACTIVITY_COMPOSER), argThat(new SettingValueMatcher(SettingValue.create(hide))));
    }

    @Test
    public void should_get_configuration() {

        Boolean COMPOSER_ACTIVITY_HIDDEN = true;
        Boolean DOCUMENT_ACTION_ACTIVITIES_HIDDEN = false;

        given(settingService.get(Context.GLOBAL, Scope.GLOBAL, HIDE_DOCUMENT_ACTION_ACTIVITIES)).willReturn((SettingValue) SettingValue.create(DOCUMENT_ACTION_ACTIVITIES_HIDDEN));
        given(settingService.get(Context.GLOBAL, Scope.GLOBAL, HIDE_USER_ACTIVITY_COMPOSER)).willReturn((SettingValue) SettingValue.create(COMPOSER_ACTIVITY_HIDDEN));

        FunctionalConfigurationResponse configuration = functionalConfigurationService.getConfiguration();

        assertThat(configuration.isHideComposerActivities(), equalTo(COMPOSER_ACTIVITY_HIDDEN));
        assertThat(configuration.isHideDocumentActionActivities(), equalTo(DOCUMENT_ACTION_ACTIVITIES_HIDDEN));
    }
}
