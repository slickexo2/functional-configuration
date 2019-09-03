<template>
	<div id="functionConfiguration-content">
	 	<div>
			<p>{{ $t('functionalConfiguration.hideDocumentActionActivities') }}</p>
			<input type="checkbox" v-model="configuration.hideDocumentActionActivities" @change="changeHideDocumentActionActivities">
			<br>
			<p>{{ $t('functionalConfiguration.hideComposerActivities') }}</p>
			<input type="checkbox" v-model="configuration.hideComposerActivities" @change="changeHideComposerActivities">
	  	</div>
		<br><br>
		<b-container>
			<b-col sm="10" md="8" lg="6" xl="6" class="my-1">
				<b-form-group
						label="Filter"
						label-cols-sm="3"
						label-align-sm="right"
						label-size="sm"
						label-for="filterInput"
						class="mb-0"
				>
					<b-input-group size="sm">
						<b-form-input
								v-model="spaceFilter"
								type="search"
								id="filterInput"
								placeholder="Type to Search"
						></b-form-input>
					</b-input-group>
				</b-form-group>
			</b-col>
		</b-container>
		<br>

		<b-container>
			<table class="table">
				<thead class="thead-dark">
				<tr>
					<th v-for="space in spaceRows">{{space}}</th>
				</tr>
				</thead>
				<tbody>
				<tr v-for="space in filterSpace">
					<!--VUE-->
					<th v-if="!space.edition" scope="row"> {{space.displayName}} </th>
					<td v-if="!space.edition" >{{space.description}}</td>
					<td v-if="space.hideActivityComposer.printed && !space.edition" >{{space.hideActivityComposer.order}} </td>
					<td v-else-if="!space.edition" ></td>
					<td v-if="!space.edition && !isEditing" ><button @click="openEdition(space)">editer</button></td>
					<td v-else-if="!space.edition && isEditing"><button disabled>editer</button></td>

					<!--EDITION-->
					<th v-if="space.edition" scope="row"> {{currentSpaceSaved.displayName}} </th>
					<td v-if="space.edition" >{{currentSpaceSaved.description}}</td>
					<td v-if="space.edition" >
						<input type="checkbox" v-model="currentSpaceSaved.hideActivityComposer.printed">
						<input type="number" v-model="currentSpaceSaved.hideActivityComposer.order">
					</td>
					<td  v-if="space.edition" >
						<button @click="save(space)">enregistrer</button>
						<button @click="cancelEdit(space)">Annuler</button>
					</td>
				</tr>
				</tbody>
			</table>
		</b-container>

        <b-button @click="makeToast()">Show Toast</b-button>
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

            functionalConfigurationService.putHideComposerActivities(this.configuration.hideComposerActivities)
                .then((response) => console.log(response))
                .catch((error) => console.log("KO"));
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
            this.isEditing = false;
            // save à la place de space <- currentSpaceSaved
            // reset currentSpaceSaved
        },
        deepCloneObject(obj){
            return JSON.parse(JSON.stringify(obj));
        },
        makeToast(append = false) {
            this.$bvToast.toast("Test", {
              title: 'BootstrapVue Toast',
              autoHideDelay: 5000,
              appendToast: append
            });
        }
    },
    computed: {
        filterSpace: function () {
            return this.spaces.filter(space => space.displayName.includes(this.spaceFilter) || space.description.includes(this.spaceFilter) )
        }
    }
}
</script>

<style>
	input{
		margin: 5px 5px;
	}
	p{
		margin: 5px 10px;
	}
</style>
