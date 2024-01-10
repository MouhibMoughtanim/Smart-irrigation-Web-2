const queryString = window.location.href;
const values = queryString.split('/');
const zone_id = 31;

// Data for temperature chart
let tempDataValues = ['Temperature'];
let tempDataKeys = ['x'];

// Data for humidity chart
let humidityDataValues = ['Humidity'];
let humidityDataKeys = ['x'];

// Chart initialization for temperature
let tempChart = c3.generate({
	bindto: '#temperature-chart',
	data: {
		x: 'x',
		columns: [tempDataKeys, tempDataValues],
		type: 'area',
		colors: {
			'Temperature': '#61bda1',
		},
		names: {
			'Temperature': 'Temperature',
		},
	},
	axis: {
		x: {
			type: 'category',
			tick: {
				culling: true,
				count: 10,
				format: '%Y-%m-%d %H:%M:%S',
			},
		},
	},
	legend: {
		show: true,
	},
	padding: {
		bottom: 20,
		top: 0,
	},
});

// Chart initialization for humidity
let humidityChart = c3.generate({
	bindto: '#humidity-chart',
	data: {
		x: 'x',
		columns: [humidityDataKeys, humidityDataValues],
		type: 'area',
		colors: {
			'Humidity': '#0074d9',
		},
		names: {
			'Humidity': 'Humidity',
		},
	},
	axis: {
		x: {
			type: 'category',
			tick: {
				culling: true,
				count: 10,
				format: '%Y-%m-%d %H:%M:%S',
			},
		},
	},
	legend: {
		show: true,
	},
	padding: {
		bottom: 20,
		top: 0,
	},
});

// Function to add data to the temperature chart
function addTempData(dateTime, temperature) {
	tempDataKeys.push(dateTime);
	tempDataValues.push(temperature);

	// Check if temperature exceeds the maximum value
	const tempMax = 22;
	if (temperature > tempMax) {
		showTemperatureExceededNotification(dateTime, temperature);
	}

	// Refresh the temperature chart every time new data is received
	updateTempChart();
}

// Function to add data to the humidity chart
function addHumidityData(dateTime, humidity) {
	humidityDataKeys.push(dateTime);
	humidityDataValues.push(humidity);

	// Refresh the humidity chart every time new data is received
	updateHumidityChart();
}

// Function to update the temperature chart
function updateTempChart() {
	// Update the existing temperature chart
	tempChart.load({
		columns: [tempDataKeys, tempDataValues],
	});
}

// Function to update the humidity chart
function updateHumidityChart() {
	// Update the existing humidity chart
	humidityChart.load({
		columns: [humidityDataKeys, humidityDataValues],
	});
}

// Function to show a notification when temperature exceeds the maximum value
function showTemperatureExceededNotification(dateTime, temperature) {
	const notificationDot = document.querySelector('.notification-dot');
	const feedsWidget = document.querySelector('.feeds_widget');

	// Update the notification count
	const notificationCount = parseInt(notificationDot.innerText);
	notificationDot.innerText = notificationCount + 1;

	// Add a new notification entry to the dropdown
	const newNotification = document.createElement('li');
	newNotification.innerHTML = `
            <a href="#">
                <div class="mr-4"><i class="fa fa-thermometer-full text-red"></i></div>
                <div class="feeds-body">
                    <h4 class="title text-red">Temperature Exceeded <small class="float-right text-muted font-12">${dateTime}</small></h4>
                    <small>Temperature exceeded the maximum value: ${temperature}°C</small>
                </div>
            </a>
        `;

	feedsWidget.insertBefore(newNotification, feedsWidget.firstChild);

	// Show the dropdown if it was hidden
	feedsWidget.style.display = 'block';
}

// Networking
let sse = new EventSource("/realtime/" + zone_id);

sse.onmessage = function (e) {
	console.log(e.data);
	const grandeur = JSON.parse(e.data);

	// Assuming grandeur.dateTime, grandeur.temperature, and grandeur.humidity are the values
	addTempData(grandeur.dateTime, grandeur.temperature);
	addHumidityData(grandeur.dateTime, grandeur.humidity);

	// Show the notification when temperature exceeds the maximum value
	if (grandeur.temperature > 50) {
		showTemperatureExceededNotification(grandeur.dateTime, grandeur.temperature);
	}
};

// Close SSE connection when the page unloads
window.addEventListener('beforeunload', function () {
	sse.close();
});

// Fetch new data and update the charts every 10 seconds
setInterval(() => {
	fetchDataFromSSE();
}, 10000);

// Function to make a request to SSE every 10 seconds
function fetchDataFromSSE() {
	// Close existing SSE connection
	sse.close();

	// Create a new SSE connection
	sse = new EventSource("/realtime/" + zone_id);

	sse.onmessage = function (e) {
		console.log(e.data);
		const grandeur = JSON.parse(e.data);

		// Assuming grandeur.dateTime, grandeur.temperature, and grandeur.humidity are the values
		addTempData(grandeur.dateTime, grandeur.temperature);
		addHumidityData(grandeur.dateTime, grandeur.humidity);
	};
}