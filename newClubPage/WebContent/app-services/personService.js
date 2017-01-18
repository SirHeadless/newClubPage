(function () {
    'use strict';

    angular
        .module('app')
        .factory('PersonService', PersonService);
    
    PersonService.$inject = ['$resource'];

    function PersonService($resource) {
        return $resource('rest-api/player/:id');
    }

})();