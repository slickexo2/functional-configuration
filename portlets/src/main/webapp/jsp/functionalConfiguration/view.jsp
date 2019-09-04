<div id="functionalConfiguration">
	<script>
		require(['SHARED/functionalConfigurationBundle'], function(functionalConfigurationApp) {
		    console.log("before init");
			functionalConfigurationApp.init();
	    });

	</script>
</div>

<style>
	a:not([href]):not([tabindex]){
		color: #fff!important;
	}
	a:not([href]):not([tabindex]):hover{
		color: #fff!important;
	}
</style>

