/*global angular*/
var appMessagesCtrlModule = angular.module('app.MessagesCtrl', []);

appMessagesCtrlModule.controller('MessagesCtrl', function ($rootScope, $scope, $location, Messages, Login) {
    "use strict";
    var reset = function () {
        $scope.selectedUser = null;
        $scope.subject = "";
        $scope.content = "";
        $scope.users = [];
        $scope.messages = [];
    };

    reset();

    if (!$rootScope.userId) {
        $location.path("/login");
    }

    $scope.init = function () {
        Messages.getActiveUsers(function (response) {
            // Transform list to dictionary
            $scope.users = [];
            for (var user in response.data) {
                if (response.data.hasOwnProperty(user)) {
                    $scope.users.push(response.data[user].username);
                }
            }
            $scope.$apply();
        });

        Messages.addOnMessageListener(function (response) {
            $scope.messages.push(response.data);
            $scope.$apply();
        });

        Messages.addOnNewUserListener(function (response) {
            for (var user in $scope.users) {
                if ($scope.users.hasOwnProperty(user)) {
                    if ($scope.users[user] === response.data.username) {
                        return;
                    }
                }
            }
            $scope.users.push(response.data.username);
            $scope.$apply();
        });

        Messages.addOnRemovedUserListener(function (response) {
            for (var user in $scope.users) {
                if ($scope.users.hasOwnProperty(user)) {
                    if ($scope.users[user] === response.data.username) {
                        $scope.users.splice(parseInt(user), 1);
                        $scope.$apply();
                        return;
                    }
                }
            }
        })
    };

    $scope.send = function () {
        Messages.sendMessage($rootScope.userId, $scope.selectedUser, $scope.subject, $scope.content);
    };

    $scope.logout = function () {
        Login.logout($rootScope.userId,
            function () {
                $rootScope.userId = null;
                reset();
                $location.path('/login');
                $scope.$apply();
            },
            function (response) {
                alert(response.message);
            });
    };

    $scope.init();

});
