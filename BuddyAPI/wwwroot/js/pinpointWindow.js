//creating the window for pinpoints
function coordInfoWindow(map, mapsMouseEvent, pinpointid) {

    return pinpointid;
    //html to set window form
    /*const contentString = '<div id="pinpointWindow">' +
        '<label>' +
        'Floor: ' + level +
        '</label>' +
        '<br/>'+
        '<label>' +
        'Location: ' + mapsMouseEvent.latLng +
        '</label>' +
        '<br/>'+
        '<label>' +
        'Select Pinpoint Type:' +
        '<select name = "pinpointType" id = "pinpointType">' +
        '<option value = "room">Room</option>' +
        '<option value="entrance/exit">Entrance/Exit</option>' +
        '<option value="staircaseTop">Staircase Top</option>' +
        '<option value="staircaseBottom">Staircase Bottom</option>' +
        '</select >' +
        '</label >' +
        '<br />' +
        '<form name="hazard-check">' +
        'Is pinpoint a hazard?' +
        '<label><input type="radio" id="hazardYes" name="hazardCheck" value="Yes" />Yes</label>' +
        '<label><input type="radio" id="hazardNo" name="hazardCheck" value="No" checked="checked" />No</label>' +
        '</form>' +
        '<label>' +
        'Select Hazard Type:' +
        '<select name="hazardType" id="hazardType">' +
        '<option value="trip">Trip Hazard</option>' +
        '<option value="fire">Fire Hazard</option>' +
        '<option value="electrical">Electrical Hazard</option>' +
        '</select>' +
        '</label>'+
        '<br />' +
        '<label for="pinpointName">Pinpoint name: <input type="text" id="pinpointName"/></label>' +
        '<br />' +
        '<label for="pinpointDescription">Description: </label>' +
        '<textarea id="pinpointDescription"></textarea>' +
        '</div >';*/

    //const pinpointWindow = new google.maps.InfoWindow({
     //   content: contentString,
    //});

    //setting position of the window to where the mouse was clicked
   // pinpointWindow.setPosition(mapsMouseEvent.latLng);
   // pinpointWindow.open(map);


}