var port = location.port;
var url = "https://localhost:" + port + "/api/Pinpoints";
var markers = [];

function AddPinpoint(ppTypeId ,level, lat, lng,hazId, ppName, ppDesc) {
    var xhttp = new XMLHttpRequest();

    xhttp.open("POST", url + "/addPinpoint", true);
    xhttp.setRequestHeader("Content-type", "application/json");

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

    //console.log(lat);
    console.log(JSON.stringify(pinpoint));

    xhttp.send(JSON.stringify(pinpoint));

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {

            //markers.push(markerObj);

            var response = JSON.parse(this.responseText);
            alert(response);
        } else {
            alert("Something went wrong while adding!" + this.status);
        }
    };
}

function EditPinpoint() {

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            alert(response);
        }
    };

    var ppTypeID;
    var hazId;

    if (document.getElementById("pinpointType").value === "room") {
        ppTypeID = 1;
    }
     if (document.getElementById("pinpointType").value === "entrance/exit") {
        ppTypeID = 2;
    }
     if (document.getElementById("pinpointType").value === "staircaseTop") {
        ppTypeID = 3;
    }
     if (document.getElementById("pinpointType").value === "staircaseBottom") {
        ppTypeID = 4;
    }

    if (document.getElementById("hazardType").value === "trip") {
        hazId = 1;
    }

    console.log(document.getElementById("hazardType").value);
    console.log(document.getElementById("pinpointType").value);
    console.log(document.getElementById("pinpointName").value);



    xhttp.open("PUT", url + "/editPinpoint/", true);
    xhttp.setRequestHeader('Content-type', 'application/json');
    var data = new FormData();
    data.append("PinpointType_Id", ppTypeID);
    //data.append("Hazard_Id", hazId);
    data.append("PinpointName", document.getElementById("pinpointName").value);
    data.append("PinpointDescription", document.getElementById("pinpointDescription").value);
    xhttp.send(JSON.stringify(data));
}

function GetAllPinpoints() {
    var level;
    var iconType

    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", url + "/getAllPinpoints", true);
    xhttp.send();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4, this.status == 200) {
            var pinpnt = JSON.parse(this.responseText);
            //console.log(pinpnt);
            pinpnt.forEach(function (data, index) {
                

                if (data.floor_Id == 2)
                    level = -1
                if (data.floor_Id == 3)
                    level = 0;

                if (data.pinpointType_Id == 1)
                    iconType = "Room.png";
                else if (data.pinpointType_Id == 2)
                    iconType = "EntExt.png";
                else if (data.pinpointType_Id == 4 || data.pinpointType_Id == 14)
                    iconType = "Stairs.png";
                else if (data.pinpointType_Id == 15)
                    iconType = "NavNode.png";

                var id = 0;

                //console.log(data);
                var markerObj = {
                    id: id,
                    level: level,
                    marker: new google.maps.Marker({
                        position: { lat: data.latitude, lng: data.longitude },
                        icon: iconBase + iconType,
                        map
                    })
                };
                markers.push(markerObj);
                id++;
            });
        }
    };
}

