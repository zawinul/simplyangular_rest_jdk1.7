
(function() {

    
	function sidebarFactory() {
		function controller() {
		}
		var obj = {
	        templateUrl: 'scripts/directives/sidebar/sidebar.html',
	        restrict: 'E',
	        controller: controller
	    };
	    return obj;	
	}	
	
	angular.module(APPNAME).directive('sidebar', sidebarFactory);

	function sidebarVoiceFactory() {
		function controller($element, $scope) {
		}
		function link(scope, element, attrs, controller, transclude) {
			// element.text('ciao');
			$('a',element).append(transclude());
			debugger;
		}
		
		var obj = {
	       	template: '	<div class="voce"><a ui-sref="dashboard.news"></a></div>',
			restrict: 'E',
			link:link,
			transclude: true,
	        controller: controller
	    };
	    return obj;	
	}	
	angular.module(APPNAME).directive('sidebarVoice', sidebarVoiceFactory);

	
	function menuFactory() {
		function controller($element, $scope) {
		}
		function link(scope, element, attrs, controller, transclude) {
			element.append(transclude());
		}
		
		var obj = {
	       	template: '<div class="sidebar">',
			restrict: 'E',
			link:link,
			transclude: true,
	        controller: controller
	    };
	    return obj;	
	}	
	angular.module(APPNAME).directive('menu', menuFactory);

})();