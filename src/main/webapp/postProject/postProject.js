'use strict';

var app = angular.module('marketplace');

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/postProject', {
    templateUrl: 'postProject/postProject.html',
    controller: 'PostProjectCtrl'
  });
}]);

app.controller('PostProjectCtrl', ['$scope', '$http', '$route', '$location', 'shared', function($scope, $http, $route, $location, shared) {
	
	this.myDate = new Date();
	this.isOpen = false;
	
	$scope.createProject = function() {
		$scope.userId = shared.getSharedVariable('userId');
		var d = $scope.deadlineDate;
		var dateStr = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate() + "T";
		var timeStr = $scope.deadlineHour + ":" + $scope.deadlineMinute + ":00";
		var deadlineStr = dateStr + timeStr;
		
		var payload = JSON.stringify(
				{seller:{userId: $scope.userId},
					description: $scope.description,
						billingType: $scope.billingType,
							startingBid: $scope.startingBid,
								deadline: deadlineStr});
		
		//alert(payload);
		$http.post('/createProject', payload).
		then
		(function(response) {
			alert('Project created!');
			$location.path('/projectList');
		},
		function(response) {
			alert('Error - Project not created.');
		});
	};
	
}]);

app.filter('range', function() {
	  return function(input, total) {
	    total = parseInt(total);
	    for (var i=0; i<total; i++) {
	    	if(i<10) {
	    		input.push('0' + i);
	    	} else {
	    		input.push(i);
	    	}
	    }
	    return input;
	  };
});