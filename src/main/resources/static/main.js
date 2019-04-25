var app = angular.module("ProductManagement", []);

app.controller("ProductController", function($scope, $http) {

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
			URL : $scope.productForm.URL
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
		$scope.productForm.URL = product.url;
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
		$scope.productForm.Description = ""
		$scope.productForm.URL = ""
	}
	;

});