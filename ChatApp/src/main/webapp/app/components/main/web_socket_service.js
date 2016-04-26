/*global angular*/
var appWebSocketModule = angular.module('app.WebSocket', []);

appWebSocketModule.factory('WebSocket', function ($location) {
    "use strict";

    var socket = null,
        onMessageListeners = {},
        openSocket = function () {
            socket = new WebSocket('ws://localhost:' + $location.port() + '/chat_app/data');
            socket.onmessage = function (event) {
                var object = JSON.parse(event.data),
                    listener = onMessageListeners[object.type];

                if (listener) {
                    listener(event.data);
                }
            };
        };

    return {
        openSocket: openSocket(),
        closeSocket: function () {
            socket.closeSocket();
            socket = null;
        },
        send: function (payload, type) {
            if (!socket) {
                this.openSocket();
            }

            var data = {};
            if (type) {
                data.payload = JSON.stringify(payload);
                data.type = type;
            } else {
                data = payload;
            }

            socket.send(JSON.stringify(data));
        },
        addOnMessageListener: function (type, listener) {
            onMessageListeners[type] = listener;
        }
    };

});