/*global angular*/
var appLoginCtrlModule = angular.module('app.LoginCtrl', []);

appLoginCtrlModule.controller('LoginCtrl', function ($rootScope, $scope, $location, Login) {
    "use strict";

    $scope.username = "";
    $scope.password = "";
    $scope.alertMessage = null;

    $scope.successfulLogin = function (data) {
        $rootScope.userId = data.userId;
        $rootScope.accessToken = data.accessToken;
        $scope.alertMessage = null;
        $location.path('/');
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
                var message = (response.data && response.data.message) ? response.data.message : "";
                $scope.alertMessage = "Status code: " + response.status + " " + message;
            }
        );
    };

    $scope.signup = function () {
        $location.path('/signup');
    };

});