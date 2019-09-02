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
        const self = this;

        functionalConfigurationService.getConfiguration()
            .then((data) => self.configuration = response);
    },
    methods:{
        changeHideDocumentActionActivities(){

            functionalConfigurationService.putHideDocumentActionActivities(this.configuration.hideDocumentActionActivities)
                .then((response) => console.log(response))
                .catch((error) => console.log("KO"));
        },

        changeHideComposerActivities(){

            functionalConfigurationService.putHideComposerActivities(this.configuration.hideComposerActivities)
                .then((response) => console.log(response))
                .catch((error) => console.log("KO"));
        }
    }
}

</script>
