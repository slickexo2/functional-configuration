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
								v-model="filter"
								type="search"
								id="filterInput"
								placeholder="Type to Search"
						></b-form-input>
						<b-input-group-append>
							<b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
						</b-input-group-append>
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
				<tr v-for="space in spaces">
					<!--VUE-->
					<th v-if="!space.edition" scope="row"> {{space.displayName}} </th>
					<td v-if="!space.edition" >{{space.description}}</td>
					<td v-if="space.hideActivityComposer.printed && !space.edition" >{{space.hideActivityComposer.order}} </td>
					<td v-else-if="!space.edition" ></td>
					<td v-if="!space.edition && !isEditing" ><button @click="edit(space, true)">editer</button></td>

					<!--EDITION-->
					<th v-if="space.edition" scope="row"> {{space.displayName}} </th>
					<td v-if="space.edition" >{{space.description}}</td>
					<td v-if="space.edition" >
						<input type="checkbox" v-model="space.hideActivityComposer.printed">
						<input type="number" v-model="space.hideActivityComposer.order">
					</td>
					<td  v-if="space.edition" ><button @click="edit(space, false)">enregistrer</button></td>
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
					'displayName',
					'description',
					'hideActivityComposer',
					'Modifier',
				],
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

			functionalConfigurationService.getConfiguration().then((response) => {
                self.configuration = response.data;

                console.log(self.configuration, response.data);
			});

            console.log("Created Initialization");
    	},
		methods:{
			changeHideDocumentActionActivities(){
				console.log("changeHideDocumentActionActivities");
				functionalConfigurationService.putHideDocumentActionActivities(this.configuration.hideDocumentActionActivities).then((response) =>
						console.log(response)
				);
			},

			changeHideComposerActivities(){
				console.log("changeHideComposerActivities");
				functionalConfigurationService.putHideComposerActivities(this.configuration.hideComposerActivities).then((response) =>
						console.log(response)
				);
			},

			edit(space, isEditing) {
				this.isEditing = isEditing;
				this.$set(space, 'edition', isEditing);
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
