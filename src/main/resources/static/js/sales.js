var nomenclatureAPI = Vue.resource("/rest-api/nomenclatures");
var saleAPI = Vue.resource("/rest-api/sale");
var items = [];

Vue.component('nomenclature-row', {
    props:['nomenclature'],
    template:
       '<div>\n' +
       '   <b>{{ nomenclature.id }}</b>\n' +
       '   {{ nomenclature.name }}\n' +
       '   {{ nomenclature.description }}\n' +
       '   <input type="text" placeholder="Количество товара" @input="putitem"/>' +
       '</div>',
    method: {
        putitem: function () {
        this.n
    }}
});

Vue.component('nomenclature-list', {
    props:['nomenclatures'],
    template:
        '<div>' +
            '<nomenclature-row v-for="nomenclature in nomenclatures"' +
                ' :key="nomenclature.id"' +
                ' :nomenclature="nomenclature" />' +
        '</div>',
    created: function() {
        nomenclatureAPI.get().then(result =>
            result.json().then(
                data => data.forEach(
                    nomenclature => this.nomenclatures.push(nomenclature)
                )
            )
        )
    }
});

var app = new Vue({
    el: '#app',
    template: '<div>' +
                '<nomenclature-list :nomenclatures="nomenclatures"/>' +
                '<input type="button" value="Save">' +
            '</div>',
    data: {
        nomenclatures: []
    }
});