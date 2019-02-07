var userAPI = Vue.resource("/rest-api/user");
var userAdminAPI = Vue.resource("/rest-api/admin/user/{id}");
var rolesAdminAPI = Vue.resource("/rest-api/admin/roles");

Vue.component('user-detals', {
        data: function() {
            return {
                name: "",
                valid: true,
                error: "",
                showErrors: false,
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
                                        ' required label="Логин"' +
                                        ' placeholder="SidorovA"' +
                                        ' type="text"' +
                                        ' v-model="username"' +
                                        ' v-if="!showErrors"' +
                                        ' :rules="[rules.required, rules.min2]"/>' +
                                    '<v-text-field' +
                                        ' required label="Пароль"' +
                                        ' placeholder="1406558"' +
                                        ' type="password"' +
                                        ' v-model="password"' +
                                        ' v-if="!showErrors"/>' +
                                    '<v-text-field required label="Полное имя"' +
                                        ' placeholder="Sidorov Ivan"' +
                                        ' type="text"' +
                                        ' v-model="fullname"' +
                                        ' v-if="!showErrors"' +
                                        ' :rules="[rules.required]"/>' +
                                    '<v-text-field required label="Email"' +
                                        ' placeholder="Email"' +
                                        ' type="text" ' +
                                        ' v-model="email"' +
                                        ' v-if="!showErrors"' +
                                        ' :rules="[rules.required, rules.emailMatch]"/>'+
                                    '<v-select' +
                                        ' :items="rolesitems"' +
                                        ' v-model="roles"\n' +
                                        '  label="Роли"\n' +
                                        ' multiple' +
                                        ' v-if = "isAdmin&&!showErrors">' +
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
                                       ' v-if = "isAdmin&&!showErrors""/>' +
                                    '<v-btn color="success" @click = "save" v-if="!showErrors">Сохранить</v-btn>' +
                                    '<v-btn @click = "clear" v-if="!showErrors">Очистить</v-btn>' +
                                    '<v-btn href="/admin/users" v-if="!showErrors">' +
                                            '<v-icon >keyboard_return</v-icon>' +
                                    '</v-btn>' +
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
                this.showErrors = false;
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
                        response =>
                        {
                            alert("Данные пользователя сохранены.")
                        },
                        result =>{
                            this.error = "Ошибка сохраненения данных пользователя. Обратитесь к администратору";
                            this.showErrors = true;
                        });
                }else{
                    var request = {
                        username: this.username,
                        fullname: this.fullname,
                        email: this.email,
                        password: this.password
                    }

                    userAPI.update(request).then(
                        response =>
                        {
                            alert("Данные пользователя сохранены.")
                        },
                        result =>{
                            this.error = "Ошибка сохраненения данных пользователя. Обратитесь к администратору";
                            this.showErrors = true;
                        })
                }
            },

        },
        created: function () {

            this.isAdmin = frontendData.profile.isAdmin;
            this.id = frontendData.profile.userId;
            this.name = frontendData.profile.name;

            if (this.isAdmin && this.id != null) {
                userAdminAPI.get({id: this.id}).then(
                    response => {
                        this.username =  response.body.name;
                        this.fullname =  response.body.fullname;
                        this.email =  response.body.email;
                        this.active =  response.body.active;
                        this.roles =  response.body.roles;
                    },
                    result =>{
                        this.error = "Ошибка получения данных пользователя. Обратитесь к администратору";
                        this.showErrors = true;
                    });

            } else {
                userAPI.get().then(
                    response => {
                       this.username =  response.body.name;
                       this.fullname =  response.body.fullname;
                       this.email =  response.body.email;
                       this.active =  response.body.active;
                       this.roles =  response.body.roles;
                    },
                    result =>{
                        this.error = "Ошибка получения данных пользователя. Обратитесь к администратору";
                        this.showErrors = true;
                    });

                if (this.isAdmin){
                    rolesAdminAPI.get().then(
                        response => {
                            this.rolesitems =  response.body
                        },
                        result =>{
                            this.error = "Ошибка получения данных о ролях. Обратитесь к администратору";
                            this.showErrors = true;
                        });
                    }
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