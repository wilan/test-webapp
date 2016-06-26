var demo = angular.module('demo', ['ngRoute', 'ngMaterial']);
demo.config(function($routeProvider){
	$routeProvider.when('/', {
	    templateUrl: 'client/index/index.html'
    })
	$routeProvider.when('/trades', {
    controller: 'tradesController',
    controllerAs: 'tradesController',
    templateUrl: 'client/trades/trades.html'
  })
})

var controllers = {};
controllers.tradesController = tradesController;

demo.controller(controllers)