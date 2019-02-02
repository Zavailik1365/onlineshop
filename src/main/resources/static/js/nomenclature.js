var nomenclatureAPI = Vue.resource("/rest-api/nomenclature/{id}");
var nomenclatureAdminAPI = Vue.resource("/rest-api/admin/nomenclature/{id}");

Vue.component('user-detals', {
        data: function() {
            return {
                username: "",
                valid: true,
                id: "",
                name: "",
                description: "",
                error: "",
                showErrors: false,
                rules: {
                    required: value => !!value || 'Поле обязательно для заполнения.',
                    min2: v => v.length >= 2 || 'Минимум 2 символа'
                    }
                }
            },
        template: '<v-app>' +
                    '<v-toolbar app dark color="primary">' +
                        '<v-toolbar-title class="white--text">Onlineshop</v-toolbar-title>' +
                            '<v-spacer></v-spacer>' +
                            '<v-btn flat href = "/user">\n' +
                                '{{ username }}' +
                            '</v-btn>' +
                            '<v-btn icon href="/logout">' +
                                '<v-icon>exit_to_app</v-icon>' +
                            '</v-btn>' +
                    '</v-toolbar>' +
                    '<v-content>' +
                        '<v-layout align-center justify-center row fill-height>' +
                            '<v-flex lg3>' +
                            '<v-input' +
                                ' prepend-icon="error"' +
                                ' v-if="showErrors"' +
                                ' v-model="error">' +
                                '{{error}}' +
                            '</v-input>' +
                            ' <v-form' +
                                ' ref="form"' +
                                ' v-model="valid"' +
                                ' lazy-validation>' +
                                    '<v-text-field' +
                                        ' required label="Наименование"' +
                                        ' placeholder="Розетка"' +
                                        ' type="text"' +
                                        ' v-model="name"' +
                                        ' :rules="[rules.required, rules.min2]"' +
                                        ' v-if="!showErrors"/>' +
                                    '<v-textarea' +
                                        ' name="input-7-4"' +
                                        ' label="Описание товара"' +
                                        ' placeholder="Розетка электрическая"' +
                                        ' value="description"' +
                                        ' v-model="description"' +
                                        ' v-if="!showErrors"/>' +
                                    '<v-btn color="success" @click = "save" v-if="!showErrors">Сохранить</v-btn>' +
                                    '<v-btn @click = "clear" v-if="!showErrors">Очистить</v-btn>' +
                                    '<v-btn href="/admin/nomenclatures" v-if="!showErrors">К списку</v-btn>' +
                                '</v-form>' +
                            '</v-flex>' +
                        '</v-layout>' +
                    '</v-content>' +
                '</v-app> ',
        methods: {

            clear: function () {
                this.name = "";
                this.description = "";
            },

            save: function () {

                if (!this.$refs.form.validate()) {
                    return;
                }

                this.showErrors = false;
                var request = {
                    name: this.name,
                    description: this.description
                };

                if (this.id == ""){
                    nomenclatureAdminAPI.save(request)
                        .then(response => {
                                alert("Данные номенклатуры сохранены.");
                                this.id = response.body.id;},
                            response =>{
                                this.error = "Ошибка сохраненения данных номенклатуры. Обратитесь к администратору";
                                this.showErrors = true;
                        })
                }else {
                    nomenclatureAdminAPI.update({id: this.id}, request)
                        .then(response =>
                            {
                                alert("Данные номенклатуры сохранены.")
                            },
                            result =>{
                                this.error = "Ошибка сохраненения данных номенклатуры. Обратитесь к администратору";
                                this.showErrors = true;
                        })
                }
            },

        },
        created: function () {

            this.isAdmin = frontendData.profile.isAdmin;
            this.id = frontendData.profile.nomenclatureId;
            this.username = frontendData.profile.name;

            if (this.id !== ""){
                nomenclatureAPI.get({id: this.id}).then(
                    response => {
                        this.description =  response.body.description;
                        this.name =  response.body.name;
                        },
                    response =>{
                        this.error = "Ошибка получения данных номенклатуры. Обратитесь к администратору";
                        this.showErrors = true;
                    })
            };
    }
});

var app = new Vue({
    el: '#app',
    template: '<div>' +
                '<user-detals></user-detals>' +
              '</div>',
});