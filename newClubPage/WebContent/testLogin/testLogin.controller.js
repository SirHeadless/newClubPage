(function () {
    'use strict';

    angular
        .module('app')
        .controller('TestLoginController', TestLoginController);

    TestLoginController.$inject = ['$cookies', 'UserService', '$location', '$rootScope', 'FlashService', '$timeout', 'LoggedService'];
    function TestLoginController($cookies, UserService, $location, $rootScope, FlashService, $timeout, LoggedService) {
        var vm = this;
        console.log('Cookies test: ');
        $timeout(function(){
            console.log($cookies.getAll())
          });  
        
        if (LoggedService.IsLogged()) {
        	vm.message = "You are logged";
        } else {
        	vm.message = "You are not logged";
        }

    }

})();
