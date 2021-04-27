
const port = location.port;
const uri = 'https://localhost:' + port + '/api/';


function include(file) {
    var script = document.createElement('script');
    script.src = file;
    script.type = 'text/javascript';
    script.defer = true;

    document.getElementsByTagName('head').item(0).appendChild(script);
}

include('/js/modeljs/roles.js');