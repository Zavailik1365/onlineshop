var nomenclatureAPI = Vue.resource("/rest-api/nomenclatures");
var saleAPI = Vue.resource("/rest-api/sale");

Vue.component('nomenclature-form', {
    props:['nomenclatures'],
    data: function() {
        return {
            id: '',
            name: '',
            description: '',
            amount: ''
        }
    },
    template: '<div>' +
        '<input type="button" value="Купить" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var nomenclature = {
                id: this.id,
                amount: this.amount
            }

            saleAPI.save({}, nomenclature).then(data=>{
                this.saleAPI.push(data);
            })
        }
    }
});

Vue.component('nomenclature-row', {
    props:['nomenclature'],
    template:
        '<div>\n' +
        '   <b>{{ nomenclature.id }}</b>\n' +
        '   {{ nomenclature.name }}\n' +
        '   {{ nomenclature.description }}\n' +
        '   <input type="text" placeholder="Количество товара" v-model="amount"/>' +
        '</div>'
});

Vue.component('nomenclature-list', {
    props:['nomenclatures'],
    // language=HTML
    template:
        '<div>' +
            '<nomenclature-form :nomenclatures="nomenclatures"/>' +
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
    template: '<nomenclature-list :nomenclatures="nomenclatures"/>',
    data: {
        nomenclatures: []
    }
});