/*global angular*/
var appMessagesModule = angular.module('app.Messages', []);

appMessagesModule.factory('Messages', function (WebSocket) {
    "use strict";

    return {
        getActiveUsers: function (onSuccess, onError) {
            WebSocket.addOnMessageListener('users', function (data) {
                var object = JSON.parse(data);
                if (object.success) {
                    object.data = JSON.parse(object.payload);
                    if (onSuccess) {
                        onSuccess(object);
                    }
                } else {
                    if (onError) {
                        onError({message: object.payload});
                    }
                }
            });

            WebSocket.send({}, 'users');
        },
        addOnNewUserListener: function (onNewUser) {
            WebSocket.addOnMessageListener('new_user', function (data) {
                var object = JSON.parse(data);
                if (object.success) {
                    object.data = JSON.parse(object.payload);
                    if (onNewUser) {
                        onNewUser(object);
                    }
                }
            });
        },
        addOnRemovedUserListener: function (onRemovedUser) {
            WebSocket.addOnMessageListener('removed_user', function(data) {
               var object = JSON.parse(data);
                if (object.success) {
                    object.data = JSON.parse(object.payload);
                    if (onRemovedUser) {
                        onRemovedUser(object);
                    }
                }
            });
        }
    };

});
