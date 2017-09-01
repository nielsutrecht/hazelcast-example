var appModule = angular.module('myApp', []);

appModule.controller('MainCtrl', ['$scope',
    function($scope) {
        var stompClient;

        $scope.timeEvents = [];
        $scope.otherEvents = [];

        $scope.connect = function() {
            stompClient = Stomp.client('ws://localhost:8080/eventsink');
            stompClient.debug = null;
            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);

                stompClient.subscribe('/topic/events', function(event){
                    $scope.addEvent(JSON.parse(event.body));
                });
            });
        }

        $scope.disconnect = function() {
            if (stompClient != null) {
                stompClient.disconnect();
            }
        }

        $scope.addEvent = function(event) {
            event.time = Date.parse(event.time);
            $scope.$apply(function() {
                if(event.key === 'time') {
                    $scope.timeEvents.push(event);
                } else {
                    if(event.key === 'log') {
                        event.content = event.level + ' ' + event.message;
                    } else if(event.key === 'login') {
                        event.content = 'User ' + event.userId + ' logged in';
                    } else {
                        event.content = 'Unknown';
                    }
                    $scope.otherEvents.push(event);
                }
            });
            console.log(event);
        }

        $scope.connect();
    }
]);

function showGreeting(message) {
    events.push(message);

    var copy = Array.from(events);
    copy.reverse();

    var response = document.getElementById('response');
    response.textContent = copy.join('\n');
}
