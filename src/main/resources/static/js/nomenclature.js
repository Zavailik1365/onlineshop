var nomenclatureAPI = Vue.resource("/rest-api/nomenclatures");
var nomenclatureAdminAPI = Vue.resource("rest-api/admin/nomenclatures/{id}");

Vue.component('nomenclature-row', {
    props:['nomenclature'],
    data:function() {
        return {
            show: false,
            name: "",
            description: "",
    }},
    template:
        '<div>' +
                '{{ nomenclature.name }}' +
                '<input type="button" value="Редактировать"' +
                    ' @click = "updateShow" v-if="!show"/>' +
                '<div>' +
                    '<input type="text" placeholder="Введите наименование"' +
                        ' v-model="name" v-if="show"/>' +
                '</div>' +
                '<div>' +
                    '<textarea type="text" placeholder="Введите описание"' +
                        ' v-model="description" v-if="show"/>' +
                '</div>' +
                '<div>' +
                    '<input type="button" value="Сохранить"' +
                        ' @click = "save" v-if="show"/>' +
                        '<input type="button" value="Свернуть"' +
                        ' @click = "updateShow" v-if="show"/>' +
                '</div>' +
        '</div>',
    methods:{

        updateShow: function () {
            this.show = !this.show;
        },

        save: function () {

            request = {
                "description": this.description,
                "name": this.name,
                "id": this.nomenclature.id,
            }

            nomenclatureAdminAPI.update(
                {id: this.nomenclature.id}, request).then(
                    this.nomenclature = request);
        }
    },
    created: function () {
        this.name = this.nomenclature.name;
        this.description = this.nomenclature.description;
    }
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
                    nomenclature => this.nomenclatures.push(nomenclature))))
    }
});

var app = new Vue({
    el: '#app',
    template: '<table>' +
                '<nomenclature-list :nomenclatures="nomenclatures"/>' +
               '</table>',
    data: {
        nomenclatures: []
    }
});