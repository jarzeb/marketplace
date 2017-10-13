var marketplace = angular.module('marketplace', []);

marketplace.factory('myService', function() {
	 var savedData = {}
	 function set(data) {
	   savedData = data;
	 }
	 function get() {
	  return savedData;
	 }

	 return {
	  set: set,
	  get: get
	 }
});

marketplace.controller('projectList', function($scope, $http, myService) {
    $http.get('http://localhost:8080/projectList').
        then(function(response) {
            $scope.projects = response.data;

        });


    $scope.openProjectDetail = function(id) {
    	//myService.set('hi');
    	document.location = 'projectDetail.html';		
	}
    
});

marketplace.controller('projectdetail', function($scope, $http, myService) {
    $http.get('http://localhost:8080/projectDetail?projectId=1').
        then(function(response) {
        	//alert(myService.get());
        	$scope.bids = response.data.bids;
            $scope.project = response.data.project;
            
        });  

});