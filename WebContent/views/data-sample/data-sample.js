(function(){


function controller($scope) {
    $scope.msg = "data sample";
	$scope.data = null;
	$scope.editItem = editItem;
	$scope.deleteItem = deleteItem;
	$scope.getData = updateTable;
	$scope.newItem = newItem;
	$scope.invia = invia;
	$scope.chiudiForm = chiudiForm;
	$scope.currObj = null;
	updateTable();

	function updateTable()  {
		rest.get('data-sample').then(function(data){
			$scope.data = data;	
			$scope.$apply();
		});
	}

	function deleteItem(item) {
		rest.del('data-sample/'+item.id).then(updateTable);
	}

	function editItem(item) {
		$scope.currObj = $.extend({}, item);
	}

	function newItem(item) {
		$scope.currObj = {id:-1, nome:'', cognome:'', citta:'', eta: 0};
	}

	function chiudiForm() {
		$scope.currObj = null;
	}

	function invia() {
		var obj = $scope.currObj;
		$scope.currObj = null;
		var isnew = obj.id == -1;
		var act = (isnew) 
			? rest.post('data-sample', obj) 
			: rest.put('data-sample', obj);
		act.alertOnError().then(updateTable);
	}

}


angular.module(APPNAME).controller("dataSampleController", controller);

})()
