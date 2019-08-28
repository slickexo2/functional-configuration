<template>
	<div id="functionConfiguration-content">
		Hello World !!
	</div>
</template>

<script>
	import functionalConfigurationService from '../services/functionalConfigurationService'
    export default {
        data() {
            return {
                errors: []
            }
        },
        created() {
        	console.log(functionalConfigurationService.getConfiguration());
            console.log("Created Initialization");
    	}
    }
</script>
