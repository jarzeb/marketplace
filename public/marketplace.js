'use strict';

// Declare app level module which depends on views, and components
var app = angular.module('marketplace', ['ngRoute']);

app.config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');
  //$routeProvider.otherwise({redirectTo: '/projectList'});
  
}]);

app.service('shared', function () {
    var self = this;
    //private shared variable
    var sharedVariables = { };
    self.getSharedVariable = getSharedVariable;
    self.setVariable = setVariable;

    //function declarations
    function getSharedVariable(name) {
        return sharedVariables[name];
    }
    function setVariable(name, value) {
        sharedVariables[name] = value;
    }
});