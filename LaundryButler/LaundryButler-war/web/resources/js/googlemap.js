function initializeMap() {
  var myCenter = new google.maps.LatLng(1.2925447, 103.7734944);
  var mapProp = {
    center: myCenter,
    zoom: 16,
    scrollwheel: false,
    draggable: false,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };

  var map = new google.maps.Map(document.getElementById("googleMapDiv"), mapProp);

  var marker = new google.maps.Marker({
    position: myCenter,
  });

  marker.setMap(map);
  console.log("google map finished loading...");
}
google.maps.event.addDomListener(window, 'load', initializeMap);

