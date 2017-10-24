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
		var monthStr = "" + (d.getMonth() + 1);
		if(monthStr.length < 2) monthStr = "0" + monthStr;
		var dayStr = "" + d.getDate();
		if(dayStr.length < 2) dayStr = "0" + dayStr;
		
		var dateStr = d.getFullYear() + "-" + monthStr + "-" + dayStr + "T";
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
			alert(response.data.error);
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