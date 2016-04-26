/*global angular*/
var app = angular.module('app', ['app.controllers', 'app.services', 'ngRoute', 'ui.bootstrap']);

app.config(function ($routeProvider) {
    "use strict";
    $routeProvider
        .when('/', {
            templateUrl: 'app/components/main/main_view.html',
            controller: 'MainCtrl'
        })
        .when('/messages', {
            templateUrl: 'app/components/messages/messages_view.html',
            controller: 'MessagesCtrl'
        })
        .when('/login', {
            templateUrl: 'app/components/login/login_view.html',
            controller: 'LoginCtrl'
        })
        .when('/signup', {
            templateUrl: 'app/components/register/register_view.html',
            controller: 'RegisterCtrl'
        })
        .when('/logout', {
            templateUrl: 'app/components/auth/login_view.html',
            controller: 'LogoutCtrl'
        })
        .otherwise('/');
});
