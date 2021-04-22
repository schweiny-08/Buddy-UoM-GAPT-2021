//Creating the map centred over UOM with default zoom level 15
function initMap() {
    const ict = new google.maps.LatLng(35.901810199007215, 14.485197413626922);
    const map = new google.maps.Map(document.getElementById("map"), {
        center: ict,
        zoom: 15,
    });    
    //setting the bounds of UOM. Bounds [zoom][coord.x][coord.y]
    //bounds are generated from map tiler folder structure when tiles are created
    const bounds = {
        19: [
            [283239, 283240],
            [206056, 206057],
        ],
        20: [
            [566478, 566480],
            [412112, 412115],
        ],
        21: [
            [1132957, 1132960],
            [824225, 824231],
        ],
    };

    //https://developers.google.com/maps/documentation/javascript/examples/maptype-image-overlay#maps_maptype_image_overlay-javascript
    //creating the imageMapType for the image to be overlayed over the map
    const blkA = new google.maps.ImageMapType({
        getTileUrl: function (coord, zoom) {
            //if statement to not render the image if outside the bounds or outside the max/min zoom levels
            if (
                zoom < 19 ||
                zoom > 21 ||
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
                "https://localhost:44346/images/ICTTiles/BlockA/",
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
    const blkB = new google.maps.ImageMapType({
        getTileUrl: function (coord, zoom) {
            //if statement to not render the image if outside the bounds or outside the max/min zoom levels
            if (
                zoom < 19 ||
                zoom > 21 ||
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
                "https://localhost:44346/images/ICTTiles/BlockB/",
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
    map.overlayMapTypes.push(blkA);
    map.overlayMapTypes.push(blkB);
}