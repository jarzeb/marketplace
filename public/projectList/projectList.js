'use strict';

var app = angular.module('marketplace');

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/projectList', {
    templateUrl: 'projectList/projectList.html',
    controller: 'ProjectListCtrl'
  });
}])

app.controller('ProjectListCtrl', ['$scope', '$http', '$location', 'shared', function($scope, $http, $location, shared) {
	  
	$http.get('/projectList').
    then(function(response) {
        $scope.projects = response.data;
    });

	$scope.openProjectDetail = function(projectId) {
		  shared.setVariable('projectId', projectId);
		  $scope.projectId = projectId;
		  $location.path("/projectDetail");
	};
	
}]);