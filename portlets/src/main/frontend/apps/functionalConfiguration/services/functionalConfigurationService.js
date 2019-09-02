import axios from "axios";

class FunctionalConfigurationService {

    constructor() {
        this.route = '/rest/functional-configuration';
    }

    getConfiguration(){
        return new Promise((resolve, reject) => {
            axios.get(this.route + "/configuration")
                .then((response) => resolve(response.data));
                .catch((error) => console.log(error));
        });
    }

    putHideComposerActivities(isHidden){

        const restRoute = this.route + "/composer-activity?hidden=" + isHidden;

        return new Promise((resolve, reject) => {
            axios.put(restRoute)
                .then((response) => resolve(response.data));
                .catch((error) => console.log(error));
    }

    putHideDocumentActionActivities(isHidden){
        const restRoute = this.route + "/document-activity?hidden=" + isHidden;

        return new Promise((resolve, reject) => {
            axios.get(restRoute)
                .then((response) => resolve(response.data));
                .catch((error) => console.log(error));
    }
}

export default new FunctionalConfigurationService();