function statusChangeCallback(response) {
  console.log('statusChangeCallback');
  console.log(response);
  if (response.status === 'connected') {
    customerLogin();
  } else if (response.status === 'not_authorized') {
    //document.getElementById('status').innerHTML = 'Please log ' +'into this app.';
  } else {
    //document.getElementById('status').innerHTML = 'Please log ' +'into Facebook.';
  }
}

function checkLoginState() {
  FB.getLoginStatus(function (response) {
    statusChangeCallback(response);
  });
}

window.fbAsyncInit = function () {
  FB.init({
    appId: '678600575613858',
    cookie: false, // enable cookies to allow the server to access 
    xfbml: true, // parse social plugins on this page
    version: 'v2.5' // use graph api version 2.5
  });

  FB.getLoginStatus(function (response) {
    statusChangeCallback(response);
  });

};

(function (d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id))
    return;
  js = d.createElement(s);
  js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function customerLogin() {
  console.log('Welcome!  Fetching your information.... ');
  FB.api('/me', {fields: ['last_name', 'first_name', 'birthday', 'email', 'gender']}, function (response) {
    console.log('Successful login for: ' + response.email);
    fbLoginButtonClick(response);
    console.log('Finish login process.');
  });
}

function customerLogout() {
  FB.logout(function (response) {
    console.log(response.status);
  });
//                        document.getElementById(":fbLogin2:status").innerHTML = "Logged out";
  console.log("Logged out!");
}