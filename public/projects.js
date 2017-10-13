angular.module('marketplace', [])
.controller('marketplace', function($scope, $http) {
    $http.get('http://localhost:8080/projectList').
        then(function(response) {
            $scope.projects = response.data;
        });


});

