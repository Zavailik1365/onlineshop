var nomenclatureAPI = Vue.resource("/rest-api/nomenclatures");

Vue.component('nomenclature-list', {
    props:['nomenclatures'],
    data: function(){
        return {
        name: frontendData.profile.name,
        url: "",
        error: "",
        showErrors: false
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
                            '<v-list two-line>' +
                            '<v-btn' +
                                ' color="success"' +
                                ' v-if="!showErrors"' +
                                ' href = "/admin/nomenclature/">' +
                                '<v-icon dark>add</v-icon>' +
                                'Добавить' +
                            '</v-btn>' +
                                ' <v-list-tile' +
                                    ' v-for="nomenclature in nomenclatures"' +
                                    ' :key="nomenclature.id"' +
                                    ' avatar>' +
                                    ' <v-list-tile-content>' +
                                        '<v-list-tile-title v-text="nomenclature.name"></v-list-tile-title>' +
                                        '<v-list-tile-sub-title v-html="nomenclature.description"></v-list-tile-sub-title>'+
                                    ' </v-list-tile-content>' +
                                    '<v-list-tile-action> ' +
                                         '<v-btn icon ripple :href="url + nomenclature.id">' +
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
          nomenclatureAPI.get().then(result =>
            result.json().then(
                data => data.forEach(
                    nomenclature => {
                        this.nomenclatures.push(nomenclature)
                    })),
              result =>{
                  this.error = "Ошибка определения списка номенклатуры. Обратитесь к администратору";
                  this.showErrors = true;
              })
        this.url = "nomenclature/";
    }
});

var app = new Vue({
    el: '#app',
    template: '<div>' +
                '<nomenclature-list :nomenclatures="nomenclatures"/>' +
               '</div>',
    data: function () {
        return {
            nomenclatures: []
        }
    }
});