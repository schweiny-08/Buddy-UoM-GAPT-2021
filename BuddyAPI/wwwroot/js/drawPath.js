var drawPath, len, lat, lng;


function calculatePath() {
    var path = [];
    var pathMarkers = [];
    var pathMarkers2 = [];
    var markerList = [];
    var final = [];
    var coordinates = [];
    var markerIds = [];
    var start = document.getElementById('startId').value;
    var end = document.getElementById('endId').value;
    let request = new XMLHttpRequest();
    request.open('GET', 'https://localhost:' + port + '/api/pinpoints/GetNavigationId?start=' + start + '&end=' + end);
    request.send();
    request.onload = () => {
        path = JSON.parse(request.response);
        len = path.length;
        for (var i = 0; i < len; i++) {
            pathMarkers[i] = markers.find(element => {
                return element.id === path[i]
            });
            markerList[i] = pathMarkers[i].marker.position + '';
            markerIds[i] = pathMarkers[i].id;
            console.log(markerIds[i]);


            final[i] = markerList[i].replace(' ', '');
            final[i] = markerList[i].split(', ');
            final[i][0] = final[i][0].replace('(', '');
            final[i][1] = final[i][1].replace(')', '');
            lat = parseFloat(final[i][0]);
            lng = parseFloat(final[i][1]);

            coordinates[i] = new google.maps.LatLng(lat, lng);
        }
        document.getElementById('pastePath').innerHTML = markerIds;


        if (drawPath != null) {
            drawPath.setMap(null);
        }
        
        drawPath = new google.maps.Polyline({
            path: coordinates,
            geodesic: true,
            strokeColor: "#FF0000",
            strokeOpacity: 1.0,
            strokeWeight: 2,
        });
        drawPath.setMap(map);
    }
}