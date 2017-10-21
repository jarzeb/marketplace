'use strict';

var app = angular.module('marketplace');

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/projectDetail', {
    templateUrl: 'projectDetail/projectDetail.html',
    controller: 'ProjectDetailCtrl'
  });
}]);

app.controller('ProjectDetailCtrl', ['$scope', '$http', '$route', 'shared', function($scope, $http, $route, shared) {
	
	$scope.projectId = shared.getSharedVariable('projectId');
	$http.get('/projectDetail?projectId=' + $scope.projectId).
    then(function(response) {
        $scope.project = response.data.project;
        $scope.bids = response.data.bids;
    });	

	
	$scope.createBid = function() {
		$scope.userId = shared.getSharedVariable('userId');
		
		var payload = JSON.stringify(
				{project: {projectId: $scope.projectId},
					buyer: {userId: $scope.userId},
						amount: $scope.bidAmount});

		$http.post('/createBid', payload).
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