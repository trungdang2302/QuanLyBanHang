var app = angular.module("ProductManagement", []);

app.controller("ProductController", function($scope, $http) {

	$scope.uploadme;
	
	$scope.uploadResult ="";
    $scope.myForm = {
        files: []
    }
    
    $scope.products = [];
	$scope.productForm = {
		ID : -1,
		Name : "",
		Description : "",
		URL : ""
	};

	_refreshProductData();

	// Add or Update Product
	$scope.submitProduct = function() {
		
		//save file to Images
		var url = "/rest/uploadMultiFiles";
        var data = new FormData();
 
        for (i = 0; i < $scope.myForm.files.length; i++) {
            data.append("files", $scope.myForm.files[i]);
        }
 
        var config = {
            transformRequest: angular.identity,
            transformResponse: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }
        
        $http.post(url, data, config).then(
            // Success
            function(response) {
                $scope.uploadResult =  response.data;
            },
            // Error
            function(response) {
                $scope.uploadResult = response.data;
            });
        
		//Update infor
		var method = "";

		if ($scope.productForm.ID == -1) {
			method = "POST";
		} else {
			method = "PUT";
		}

		var kq = {
			ID : $scope.productForm.ID,
			Name :$scope.productForm.Name ,
			Description : $scope.productForm.Description,
			URL : $scope.myForm.files[0].name
		};

		$http({
			method : method,
			url : '/product',
			data : angular.toJson(kq),
			headers : {
				'Content-Type' : 'application/json'
			}
		}).then(_success, _error);
	};

	$scope.createProduct = function() {
		_clearFormData();
	}
	// Delete Product
	$scope.deleteProduct = function(product) {
		$http({
			method : 'DELETE',
			url : '/product/' + product.id
		}).then(_success, _error);
	};
	// Edit Product
	$scope.editProduct = function(product) {
		$scope.productForm.ID = product.id;
		$scope.productForm.Name = product.name;
		$scope.productForm.Description = product.description;
		$scope.productForm.URL = myForm.files[0];
		$scope.uploadme = product.url;
	};

	// Get product
	function _refreshProductData() {
		$http({
			method : 'GET',
			url : '/products'
		}).then(function(res) { // success
			$scope.products = res.data;
		}, function(res) { // error
			console.log("Error o day ne : " + res.status + " : " + res.data);
		});
	}
	;

	function _success(res) {
		_refreshProductData();
		_clearFormData();
	}
	;

	function _error(res) {
		var data = res.data;
		var status = res.status;
		var header = res.header;
		var config = res.config;
		alert("Error: " + status + ":" + data);
	}
	;

	function _clearFormData() {
		$scope.productForm.ID = -1;
		$scope.productForm.Name = "";
		$scope.productForm.Description = "";
		$scope.productForm.URL = "";
		$scope.uploadme = null;
		document.getElementById('example-file').value = null;
	}
	;

});

app.directive("fileread", [
	  function() {
	    return {
	      scope: {
	        fileread: "="
	      },
	      link: function(scope, element, attributes) {
	        element.bind("change", function(changeEvent) {
	          var reader = new FileReader();
	          reader.onload = function(loadEvent) {
	            scope.$apply(function() {
	              scope.fileread = loadEvent.target.result;
	            });
	          }
	          reader.readAsDataURL(changeEvent.target.files[0]);
	        });
	      }
	    }
	  }
	]);

app.directive('fileModel', ['$parse', function ($parse) {
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;
           
          element.bind('change', function(){
             scope.$apply(function(){
                modelSetter(scope, element[0].files[0]);
             });
          });
       }
    };
     
}]);