
//variable to hold the optional info window displaying zoom level, latlng, world, pixel and tile coordinates
function initMap() {
    const ict = new google.maps.LatLng(35.901810199007215, 14.485197413626922);
    const map = new google.maps.Map(document.getElementById("map"), {
        center: ict,
        zoom: 15,
    });
    const coordInfoWindow = new google.maps.InfoWindow();
    coordInfoWindow.setContent(createInfoWindowContent(ict, map.getZoom()));
    coordInfoWindow.setPosition(ict);
    coordInfoWindow.open(map);
    //event listener that fires when zoom level changes
    map.addListener("zoom_changed", () => {
        coordInfoWindow.setContent(
            createInfoWindowContent(ict, map.getZoom())
        );
        coordInfoWindow.open(map);
    });
}


const TILE_SIZE = 256;

//https://developers.google.com/maps/documentation/javascript/examples/map-coordinates#all
//funtion to create info window
function createInfoWindowContent(latLng, zoom) {
    const scale = 1 << zoom;
    const worldCoordinate = project(latLng);
    const pixelCoordinate = new google.maps.Point(
        Math.floor(worldCoordinate.x * scale),
        Math.floor(worldCoordinate.y * scale)
    );
    const tileCoordinate = new google.maps.Point(
        Math.floor((worldCoordinate.x * scale) / TILE_SIZE),
        Math.floor((worldCoordinate.y * scale) / TILE_SIZE)
    );
    return [
        "University of Malta, MT",
        "LatLng: " + latLng,
        "Zoom level: " + zoom,
        "World Coordinate: " + worldCoordinate,
        "Pixel Coordinate: " + pixelCoordinate,
        "Tile Coordinate: " + tileCoordinate,
    ].join("<br>");
}

//https://developers.google.com/maps/documentation/javascript/examples/map-coordinates#all
//function to return world coordinates when supplying latlng
function project(latLng) {
    let siny = Math.sin((latLng.lat() * Math.PI) / 180);
    // Truncating to 0.9999 effectively limits latitude to 89.189. This is
    // about a third of a tile past the edge of the world tile.
    siny = Math.min(Math.max(siny, -0.9999), 0.9999);
    return new google.maps.Point(
        TILE_SIZE * (0.5 + latLng.lng() / 360),
        TILE_SIZE * (0.5 - Math.log((1 + siny) / (1 - siny)) / (4 * Math.PI))
    );
}