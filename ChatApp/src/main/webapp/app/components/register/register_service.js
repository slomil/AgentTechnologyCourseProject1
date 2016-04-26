/*global angular*/
var appRegisterModule = angular.module('app.Register', []);

appRegisterModule.factory('Register', function (WebSocket) {
    "use strict";

    return {
        register: function (username, password, onSuccess, onError) {
            WebSocket.addOnMessageListener('register', function (data) {
                var object = JSON.parse(data);
                if (object.success) {
                    object.data = JSON.parse(object.payload);
                    onSuccess(object);
                } else {
                    onError({message: object.payload});
                }
            });

            WebSocket.send({
                username: username,
                password: password
            }, 'register')
        }
    };

});
