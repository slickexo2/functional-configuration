import FunctionalConfigurationApp from './components/functionalConfiguration.vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import Bootstrap from 'bootstrap-vue'


Vue.config.productionTip = false
Vue.use(Bootstrap)

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