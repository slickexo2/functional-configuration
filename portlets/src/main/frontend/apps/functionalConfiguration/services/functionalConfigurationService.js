import axios from "axios";

class FunctionalConfigurationService {

    route = '/rest/functional-configuration';

    getConfiguration(){
        return new Promise((resolve, reject) => {
            axios.get(this.route + "/configuration")
                .then((response) => resolve(response.data));
                .catch((error) => console.log(error));
        });
    },

    putHideComposerActivities(isHidden){
        const restRoute = this.route + "/composer-activity?hidden=" + isHidden;
        return axios.put(restRoute);
    },

    putHideDocumentActionActivities(isHidden){
        const restRoute = this.route + "/document-activity?hidden=" + isHidden;
        return axios.put(restRoute);
    }
}

export default new FunctionalConfigurationService();