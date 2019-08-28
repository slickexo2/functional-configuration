<template>
	<div id="functionConfiguration-content">
		 <div>
			 {{ $t('functionalConfiguration.hideDocumentActionActivities') }}
			 <input type="checkbox" v-model="configuration.hideDocumentActionActivities" @change="changeHideDocumentActionActivities">
			 {{configuration.hideDocumentActionActivities}}
				<br>
            {{ $t('functionalConfiguration.hideComposerActivities') }}
			<input type="checkbox" v-model="configuration.hideComposerActivities" @change="changeHideComposerActivities">
            {{configuration.hideComposerActivities}}
          </div>
	</div>
</template>

<script>
	import functionalConfigurationService from '../services/functionalConfigurationService'
    export default {
        data() {
            return {
                errors: [],
                configuration: {}
            }
        },
        created() {
			this.configuration.hideDocumentActionActivities = true;
			this.configuration.hideComposerActivities = true;
			functionalConfigurationService.getConfiguration().then((response) =>
					console.log(response)
			);

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
			}
		}
    }

</script>
