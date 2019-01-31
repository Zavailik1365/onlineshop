var userAPI = Vue.resource("/rest-api/admin/user");

Vue.component('user-list', {
    props:['users'],
    data: function(){
        return {
        name: frontendData.profile.name,
        url: ""
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
                            '<v-list two-line>' +
                                ' <v-list-tile' +
                                    ' v-for="user in users"' +
                                    ' :key="user.id"' +
                                    ' avatar>' +
                                    ' <v-list-tile-content>' +
                                        ' <v-list-tile-title v-text="user.name"></v-list-tile-title>' +
                                        '<v-list-tile-sub-title v-html="user.fullname"></v-list-tile-sub-title>'+
                                    ' </v-list-tile-content>' +
                                    '<v-list-tile-action> ' +
                                         '<v-btn icon ripple :href="url">' +
                                            '<v-icon color="grey lighten-1">edit</v-icon>' +
                                         '</v-btn>' +
                                    '</v-list-tile-action>' +
                                ' </v-list-tile>' +
                            ' </v-list>' +
                        ' </v-flex>' +
                    '</v-layout>' +
                '</v-container>' +
           '</v-content>' +
        '</v-app>',
      created: function() {
        userAPI.get().then(result =>
            result.json().then(
                data => data.forEach(
                    user => {
                        this.users.push(user);
                        this.url = "user/" + user.id;
                    })))
    }
});

var app = new Vue({
    el: '#app',
    template: '<div>' +
                '<user-list :users="users"/>' +
               '</div>',
    data: function () {
        return {
        users: []
        }
    }
});