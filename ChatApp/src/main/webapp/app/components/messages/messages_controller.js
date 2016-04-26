/*global angular*/
var appMessagesCtrlModule = angular.module('app.MessagesCtrl', []);

appMessagesCtrlModule.controller('MessagesCtrl', function ($rootScope, $scope, $location, Messages) {
    "use strict";

    $scope.selectedUser = null;
    $scope.newMessage = "";
    $scope.users = {};
    $scope.messages = [];

    if (!$rootScope.userId) {
        $location.path("/login");
    }

    $scope.init = function () {
        Messages.getActiveUsers(function (response) {
            // Transform list to dictionary
            var userIdx, user;
            $scope.users = {};
            for (userIdx in response.data) {
                if (response.data.hasOwnProperty(userIdx)) {
                    user = response.data[userIdx];
                    $scope.users[user.username] = false;
                }
            }
            $scope.$apply();
        });

        Messages.addOnNewUserListener(function (response) {
            $scope.users[response.data.username] = false;
            $scope.$apply();
        });

        Messages.addOnRemovedUserListener(function (response) {
            delete $scope.users[response.data.username];
            $scope.$apply();
        })
    };

    $scope.send = function () {

    };

    $scope.init();

});
