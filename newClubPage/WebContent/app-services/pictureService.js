(function () {
    'use strict';

    angular
        .module('app')
        .factory('PictureService', PictureService);
    
    PictureService.$inject = ['$resource', '$http'];

    function PictureService($resource, $http) {
        var service = {};

        service.Save = Save;

        return service;
        function Save2(filename){
            return $resource('rest-api/file/upload',  {},
          	      {
              save: {
                  transformRequest: formDataObject,
                  headers: {'Content-Type':undefined, enctype:'multipart/form-data'}
              },
            }).save();
        }

    	function Save(file){
    		var fd = new FormData();
            fd.append('file', file);
            $http.post('rest-api/file/upload', fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            .success(function(){
            })
            .error(function(){
            });
    		
    		
    		
//	    	$http({
//	    	    method: 'POST',
//	    	    url: 'rest-api/file/upload',
//	    	    headers: {'Content-Type': 'multipart/form-data'},
//	    	    data: 'name="uploadedFile"' + '&filename='+filename
//	    	}).then(function successCallback(response) {
//	    		callback(response);
//	    	     $timeout(function(){
//	    	    	 console.log('Cookies: ');
//	    	         console.log($cookies.getAll());
//	    	       }); 
//	    	  }, function errorCallback(response) {
//	    		  console.log('Login unsuccessfull');
//	    	    // called asynchronously if an error occurs
//	    	    // or server returns response with an error status.
//	    	  });
    	}
    }

})();