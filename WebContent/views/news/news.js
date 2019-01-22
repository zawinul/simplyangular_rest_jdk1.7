(function(){

var apikey = '6381f639bec84a5b906908248038ba04';

function controller($scope) {

    $scope.msg = "News";
	$scope.data = null;

	updateTable();

	function updateTable()  {
		var api = 'https://newsapi.org/v2/top-headlines';
		var params = {
			apiKey: '6381f639bec84a5b906908248038ba04',
			country: 'it',
			pageSize: 100
		};
		rest.get(api, params).then(function(data){
			console.log(JSON.stringify(data,null,2));
			$scope.data = data.articles;	
			$scope.$apply();
		});
	}
}


angular.module(APPNAME).controller("newsController", controller);

})()
