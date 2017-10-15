'use strict';

var app = angular.module('marketplace');

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/bidList', {
    templateUrl: 'bidList/bidList.html',
    controller: 'BidListCtrl'
  });
}])

app.controller('BidListCtrl', ['$scope', '$http', '$location', 'shared', function($scope, $http, $location, shared) {
	$scope.userId = shared.getSharedVariable('userId');

	if($scope.userId != undefined) {
		$http.get('/findBidsByBuyerId?buyerId=' + $scope.userId).
		then(function(response) {
			$scope.bids = response.data;
		});
	}

	$scope.openProjectDetail = function(projectId) {
		  shared.setVariable('projectId', projectId);
		  $scope.projectId = projectId;
		  $location.path("/projectDetail");
	};
	
}]);