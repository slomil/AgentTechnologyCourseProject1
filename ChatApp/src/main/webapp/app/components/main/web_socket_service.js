/*global angular*/
var appWebSocketModule = angular.module('app.WebSocket', []);

appWebSocketModule.factory('WebSocket', function () {
    "use strict";

    // TODO: Remove ID listeners
    var socket = null,
        idCounter = 0,
        onMessageListeners = [],
        onMessageIdListeners = {},
        openSocket = function () {
            socket = new WebSocket('ws://localhost:8080/chat_app/data');
            socket.onmessage = function (event) {

                if (event.data.id && onMessageIdListeners.hasOwnProperty(event.data.id)) {
                    onMessageIdListeners[event.data.id](event.data);
                    delete onMessageIdListeners[event.data.id];
                }

                for (var listener in onMessageListeners) {
                    if (onMessageListeners.hasOwnProperty(listener)) {
                        onMessageListeners[listener](event.data);
                    }
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
        addOnMessageListener: function (listener, createId) {
            if (createId) {
                onMessageIdListeners[idCounter++] = listener;
            } else {
                onMessageListeners.push(listener);
            }
        }
    };

});