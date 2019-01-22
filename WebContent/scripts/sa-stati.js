var stati_applicazione = [
	{
		name: 'dashboard',
		url: '/dashboard',
		templateUrl: 'views/dashboard/dashboard.html',
		//template:'<div>ciao</div>',
		controller: "dashboardController",
		dipendenze: [
			
			"libs/font-awesome/css/font-awesome.min.css",
			"libs/jquery.dataTables.js",
			"libs/bootstrap/js/bootstrap.min.js",
			"libs/json3.min.js",
			

			'views/dashboard/dashboard.js',
			'scripts/directives/header/header.js',
			'scripts/directives/header/header.css',
			'scripts/directives/footer/footer.js',
			'scripts/directives/footer/footer.css',

			'scripts/directives/menu/menu.js',
			'scripts/directives/menu/menu.css', 

			'views/dashboard/dashboard.css'
		]
	},
	{
		isDefault: true,
		name: 'dashboard.home',
		url: '/home',
		templateUrl: 'views/dashboard/home.html',
		controller: function () {
		},
		dipendenze: [
		]
	},
	{
		name: 'dashboard.act1',
		url: '/act1',
		template: '<h1 style="text-align:center;padding:30px">attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno attività uno </h1>',
	},
	{
		name: 'dashboard.act2',
		url: '/act2',
		template: '<h1 style="text-align:center;padding:30px">attività due</h1>',
	},
	{
		name: 'dashboard.data-sample',
		url: '/data-sample',
		templateUrl: 'views/data-sample/data-sample.html',
		controller: "dataSampleController",
		dipendenze: [
			'views/data-sample/data-sample.js',
			'views/data-sample/data-sample.css'
		]
	},
	{
		name: 'dashboard.news',
		url: '/news',
		templateUrl: 'views/news/news.html',
		controller: "newsController",
		dipendenze: [
			'views/news/news.js',
			'views/news/news.css'
		]
	}


]

