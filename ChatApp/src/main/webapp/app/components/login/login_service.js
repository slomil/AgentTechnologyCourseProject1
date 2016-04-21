/*global angular*/
var appLoginModule = angular.module('app.Login', []);

appLoginModule.factory('Login', function (WebSocket) {
    "use strict";

    return {
        login: function (username, password, onSuccess, onError) {
            WebSocket.addOnMessageListener(function (data) {
                console.log(data);
            }, true);

            WebSocket.send({
                username: username,
                password: password
            }, 'login')
        },
        isLogged: function (userId, accessToken, onSuccess, onError) {

        }
    };

});