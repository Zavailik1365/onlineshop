var nomenclatureAPI = Vue.resource("/rest-api/nomenclatures");
var saleAPI = Vue.resource("/rest-api/sale");
var items = [];

Vue.component('nomenclature-row', {
    props:['nomenclature'],
    data:function() {
        return {
        nomenclature_id: "",
        amount: 0
        }
    },
    template:
        '<tr>' +
           '<td>   {{ nomenclature.name }} </td>\n' +
           '<td>   {{ nomenclature.description }} </td>\n' +
           '<td>   <input' +
                ' type="number" v-model = "amount" value = 0' +
                ' placeholder="Количество товара" @input="save"' +
                ' id="amount_input"/> </td>' +
        '</tr>',
    methods: {
        save: function () {

            var wasChange = false;

            for (item in items){
                if (items[item].nomenclature_id == this.nomenclature.id) {
                    items[item].amount = Number(this.amount);
                    wasChange = true;
                }
            }
            if (!wasChange) {
                var item = {
                    "nomenclature_id": this.nomenclature.id,
                    "amount": Number(this.amount)
                };
                items.push(item);
            }
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
    data: {
        nomenclatures: []
    },
    template: '<div>' +
            '<table>' +
                '<nomenclature-list :nomenclatures="nomenclatures"/>' +
            '</table>' +
            '<input type="button" value="Купить" @click = "save" id="Save">' +
        '</div>',
    methods: {
        save: function () {
            request = {
                "items": items
            }

            saleAPI.save(request).then(
                function (response) {
                    items = [];
                    inputList = document.getElementsByTagName('input')
                    for (item in inputList){
                        if (inputList[item].id == "amount_input"){
                            inputList[item].value = 0;
                        }
                    }
                    },
                function (error) {
                    alert("Не удалось сохранить продажу обратитель к администратору.");
                }
                );
        }
    }

});