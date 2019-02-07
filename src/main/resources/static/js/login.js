
var app = new Vue({
    el: '#app',
    data: function() {
        return {
            valid: true,
            username: "",
            password: "",
            rules: {
                required: value => !!value || 'Поле обязательно для заполнения.',
            }
        }
    },
    template: '<v-app> ' +
        '<v-toolbar app dark color="primary">' +
            '<v-btn flat href="/">' +
              '<v-toolbar-title class="white--text">Online shop</v-toolbar-title>' +
            '</v-btn>' +
        '</v-toolbar>' +
        '<v-content>' +
            '<v-layout align-center justify-center column fill-height>' +
                '<v-flex lg3>' +
                    '<v-form ref="form" th:action="@{/login}" method="post">' +
                        '<v-text-field' +
                            ' required label="Логин"' +
                            ' placeholder="Sidorov"' +
                            ' type="text"' +
                            ' name="username"' +
                            ' v-model="username"' +
                            ':rules="[rules.required]"/>' +
                        '<v-text-field' +
                            ' required label="Пароль"' +
                            ' placeholder="1406558"' +
                            ' type="password"' +
                            ' name="password"' +
                            ' v-model="password"/>' +
                        '<v-btn color="success" @click="login">Войти</v-btn>' +
                        '<v-btn href="/registration">Зарегистрироваться</v-btn>' +
                    '</v-form>' +
                '</v-flex>' +
            '</v-layout justify-center>' +
        '</v-content> ' +
        '</v-app>',
    methods: {

        login: function () {

            if (!this.$refs.form.validate()) {
                return;
            }

            this.$refs.form.$el.submit();

        }
    }
});