var salesAdminAPI = Vue.resource("/rest-api/admin/sales");
var saleAdminDeleteAPI = Vue.resource("/rest-api/admin/sale/{id}");
var salesAPI = Vue.resource("/rest-api/sales");

Vue.component('sales-list', {
    props:['sales'],
    data: function(){
        return {
            name: frontendData.profile.name,
            url: "",
            error: "",
            showErrors: false,
         }
    },
    template:
        '<v-app>' +
            '<v-toolbar app dark color="primary">' +
                '<v-toolbar-title class="white--text">Onlineshop</v-toolbar-title>' +
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
                            '<v-btn' +
                                ' color="primary"' +
                                ' v-if="!showErrors"' +
                                ' href = "/sale">' +
                                'Добавить' +
                            '</v-btn>' +
                            '<v-list two-line>' +
                                ' <v-list-tile' +
                                ' v-for="sale in sales"' +
                                ' :key="sale.id"' +
                                ' avatar>' +
                                ' <v-list-tile-content>' +
                                    ' <v-list-tile-title v-text="sale.title"></v-list-tile-title>' +
                                    '<v-list-tile-sub-title v-html="sale.username"></v-list-tile-sub-title>' +
                                ' </v-list-tile-content>' +
                                '<v-list-tile-action> ' +
                                    '<div>' +
                                        '<v-btn icon ripple :href="url + sale.id">' +
                                            '<v-icon color="grey lighten-1">edit</v-icon>' +
                                         '</v-btn>' +
                                        '<v-btn icon ripple @click="deleteSale(sale)">' +
                                            '<v-icon color="grey lighten-1">delete</v-icon>' +
                                        '</v-btn>' +
                                    '</div>' +
                               '</v-list-tile-action>' +
                                ' </v-list-tile>' +
                            ' </v-list>' +
                        ' </v-flex>' +
                    '</v-layout>' +
                '</v-container>' +
            '</v-content>' +
        '</v-app>',
    methods:{
        deleteSale: function (sale) {

            this.showErrors = false;

            saleAdminDeleteAPI.delete({id: sale.id}).then(response =>{
                for (item in this.sales){
                    if (this.sales[item] == sale){
                        this.sales.splice(item, 1);
                        break;
                        }
                    }
                },
                result =>{
                    this.error = "Ошибка удаления продажи. Обратитесь к администратору";
                    this.showErrors = true;
                });

        }
    },
    created: function() {

        this.url = "sale/";

        salesAdminAPI.get().then(result =>
                result.json().then(
                    data => data.forEach(
                        sale => {
                            representation = {
                                id:sale.id,
                                username:sale.userName,
                                title:"Продажа № " + sale.id,
                            };
                            this.sales.push(representation);
                        })),
            result =>{
                this.error = "Ошибка определения списка продаж. Обратитесь к администратору";
                this.showErrors = true;
            });
    }
});

var app = new Vue({
    el: '#app',
    template: '<div>' +
            '<sales-list :sales="sales"/>' +
        '</div>',
    data: function () {
        return {
            sales: []
        }
    }
});