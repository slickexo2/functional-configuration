<template>
  <div id="functionConfiguration-content">
      <!--        configuration.hideDocumentActionActivities SWICH         -->
      <div class="notification">
          <div v-for="notification in notifications" class="alert" role="alert" v-if="notification.visible" :class="notification.cssClass">
              {{notification.title}}
          </div>
      </div>
    <div class="custom-control custom-switch hide-switches">
      <input
        type="checkbox"
        class="custom-control-input"
        id="hideDocumentActionActionActivitiesSwitch"
        v-model="configuration.hideDocumentActionActivities"
        @change="changeHideDocumentActionActivities"
      />
      <label
        class="custom-control-label"
        for="hideDocumentActionActionActivitiesSwitch"
      >{{ $t('functionalConfiguration.hideDocumentActionActivities') }}</label>
    </div>
      <!--        configuration.activityComposerVisible SWICH         -->
    <div class="custom-control custom-switch hide-switches">
      <input
        type="checkbox"
        class="custom-control-input"
        id="activityComposerVisibleSwitch"
        v-model="configuration.activityComposerVisible"
        @change="changeactivityComposerVisible"
      />
      <label
        class="custom-control-label"
        for="activityComposerVisibleSwitch"
      >{{ $t('functionalConfiguration.activityComposerVisible') }}</label>
    </div>

    <br/>
    <br/>
    
    <div class="col-6 col-md-4 input-group mb-1 table-search">
        <!--        input search         -->
        <input
            type="text"
            class="form-control"
            :placeholder="$t('functionalConfiguration.table.search')"
            v-model="spaceFilter"/>

        <!--        input search BUTTON clear         -->
        <div class="input-group-prepend">
            <button
                class="btn btn-outline-secondary"
                type="button"
                @click="clearSearch"
                >{{$t('functionalConfiguration.table.clear')}}</button>
        </div>
    </div>
    <div class="table-wrapper">
        <table class="table">
            <!--        collumns         -->
        <thead class="thead-dark">
            <tr>
            <th class="table-collumns" @click="orderDisplayName">{{$t('functionalConfiguration.table.displayName')}}
                    <font-awesome-icon v-if="displayNameOrder === 0" :icon="['fas', 'sort']" />
                    <font-awesome-icon v-if="displayNameOrder === 1" :icon="['fas', 'sort-down']" />
                    <font-awesome-icon v-if="displayNameOrder === -1" :icon="['fas', 'sort-up']" />
            </th>
            <th class="table-collumns">{{$t('functionalConfiguration.table.description')}}</th>
            <th class="table-collumns">{{$t('functionalConfiguration.table.hideActivityComposer')}}</th>
            <th class="table-collumns" @click="orderHighlightConfiguration">{{$t('functionalConfiguration.table.highLightSpaceorder')}}
                <font-awesome-icon v-if="highlightConfigurationOrder === 0" :icon="['fas', 'sort']" />
                <font-awesome-icon v-if="highlightConfigurationOrder === 1" :icon="['fas', 'sort-down']" />
                <font-awesome-icon v-if="highlightConfigurationOrder === -1" :icon="['fas', 'sort-up']" />
            </th>
            <th class="table-collumns">{{$t('functionalConfiguration.table.actions')}}</th>
            </tr>
        </thead>
        <tbody>
            <tr class="table-rows" v-for="space in filteredSpaces" :key="space.id">
            <!--VUE-->
                <!--       SHOW displayName in view mode         -->
            <th class="table-displayName" scope="row" v-if="!space.edition">
                <p class="p-title">{{space.displayName}}</p>
            </th>
                <!--       SHOW description in view mode         -->
            <td class="table-description" v-if="!space.edition">
                <p class="p-description">{{space.description}}</p>
            </td>
                <!--       SHOW hideActivity in view mode         -->
            <td class="table-hide" v-if="!space.edition">
                <div class="custom-control custom-switch">
                <input
                    class="custom-control-input"
                    id="hideActivityComposerSwitch"
                    type="checkbox"
                    v-model="space.hideActivityComposer"
                    disabled
                />
                <label class="custom-control-label" for="hideActivityComposerSwitch"></label>
                </div>
            </td>
                <!--       SHOW order in view mode         -->
            <td
                class="table-order"
                v-if="space.highlightConfiguration && space.highlightConfiguration.highlight && !space.edition"
            >{{space.highlightConfiguration.order}}</td>
                <!--       SHOW order if empty in view mode         -->
            <td class="table-order" v-else-if="!space.edition"></td>
                <!--       SHOW button EDIT in view mode         -->
            <td class="table-edition" v-if="!space.edition && !isEditing">
                <button @click="openEdition(space)" class="btn btn-outline-dark edition-buttons">
                <font-awesome-icon :icon="['fas', 'edit']" />
                </button>
            </td>
                <!--       SHOW button EDIT in DISABLED view mode         -->
            <td class="table-edition" v-else-if="!space.edition && isEditing">
                <button class="btn btn-outline-dark edition-buttons" disabled>
                <font-awesome-icon :icon="['fas', 'edit']" />
                </button>
            </td>

            <!-- /// EDITION /// -->
            <!--       SHOW displayName in edition mode         -->
            <th class="table-displayName" scope="row" v-if="space.edition">
                <p class="p-title">{{currentSpaceSaved.displayName}}</p>
            </th>
                <!--       SHOW description in edition mode         -->
            <td class="table-description" v-if="space.edition">
                <p class="p-description">{{currentSpaceSaved.description}}</p>
            </td>
                <!--       SHOW buttons in edition         -->
            <td class="table-hide" v-if="space.edition">
                <div class="custom-control custom-switch">
                <input
                    class="custom-control-input"
                    id="hideActivityComposerSwitchEdit"
                    type="checkbox"
                    v-model="currentSpaceSaved.hideActivityComposer"
                />
                <label class="custom-control-label" for="hideActivityComposerSwitchEdit"></label>
                </div>
            </td>
                <!--       SHOW checkbox and input for order in edition mode        -->
            <td class="table-order" v-if="space.edition">
                <div class="input-group">
                <div class="input-group-prepend">
                    <div class="input-group-text">
                    <input
                        aria-label="Checkbox for following text input"
                        type="checkbox"
                        v-model="currentSpaceSaved.highlightConfiguration.highlight"
                    />
                    </div>
                </div>
                <input
                    aria-label="Text input with checkbox"
                    class="form-control"
                    type="number"
                    v-model="currentSpaceSaved.highlightConfiguration.order"
                />
                </div>
            </td>
            <!--       SHOW buttons in edition mode        -->
            <td class="table-edition" v-if="space.edition">
                <button @click="cancelEdit(space)" class="btn btn-outline-warning edition-buttons">
                <font-awesome-icon :icon="['fas', 'times']" />
                </button>
                <button @click="save(space)" class="btn btn-outline-primary edition-buttons">
                <font-awesome-icon :icon="['fas', 'check']" />
                </button>
            </td>
            </tr>
        </tbody>
        </table>
    </div>
  </div>
</template>

<script>
import functionalConfigurationService from "../services/functionalConfigurationService";

export default {
  data() {
    return {
      highlightConfigurationOrder: SORT_STATE.NONE,
      displayNameOrder: SORT_STATE.ASC,
      isEditing: false,
      configuration: {},
      currentSpaceSaved: {},
      spaceFilter: "",
      spaces: [],
      notifications: []
    };
  },
  created() {
    const self = this;

    functionalConfigurationService
      .getConfiguration()
      .then(data => (self.configuration = data));
  },
  methods: {
      //  Edit hideDocumentActionActivities
    changeHideDocumentActionActivities() {
      functionalConfigurationService
        .putHideDocumentActionActivities(
          this.configuration.hideDocumentActionActivities
        )
        .then(response => {
            this.successResponse();
        })
        .catch(error => {
            this.failedResponse();
        });
    },
    //  Edit activityComposerVisible
    changeactivityComposerVisible() {
      functionalConfigurationService
        .putactivityComposerVisible(this.configuration.activityComposerVisible)
        .then(response => {
            this.successResponse();
            }
        )
          .catch(error => {
              this.failedResponse();
          });
    },
      // open the edition for a line
    openEdition(space) {
      this.isEditing = true;
      this.currentSpaceSaved = this.deepCloneObject(space);
      this.$set(space, "edition", true);
    },
      // stop the edition
    cancelEdit(space) {
      this.isEditing = false;
      this.$set(space, "edition", false);
    },
      // save a space
    save(space) {
        const self = this;

        functionalConfigurationService.putSpaceConfiguration(self.currentSpaceSaved)
          .then(data => {

            space.hideActivityComposer = data.hideActivityComposer;
            space.highlightConfiguration = data.highlightConfiguration;
        
            self.cancelEdit(space);
            delete self.currentSpaceSaved;

              this.successResponse();
          })
            .catch(error => {
                this.failedResponse();
            });
    },
      // copy a space content for save
    deepCloneObject(obj) {
      return JSON.parse(JSON.stringify(obj));
    },
      // clear the search input
    clearSearch() {
      this.spaceFilter = "";
    },
      successResponse(){
          this.toastSuccess(this.$t('functionalConfiguration.toast.success.message'));
      },
      failedResponse(){
          this.toastDanger(this.$t('functionalConfiguration.toast.fail.message'));
      },
      toastSuccess(message) {
          this.makeToast(message, TOAST_TYPE.SUCCESS);
      },
      toastDanger(message) {
          this.makeToast(message, TOAST_TYPE.DANGER);
      },
      makeToast(title, cssClass) {
          const DEFAULT_TOAST_DELAY = 5000;

          const notification = { id: this.notifications.length, title: title, visible: true, cssClass: cssClass };
          this.notifications.push(notification);

          setTimeout(() => { notification.visible = false; }, DEFAULT_TOAST_DELAY);
      },
      orderDisplayName(){
          this.highlightConfigurationOrder = SORT_STATE.NONE;
          if(this.displayNameOrder === SORT_STATE.ASC){
              this.displayNameOrder = SORT_STATE.DESC
          } else {
      // change order in diplayName
              this.displayNameOrder = SORT_STATE.ASC
          }
      },
      // change order in highlightConfiguration
      orderHighlightConfiguration(){
          this.displayNameOrder = SORT_STATE.NONE;
          if(this.highlightConfigurationOrder === SORT_STATE.ASC){
              this.highlightConfigurationOrder = SORT_STATE.DESC
          } else {
              this.highlightConfigurationOrder = SORT_STATE.ASC
          }
      }

  },
  computed: {
      // filter spaces
    filteredSpaces: function() {
      if (!this.configuration || !this.configuration.spaceConfigurations) {
        return [];
      }

      const diplayNameAndDescriptionFilter = space => {
        const displayName = space.displayName ? space.displayName : "";
        const description = space.description ? space.description : "";

        return (
          displayName.includes(this.spaceFilter) ||
          description.includes(this.spaceFilter)
          );
      }

        var spaces = this.configuration.spaceConfigurations.filter(diplayNameAndDescriptionFilter);
        if (this.displayNameOrder === SORT_STATE.ASC){
            spaces = spaces.sort((a, b) => a.displayName.localeCompare(b.displayName));
        } else if(this.displayNameOrder === SORT_STATE.DESC){
            spaces = spaces.sort((b, a) => a.displayName.localeCompare(b.displayName));
        }

        if (this.highlightConfigurationOrder === SORT_STATE.ASC){
            spaces = spaces.sort(function(a, b) {
                return a.highlightConfiguration.order - b.highlightConfiguration.order;
            });
        } else if(this.highlightConfigurationOrder === SORT_STATE.DESC){
            spaces = spaces.sort(function(b, a) {
                return a.highlightConfiguration.order - b.highlightConfiguration.order;
            });
        }

        return spaces;
    }
  }

};
const TOAST_TYPE = {
    SUCCESS: 'alert-success',
    DANGER: ' alert-danger',
}

const SORT_STATE = {
    DESC: -1,
    NONE: 0,
    ASC: 1
}
</script>

<style scoped>

.form-control {
  height: 40px;
}
    .table-search{
        margin-left: 5px;
    }
.table {
  border: 1px solid grey;
}
.hide-switches {
  margin: 20px;
}
.spaces-table-container {
  margin-left: 0 !important;
}
.table th,
.table td {
  vertical-align: inherit !important;
  text-align: center !important;
}

.table-displayName {
  width: 20%;
}
.table-description {
  width: 35%;
}
.table-hide {
  width: 10%;
}
.table-order {
  width: 15%;
}
.table-edition {
  width: 20%;
}
.edition-buttons {
  margin: 0 5px;
  width: 40px;
  height: 40px;
}
.p-description {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  line-height: 20px;
  max-height: 40px;
  margin-bottom: 0 !important;
}
.p-title {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  line-height: 20px;
  max-height: 40px;
  margin-bottom: 0 !important;
}
.table-wrapper {
    margin: 20px;
}
.notification {
    position: absolute;
    min-width: 300px;
    max-width: 300px;
    right: 10px;
    top: 67px;
}
</style>
