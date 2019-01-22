function login() {
	var req = $.ajax({
		url: '../rest/session/login',
		method: 'POST',
		data : {
			user: $('.login').val(),
			password: $('.password').val()	
		}
	});
	
	req.then(function(res) {
		console.log({session_login_res: res});
		if (res) {
			var x = location.href.split('#')[1];
			if (x)
				location.href = atob(x);
			else
				location.href = ".."
		}
		else
			alert('accesso negato!');
	});
}

$(function(){
	$('.login-view .ok').click(login);
});