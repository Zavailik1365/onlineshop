var nomenclatureAPI = Vue.resource("/admin/nomenclature/{/id}");

Vue.component('nomenclature-form', {
    props:['nomenclatures'],
    data: function() {
        return {
            name: '',
            description: ''
        }
    },
    template: '<div>' +
        '<input type="text" placeholder="Write name" v-model="name"/>' +
        '<input type="text" placeholder="Write description" v-model="description"/>' +
        '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var nomenclature = {
                name: this.name,
                description: this.description
            }

            nomenclatureAPI.save({}, nomenclature).then(data=>{
                this.nomenclatures.push(data);
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
    template: '<nomenclature-list :nomenclatures="nomenclatures"/>,
    data: {
        nomenclatures: []
    }
});