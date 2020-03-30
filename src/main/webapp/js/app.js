//- creating angular application
const Hobt = angular.module('Hobt', ['hobt.common']);

Hobt.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {

        //- where all route changes are set
        $routeProvider.when('/login', {
            templateUrl: 'js/access/login.html',
            controller: 'LoginController'
        });
        $locationProvider.html5Mode(true);
    }]
);
