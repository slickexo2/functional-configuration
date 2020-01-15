<template>
  <div>
      <form class="form-horizontal" :action="$preferences.actionURL" method="POST">

        <div class="control-group">
            <label class="control-label" for="portlet_name">Nom de la portlet : </label>
            <div class="controls">
                <input type="text" name="portlet_name" v-model="$preferences.portletName"/>
            </div>
        </div>


        <div class="control-group">
            <label class="control-label" for="portlet_name">Sélection du groupe : </label>
            <div class="controls">
                <select name="portlet_group" v-model="$preferences.portletGroup">

                    <option v-for="index in 10" :value="index">Groupe {{index}}</option>
                </select>
            </div>
        </div>


        <div class="control-group submit-form">
            <input type="submit" class="btn" value="Sauvegarder"/>
            <input type="button" class="btn" value="Fermer" onclick="javascript:eXo.webui.UIForm.submitForm('UIPortletForm','Close',true)"/>
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
