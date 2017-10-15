'use strict';

var app = angular.module('marketplace');

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/projectDetail', {
    templateUrl: 'projectDetail/projectDetail.html',
    controller: 'ProjectDetailCtrl'
  });
}]);

app.controller('ProjectDetailCtrl', ['$scope', '$http', '$route', 'shared', function($scope, $http, $route, shared) {
	
	$scope.shared = shared;
	$scope.projectId = shared.getSharedVariable('projectId');
	$http.get('/projectDetail?projectId=' + $scope.projectId).
    then(function(response) {
        $scope.project = response.data.project;
        $scope.bids = response.data.bids;
    });	

	$scope.createBid = function() {
		$scope.userId = shared.getSharedVariable('userId');
		$http.post('/createBid?buyerId=' + $scope.userId + '&projectId=' + $scope.projectId + '&amount=' + $scope.bidAmount).
		then(function(response) {
			if(response.data.result == 'true') {
				alert('bid accepted');
			} else {
				alert(response.data.error);
			}
			$route.reload();
		});
	};
	
}]);