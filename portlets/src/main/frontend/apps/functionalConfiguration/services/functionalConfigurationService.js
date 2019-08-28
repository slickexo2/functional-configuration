import axios from "axios";

let route = '/rest/functional-configuration';
export default {
    getConfiguration(){
        return axios.get(route + "/configuration");
    },

    putHideComposerActivities(isHidden){
        console.log("put /hide-user-activity-composer");
        return axios.put(route + "/composer-activity?hidden=" + isHidden);
    },

    putHideDocumentActionActivities(isHidden){
        console.log("put /hide-document-activities");
        return axios.put(route + "/hide-document-activities?isHidden=" + isHidden);
    }


//    updateDocumentActionActivities(){
//        return axios.put("/rest/functional-configuration/document-action-activities?hidden=true").then((response) => console.log(response));
////        return "test2";
//    }
}