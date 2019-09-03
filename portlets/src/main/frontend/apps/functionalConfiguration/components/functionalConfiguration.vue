<template>
	<div id="functionConfiguration-content">
<!--	 	<div>-->
<!--			<p>{{ $t('functionalConfiguration.hideDocumentActionActivities') }}</p>-->
<!--			<input type="checkbox" v-model="configuration.hideDocumentActionActivities" @change="changeHideDocumentActionActivities">-->
<!--			<br>-->
<!--			<p>{{ $t('functionalConfiguration.hideComposerActivities') }}</p>-->
<!--			<input type="checkbox" v-model="configuration.hideComposerActivities" @change="changeHideComposerActivities">-->
<!--	  	</div>-->

		<div class="custom-control custom-switch hide-switches">
			<input type="checkbox" class="custom-control-input" id="hideDocumentActionActionActivitiesSwitch" v-model="configuration.hideDocumentActionActivities" @change="changeHideDocumentActionActivities">
			<label class="custom-control-label" for="hideDocumentActionActionActivitiesSwitch">{{ $t('functionalConfiguration.hideDocumentActionActivities') }}</label>
		</div>
		<div class="custom-control custom-switch hide-switches">
			<input type="checkbox" class="custom-control-input" id="hideComposerActivitiesSwitch" v-model="configuration.hideComposerActivities" @change="changeHideComposerActivities">
			<label class="custom-control-label" for="hideComposerActivitiesSwitch">{{ $t('functionalConfiguration.hideComposerActivities') }}</label>
		</div>
		<br><br>
        <b-container>
            <b-col class="my-1" lg="6" md="8" sm="10" xl="6">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" :placeholder=$t('functionalConfiguration.table.search') v-model="spaceFilter">
                    <div class="input-group-prepend">
                        <button class="btn btn-outline-secondary" type="button" @click="clearSearch">{{$t('functionalConfiguration.table.clear')}}</button>
                    </div>

                </div>
            </b-col>
        </b-container>
		<br>

        <b-container class="spaces-table-container">
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th class="table-collumns"  v-for="space in spaceRows">{{space}}</th>
                </tr>
                </thead>
                <tbody>
                <tr class="table-rows"  v-for="space in filterSpace">
                    <!--VUE-->
                    <th class="table-displayName" scope="row" v-if="!space.edition"><p class="p-title">{{space.displayName}}</p></th>
                    <td class="table-description" v-if="!space.edition"><p class="p-description">{{space.description}}</p></td>
                    <td class="table-hide" v-if="!space.edition">
                        <div class="custom-control custom-switch">
                            <input class="custom-control-input" id="hideActivityComposerSwitch" type="checkbox" v-model="space.hideActivityComposer" disabled>
                            <label class="custom-control-label" for="hideActivityComposerSwitch"></label>
                        </div>
                    </td>
                    <td class="table-order" v-if="space.order.printed && !space.edition">{{space.order.order}} </td>
                    <td class="table-order" v-else-if="!space.edition"></td>
                    <td class="table-edition" v-if="!space.edition && !isEditing">
                        <button @click="openEdition(space)" class="btn btn-outline-dark edition-buttons" >
                            <font-awesome-icon :icon="['fas', 'edit']" />
                        </button>
                    </td>
                    <td class="table-edition" v-else-if="!space.edition && isEditing">
                        <button class="btn btn-outline-dark edition-buttons" disabled>
                            <font-awesome-icon :icon="['fas', 'edit']" />
                        </button>
                    </td>

                    <!--EDITION-->
                    <th class="table-displayName" scope="row" v-if="space.edition"><p class="p-title">{{currentSpaceSaved.displayName}}</p></th>
                    <td class="table-description" v-if="space.edition"><p class="p-description">{{currentSpaceSaved.description}}</p></td>
                    <td class="table-hide" v-if="space.edition">
                        <div class="custom-control custom-switch">
                            <input class="custom-control-input" id="hideActivityComposerSwitchEdit" type="checkbox" v-model="currentSpaceSaved.hideActivityComposer">
                            <label class="custom-control-label" for="hideActivityComposerSwitchEdit"></label>
                        </div>
                    </td>
                    <td class="table-order" v-if="space.edition">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <input aria-label="Checkbox for following text input" type="checkbox" v-model="currentSpaceSaved.order.printed">
                                </div>
                            </div>
                            <input aria-label="Text input with checkbox" class="form-control" type="number" v-model="currentSpaceSaved.order.order">
                        </div>
                    </td>
                    <td  class="table-edition" v-if="space.edition">
                        <button @click="cancelEdit(space)"  class="btn btn-outline-warning edition-buttons">
                            <font-awesome-icon :icon="['fas', 'times']" />
                        </button>
                        <button @click="save(space)"  class="btn btn-outline-primary edition-buttons">
                            <font-awesome-icon :icon="['fas', 'check']" />
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </b-container>
	</div>
</template>

<script>
	import functionalConfigurationService from '../services/functionalConfigurationService'
    export default {
        data() {
            return {
				isEditing: false,
                configuration: {},
				spaceRows: [
                    $t('functionalConfiguration.table.displayName'), //'displayName',
                    $t('functionalConfiguration.table.description'), //'description',
                    $t('functionalConfiguration.table.hideActivityComposer'), //'hideActivityComposer',
                    $t('functionalConfiguration.table.order'), //'order',
                    $t('functionalConfiguration.table.edit'), //'Modifier',
                ],
				currentSpaceSaved:Object,
				spaceFilter:"",
				spaces: [
                    { id: "0", displayName: "pauline, pauline, pauline, pauline, pauline, pauline, pauline", description: "ceci est une desest une desest une desest une desest une desest une desest une desest une desest une description", order: { printed: true, order: 1 }, hideActivityComposer:true },
                    { id: "1", displayName: "pierre", description: "ceci est une description", order: { printed: false, order: 0 } , hideActivityComposer:true },
                    { id: "2", displayName: "thomas", description: "ceci est  description", order: { printed: false, order: 0 }, hideActivityComposer:false  },
                    { id: "3", displayName: "yves", description: "ceci est une description", order: { printed: false, order: 0 } , hideActivityComposer:true },
                    { id: "4", displayName: "yann", description: "ceci est une ", order: { printed: false, order: 0 } , hideActivityComposer:false },
                    { id: "5", displayName: "alice", description: "ceci est une description", order: { printed: true, order: 2 }, hideActivityComposer:false  },
                    { id: "6", displayName: "rené", description: "ceci  une description", order: { printed: false, order: 0 }, hideActivityComposer:false  },
                    { id: "7", displayName: "paul", description: "ceci est une description", order: { printed: false, order: 8 }, hideActivityComposer:true  },
                ],
            }
        },
        created() {
            const self = this;

export default {
    data() {
        return {
            isEditing: false,
            configuration: {},
            spaceRows: [
                'displayName',
                'description',
                'hideActivityComposer',
                'Modifier',
            ],
            currentSpaceSaved:Object,
            spaceFilter:"",
            spaces: [
                { id: "0", displayName: "pauline", description: "ceci est une description", hideActivityComposer: { printed: true, order: 1 } },
                { id: "0", displayName: "pierre", description: "ceci est une description", hideActivityComposer: { printed: false, order: 0 } },
                { id: "0", displayName: "thomas", description: "ceci est  description", hideActivityComposer: { printed: false, order: 0 } },
                { id: "0", displayName: "yves", description: "ceci est une description", hideActivityComposer: { printed: false, order: 0 } },
                { id: "0", displayName: "yann", description: "ceci est une ", hideActivityComposer: { printed: false, order: 0 } },
                { id: "0", displayName: "alice", description: "ceci est une description", hideActivityComposer: { printed: true, order: 2 } },
                { id: "0", displayName: "rené", description: "ceci  une description", hideActivityComposer: { printed: false, order: 0 } },
                { id: "0", displayName: "paul", description: "ceci est une description", hideActivityComposer: { printed: false, order: 8 } },
            ],
        }
    },
    created() {
        const self = this;

        functionalConfigurationService.getConfiguration()
            .then((data) => self.configuration = response);
    },
    methods:{
        changeHideDocumentActionActivities() {

            functionalConfigurationService.putHideDocumentActionActivities(this.configuration.hideDocumentActionActivities)
                .then((response) => console.log(response))
                .catch((error) => console.log("KO"));
        },
        changeHideComposerActivities(){

			changeHideComposerActivities(){
				console.log("changeHideComposerActivities");
				functionalConfigurationService.putHideComposerActivities(this.configuration.hideComposerActivities).then((response) =>
						console.log(response)
				);
			},

            openEdition(space) {
                this.currentSpaceSaved = this.deepCloneObject(space);
                this.isEditing = true;
                this.$set(space, 'edition', true);
            },
            cancelEdit(space){
                this.isEditing = false;
                this.$set(space, 'edition', false);
            },
            save(space){


                this.isEditing = false; // une fois la sauvegarde finie
                this.$set(space, 'edition', false);
            },
            deepCloneObject(obj){
                return JSON.parse(JSON.stringify(obj));
            },
            clearSearch(){
                this.spaceFilter = "";
            }
        },
        computed: {
            filterSpace: function () {
                return this.spaces.filter(space => space.displayName.includes(this.spaceFilter) || space.description.includes(this.spaceFilter) )
            }
        }
    }
}
</script>

<style>
    .form-control{
        height: 40px;
    }
    .table{
        border: 1px solid grey;
    }
    .hide-switches{
        margin: 20px;
    }
    .spaces-table-container{
        margin-left: 0!important;
    }
    .table th, .table td{
        vertical-align: inherit!important;
        text-align: center!important;
    }

    .table-displayName{
        width: 20%;
    }
    .table-description{
        width: 35%;
    }
    .table-hide{
        width: 10%;
    }
    .table-order{
        width: 15%;
    }
    .table-edition{
        width: 20%;
    }
    .edition-buttons{
        margin: 0 5px;
        width: 40px;
        height: 40px;
    }
    .p-description{
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        line-height: 20px;
        max-height: 40px;
        margin-bottom: 0!important;
    }
    .p-title{
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        line-height: 20px;
        max-height: 40px;
        margin-bottom: 0!important;
    }
</style>
