/*global angular*/
var appWebSocketModule = angular.module('app.WebSocket', []);

appWebSocketModule.factory('WebSocket', function ($location, $interval) {
    "use strict";

    var socket = null,
        onMessageListeners = {};

    return {
        openSocket: function () {
            socket = new WebSocket('ws://' + $location.host() + ":" + $location.port() + '/chat_app/data');
            socket.onmessage = function (event) {
                var object = JSON.parse(event.data),
                    listener = onMessageListeners[object.type];

                if (listener) {
                    listener(event.data);
                }
            };
        },
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

            if (socket.readyState != -1) {
                try {
                    socket.send(JSON.stringify(data));
                } catch (ex) {
                    console.log(ex);
                }
            } else {
                var intervalId = $interval(function () {
                    if (socket.readyState != -1) {
                        socket.send(JSON.stringify(data));
                        $interval.clear(intervalId);
                    }
                }, 1000);
            }
        },
        addOnMessageListener: function (type, listener) {
            onMessageListeners[type] = listener;
        },
        setOnCloseListener: function (listener) {
            socket.onclose = function() {
                socket = null;
                listener();
            }
        }
    };

});