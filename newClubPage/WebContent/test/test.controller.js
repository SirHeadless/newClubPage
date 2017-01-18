(function () {
    'use strict';

    angular
        .module('app')
        .controller('TestController', TestController);

    TestController.$inject = ['$cookies', 'UserService', '$location', '$rootScope', 'FlashService', '$timeout'];
    function TestController($cookies, UserService, $location, $rootScope, FlashService, $timeout) {
        var vm = this;
        console.log('Cookies test: ');
        $timeout(function(){
            console.log($cookies.getAll())
          });  
        
        loadTest();
        
        function loadTest() {
            UserService.GetTest()
                .then(function (test) {
                    vm.test = test.test;
                });
        }
    }

})();
