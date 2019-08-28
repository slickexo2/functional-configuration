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
    export default {
        data() {
            return {
                errors: [],
                unsetDocumentActivities: true
            }
        },
        created() {
            console.log("Created Initialization");
    	}
    }
</script>
