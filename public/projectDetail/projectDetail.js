'use strict';

var app = angular.module('marketplace');

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/projectDetail', {
    templateUrl: 'projectDetail/projectDetail.html',
    controller: 'ProjectDetailCtrl'
  });
}]);

app.controller('ProjectDetailCtrl', ['$scope', '$http', 'shared', function($scope, $http, shared) {
	
	$http.get('/projectDetail?projectId=' + shared.getSharedVariable('projectId')).
    then(function(response) {
        $scope.project = response.data.project;
        $scope.bids = response.data.bids;
    });	

	
}]);