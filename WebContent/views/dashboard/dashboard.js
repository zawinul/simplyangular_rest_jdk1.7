(function() {
	function dashboardController($scope) {
		setInterval(resize, 5000); // non si sa mai
		$(window).resize(resize);
		resize();

		$scope.$on('$viewContentLoaded', function(){
			setTimeout(resize,100);
		});
	}
	
	function resize() {
		//console.log('resize');
		var hbody = $('body').height();
		var hheader = $('.dashboard .header').height();
		var hfooter = $('.dashboard .footer').height();
		$('.body-center').height(hbody-hheader-hfooter);
	}
	angular.module(APPNAME).controller("dashboardController", dashboardController);
	
})();