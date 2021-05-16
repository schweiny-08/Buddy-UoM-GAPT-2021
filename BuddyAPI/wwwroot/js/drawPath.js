var drawPathLvl0, drawPathLvlMin1, len, lat, lng;


function calculatePath() {
    var countLvl0 = 0;
    var countLvlMin1 = 0;
    var path = [];
    var pathMarkers = [];
    var markerListLvl0 = [];
    var markerListLvlMin1 = [];
    var formattedCoord0 = [];
    var formattedCoordMin1 = [];
    var coordinatesLvl0 = [];
    var coordinatesLvlMin1 = [];
    var markerLvl0Ids = [];
    var markerLvlMin1Ids = [];
    var start = document.getElementById('startId').value;
    var end = document.getElementById('endId').value;
    let request = new XMLHttpRequest();

    document.getElementById('pastePath').innerHTML = 'Calculating path';

    request.open('GET', 'https://localhost:' + port + '/api/pinpoints/GetNavigationId?start=' + start + '&end=' + end);
    request.send();
    request.onload = () => {
        path = JSON.parse(request.response);
        len = path.length;
        for (var i = 0; i < len; i++) {
            pathMarkers[i] = markers.find(element => {
                return element.id === path[i]
            });
            //console.log(pathMarkers[i].level);

            if (pathMarkers[i].level == 0) {
                markerListLvl0[countLvl0] = pathMarkers[i].marker.position + '';
                markerLvl0Ids[countLvl0] = pathMarkers[i].id;

                formattedCoord0[countLvl0] = markerListLvl0[countLvl0].replace(' ', '');
                formattedCoord0[countLvl0] = markerListLvl0[countLvl0].split(', ');
                formattedCoord0[countLvl0][0] = formattedCoord0[countLvl0][0].replace('(', '');
                formattedCoord0[countLvl0][1] = formattedCoord0[countLvl0][1].replace(')', '');
                lat = parseFloat(formattedCoord0[countLvl0][0]);
                lng = parseFloat(formattedCoord0[countLvl0][1]);

                coordinatesLvl0[countLvl0] = new google.maps.LatLng(lat, lng);
                countLvl0++;
                //console.log(coordinatesLvl0[i]);
            }
            else if (pathMarkers[i].level == -1) {
                markerListLvlMin1[countLvlMin1] = pathMarkers[i].marker.position + '';
                markerLvlMin1Ids [countLvlMin1] = pathMarkers[i].id;

                formattedCoordMin1[countLvlMin1] = markerListLvlMin1[countLvlMin1].replace(' ', '');
                formattedCoordMin1[countLvlMin1] = markerListLvlMin1[countLvlMin1].split(', ');
                formattedCoordMin1[countLvlMin1][0] = formattedCoordMin1[countLvlMin1][0].replace('(', '');
                formattedCoordMin1[countLvlMin1][1] = formattedCoordMin1[countLvlMin1][1].replace(')', '');
                lat = parseFloat(formattedCoordMin1[countLvlMin1][0]);
                lng = parseFloat(formattedCoordMin1[countLvlMin1][1]);

                coordinatesLvlMin1[countLvlMin1] = new google.maps.LatLng(lat, lng);
                countLvlMin1++;
            }
        }
        //console.log(markerLvl0Ids);
        //console.log(markerLvlMin1Ids);
        document.getElementById('pastePath').innerHTML = markerLvl0Ids + '<br>' + markerLvlMin1Ids;


        if (drawPathLvl0 != null) {
            drawPathLvl0.setMap(null);
        }

        drawPathLvl0 = new google.maps.Polyline({
            path: coordinatesLvl0,
            geodesic: true,
            strokeColor: "#FF0000",
            strokeOpacity: 1.0,
            strokeWeight: 2,
        });

        if (drawPathLvlMin1 != null) {
            drawPathLvlMin1.setMap(null);
        }

        drawPathLvlMin1 = new google.maps.Polyline({
            path: coordinatesLvlMin1,
            geodesic: true,
            strokeColor: "#FF0000",
            strokeOpacity: 1.0,
            strokeWeight: 2,
        });


        if (document.getElementById('level 0').checked) {
            drawPathLvlMin1.setMap(null);
            if (drawPathLvl0 != null) {
                drawPathLvl0.setMap(map);
            }
        }

        if (document.getElementById('level -1').checked) {
            drawPathLvl0.setMap(null);
            if (drawPathLvlMin1 != null) {
                drawPathLvlMin1.setMap(map);
            }
        }
    }
}