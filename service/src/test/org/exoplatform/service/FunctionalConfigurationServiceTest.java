package org.exoplatform.service;

import static org.exoplatform.service.FunctionalConfigurationService.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
    public void should_hide_user_activity_composer() {
        String hide = "true";

        doNothing().when(settingService).set(any(), any(), any(), any());

        functionalConfigurationService.configureActivityComposer(hide);

        verify(settingService).set(eq(Context.GLOBAL), eq(Scope.GLOBAL), eq(HIDE_USER_ACTIVITY_COMPOSER), argThat(new SettingValueMatcher(SettingValue.create(hide))));
    }

    @Test
    public void should_show_document_action_activities() {
        String hide = "false";

        doNothing().when(settingService).set(any(), any(), any(), any());

        functionalConfigurationService.configureDocumentActionActivities(hide);

        verify(settingService).set(eq(Context.GLOBAL), eq(Scope.GLOBAL), eq(HIDE_DOCUMENT_ACTION_ACTIVITIES), argThat(new SettingValueMatcher(SettingValue.create(hide))));
    }

    @Test
    public void should_get_configuration() {

        FunctionalConfigurationWithSpacesLoadingService functionalConfigurationService = new FunctionalConfigurationWithSpacesLoadingService(settingService, spaceService);

        Boolean COMPOSER_ACTIVITY_HIDDEN = true;
        Boolean DOCUMENT_ACTION_ACTIVITIES_HIDDEN = false;

        given(settingService.get(Context.GLOBAL, Scope.GLOBAL, HIDE_DOCUMENT_ACTION_ACTIVITIES)).willReturn((SettingValue) SettingValue.create(DOCUMENT_ACTION_ACTIVITIES_HIDDEN));
        given(settingService.get(Context.GLOBAL, Scope.GLOBAL, HIDE_USER_ACTIVITY_COMPOSER)).willReturn((SettingValue) SettingValue.create(COMPOSER_ACTIVITY_HIDDEN));
        given(settingService.get(Context.GLOBAL, Scope.GLOBAL, SPACES_WITHOUT_ACTIVITY_COMPOSER)).willReturn((SettingValue) SettingValue.create("id_1;id_2"));
        given(settingService.get(Context.GLOBAL, Scope.GLOBAL, HIGHLIGHT_SPACES)).willReturn((SettingValue) SettingValue.create("id_1#2;id_2#1;id_4#5"));

        FunctionalConfiguration configuration = functionalConfigurationService.getConfiguration();

        assertThat(configuration.isHideComposerActivities(), equalTo(COMPOSER_ACTIVITY_HIDDEN));
        assertThat(configuration.isHideDocumentActionActivities(), equalTo(DOCUMENT_ACTION_ACTIVITIES_HIDDEN));
        assertThat(configuration.getSpaceConfigurations().size(), equalTo(3));

        SpaceConfiguration expectedSpace0 = new SpaceConfigurationBuilder().spaceId("1").displayName("DISPLAY_NAME_1").activityComposerVisible(false).descritpion("DESCRIPTION_1").order(2).build();
        SpaceConfiguration expectedSpace1 = new SpaceConfigurationBuilder().spaceId("2").displayName("DISPLAY_NAME_2").activityComposerVisible(false).descritpion("DESCRIPTION_2").order(1).build();
        SpaceConfiguration expectedSpace2 = new SpaceConfigurationBuilder().spaceId("3").displayName("DISPLAY_NAME_3").activityComposerVisible(true).descritpion("DESCRIPTION_3").build();

        assertSpaceConfiguration(configuration.getSpaceConfigurations().get(0), expectedSpace0);
        assertSpaceConfiguration(configuration.getSpaceConfigurations().get(1), expectedSpace1);
        assertSpaceConfiguration(configuration.getSpaceConfigurations().get(2), expectedSpace2);
    }

    private void assertSpaceConfiguration(SpaceConfiguration actual, SpaceConfiguration expected) {
        assertThat(actual.getId(), equalTo(expected.getId()));
        assertThat(actual.getDisplayName(), equalTo(expected.getDisplayName()));
        assertThat(actual.getDescription(), equalTo(expected.getDescription()));
        assertThat(actual.isActivityComposerVisible(), equalTo(expected.isActivityComposerVisible()));
        HighlightSpaceConfiguration highlightConfiguration = actual.getHighlightConfiguration();

        if (Objects.nonNull(highlightConfiguration) && Objects.nonNull(expected.getHighlightConfiguration())) {
            assertThat(highlightConfiguration.getOrder(), equalTo(expected.getHighlightConfiguration().getOrder()));
            assertThat(highlightConfiguration.isHighlight(), equalTo(expected.getHighlightConfiguration().isHighlight()));
        }
    }

    @Test
    public void should_update_space() {

        String SPACE_ID = "123";
        String SPACE_DISPLAY_NAME = "display name";
        String SPACE_DESCRIPTION = "a description";
        boolean SPACE_IS_HIGHLIGHT = true;
        int SPACE_HIGHLIGHT_ORDER = 5;

        SpaceConfiguration spaceConfiguration = new SpaceConfiguration();
        spaceConfiguration.setId(SPACE_ID);
        spaceConfiguration.setDisplayName(SPACE_DISPLAY_NAME);
        spaceConfiguration.setDescription(SPACE_DESCRIPTION);
        spaceConfiguration.setActivityComposerVisible(false);
        HighlightSpaceConfiguration highlightConfiguration = new HighlightSpaceConfiguration();
        highlightConfiguration.setHighlight(SPACE_IS_HIGHLIGHT);
        highlightConfiguration.setOrder(SPACE_HIGHLIGHT_ORDER);
        spaceConfiguration.setHighlightConfiguration(highlightConfiguration);

        Space space = new Space();
        space.setId(SPACE_ID);

        given(spaceService.getSpaceById(SPACE_ID)).willReturn(space);
        given(settingService.get(Context.GLOBAL, Scope.GLOBAL, HIGHLIGHT_SPACES)).willReturn((SettingValue) SettingValue.create(""));
        given(settingService.get(Context.GLOBAL, Scope.GLOBAL, SPACES_WITHOUT_ACTIVITY_COMPOSER)).willReturn((SettingValue) SettingValue.create(""));

        doNothing().doNothing().when(settingService).set(any(), any(), any(), any());

        SpaceConfiguration updatedSpace = functionalConfigurationService.updateSpaceConfiguration(spaceConfiguration);

        verify(settingService).set(eq(Context.GLOBAL), eq(Scope.GLOBAL), eq(HIGHLIGHT_SPACES), argThat(new SettingValueMatcher(SettingValue.create(space.getId() + "#" + SPACE_HIGHLIGHT_ORDER))));
        verify(settingService).set(eq(Context.GLOBAL), eq(Scope.GLOBAL), eq(SPACES_WITHOUT_ACTIVITY_COMPOSER), argThat(new SettingValueMatcher(SettingValue.create(space.getId()))));

        assertThat(updatedSpace.getId(), equalTo(SPACE_ID));
        assertThat(updatedSpace.getDisplayName(), equalTo(SPACE_DISPLAY_NAME));
        assertThat(updatedSpace.getDescription(), equalTo(SPACE_DESCRIPTION));
        assertThat(updatedSpace.getHighlightConfiguration().isHighlight(), equalTo(SPACE_IS_HIGHLIGHT));
        assertThat(updatedSpace.getHighlightConfiguration().getOrder(), equalTo(SPACE_HIGHLIGHT_ORDER));
    }


    @Test(expected = FunctionalConfigurationRuntimeException.class)
    public void should_send_error_when_space_not_found() {

        given(spaceService.getSpaceById(any())).willReturn(null);

        functionalConfigurationService.updateSpaceConfiguration(new SpaceConfiguration());
    }


    class SpaceConfigurationBuilder {

        private SpaceConfiguration spaceConfiguration;

        SpaceConfigurationBuilder() {
            this.spaceConfiguration = new SpaceConfiguration();
        }

        SpaceConfigurationBuilder spaceId(String spaceId) {
            spaceConfiguration.setId(spaceId);
            return this;
        }

        SpaceConfigurationBuilder displayName(String displayName) {
            spaceConfiguration.setDisplayName(displayName);
            return this;
        }

        SpaceConfigurationBuilder descritpion(String description) {
            spaceConfiguration.setDescription(description);
            return this;
        }

        SpaceConfigurationBuilder activityComposerVisible(boolean activityComposerVisible) {
            spaceConfiguration.setActivityComposerVisible(activityComposerVisible);
            return this;
        }

        SpaceConfigurationBuilder order(int order) {
            HighlightSpaceConfiguration highlightConfiguration = new HighlightSpaceConfiguration();
            highlightConfiguration.setOrder(order);
            highlightConfiguration.setHighlight(true);
            spaceConfiguration.setHighlightConfiguration(highlightConfiguration);
            return this;
        }

        SpaceConfiguration build() {
            return spaceConfiguration;
        }
    }

    class FunctionalConfigurationWithSpacesLoadingService extends FunctionalConfigurationService {

        FunctionalConfigurationWithSpacesLoadingService(SettingService settingService, SpaceService spaceService) {
            super(settingService, spaceService);
        }

        @Override
        protected List<Space> findAllSpaces() {

            Space space1 = new Space();
            space1.setId("1");
            space1.setDisplayName("DISPLAY_NAME_1");
            space1.setDescription("DESCRIPTION_1");
            Space space2 = new Space();
            space2.setId("2");
            space2.setDisplayName("DISPLAY_NAME_2");
            space2.setDescription("DESCRIPTION_2");
            Space space3 = new Space();
            space3.setId("3");
            space3.setDisplayName("DISPLAY_NAME_3");
            space3.setDescription("DESCRIPTION_3");

            return Arrays.asList(space1, space2, space3);
        }
    }
}
