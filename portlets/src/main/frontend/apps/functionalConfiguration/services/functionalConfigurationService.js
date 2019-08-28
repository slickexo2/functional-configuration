import axios from "axios";

export default {
    getConfiguration(){
        axios.get("/rest/functional-configuration/configuration").then((response) => console.log(response));
        return "test2";
    }

}