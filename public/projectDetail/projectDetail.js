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
		$http.post('/createBid?buyerId=1&projectId=' + $scope.projectId + '&amount=' + $scope.bidAmount).
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