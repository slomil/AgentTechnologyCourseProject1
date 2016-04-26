/*global angular*/
var appMessagesCtrlModule = angular.module('app.MessagesCtrl', []);

appMessagesCtrlModule.controller('MessagesCtrl', function ($scope) {
    "use strict";

    $scope.selectedUser = null;
    $scope.newMessage = "";
    $scope.users = [];
    $scope.messages = [];

    for (var i = 1; i < 100; i++) {
        $scope.users.push({
            username: 'user' + i
        });
        $scope.messages.push({
            user: {
                username: 'user' + i
            },
            date: new Date(),
            subject: "Subject",
            content: "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"
        })
    }

    $scope.send = function() {

    }

});
