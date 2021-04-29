var port = location.port;
var url = "https://localhost:" + port + "/api/PinpointTypes";

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

function GetPinpointTypeById(id) {
    console.log(id);
    //const itemId = document.getElementById('pinpointTypeIdSearch').value;
    let getUrl = `${url + "/getPinpointType?id="}${id}`;

    var retData;

    fetch(getUrl)
        .then(response => response.json())
        .then(data => retData = data)//document.getElementById("searchPinpointByIdOutput").innerHTML = JSON.stringify(data))
        .catch(error => console.error('Unable to get Pinpoint Type.', error))
    return retData;
}

function EditPinpointTypeById(id) {}

function GetAllPinpointTypes() {
    //const itemId = document.getElementById('pinpointTypeIdSearch').value;
    for (var id = 0; id <= 4; id++) {
        //not displaying 
        let getUrl = `${url + "/getPinpointType?id="}${id}`;

        fetch(getUrl)
            .then(response => response.json())
            .then(data => document.getElementById("searchPinpointByIdOutput").innerHTML = JSON.stringify(data))
            .catch(error => console.error('Unable to get Pinpoint Type.', error))
    }

}