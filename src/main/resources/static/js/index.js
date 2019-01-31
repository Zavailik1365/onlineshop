
var app = new Vue({
    el: '#app',
    template: '<v-app> ' +
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
                    '<v-layout align-center justify-center>' +
                        'Добро пожаловать в наш интернет-магазин!' +
                        '<v-btn color="success" href="/sales">К покупкам</v-btn>\n' +
                    '</v-layout>' +
                '</v-content> ' +
        '</v-app>',
    data: function () {
        return {
            name: frontendData.profile.name
        }
    }
});