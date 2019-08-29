import axios from "axios";

let route = '/rest/functional-configuration';
export default {
    getConfiguration(){
        return axios.get(route + "/configuration");
    },

    putHideComposerActivities(isHidden){
        const restRoute = route + "/composer-activity?hidden=" + isHidden;
        console.log(restRoute);
        return axios.put(restRoute);
    },

    putHideDocumentActionActivities(isHidden){
        const restRoute = route + "/document-activity?hidden=" + isHidden;
        console.log(restRoute);
        return axios.put(restRoute);
    }


//    updateDocumentActionActivities(){
//        return axios.put("/rest/functional-configuration/document-action-activities?hidden=true").then((response) => console.log(response));
////        return "test2";
//    }
}