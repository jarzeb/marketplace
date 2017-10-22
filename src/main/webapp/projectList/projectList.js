'use strict';

var app = angular.module('marketplace');

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/projectList', {
    templateUrl: 'projectList/projectList.html',
    controller: 'ProjectListCtrl'
  });
}])

app.controller('ProjectListCtrl', ['$scope', '$http', '$location', '$routeParams', 'shared', function($scope, $http, $location, $routeParams, shared) {

	if($location.search().filter == 'seller') {
		$scope.url = '/projectListBySeller?sellerId=' + shared.getSharedVariable('userId');
	} else if ($location.search().filter == 'buyer') {
		$scope.url = '/projectListByBuyer?buyerId=' + shared.getSharedVariable('userId');
	} else {
		$scope.url = '/projectList';
	}

	$http.get($scope.url).
    then(function(response) {
        $scope.projects = response.data;
    });

	$scope.openProjectDetail = function(projectId) {
		  shared.setVariable('projectId', projectId);
		  $scope.projectId = projectId;
		  $location.path("/projectDetail");
	};
	
}]);