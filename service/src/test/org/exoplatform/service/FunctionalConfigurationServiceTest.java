package org.exoplatform.service;

import org.exoplatform.commons.api.settings.SettingService;
import org.exoplatform.commons.api.settings.SettingValue;
import org.exoplatform.commons.api.settings.data.Context;
import org.exoplatform.commons.api.settings.data.Scope;
import org.exoplatform.rest.response.FunctionalConfiguration;
import org.exoplatform.rest.response.HighlightSpaceConfiguration;
import org.exoplatform.rest.response.SpaceConfiguration;
import org.exoplatform.service.exception.FunctionalConfigurationRuntimeException;
import org.exoplatform.social.core.space.model.Space;
import org.exoplatform.social.core.space.spi.SpaceService;
import org.exoplatform.test.matchers.SettingValueMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.exoplatform.service.FunctionalConfigurationService.*;
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

        FunctionalConfiguration configuration = functionalConfigurationService.getConfiguration();

        assertThat(configuration.isHideComposerActivities(), equalTo(COMPOSER_ACTIVITY_HIDDEN));
        assertThat(configuration.isHideDocumentActionActivities(), equalTo(DOCUMENT_ACTION_ACTIVITIES_HIDDEN));
    }


    @Test
    public void should_update_space() {

        String SPACE_ID = "123";
        String SPACE_DISPLAY_NAME = "display name";
        String SPACE_PRETTY_NAME = "pretty name";
        String SPACE_DESCRIPTION = "a description";
        boolean SPACE_IS_HIGHLIGHT = true;
        int SPACE_HIGHLIGHT_ORDER = 5;

        SpaceConfiguration spaceConfiguration = new SpaceConfiguration();
        spaceConfiguration.setId(SPACE_ID);
        spaceConfiguration.setDisplayName(SPACE_DISPLAY_NAME);
        spaceConfiguration.setDescription(SPACE_DESCRIPTION);
        spaceConfiguration.setHideActivityComposer(true);
        HighlightSpaceConfiguration highlightConfiguration = new HighlightSpaceConfiguration();
        highlightConfiguration.setHighlight(SPACE_IS_HIGHLIGHT);
        highlightConfiguration.setOrder(SPACE_HIGHLIGHT_ORDER);
        spaceConfiguration.setHighlightConfiguration(highlightConfiguration);

        Space space = new Space();
        space.setId(SPACE_ID);
        space.setPrettyName(SPACE_PRETTY_NAME);

        given(spaceService.getSpaceById(SPACE_ID)).willReturn(space);
        given(settingService.get(Context.GLOBAL, Scope.GLOBAL, "highlightspaces")).willReturn((SettingValue) SettingValue.create(""));
        given(settingService.get(Context.GLOBAL, Scope.GLOBAL, SPACES_WITHOUT_ACTIVITY_COMPOSER)).willReturn((SettingValue) SettingValue.create(""));

        doNothing().doNothing().when(settingService).set(any(), any(), any(), any());
//        doNothing().when(settingService).set(any(), any(), any(), any());

        SpaceConfiguration updatedSpace = functionalConfigurationService.updateSpaceConfiguration(spaceConfiguration);
//
//        verify(settingService).get(Context.GLOBAL, Scope.GLOBAL, "highlightspaces");
//        verify(settingService).get(Context.GLOBAL, Scope.GLOBAL, SPACES_WITHOUT_ACTIVITY_COMPOSER);

//        verify(settingService, times(2)).set(any(), any(), any(), any());
        verify(settingService).set(eq(Context.GLOBAL), eq(Scope.GLOBAL), eq("highlightspaces"), argThat(new SettingValueMatcher(SettingValue.create(space.getPrettyName() + "#" + SPACE_HIGHLIGHT_ORDER))));
        verify(settingService).set(eq(Context.GLOBAL), eq(Scope.GLOBAL), eq(SPACES_WITHOUT_ACTIVITY_COMPOSER), argThat(new SettingValueMatcher(SettingValue.create(space.getPrettyName()))));

        assertThat(updatedSpace.getId(), equalTo(SPACE_ID));
        assertThat(updatedSpace.getDisplayName(), equalTo(SPACE_DISPLAY_NAME));
        assertThat(updatedSpace.getDescription(), equalTo(SPACE_DESCRIPTION));
        assertThat(updatedSpace.getHighlightConfiguration().isHighlight(), equalTo(SPACE_IS_HIGHLIGHT));
        assertThat(updatedSpace.getHighlightConfiguration().getOrder(), equalTo(SPACE_HIGHLIGHT_ORDER));
    }


//    @Test
//    public void should_delete_highlight_configuration_if_exist() {
//
//        String SPACE_ID = "123";
//        String SPACE_DISPLAY_NAME = "display name";
//        String SPACE_PRETTY_NAME = "pretty name";
//        String SPACE_DESCRIPTION = "a description";
//        boolean SPACE_IS_HIGHLIGHT = false;
//        int SPACE_HIGHLIGHT_ORDER = 5;
//
//        SpaceConfiguration spaceConfiguration = new SpaceConfiguration();
//        spaceConfiguration.setId(SPACE_ID);
//        spaceConfiguration.setDisplayName(SPACE_DISPLAY_NAME);
//        spaceConfiguration.setDescription(SPACE_DESCRIPTION);
//        spaceConfiguration.setHideActivityComposer(true);
//        HighlightSpaceConfiguration highlightConfiguration = new HighlightSpaceConfiguration();
//        highlightConfiguration.setHighlight(SPACE_IS_HIGHLIGHT);
//        highlightConfiguration.setOrder(SPACE_HIGHLIGHT_ORDER);
//        spaceConfiguration.setHighlightConfiguration(highlightConfiguration);
//
//        Space space = new Space();
//        space.setId(SPACE_ID);
//        space.setPrettyName(SPACE_PRETTY_NAME);
//
//        given(spaceService.getSpaceById(SPACE_ID)).willReturn(space);
//        given(settingService.get(Context.GLOBAL, Scope.GLOBAL, "highlightspaces")).willReturn((SettingValue) SettingValue.create(SPACE_PRETTY_NAME));
//        given(settingService.get(Context.GLOBAL, Scope.GLOBAL, SPACES_WITHOUT_ACTIVITY_COMPOSER)).willReturn((SettingValue) SettingValue.create(""));
//
//        doNothing().when(settingService).set(any(), any(), any(), any());
//
//        SpaceConfiguration updatedSpace = functionalConfigurationService.updateSpaceConfiguration(spaceConfiguration);
//
//        verify(settingService).get(Context.GLOBAL, Scope.GLOBAL, "highlightspaces", SettingValue.create(SPACE_PRETTY_NAME + "#" + SPACE_HIGHLIGHT_ORDER + ";"));
//        verify(settingService).get(Context.GLOBAL, Scope.GLOBAL, SPACES_WITHOUT_ACTIVITY_COMPOSER, SettingValue.create(""));
//
//        verify(settingService).set(Context.GLOBAL, Scope.GLOBAL, "highlightspaces", SettingValue.create(SPACE_PRETTY_NAME + "#" + SPACE_HIGHLIGHT_ORDER + ";"));
//        verify(settingService).set(Context.GLOBAL, Scope.GLOBAL, SPACES_WITHOUT_ACTIVITY_COMPOSER, SettingValue.create(""));
//
//        assertThat(updatedSpace.getId(), equalTo(SPACE_ID));
//        assertThat(updatedSpace.getDisplayName(), equalTo(SPACE_DISPLAY_NAME));
//        assertThat(updatedSpace.getDescription(), equalTo(SPACE_DESCRIPTION));
//        assertThat(updatedSpace.getHighlightConfiguration().isHighlight(), equalTo(SPACE_IS_HIGHLIGHT));
//        assertThat(updatedSpace.getHighlightConfiguration().getOrder(), equalTo(SPACE_HIGHLIGHT_ORDER));
//    }


    @Test(expected = FunctionalConfigurationRuntimeException.class)
    public void should_send_error_when_space_not_found() {

        given(spaceService.getSpaceById(any())).willReturn(null);

        functionalConfigurationService.updateSpaceConfiguration(new SpaceConfiguration());
    }
}
