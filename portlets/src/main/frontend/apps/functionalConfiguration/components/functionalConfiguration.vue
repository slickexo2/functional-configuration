<template>
	<div id="functionConfiguration-content">
		 <div>

            {{ $t('functionalConfiguration.unsetDocumentActivities') }}
			<input type="checkbox" v-model="unsetDocumentActivities">

            {{unsetDocumentActivities}}
          </div>
	</div>
</template>

<script>
	import functionalConfigurationService from '../services/functionalConfigurationService'
    export default {
        data() {
            return {
                errors: [],
                unsetDocumentActivities: true
            }
        },
        created() {
        	console.log(functionalConfigurationService.getConfiguration());
            console.log("Created Initialization");
    	}
    }
</script>
