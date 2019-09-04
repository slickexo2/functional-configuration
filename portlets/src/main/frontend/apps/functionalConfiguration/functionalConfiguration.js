import FunctionalConfigurationApp from './components/functionalConfiguration.vue'
// import 'bootstrap/dist/css/bootstrap.css'
// import 'bootstrap-vue/dist/bootstrap-vue.css'
import Bootstrap from 'bootstrap-vue'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'

Vue.config.productionTip = false
Vue.use(Bootstrap)
library.add(fas);
Vue.component('font-awesome-icon', FontAwesomeIcon)

const lang = eXo.env.portal.language;
const url = `/functional-configuration-portlets/vuesLocales/locale_${lang}.json`;

export function init() {
	exoi18n.loadLanguageAsync(lang, url).then(i18n => {
		new Vue({
			render: h => h(FunctionalConfigurationApp),
			i18n
		}).$mount('#functionalConfiguration');
	});
}