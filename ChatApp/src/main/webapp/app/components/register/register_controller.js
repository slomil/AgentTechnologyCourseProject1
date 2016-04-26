var appRegisterCtrlModule = angular.module('app.RegisterCtrl', []);

appRegisterCtrlModule.controller('RegisterCtrl', function ($rootScope, $scope, $location, Register) {
    "use strict";

    $scope.username = "";
    $scope.password = "";
    $scope.renteredPassword = "";
    $scope.alertMessage = null;

    if ($rootScope.userId) {
        $location.path("/messages");
    }

    $scope.successfulRegistration = function (data) {
        $rootScope.userId = data.username;
        $scope.alertMessage = null;
        $location.path('/login');
        $scope.$apply();
    };

    $scope.register = function () {
        if (!$scope.username) {
            $scope.alertMessage = 'Username cannot be empty.';
        } else if (!$scope.password) {
            $scope.alertMessage = 'Password cannot be empty.';
        } else if ($scope.password != $scope.renteredPassword) {
            $scope.alertMessage = 'Passwords do not match.'
        } else {
            $scope.alertMessage = null;
        }

        if ($scope.alertMessage) {
            return;
        }

        Register.register($scope.username, $scope.password,
            function (response) {
                $scope.successfulRegistration(response.data);
            },
            function (response) {
                $scope.alertMessage = "Error: " + response.message;
            }
        );
    };

    $scope.login = function () {
        $location.path('/login');
    };
});
