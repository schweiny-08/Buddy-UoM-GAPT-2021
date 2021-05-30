var port = location.port;
var url = "https://localhost:" + port + "/api/Pinpoints";
var markers = [];

function AddPinpoint(ppTypeId, level, lat, lng, hazId, ppName, ppDesc) {
    var xhttp = new XMLHttpRequest();

    xhttp.open("POST", url + "/addPinpoint", true);
    xhttp.setRequestHeader("Content-type", "application/json");

    //creating pinpoint object
    var pinpoint = {
        Pinpoint_Id: 0,
        PinpointType_Id: ppTypeId,
        Floor_Id: level,
        Latitude: lat,
        Longitude: lng,
        Hazard_Id: hazId,
        PinpointName: ppName,
        PinpointDescription: ppDesc
    };

    //sending request
    xhttp.send(JSON.stringify(pinpoint));

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            alert(response);
        } else {
            alert("Something went wrong while adding!" + this.status);
        }
    };
}

//returning all pinpoints from database
function GetAllPinpoints() {
    var level;
    var iconType;
    var pinpnt;

    //sending request to API to retrieve all pinpoints
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", url + "/getAllPinpoints", true);
    xhttp.responseType = "text";
    xhttp.send();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4, this.status == 200) {
            if (this.responseText != "") {
                pinpnt = JSON.parse(this.responseText);
                pinpnt.forEach(function (data, index) {

                    //setting the floor level
                    if (data.floor_Id == 2)
                        level = -1
                    if (data.floor_Id == 3)
                        level = 0;
                    //setting the icon type
                    if (data.pinpointType_Id == 1)
                        iconType = "Room.png";
                    else if (data.pinpointType_Id == 2)
                        iconType = "EntExt.png";
                    else if (data.pinpointType_Id == 4 || data.pinpointType_Id == 14)
                        iconType = "Stairs.png";
                    else if (data.pinpointType_Id == 15)
                        iconType = "";

                    var markerObj = {
                        id: data.pinpoint_Id,
                        name: data.pinpointName,
                        level: level,
                        marker: new google.maps.Marker({
                            position: { lat: data.latitude, lng: data.longitude },
                            icon: iconBase + iconType,
                            map
                        })
                    };

                    //adding event listener for double-clicking to show pinpoint name
                    markerObj.marker.addListener("dblclick", function () {
                        const coordInfoWindow = new google.maps.InfoWindow();
                        coordInfoWindow.setContent(String(data.pinpointName));
                        coordInfoWindow.setPosition(markerObj.marker.position);
                        coordInfoWindow.open(map);

                    });

                    //adding event listener to set the clicked pinpoint as the start point
                    markerObj.marker.addListener("click", function () {

                        document.getElementById("startPointName").value = markerObj.name;
                        document.getElementById("startId").value = markerObj.id;
                    });

                    ////adding event listener to set the right-clicked pinpoint as the end point
                    markerObj.marker.addListener("rightclick", function () {

                        document.getElementById("endPointName").value = markerObj.name;
                        document.getElementById("endId").value = markerObj.id;
                        savePinpointAsSession("EndLocation", data);
                    });

                    //adding the marker object to the markers array
                    markers.push(markerObj);
                    //clicking the no overlay option in order to not show any pinpoints on page load
                    document.getElementById('no-overlay').click();
                });
            }
        }
    };
}

