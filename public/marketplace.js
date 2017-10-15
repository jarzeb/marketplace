'use strict';

var app = angular.module('marketplace', ['ngRoute']);

app.config(['$locationProvider', '$routeProvider', '$httpProvider', function($locationProvider, $routeProvider, $httpProvider) {
  $locationProvider.hashPrefix('!');
  
  if (!$httpProvider.defaults.headers.get) {
	  $httpProvider.defaults.headers.get = {};    
  }    

  $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
  $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
  $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
  
}]);

app.service('shared', function () {
    var self = this;
    var sharedVariables = {};
    self.getSharedVariable = getSharedVariable;
    self.setVariable = setVariable;

    function getSharedVariable(name) {
        return sharedVariables[name];
    }
    function setVariable(name, value) {
        sharedVariables[name] = value;
    }
});

app.controller('UserController', ['$scope', '$http', '$location', 'shared', function($scope, $http, $location, shared) {	  
	$http.get('/getFirst10Users').
    then(function(response) {
        $scope.users = response.data;
    });
	
	$scope.updateUser = function() {
		shared.setVariable('userId', $scope.selectedUser);
		$location.path("/");
	};


}]);