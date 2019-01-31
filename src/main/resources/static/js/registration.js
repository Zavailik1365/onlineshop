var registrationAPI = Vue.resource("/registration");

var app = new Vue({
    el: '#app',
    data: function() {
        return {
            valid: true,
            username: "",
            fullname: "",
            password: "",
            email: "",
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
    template: '<v-app> ' +
                '<v-toolbar app dark color="primary">' +
                    '<v-toolbar-title class="white--text">Onlineshop</v-toolbar-title>' +
                '</v-toolbar>' +
                '<v-content>' +
                    '<v-layout align-center justify-center column fill-height>' +
                        '<v-flex lg3>' +
                            '<v-form ref="form" th:action="@{/registration}" method="post">' +
                                '<v-text-field' +
                                    ' required label="Логин"' +
                                    ' placeholder="SidorovA"' +
                                    ' type="text"' +
                                    ' name="username"' +
                                    ' v-model="username"' +
                                    ' :rules="[rules.required, rules.min2]"/>' +
                                '<v-text-field' +
                                    ' required label="Пароль"' +
                                    ' placeholder="1406558"' +
                                    ' type="password"' +
                                    ' v-model="password"' +
                                    ' name="password"' +
                                    ' :rules="[rules.required, rules.min2]"/>' +
                                '<v-text-field required label="Полное имя"' +
                                    ' placeholder="Sidorov Ivan"' +
                                    ' type="text"' +
                                    ' v-model="fullname"' +
                                    ' name="fullname"' +
                                    ' :rules="[rules.required]"/>' +
                                '<v-text-field required label="Email"' +
                                     ' placeholder="Email"' +
                                     ' type="text" ' +
                                     ' v-model="email"' +
                                     ' name="email"' +
                                     ' :rules="[rules.required, rules.emailMatch]"/>' +
                                '<div class="g-recaptcha"' +
                                    ' data-sitekey="6LcdFI4UAAAAAB_TutYjI2f7SsNyy54TuNES4ErS"/>' +
                                '<v-btn color="success" @click="save">Добавить</v-btn>' +
                                '<v-btn @click = "clear" >Очистить</v-btn>' +
                                '<v-btn href="/">На главную</v-btn>' +
                            '</v-form>' +
                        '</v-flex>' +
                    '</v-layout justify-center>' +
                '</v-content> ' +
            '</v-app>',
    methods: {

        clear: function () {
            this.username = "";
            this.fullname = "";
            this.email = "";
            this.password = "";
         },

        save: function () {

            if (!this.$refs.form.validate()) {
                return;
            }

            this.$refs.form.$el.submit();

        }
    }
});