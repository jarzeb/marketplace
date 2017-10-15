'use strict';

var app = angular.module('marketplace');

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/projectDetail', {
    templateUrl: 'projectDetail/projectDetail.html',
    controller: 'ProjectDetailCtrl'
  });
}]);

app.controller('ProjectDetailCtrl', ['$scope', '$http', 'shared', function($scope, $http, shared) {
	
	$scope.projectId = shared.getSharedVariable('projectId');
	$http.get('/projectDetail?projectId=' + $scope.projectId).
    then(function(response) {
        $scope.project = response.data.project;
        $scope.bids = response.data.bids;
    });	

	$scope.createBid = function() {
		  //shared.setVariable('projectId', projectId);
		  //$scope.projectId = projectId;
		  //$location.path("/projectDetail");
		$http.post('/createBid?buyerId=1&projectId=' + $scope.projectId + '&amount=' + $scope.bidAmount);
	};
	
}]);