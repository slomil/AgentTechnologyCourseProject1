/*global angular*/
var appLoginModule = angular.module('app.Login', []);

appLoginModule.factory('Login', function (WebSocket) {
    "use strict";

    return {
        login: function (username, password, onSuccess, onError) {
            WebSocket.addOnMessageListener('login', function (data) {
                var object = JSON.parse(data);
                if (object.success) {
                    if (onSuccess) {
                        object.data = JSON.parse(object.payload);
                        onSuccess(object);
                    }
                } else {
                    onError({message: object.payload});
                }
            });

            WebSocket.send({
                username: username,
                password: password
            }, 'login')
        },
        logout: function (username, onSuccess, onError) {
            WebSocket.addOnMessageListener('logout', function (data) {
                var object = JSON.parse(data);
                if (object.success) {
                    if (onSuccess) {
                        onSuccess(data);
                    }
                } else {
                    if (onError) {
                        onError({message: object.payload});
                    }
                }
            });

            WebSocket.send({
                username: username
            }, 'logout');
        }
    };

});