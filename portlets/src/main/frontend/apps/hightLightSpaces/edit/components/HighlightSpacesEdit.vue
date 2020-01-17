<template>
  <div>
      <form class="form-horizontal" :action="$preferences.actionURL" method="POST">

        <div class="control-group">
            <label class="control-label" for="portlet_name">{{$t('functionalConfiguration.highlightSpaces.edit.portletName')}}</label>
            <div class="controls">
                <input type="text" name="portlet_name" v-model="$preferences.portletName"/>
            </div>
        </div>


        <div class="control-group">
            <label class="control-label" for="portlet_name">{{$t('functionalConfiguration.highlightSpaces.edit.groupSelection')}}</label>
            <div class="controls">
                <select name="portlet_group" v-model="$preferences.portletGroup">

                    <option v-for="index in 10" :value="index" :key="index">{{$t('functionalConfiguration.highlightSpaces.edit.group')}} {{index}}</option>
                </select>
            </div>
        </div>


        <div class="control-group submit-form">
            <input type="submit" class="btn" :value="$t('functionalConfiguration.highlightSpaces.edit.save')"/>
            <!-- <input type="button" class="btn" :value="$t('functionalConfiguration.highlightSpaces.edit.close')" onclick="javascript:eXo.webui.UIForm.submitForm('UIPortletForm','Close',true)"/> -->
        </div>
      </form>
  </div>
</template>

<script>
export default {}
</script>

<style scoped>

    .submit-form {
        text-align: center;
        margin-top: 40px;
    }

</style>
