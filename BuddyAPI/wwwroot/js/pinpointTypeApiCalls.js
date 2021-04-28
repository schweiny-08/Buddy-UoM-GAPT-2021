var port = location.port;
const url = "https://localhost:" + port + "/api/PinpointTypes/addPinpointType";

function AddPinpointType() {
    var xhttp = new XMLHttpRequest();

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    var obj = { PinpointType_Id: 0, PinpointTypeName: document.getElementById("pinpointTypeName").value, PinpointTypeIcon: document.getElementById("pinpointTypeIcon").value };
    xhttp.send(JSON.stringify(obj));

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 201) {
            var response = JSON.parse(this.responseText);
            alert(response);
        } else {
            alert("Something went wrong while adding!" + this.status);
        }
    };
}