'use strict';

var conf = {};
var rootScope;
var mainInjector;

//-----------------------------------------------------------------------------------

(function () {

	var app;
	var defaultState;

	function main() {
		var w1 = richiediConfigurazione();
		//var w2 = getInitialData();
		w1.then(function(data) {
			if (!data.user) {
				location.href="./login#"+btoa(location.href);
				return;
			}
			console.log({configurazione:data});
			initAngular();
		});
	}
	window.main = main;

	// callback chiamata da Angular in fase di configurazione
	function configurazioneAngularCallback($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {
		console.log('configurazioneAngularCallback begin');
		$ocLazyLoadProvider.config({
			debug: false,
			events: true
		});

		function initState(cfg) {
			if (cfg.dipendenze) {
				var files=[];
				
				for(var i=0; i<cfg.dipendenze.length;i++)
					files.push(cfg.dipendenze[i]);
					
				cfg.resolve = {
					loadMe:	function ($ocLazyLoad) {
						return $ocLazyLoad.load({
							name: APPNAME,
							files: files
						});
					}
				};
			}
			if (cfg.isDefault)
				defaultState = cfg.name;
			var stato = $stateProvider.state(cfg.name, cfg);
		};

		// inizializza gli stati
		for (var i = 0; i < stati_applicazione.length; i++) {
			initState(stati_applicazione[i]);
		}

		if (defaultState)
			$urlRouterProvider.otherwise(defaultState);
		console.log('configurazioneAngularCallback end');
	}


	// callback chiamata da Angular in fase di start
	function avvioAngularCallback($rootScope, $state, $injector) {
		// inizializza il rootScope e altre variabili globali, fa partire la prima pagina se necessario
		mainInjector = $injector;
		rootScope = $rootScope;
		rootScope.conf = conf;

		console.log("AVVIO st=" + $state.current.name);
		var target = ($state.current.name)
			? $state.current.name
			: ((defaultState)?defaultState:null);
		$state.go(target);
	}



	function initAngular() {
		var app = angular.module(APPNAME, [
			'ng',
			'ngLocale',
			'oc.lazyLoad',
			'ui.router',
			'ui.bootstrap',
			"ui.bootstrap.tpls",
			'ui.bootstrap.datepicker',
			'lr.upload'
		]);

		// setta configurazioneAngularCallback e avvioAngularCallback come callback (non le chiama)
		app.config(configurazioneAngularCallback);
		app.run(avvioAngularCallback);
		
		// start angular (chiamera' le callback)
		var angularDiv = $('#angular-main');
		angular.bootstrap(angularDiv, [APPNAME]);
	}


	function richiediConfigurazione() {
		return rest.get("session/configuration").then(function (data) {
			$.extend(conf, data);
			return conf;
		});
	}




	$(main);


})();


// function addCss(x) {
// 	$('<link/>').attr({
// 		rel:'stylesheet',
// 		href:x
// 	}).appendTo('head');
// }