
(function() {

	// SEZIONE
	function sezioneFactory() {
		function controller($element, $scope, $state) {
		}

		function link(scope, element, attrs, controller, transclude) {
			var a = $('<div class="titolo"><i class="fa fa-plus"/><i class="fa fa-minus"/></div>');
			var b = $('<div class="sottomenu"/>');
	
			var c = transclude();
			var voices = c.filter('.voce, sezione');
			var head = c.not(voices);
			a.append(head).appendTo(element);
			b.append(voices).appendTo(element);

			a.click(function() {
				element.toggleClass('chiusa');
			});
		}
		
		var obj = {
			restrict: 'E',
			//replace: true,
			link:link,
			transclude: true,
	        controller: controller
	    };
	    return obj;	
	}	
	angular.module(APPNAME).directive('sezione', sezioneFactory);


	// VOCE

	function voceFactory() {
		function controller($element, $scope, $state) {
			$element.click(function() {
				$('.menu .voce').removeClass('current');
				$element.addClass('current');
				var stato = $element.attr('stato');
				if (stato)
					$state.go(stato);
			});
		}

		function link(scope, element, attrs, controller, transclude) {
			element.append(transclude());
		}
		
		var obj = {
	       	template: '<div class="voce"></div>',
			restrict: 'E',
			replace: true,
			link:link,
			transclude: true,
	        controller: controller
	    };
	    return obj;	
	}	
	angular.module(APPNAME).directive('voce', voceFactory);

	// MENU

	function menuFactory() {
		function controller($element, $scope) {
		}
		function link(scope, element, attrs, controller, transclude) {
			$(element).append(transclude());
		}
		
		var obj = {
	       	template: '<div class="menu">',
			restrict: 'E',
			replace: true,
			link:link,
			transclude: true,
	        controller: controller
	    };
	    return obj;	
	}	
	angular.module(APPNAME).directive('menu', menuFactory);

})();