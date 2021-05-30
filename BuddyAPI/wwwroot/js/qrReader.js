var port = location.port;
var url = "https://localhost:" + port + "/api/Pinpoints";

//function to turn on camera on html page
function turnOnCamera() {

    document.getElementById("preview").hidden = false;

    let scanner = new Instascan.Scanner({ video: document.getElementById('preview') });

    //listener for the scanning function
    scanner.addListener('scan', async function (content) {
        alert("QR Coordinate has been received");
        document.getElementById("preview").hidden = true;

        if (content == 'https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstleyVEVO') {
            //Rick Roll Redirect
            location.href = content;
        } else {
            //set the key variable and get the pinpoint object
            var key = "CurrentLocation";
            await retreivePinpointObject(key, content);
            scanner.stop();
        }
    });

    Instascan.Camera.getCameras().then(function (cameras) {
        if (cameras.length > 0) {
            scanner.start(cameras[0]);
        } else {
            console.error('No cameras found.');
        }
    }).catch(function (e) {
        console.error(e);
    });
}

async function RetreiveNavigation() {
    let startpoint = document.getElementById("startId").value;
    let endpoint = document.getElementById("endId").value;

    if (startpoint == "" && endpoint == "") {
        alert("You did not input any start on end points");
    }
    else if (startpoint == "") {
        alert("You forgot to add a starting Location");
    } else if (endpoint == "") {
        alert("You forgot to add an end Location");
    } else {
        startpoint = parseInt(startpoint);
        endpoint = parseInt(endpoint);
        document.getElementById("GetNavigation").hidden = true;

        //calling the calculate path function which in turn calls the navigation API
        calculatePath(startpoint, endpoint);
        document.getElementById("UpdateNavigation").hidden = false;
    }
}

//function to reset everything back to default values
function ResetNavigation() {
    document.getElementById("startPointName").value = "";
    document.getElementById("endPointName").value = "";
    document.getElementById("startId").value = "";
    document.getElementById("endId").value = "";


    document.getElementById("GetNavigation").hidden = false;
    document.getElementById("UpdateNavigation").hidden = true;

}

//function to get a pinpoint object based on the name passed
function retreivePinpointObject(key, pinpointQR_Url) {
    var xhttp = new XMLHttpRequest();
    var pinpointURL = url + pinpointQR_Url;
    xhttp.open("GET", pinpointURL, true);
    xhttp.responseType = 'text';
    xhttp.send();

    xhttp.onreadystatechange = async function () {
        if (this.readyState == 4, this.status == 200) {
            if (this.responseText != "") {
                var pinpoint = await JSON.parse(this.responseText);
                document.getElementById("startPointName").value = pinpoint.pinpointName;
                document.getElementById("startId").value = pinpoint.pinpoint_Id;
                savePinpointAsSession(key, pinpoint);
            }
        }
    }
}

function savePinpointAsSession(key, pinpoint) {
    var xhttp = new XMLHttpRequest();

    xhttp.open("POST", url + "/SetSessionLocation?key=" + key, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.responseType = 'json';
    xhttp.send(JSON.stringify(pinpoint));

    xhttp.onreadystatechange = function () {

        if ((this.readyState == 2 || this.readyState == 4) && this.status == 200) {
            //if (this.responseText != "") {
            //var pinObject = JSON.parse(this.response);
            //alert(pinObject);
            //}
        } else {
            alert("Something went wrong whilst setting Session Variable: " + key + " Status= " + this.status + " ReadyState= " + this.readyState);
        }
    }

}

function getSessionAsPinpoint(key) {
    var retreivedPinpoint;
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", url + "/GetSessionLocation?key=" + key, true);
    //xhttp.responseType = 'text';
    xhttp.send();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4, this.status == 200) {
            if (this.responseText != "") {
                retreivedPinpoint = JSON.parse(this.responseText);
                console.log("From Get Location Session API");
                //console.log(pinpoint);
            }
        }
    }
    return retreivedPinpoint;
}



