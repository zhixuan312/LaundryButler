var myCenter = new google.maps.LatLng(1.294807, 103.773800);

function initialize() {
  console.log("google map loaded");
  var mapProp = {
    center: myCenter,
    zoom: 14,
    scrollwheel: false,
    draggable: false,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };

  var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

  var marker = new google.maps.Marker({
    position: myCenter,
  });

  marker.setMap(map);
}

google.maps.event.addDomListener(window, 'load', initialize);
