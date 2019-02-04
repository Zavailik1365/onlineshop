
var app = new Vue({
    el: '#app',
    template: '<v-app> ' +
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
                '<v-content>' +
                    '<v-layout align-center justify-center column fill-height>' +
                        '<blockquote class="blockquote">' +
                            'Команда интернет-магазина «Online shop» рада приветствовать вас! Мы работаем на <br>' +
                            'столичном рынке розеток более 10 лет и каждый год количество наших клиентов<br>' +
                            'прирастает довольными покупателями.' +
                        '</blockquote>' +
                        '<v-btn color="success" href="/purchase">К покупкам</v-btn>' +
                    '</v-layout>' +
                '</v-content> ' +
        '</v-app>',
    data: function () {
        return {
            name: frontendData.profile.name,
            isAdmin: frontendData.profile.isAdmin
        }
    }
});