(function () {
    'use strict';

    angular
        .module('app', ['ui.router', 'ngResource', 'ngGrid', 'ngCookies'])
        .config(config)
        .config(config2)
        .run(run);


	config2.$inject = ['$httpProvider'];
	function config2($httpProvider) {
		$httpProvider.defaults.withCredentials = true;
	}
        
    config.$inject = ['$stateProvider', '$urlRouterProvider'];
    function config($stateProvider, $urlRouterProvider) {
    	$urlRouterProvider.otherwise("/");
    	$stateProvider
            .state('home', {
            	url: "/",
            	templateUrl: 'home/home.view.html',
            	controller: 'HomeController',
            	controllerAs: 'vm',
            })
            .state('login', {
            	url: "/login",
            	templateUrl: 'login/login.view.html',
            	controller: 'LoginController',
            	controllerAs: 'vm',
            })
            .state('register', {
            	url: "/register",
            	templateUrl: 'register/register.view.html',
            	controller: 'RegisterController',
            	controllerAs: 'vm',
            })
            .state('test', {
            	url: "/test",
            	templateUrl: 'test/test.view.html',
            	controller: 'TestController',
            	controllerAs: 'vm',
            })
            .state('testLogin', {
            	url: "/testLogin",
            	templateUrl: 'testLogin/testLogin.view.html',
            	controller: 'TestLoginController',
            	controllerAs: 'vm',
            })            
            .state('player', {
            	url: "/player",
            	templateUrl: 'player/player.view.html',
            	controller: 'PersonsListController',
            	controllerAs: 'vm',
            })
            .state('picture', {
            	url: "/picture",
            	templateUrl: 'picture/picture.view.html',
            	controller: 'PictureUploadController',
            	controllerAs: 'vm',
            }) 
    }

    run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
    function run($rootScope, $location, $cookies, $http) {
        // keep user logged in after page refresh
    	$cookies.put("JSESSIONID", "JohnDoe");
        $rootScope.globals = $cookies.getObject('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
//            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
//            var allowedPage = $.inArray($location.path(), ['/test']) === 0;
//            var loggedIn = $rootScope.globals.currentUser;
//            if (restrictedPage && !loggedIn && !allowedPage) {
//                $location.path('/login');
//            }
        });
    }

})();