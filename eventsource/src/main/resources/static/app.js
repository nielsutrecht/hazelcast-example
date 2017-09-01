var appModule = angular.module('myApp', []);

appModule.controller('MainCtrl', ['mainService','$scope','$http',
    function(mainService, $scope, $http) {
        $scope.userId = '00000000-0000-0000-0000-000000000000';
        $scope.logLevel = 'INFO';
        $scope.logMessage = '';

        $scope.login = function() {
            mainService.loginEvent($scope.userId).then(function(data) {
            });
        }

        $scope.log = function() {
            mainService.logEvent($scope.logLevel, $scope.logMessage).then(function(data) {
            });

            $scope.logMessage = '';
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
