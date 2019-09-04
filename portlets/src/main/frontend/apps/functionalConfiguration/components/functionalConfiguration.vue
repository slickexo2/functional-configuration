<template>
  <div id="functionConfiguration-content">
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
    <div class="custom-control custom-switch hide-switches">
      <input
        type="checkbox"
        class="custom-control-input"
        id="hideComposerActivitiesSwitch"
        v-model="configuration.hideComposerActivities"
        @change="changeHideComposerActivities"
      />
      <label
        class="custom-control-label"
        for="hideComposerActivitiesSwitch"
      >{{ $t('functionalConfiguration.hideComposerActivities') }}</label>
    </div>

    <br/>
    <br/>
    
    <div class="col-6 col-md-4 input-group mb-1">
        <input
            type="text"
            class="form-control"
            :placeholder="$t('functionalConfiguration.table.search')"
            v-model="spaceFilter"/>

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
        <thead class="thead-dark">
            <tr>
            <th class="table-collumns">{{$t('functionalConfiguration.table.displayName')}}</th>
            <th class="table-collumns">{{$t('functionalConfiguration.table.description')}}</th>
            <th class="table-collumns">{{$t('functionalConfiguration.table.hideActivityComposer')}}</th>
            <th class="table-collumns">{{$t('functionalConfiguration.table.highLightSpaceorder')}}</th>
            <th class="table-collumns">{{$t('functionalConfiguration.table.actions')}}</th>
            </tr>
        </thead>
        <tbody>
            <tr class="table-rows" v-for="space in filteredSpaces" :key="space.id">
            <!--VUE-->
            <th class="table-displayName" scope="row" v-if="!space.edition">
                <p class="p-title">{{space.displayName}}</p>
            </th>
            <td class="table-description" v-if="!space.edition">
                <p class="p-description">{{space.description}}</p>
            </td>
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
            <td
                class="table-order"
                v-if="space.highlightConfiguration && space.highlightConfiguration.highlight && !space.edition"
            >{{space.highlightConfiguration.order}}</td>
            <td class="table-order" v-else-if="!space.edition"></td>
            <td class="table-edition" v-if="!space.edition && !isEditing">
                <button @click="openEdition(space)" class="btn btn-outline-dark edition-buttons">
                <font-awesome-icon :icon="['fas', 'edit']" />
                </button>
            </td>
            <td class="table-edition" v-else-if="!space.edition && isEditing">
                <button class="btn btn-outline-dark edition-buttons" disabled>
                <font-awesome-icon :icon="['fas', 'edit']" />
                </button>
            </td>

            <!--EDITION-->
            <th class="table-displayName" scope="row" v-if="space.edition">
                <p class="p-title">{{currentSpaceSaved.displayName}}</p>
            </th>
            <td class="table-description" v-if="space.edition">
                <p class="p-description">{{currentSpaceSaved.description}}</p>
            </td>
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
      isEditing: false,
      configuration: {},
      currentSpaceSaved: {},
      spaceFilter: "",
      spaces: []
    };
  },
  created() {
    const self = this;

    functionalConfigurationService
      .getConfiguration()
      .then(data => (self.configuration = data));
  },
  methods: {
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
    changeHideComposerActivities() {
      functionalConfigurationService
        .putHideComposerActivities(this.configuration.hideComposerActivities)
        .then(response => {
            this.successResponse();
            }
        )
          .catch(error => {
              this.failedResponse();
          });
    },
    openEdition(space) {
      this.isEditing = true;
      this.currentSpaceSaved = this.deepCloneObject(space);
      this.$set(space, "edition", true);
    },
    cancelEdit(space) {
      this.isEditing = false;
      this.$set(space, "edition", false);
    },
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
    deepCloneObject(obj) {
      return JSON.parse(JSON.stringify(obj));
    },
    clearSearch() {
      this.spaceFilter = "";
    },
    failedResponse(){
        console.log(this);
        this.makeToast(this.$t('functionalConfiguration.toast.fail'), this.$t('functionalConfiguration.toast.fail.message'), 'danger')
    },
    successResponse(){
        console.log(this);
        this.makeToast(this.$t('functionalConfiguration.toast.success'), this.$t('functionalConfiguration.toast.success.message'), 'success')
    },
    makeToast(title, message, variant) {
        this.$bvToast.toast(message, {
            title: title,
            variant: variant,
            solid: true,
        })
    }
  },
  computed: {
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

      return this.configuration.spaceConfigurations.filter(diplayNameAndDescriptionFilter);
    }
  }
};
</script>

<style scoped lang="scss">
    @import "~bootstrap/dist/css/bootstrap.css";
    @import "~bootstrap/scss/bootstrap";
    @import "~bootstrap-vue/dist/bootstrap-vue.css";
.form-control {
  height: 40px;
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
</style>
