/*global angular*/
var appMainCtrlModule = angular.module('app.MainCtrl', []);

appMainCtrlModule.controller('MainCtrl', function ($rootScope, $scope, $location, $route, Login) {
    "use strict";

    var redirectToLogin = function () {
        if ($location.path().lastIndexOf('/login', 0) === 0) {
            $route.reload();
        } else {
            $location.path('/login');
        }
    },
        redirectToHome = function () {
            if ($location.path().lastIndexOf('/messages', 0) === 0) {
                $route.reload();
            } else {
                $location.path('/messages');
            }
        };


    if ($rootScope.userId && $rootScope.accessToken) {
        Login.isLogged($rootScope.userId, $rootScope.accessToken, redirectToHome, redirectToLogin);
    } else {
        redirectToLogin();
    }

});