'use strict';

var app = angular.module('marketplace', ['ngRoute', 'ngMaterial', 'ngMessages']);

app.config(['$locationProvider', '$routeProvider', '$httpProvider', function($locationProvider, $routeProvider, $httpProvider) {
  $locationProvider.hashPrefix('!');
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

app.controller('UserController', ['$scope', '$http', '$location', '$route', 'shared', function($scope, $http, $location, $route, shared) {	  
	this.myDate = new Date();
	this.isOpen = false;

	$http.get('/getFirst25Users').
    then(function(response) {
        $scope.users = response.data;
        angular.forEach($scope.users, function(user) {
        	user.str = user.userId + " - " + user.username;
        	user.str += ' [' + (user.seller==true ? 's' : '') + (user.buyer==true ? 'b' : '') + ']';
        });
    });
	
	$location.path("/projectList");
	$scope.updateUser = function() {
		shared.setVariable('userId', $scope.selectedUser);
		$route.reload();
	};
	
}]);