import { markers, markerObj } from '../js/ictMap';

var port = location.port;
const url = "https://localhost:" + port + "/api/Pinpoints";

function AddPinpoint(pinpoint) {
    var xhttp = new XMLHttpRequest();

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(JSON.stringify(pinpoint));

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {

            markers.push(markerObj);

            var response = JSON.parse(this.responseText);
            alert(response);
        } else {
            alert("Something went wrong while adding!" + this.status);
        }
    };
}
//export {AddPinpoint};