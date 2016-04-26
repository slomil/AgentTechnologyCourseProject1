/*global angular*/
var appLoginCtrlModule = angular.module('app.LoginCtrl', []);

appLoginCtrlModule.controller('LoginCtrl', function ($rootScope, $scope, $location, Login) {
    "use strict";

    $scope.username = "";
    $scope.password = "";
    $scope.alertMessage = null;

    if ($rootScope.userId) {
        $location.path("/messages");
    }

    $scope.successfulLogin = function (data) {
        $rootScope.userId = data.username;
        $scope.alertMessage = null;
        $location.path('/');
        $scope.$apply();
    };

    $scope.login = function () {
        if (!$scope.username) {
            $scope.alertMessage = 'Username cannot be empty.';
        } else if (!$scope.password) {
            $scope.alertMessage = 'Password cannot be empty.';
        } else {
            $scope.alertMessage = null;
        }

        if ($scope.alertMessage) {
            return;
        }

        Login.login($scope.username, $scope.password,
            function (response) {
                $scope.successfulLogin(response.data);
            },
            function (response) {
                $scope.alertMessage = "Error: " + response.message;
            }
        );
    };

    $scope.signup = function () {
        $location.path('/signup');
    };

});