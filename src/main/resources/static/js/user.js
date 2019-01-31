var userAPI = Vue.resource("/rest-api/user");
var userAdminAPI = Vue.resource("/rest-api/admin/user/{id}");
var rolesAdminAPI = Vue.resource("/rest-api/admin/roles");

Vue.component('user-detals', {
        data: function() {
            return {
                name: "",
                valid: true,
                id: "",
                username: "",
                fullname: "",
                password: "",
                email: "",
                active: "",
                roles: [],
                rolesitems:[],
                rules: {
                    required: value => !!value || 'Поле обязательно для заполнения.',
                    min2: v => v.length >= 2 || 'Минимум 2 символа',
                    emailMatch: value => {
                        const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                        return pattern.test(value) || 'Не верный формат email.'
                    }
                }
            }
        },
        template: '<v-app>' +
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
                    '<v-content>' +
                        '<v-layout align-center justify-center row fill-height>' +
                            '<v-flex lg3>' +
                            ' <v-form' +
                                ' ref="form"' +
                                ' v-model="valid"' +
                                ' lazy-validation>' +
                                    '<v-text-field' +
                                        ' required label="Логин"' +
                                        ' placeholder="SidorovA"' +
                                        ' type="text"' +
                                        ' v-model="username"' +
                                        ' :rules="[rules.required, rules.min2]"/>' +
                                    '<v-text-field' +
                                        ' required label="Пароль"' +
                                        ' placeholder="1406558"' +
                                        ' type="password"' +
                                        ' v-model="password"/>' +
                                    '<v-text-field required label="Полное имя"' +
                                        ' placeholder="Sidorov Ivan"' +
                                        ' type="text"' +
                                        ' v-model="fullname"' +
                                        ' :rules="[rules.required]"/>' +
                                    '<v-text-field required label="Email"' +
                                        ' placeholder="Email"' +
                                        ' type="text" ' +
                                        ' v-model="email"' +
                                        ' :rules="[rules.required, rules.emailMatch]"/>'+
                                    '<v-select' +
                                        ' :items="rolesitems"' +
                                        ' v-model="roles"\n' +
                                        '  label="Роли"\n' +
                                        ' multiple' +
                                        ' v-if = "isAdmin">' +
                                        '<template' +
                                            ' slot="selection"' +
                                            ' slot-scope="{ item, index }">' +
                                            '<v-chip v-if="index === 0">' +
                                                ' <span>{{ item }}</span>' +
                                            '</v-chip>' +
                                            '<span' +
                                                ' v-if="index === 1"' +
                                                ' class="grey--text caption">' +
                                                '(+{{ roles.length - 1 }} others)' +
                                            '</span>' +
                                        '</template>' +
                                    '</v-select>' +
                                    '<v-checkbox' +
                                        ' required label="Активный"' +
                                        ' v-model="active"' +
                                       ' v-if = "isAdmin"/>' +
                                    '<v-btn color="success" @click = "save">Сохранить</v-btn>' +
                                    '<v-btn @click = "clear">Очистить</v-btn>' +
                                '</v-form>' +
                            '</v-flex>' +
                        '</v-layout>' +
                    '</v-content>' +
                '</v-app> ',
        methods: {

            clear: function () {
                this.username = "";
                this.fullname = "";
                this.email = "";
                this.password = "";
                this.roles = []
            },

            save: function () {

                if (!this.$refs.form.validate()) {
                    return;
                }

                if (this.isAdmin) {
                    var request = {
                        username: this.username,
                        fullname: this.fullname,
                        email: this.email,
                        active: this.active,
                        password: this.password,
                        roles: this.roles
                    }
                    userAdminAPI.update({id: this.id}, request).then(
                        alert("Данные пользователя сохранены.")
                    )
                }else{
                    var request = {
                        username: this.username,
                        fullname: this.fullname,
                        email: this.email,
                        password: this.password
                    }

                    userAPI.update(request).then(
                        alert("Данные пользователя сохранены.")
                    )
                }
            },

        },
        created: function () {

            this.isAdmin = frontendData.profile.isAdmin;
            this.id = frontendData.profile.id;
            this.name = frontendData.profile.name;

            if (this.isAdmin) {
                userAdminAPI.get({id: this.id}).then( response => {
                    this.username =  response.body.name;
                    this.fullname =  response.body.fullname;
                    this.email =  response.body.email;
                    this.active =  response.body.active;
                    this.roles =  response.body.roles;
                })

                rolesAdminAPI.get().then( response => {
                    this.rolesitems =  response.body
                })

            } else {
                userAPI.get().then( response => {
                   this.username =  response.body.name;
                   this.fullname =  response.body.fullname;
                   this.email =  response.body.email;
                   this.active =  response.body.active;
                   this.roles =  response.body.roles;
                })
            }
        }
    }
);

var app = new Vue({
    el: '#app',
    template: '<div>' +
                '<user-detals></user-detals>' +
              '</div>',
});