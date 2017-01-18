(function () {
    'use strict';

    angular
        .module('app')
        .factory('LoggedService', LoggedService);

    LoggedService.$inject = ['$http', '$cookies', '$rootScope', '$timeout', 'UserService'];
    function LoggedService($http, $cookies, $rootScope, $timeout, UserService) {
    	var service = {};

        service.IsLogged = IsLogged;
        service.SetLogged = SetLogged;
        return service;

        function SetLogged(loggedboolean) {
        	this.logged = loggedboolean;

        }

        function IsLogged() {
        	if(this.logged == null || typeof this.logged == 'undefined'){
        		return false;
        	}
        	return this.logged;
        }
    }
})();