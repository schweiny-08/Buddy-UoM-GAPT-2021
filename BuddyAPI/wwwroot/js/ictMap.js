//import {AddPinpoint} from '../js/pinpointApiCalls.js';

var port = location.port;
var markers = [];
var level = 0;
var id = 0;

//Creating the map centred over UOM with default zoom level 15
function initMap() {
    const ict = new google.maps.LatLng(35.901810199007215, 14.485197413626922);
    const map = new google.maps.Map(document.getElementById("map"), {
        center: ict,
        zoom: 15,
    });

    //Creating an array of objects
    //Object with level and googleMarker
    //var markers = [
    //    { level: 0, marker: new google.maps.Marker({ position: { lat: 35.901810199007214, lng: 15.485197413626921 }, map, title: "Level 0 Marker" }) },
    //   { level: -1, marker: new google.maps.Marker({ position: { lat: 34.901810199007216, lng: 14.485197413626923 }, map, title: "Level -1 Marker" }) }
    //]; 


    const coordInfoWindow = new google.maps.InfoWindow();
    coordInfoWindow.setContent(createInfoWindowContent(ict, map.getZoom()));
    coordInfoWindow.setPosition(ict);
    //coordInfoWindow.open(map);
    //event listener that fires when zoom level changes
    map.addListener("zoom_changed", () => {
        coordInfoWindow.setContent(
            createInfoWindowContent(ict, map.getZoom())
        );
        //coordInfoWindow.open(map);
    });
    //setting the bounds of UOM. Bounds [zoom][coord.x][coord.y]
    //bounds are generated from map tiler folder structure when tiles are created
    const bounds = {
        18: [
            [141619, 141620],
            [103028, 103028],
        ],
        19: [
            [283239, 283240],
            [206056, 206057],
        ],
        20: [
            [566478, 566480],
            [412112, 412115],
        ],
        21: [
            [1132956, 1132961],
            [824225, 824231],
        ],
        22: [
            [2265913, 2265922],
            [1648450, 1648462],
        ],
    };

    //const bounds2 = {
    //    19: [
    //        [283239, 283240],
    //        [206056, 206057],
    //    ],
    //    20: [
    //        [566478, 566480],
    //        [412112, 412115],
    //    ],
    //    21: [
    //        [1132957, 1132960],
    //        [824225, 824231],
    //    ],
    //};

    //https://developers.google.com/maps/documentation/javascript/examples/maptype-image-overlay#maps_maptype_image_overlay-javascript
    //creating the imageMapType for the image to be overlayed over the map


    const ictLvlMinOne = new google.maps.ImageMapType({
        getTileUrl: function (coord, zoom) {
            //if statement to not render the image if outside the bounds or outside the max/min zoom levels
            if (
                zoom < 18 ||
                zoom > 22 ||
                bounds[zoom][0][0] > coord.x ||
                coord.x > bounds[zoom][0][1] ||
                bounds[zoom][1][0] > coord.y ||
                coord.y > bounds[zoom][1][1]
            ) {
                return "";
            }
            //returning the required tiles by requesting the specific png images
            //link is according to the port being used 
            return [
                "https://localhost:",
                port,
                "/images/Level-1/",
                zoom,
                "/",
                coord.x,
                "/",
                coord.y,
                ".png",
            ].join("");
        },
        tileSize: new google.maps.Size(256, 256),
    });

    const ictLvlZero = new google.maps.ImageMapType({
        getTileUrl: function (coord, zoom) {
            //if statement to not render the image if outside the bounds or outside the max/min zoom levels
            if (
                zoom < 18 ||
                zoom > 22 ||
                bounds[zoom][0][0] > coord.x ||
                coord.x > bounds[zoom][0][1] ||
                bounds[zoom][1][0] > coord.y ||
                coord.y > bounds[zoom][1][1]
            ) {
                return "";
            }
            //returning the required tiles by requesting the specific png images
            //link is according to the port being used 
            return [
                "https://localhost:",
                port,
                "/images/Level0/",
                zoom,
                "/",
                coord.x,
                "/",
                coord.y,
                ".png",
            ].join("");
        },
        tileSize: new google.maps.Size(256, 256),
    });

    const ictLvlMinTwo = new google.maps.ImageMapType({
        getTileUrl: function (coord, zoom) {
            //if statement to not render the image if outside the bounds or outside the max/min zoom levels
            if (
                zoom < 18 ||
                zoom > 22 ||
                bounds[zoom][0][0] > coord.x ||
                coord.x > bounds[zoom][0][1] ||
                bounds[zoom][1][0] > coord.y ||
                coord.y > bounds[zoom][1][1]
            ) {
                return "";
            }
            //returning the required tiles by requesting the specific png images
            //link is according to the port being used 
            return [
                "https://localhost:",
                port,
                "/images/Level-2/",
                zoom,
                "/",
                coord.x,
                "/",
                coord.y,
                ".png",
            ].join("");
        },
        tileSize: new google.maps.Size(256, 256),
    });

    const ictLvlOne = new google.maps.ImageMapType({
        getTileUrl: function (coord, zoom) {
            //if statement to not render the image if outside the bounds or outside the max/min zoom levels
            if (
                zoom < 18 ||
                zoom > 22 ||
                bounds[zoom][0][0] > coord.x ||
                coord.x > bounds[zoom][0][1] ||
                bounds[zoom][1][0] > coord.y ||
                coord.y > bounds[zoom][1][1]
            ) {
                return "";
            }
            //returning the required tiles by requesting the specific png images
            //link is according to the port being used 
            return [
                "https://localhost:",
                port,
                "/images/Level1/",
                zoom,
                "/",
                coord.x,
                "/",
                coord.y,
                ".png",
            ].join("");
        },
        tileSize: new google.maps.Size(256, 256),
    });

    var radio = document.forms["level-selector"].elements["level"];
    for (var i = 0; i < radio.length; i++) {
        radio[i].onclick = function () {
            if (document.getElementById('level -2').checked) {
                level = -2;

                map.overlayMapTypes.clear();
                map.overlayMapTypes.push(ictLvlMinTwo);
                document.getElementById('status').innerHTML = "Level -2 Parking";

                //Show appropriate markers
                for (let j = 0; j < markers.length; j++) {
                    if (markers[j].level == -2) {
                        markers[j].marker.setMap(map);
                    } else {
                        markers[j].marker.setMap(null);
                    }
                }
            }
            if (document.getElementById('level -1').checked) {
                level = -1;

                map.overlayMapTypes.clear();
                map.overlayMapTypes.push(ictLvlMinOne);
                document.getElementById('status').innerHTML = "Level -1";

                //Show appropriate markers
                for (let j = 0; j < markers.length; j++) {
                    if (markers[j].level == -1) {
                        markers[j].marker.setMap(map);
                    } else {
                        markers[j].marker.setMap(null);
                    }
                }

            }
            if (document.getElementById('level 0').checked) {
                level = 0;

                map.overlayMapTypes.clear();
                map.overlayMapTypes.push(ictLvlZero);
                document.getElementById('status').innerHTML = "Level 0";

                //Show appropriate markers
                for (let j = 0; j < markers.length; j++) {
                    if (markers[j].level == 0) {
                        markers[j].marker.setMap(map);
                    } else {
                        markers[j].marker.setMap(null);
                    }
                }
            }

            if (document.getElementById('level 1').checked) {
                level = 1;

                map.overlayMapTypes.clear();
                map.overlayMapTypes.push(ictLvlOne);
                document.getElementById('status').innerHTML = "Level 1";

                //Show appropriate markers
                for (let j = 0; j < markers.length; j++) {
                    if (markers[j].level == 1) {
                        markers[j].marker.setMap(map);
                    } else {
                        markers[j].marker.setMap(null);
                    }
                }
            }

            if (document.getElementById('no-overlay').checked) {
                level = -999;

                map.overlayMapTypes.clear();
                document.getElementById('status').innerHTML = "No Overlay";
            }
        }

        // Adds marker when user clicks on map
        map.addListener("click", (mapsMouseEvent) => {
            addMarker(map, mapsMouseEvent)
        });
    }
}

function addMarker(map, mapsMouseEvent) {
        var marker = new google.maps.Marker({
            position: mapsMouseEvent.latLng,
            map,
            title: "Basic Marker"
        });


        //Add listener to the marker to splice element from array and remove from map on rightclick
        marker.addListener("rightclick", function () {
            for (let i = 0; i < markers.length; i++) {
                if (markers[i].marker == marker) {
                    //alert("IN IF STATEMENT");
                    //delete markers[i];
                    markers.splice(i, 1);
                    marker.setMap(null);
                    console.log(markers);
                }
            }
        });


        marker.addListener("dblclick", (mapsMouseEvent) => {
            //pinpointWindow(map, mapsMouseEvent)
        });

        var markerObj = {
            id: id, level: level, marker: marker
        }

        markers.push(markerObj);

        //calling the pinpoint window function
        //pinpointWindow(map, mapsMouseEvent);

        id++;

        console.log("LAT" + JSON.stringify(marker.position.lng()));

        AddPinpoint(
            1,
            level,
            marker.position.lat(),
            marker.position.lng(),
            1,
            "DefaultName",
            "This is a default description"
        );

        /*   export { markers, markerObj};*/
}

