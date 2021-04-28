var port = location.port;
const url = "https://localhost:" + port + "/api/PinpointTypes";

function AddPinpointType() {
    var xhttp = new XMLHttpRequest();

    xhttp.open("POST", url +"/addPinpointType", true);
    xhttp.setRequestHeader("Content-type", "application/json");
    var obj = { PinpointType_Id: 0, PinpointTypeName: document.getElementById("pinpointTypeName").value, PinpointIcon: document.getElementById("PinpointTypeIcon").value };
    console.log(JSON.stringify(obj));
    xhttp.send(JSON.stringify(obj));

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 201) {
            var response = JSON.parse(this.responseText);
            //console.log(document.getElementById("pinpointTypeIcon").value);
            alert(response);

        } else {
            alert("Something went wrong while adding!" + this.status);
        }
    };
}

function GetPinpointTypeById() {

    const itemId = document.getElementById('pinpointTypeIdSearch').value;
    let getUrl = `${url + "/getPinpointType?id="}${itemId}`;

    fetch(getUrl)
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error('Unable to get Pinpoint Type.', error))

   /* var xhttp = new XMLHttpRequest();
    xhttp.open("GET", url + "/getPinpointType", document.getElementById("pinpointTypeIdSearch").value, true);
    console.log(document.getElementById("pinpointTypeIdSearch").value);
    xhttp.send();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            alert(response);
        } else {
            alert("Something went wrong while sarching!" + this.status);
        }
    }*/
}