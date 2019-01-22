rest = function(){

	function get(resource, data, config){
		return ajax('GET', resource, data, config);
	}

	function post(resource, data, config){
		return ajax('POST', resource, data, config);
	}

	function put(resource, data, config){
		return ajax('PUT', resource, data, config);
	}

	function patch(resource, data, config){
		return ajax('PATCH', resource, data, config);
	}

	function del(resource, data, config){
		return ajax('DELETE', resource, data, config);
	}

	function postObject(resource, data, config){
		return ajax('POST', resource, data, config, true);
	}

	function putObject(resource, data, config){
		return ajax('PUT', resource, data, config, true);
	}

	function patchObject(resource, data, config){
		return ajax('PATCH', resource, data, config, true);
	}

	function ajax(method, resource, data, config, sendObject) {
		var params = "";
		var isLocalResource = resource.indexOf('http')!=0;

		var c = {
			method:method,
			url: (isLocalResource) ? 'rest/'+resource : resource,
		}
		if (sendObject) {
			c.processData = false;
			data = JSON.stringify(data, null,2);
			c.contentType ='application/json';
		}
		if (data)
			c.data = data;
		if (config)
			$.extend(c, config);
		console.log({ajax:c});
		var ret = $.ajax(c);
		ret.alertOnError = function() {
			ret.fail(function(jqXHR, textStatus, errorThrown){
				if (jqXHR.responseText)
					alert(jqXHR.responseText);
				else
					alert('error: '+c.method+' '+resource);
				console.log({alertOnError:{jqXHR:jqXHR, textStatus:textStatus, errorThrown:errorThrown}});
			});
			return ret;
		};
		return ret;
	} 	

	return {
		get:get,
		post:post,
		put:put,
		patch:patch,
		del:del,
		postObject:postObject,
		putObject:putObject,
		patchObject:patchObject
	};

}();
