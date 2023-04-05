var markersGroup;

function callPingApi() {
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            handlePingApiResponse(this.responseText);
        }
    };

    const url = 'http://localhost:8080/api/ping';
    xhttp.open("GET", url, true);
    xhttp.send();
}

function callUploadCsvApi() {
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            handleUploadCsvApiResponse(this.responseText);
        }
    };

    var fileInput = document.getElementById('formFile');
    var formData = new FormData();
    formData.append('multiPartFile', fileInput.files[0]);

    const url = 'http://localhost:8080/api/csv';
    xhttp.open("POST", url, true);
    xhttp.send(formData);
}

function handlePingApiResponse(responseText) {
    console.log(responseText);
}

function handleUploadCsvApiResponse(responseText) {
    console.log(responseText);
}

function init() {
    initMap();

    document.getElementById('upload-button').addEventListener('click', uploadButtonClicked);

    setupWebsocketConnection();
}

function initMap() {
    var map = L.map('map').setView([51.505, -0.09], 13);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);

    markersGroup = L.layerGroup();

    map.addLayer(markersGroup);
}

function uploadButtonClicked() {
    // callPingApi();
    callUploadCsvApi();
}

function setupWebsocketConnection() {
    console.log('setting up web socket connection');

    const url = 'ws://localhost:8080/tsp-websocket';
    
    var stompClient = Stomp.client(url);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/graph-action', function (greeting) {
            handleWebsocketMessage(greeting.body);
        });
    });
}

function handleWebsocketMessage(message) {
    var json = JSON.parse(message);
    var action = json.actionType;
    var payload = json.payload;

    if (action === 'add-point') {
        handleAddPointAction(payload);
    } else if (action == 'clear-map') {
        handleClearMapAction(payload);
    }
}

function handleAddPointAction(payload) {
    // var marker = L.marker([payload.latitude, payload.longitude]);
    var marker = L.circle([payload.latitude, payload.longitude], 10);
    markersGroup.addLayer(marker);
}

function handleClearMapAction(payload) {
    markersGroup.clearLayers();
}