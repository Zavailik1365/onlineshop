var nomenclatureAPI = Vue.resource("rest-api/nomenclatures");
var saleAPI = Vue.resource("rest-api/sale");
var items = [];

Vue.component('nomenclature-list', {
    props:['nomenclatures'],
    data:function() {
        return {
            name: frontendData.profile.name,
            isAdmin: frontendData.profile.isAdmin,
            error: "",
            showErrors: false
        }
    },
    template:
        '<v-app>' +
            '<v-toolbar app dark color="primary">' +
                '<v-btn flat href="/">' +
                    '<v-toolbar-title class="white--text">Online shop</v-toolbar-title>' +
                '</v-btn>' +
                '<div v-if="isAdmin">' +
                    '<v-btn flat href="/admin/nomenclatures">Номенклатура</v-btn>' +
                    '<v-btn flat href="/admin/users">Пользователи</v-btn>' +
                    '<v-btn flat href="/admin/sales">Продажи</v-btn>' +
                '</div>' +
                '<v-spacer></v-spacer>' +
                '<v-btn flat href = "/user">\n' +
                    '{{ name }}' +
                '</v-btn>' +
                '<v-btn icon href="/logout">' +
                    '<v-icon>exit_to_app</v-icon>' +
                '</v-btn>' +
            '</v-toolbar>' +
            ' <v-content>' +
                '<v-container>' +
                    '<v-layout align-center justify-center row fill-height>' +
                        '<v-flex lg9>' +
                            '<v-input' +
                                ' prepend-icon="error"' +
                                ' v-if="showErrors"' +
                                ' v-model="error">' +
                                '{{error}}' +
                            '</v-input>' +
                            '<v-list two-line v-if="!showErrors">' +
                                    ' <v-list-tile' +
                                        ' v-for="nomenclature in nomenclatures"' +
                                        ' :key="nomenclature.id"' +
                                        ' avatar>' +
                                        ' <v-list-tile-content>' +
                                            '<v-list-tile-title v-text="nomenclature.name"/>' +
                                            '<v-list-tile-sub-title v-html="nomenclature.description"/>' +
                                        ' </v-list-tile-content>' +
                                        '<v-list-tile-action>' +
                                            '<v-text-field' +
                                                ' v-model="nomenclature.amount"' +
                                                ' type="number"' +
                                                ' min="0"' +
                                                ' :clearable=true />' +
                                        '</v-list-tile-action>'+
                                    ' </v-list-tile>' +
                                ' </v-list>' +
                                '<div v-if="!showErrors">' +
                                    '<v-btn' +
                                        ' color="success"' +
                                        ' @click = "save"' +
                                        ' v-if="!showErrors">' +
                                    'Сохранить</v-btn>' +
                                    '<v-btn' +
                                        ' @click = "clearNomenclatures"' +
                                        ' v-if="!showErrors">' +
                                            'Очистить' +
                                    '</v-btn>' +
                                    '<v-btn href="/" v-if="!showErrors">' +
                                        '<v-icon >keyboard_return</v-icon>' +
                                '</v-btn>' +
                                '</div>' +
                        ' </v-flex>' +
                    '</v-layout>' +
                '</v-container>' +
            '</v-content>' +
        '</v-app>',

    methods: {

        save: function () {

            request = {
                "items": []
            };

            for(index in this.nomenclatures){
                if (this.nomenclatures[index].amount !== 0){
                    itemRequest = {
                        nomenclature_id: this.nomenclatures[index].id,
                        amount: this.nomenclatures[index].amount
                    }
                    request.items.push(itemRequest);
                }
            }

            if (request.items.length === 0){
                alert("Не выбрана номенклатура для покупки")
                return;
            }

            saleAPI.save(request).then(
                response => {
                    items = [];
                    alert("Продажа успешно сохранена");
                },
                response => {
                    this.error = "Ошибка создания продажи. Обратитесь к администратору";
                    this.showErrors = true;
                }
            );

            for(index in this.nomenclatures){
                this.nomenclatures[index].amount = 0;
            }
        },

        clearNomenclatures: function () {
            for(index in this.nomenclatures){
                this.nomenclatures[index].amount = 0;
            }
        }},

    created: function() {
        nomenclatureAPI.get().then(result =>
                result.json().then(
                    data => data.forEach(
                        nomenclature => {
                            nomenclatureItem = {
                                id : nomenclature.id,
                                name : nomenclature.name,
                                description : nomenclature.description,
                                amount : 0,
                            };
                            this.nomenclatures.push(nomenclatureItem)
                        })),
            result =>{
                this.error = "Ошибка определения списка номенклатуры. Обратитесь к администратору";
                this.showErrors = true;
            })
    }
});

var app = new Vue({
    el: '#app',
    data: {
        nomenclatures: []
    },
    template: '<div>' +
                    '<nomenclature-list :nomenclatures="nomenclatures"/>' +
                '</div>',


});