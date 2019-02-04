var nomenclatureAPI = Vue.resource("/rest-api/nomenclatures");
var saleAdminAPI = Vue.resource("/rest-api/admin/sale/{id}");
var salesAPI = Vue.resource("/rest-api/sale/{id}");

Vue.component('sale-detal', {
    props: ["nomenclatures"],
    data: function(){
        return {
            name: frontendData.profile.name,
            saleId: frontendData.profile.saleId,
            url: "",
            error: "",
            showErrors: false,
            dialog: false,
            editedItem: {
                nomenclatureId: "",
                nomenclatureName: "",
                amount: 0,
                description: ""
            },
            defaultItem: {
                nomenclatureId: "",
                nomenclatureName: "",
                amount: 0
            },
            editedIndex: -1,
            nomenclatureId: 0,
            nomenclatureDescription: "",
            saleItems: [],
            headers: [
                {
                    text: 'Номенклатура',
                    align: 'left',
                    sortable: true,
                    value: 'nomenclatureName'
                },
            {
                text: 'Количество',
                align: 'left',
                sortable: true,
                value: 'amount'
            }],
            rules: {
                required: value => !!value || 'Поле обязательно для заполнения.'
            }
        }
    },
    template:
        '<v-app>' +
            '<v-toolbar app dark color="primary">' +
                '<v-btn flat href="/">' +
                    '<v-toolbar-title class="white--text">Online shop</v-toolbar-title>' +
                '</v-btn>' +
                '<v-btn flat href="/admin/nomenclatures">Номенклатура</v-btn>' +
                '<v-btn flat href="/admin/users">Пользователи</v-btn>' +
                '<v-btn flat href="/admin/sales">Продажи</v-btn>' +
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
                            '<v-toolbar flat color="white">' +
                                '<v-toolbar-title>Товары</v-toolbar-title>' +
                                '<v-divider' +
                                    ' class="mx-2"' +
                                    ' inset' +
                                    ' vertical/>' +
                                '<v-spacer></v-spacer>' +
                                '<v-input' +
                                    ' prepend-icon="error"' +
                                    ' v-if="showErrors"' +
                                    ' v-model="error">' +
                                    '{{error}}' +
                                '</v-input>' +
                                '<v-dialog v-model="dialog" max-width="500px">' +
                                    '<v-btn' +
                                        ' slot="activator"' +
                                        ' color="primary"' +
                                        ' dark class="mb-2"' +
                                        ' v-if="!showErrors">' +
                                            'Добавить' +
                                    '</v-btn>' +
                                    '<v-card>' +
                                        '<v-card-title>' +
                                            '<span class="headline">{{ formTitle }}</span>' +
                                        '</v-card-title>' +
                                        '<v-card-text>' +
                                            '<v-form ref="form">' +
                                                '<v-container grid-list-md>' +
                                                    '<v-layout wrap>' +
                                                        '<v-flex xs14 sm7 md6>' +
                                                            '<v-select' +
                                                                ' :items="nomenclatures"' +
                                                                ' v-model="nomenclatureId"' +
                                                                ' item-text="name"' +
                                                                ' item-value="id"' +
                                                                ' :hint="`${nomenclatureDescription}`"' +
                                                                ' @input="updateDescription"' +
                                                                ' label="Номенклатура"' +
                                                                ' :rules="[rules.required]"/>' +
                                                        '</v-flex>' +
                                                        '<v-flex xs12 sm6 md4>' +
                                                        '<v-text-field' +
                                                                ' v-model="editedItem.amount"' +
                                                                ' label="Количество"' +
                                                                ' type="number"' +
                                                                ' min="0"' +
                                                                ' :rules="[rules.required]"/>' +
                                                        '</v-flex>' +
                                                    '</v-layout>' +
                                                '</v-container>' +
                                                '</v-form>' +
                                            '</v-card-text>' +
                                        '<v-card-actions>' +
                                            '<v-spacer></v-spacer>' +
                                            '<v-btn' +
                                                ' color="success"' +
                                                ' flat @click="saveItem">' +
                                                    'Добавить' +
                                            '</v-btn>' +
                                            '<v-btn' +
                                                ' color="blue darken-1"' +
                                                ' flat @click="close">' +
                                                'Отменить' +
                                            '</v-btn>' +
                                        '</v-card-actions>' +
                                    '</v-card>' +
                                '</v-dialog>' +
                            '</v-toolbar>' +
                            '<v-data-table' +
                                ' :headers="headers"' +
                                ' :items="saleItems"' +
                                ' class="elevation-1"' +
                                ' v-if="!showErrors">' +
                                '<template slot="items" slot-scope="props">' +
                                    '<td class="text-xs-left">{{ props.item.nomenclatureName }}</td>' +
                                    '<td class="text-xs-left">{{ props.item.amount }}</td>' +
                                    '<td class="justify-center layout px-0">' +
                                        '<v-icon' +
                                            ' small' +
                                            ' class="mr-2"' +
                                            ' @click="editItem(props.item)">' +
                                            'edit' +
                                        '</v-icon>' +
                                        '<v-icon' +
                                            ' small' +
                                            ' @click="deleteItem(props.item)">' +
                                            ' delete' +
                                        '</v-icon>' +
                                    '</td>' +
                                '</template>' +
                            '</v-data-table>' +
                            '<div v-if="!showErrors">' +
                                '<v-btn' +
                                    ' color="success"' +
                                    ' @click = "save"' +
                                    ' v-if="!showErrors">' +
                                        'Сохранить</v-btn>' +
                                    '<v-btn @click = "clear" v-if="!showErrors">Очистить</v-btn>' +
                                    '<v-btn href="/admin/sales" v-if="!showErrors">' +
                                        '<v-icon >keyboard_return</v-icon>' +
                                    '</v-btn>' +
                            '</div>' +
                        '</v-flex>' +
                    '</v-layout>' +
                 '</v-container>' +
            '</v-content>' +
        '</v-app>',
    methods:{

        clear: function () {
            this.saleItems = [];
            this.editedIndex = -1;
            this.dialog = false
        },

        save: function () {

            request = {
                "items": []
            };

            for (index in this.saleItems) {
                request.items.push(
                    {
                        "nomenclature_id": this.saleItems[index].nomenclatureId,
                        "amount": this.saleItems[index].amount
                    }
                )
            }
            if (this.saleId === "") {
                salesAPI.save(request).then(
                    response => {
                        this.saleId = response.body;
                        alert("Продажа успешно создана.");
                    },
                    response =>{
                        this.error = "Ошибка сохранения продажи данных продажи. Обратитесь к администратору";
                        this.showErrors = true;
                    })
            } else {
                saleAdminAPI.update({id:this.saleId}, request).then(
                    response => {
                        alert("Продажа успешно обновлена.")},
                    response =>{
                        this.error = "Ошибка определения данных продажи. Обратитесь к администратору";
                        this.showErrors = true;
                    });
            }
        },

        close () {
            this.dialog = false;
            setTimeout(() => {
                this.editedItem = Object.assign({}, this.defaultItem)
                this.editedIndex = -1
            }, 300)
        },

        saveItem () {

            if (!this.$refs.form.validate()) {
                return;
            }

            for (item in this.nomenclatures) {
                if (this.nomenclatures[item].id == this.nomenclatureId){
                    nomenclatureItem = this.nomenclatures[item];
                    this.nomenclatureId = 0;
                    break;
                }
            }

            this.editedItem.nomenclatureId = nomenclatureItem.id;
            this.editedItem.nomenclatureName = nomenclatureItem.name;

            if (this.editedIndex > -1) {
                Object.assign(this.saleItems[this.editedIndex], this.editedItem)
            } else {
                this.saleItems.push(this.editedItem)
            }
            this.close()
        },

        editItem (item) {
            this.editedIndex = this.saleItems.indexOf(item);
            this.editedItem = Object.assign({}, item);
            this.dialog = true;

            for (item in this.nomenclatures) {
                if (this.nomenclatures[item].id === this.editedItem.nomenclatureId){
                    this.nomenclatureId = this.nomenclatures[item].id;
                    break;
                }
            }
         },

        deleteItem (item) {
            const index = this.saleItems.indexOf(item)
            confirm('Вы хотите удалить строку?') && this.saleItems.splice(index, 1)
        },

        updateDescription(id){

            for (item in this.nomenclatures) {
                if (this.nomenclatures[item].id === id){
                    this.nomenclatureDescription = this.nomenclatures[item].description;
                    break;
                }
            }
        }
    },
    computed: {
        formTitle () {
            return this.editedIndex === -1 ? 'Добавление' : 'Редактирование';
        }
    },

    watch: {
        dialog (val) {
            val || this.close();
        }
    },

    created: function() {

        this.saleId = frontendData.profile.saleId;
        this.name = frontendData.profile.name;

        nomenclatureAPI.get().then(response =>
                response.json().then(
                    data => data.forEach(
                        nomenclature => {
                            this.nomenclatures.push(nomenclature)
                        })),
            response =>{
                this.error = "Ошибка определения списка номенклатуры. Обратитесь к администратору";
                this.showErrors = true;
            });

        if (this.saleId != ""){
                saleAdminAPI.get({id:this.saleId}).then(response =>
                    response.json().then(
                        data => data.forEach(
                            saleitem => {
                                this.saleItems.push(saleitem)
                            })),
                response =>{
                    this.error = "Ошибка определения данных продажи. Обратитесь к администратору";
                    this.showErrors = true;
                })
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<div>' +
                    '<sale-detal :nomenclatures="nomenclatures"/>' +
               '</div>',
    data: function () {
        return {
            nomenclatures: []
        }
    },
});