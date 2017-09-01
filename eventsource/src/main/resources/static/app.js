var appModule = angular.module('myApp', []);

appModule.controller('MainCtrl', ['mainService','$scope','$http',
    function(mainService, $scope, $http) {
        $scope.logLevel = 'INFO';
        $scope.logMessage = '';

        $scope.lastSent = null;
        $scope.lastSentAt = null;

        $scope.login = function(userId) {
            mainService.loginEvent(userId).then(function(data) {
                $scope.sent('Login for user ' + userId);
            });
        }

        $scope.log = function() {
            mainService.logEvent($scope.logLevel, $scope.logMessage).then(function(data) {
                $scope.sent('Log message with level ' + $scope.logLevel + ' and message ' + $scope.logMessage);
            });

            $scope.logMessage = '';
        }

        $scope.sent = function(message) {
            $scope.lastSent = message;
            $scope.lastSentAt = new Date();
        }
    }
]);

appModule.service('mainService', function($http) {
    return {
        loginEvent : function(userId) {
            return $http.post('/event/login/' + userId, {}).then(function(response) {
                return response.data;
            });
        },

        logEvent : function(level, message) {
            return $http.post('/event/log', {level: level, message: message}).then(function(response) {
                return response.data;
            });
        }
    };
});
