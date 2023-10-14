
var data = [],
    totalPoints = 110;
var updateInterval = 320;
var realtime = 'on';

$(function () {
    //Real time ==========================================================================================
    var plot = $.plot('#humidite', [getRandomData()], {
        series: {
            shadowSize: 0,
            color: '#2c83b6'
        },
        grid: {
            borderColor: '#efefef',
            borderWidth: 1,
            tickColor: '#efefef'
        },
        lines: {
            fill: true
        },
        yaxis: {
            min: 0,
            max: 100
        },
        xaxis: {
            min: 0,
            max: 100
        }
    });

    function updateRealTime() {
        plot.setData([getRandomData()]);
        plot.draw();

        var timeout;
        if (realtime === 'on') {
            timeout = setTimeout(updateRealTime, updateInterval);
        } else {
            clearTimeout(timeout);
        }
    }

    updateRealTime();

    $('#realtime').on('change', function () {
        realtime = this.checked ? 'on' : 'off';
        updateRealTime();
    });
    //====================================================================================================
	

});

$(function () {
    //Real time ==========================================================================================
    var plot = $.plot('#temp', [getRandomData()], {
        series: {
            shadowSize: 0,
            color: '#E76886'
        },
        grid: {
            borderColor: '#efefef',
            borderWidth: 1,
            tickColor: '#efefef'
        },
        lines: {
            fill: true
        },
        yaxis: {
            min: 0,
            max: 100
        },
        xaxis: {
            min: 0,
            max: 100
        }
    });

    function updateRealTime() {
        plot.setData([getRandomData()]);
        plot.draw();

        var timeout;
        if (realtime === 'on') {
            timeout = setTimeout(updateRealTime, updateInterval);
        } else {
            clearTimeout(timeout);
        }
    }

    updateRealTime();

    $('#realtime').on('change', function () {
        realtime = this.checked ? 'on' : 'off';
        updateRealTime();
    });
    //====================================================================================================
	

});

$(function () {
    //Real time ==========================================================================================
    var plot = $.plot('#lumino', [getRandomData()], {
        series: {
            shadowSize: 0,
            color: '#ffc107'
        },
        grid: {
            borderColor: '#efefef',
            borderWidth: 1,
            tickColor: '#efefef'
        },
        lines: {
            fill: true
        },
        yaxis: {
            min: 0,
            max: 100
        },
        xaxis: {
            min: 0,
            max: 100
        }
    });

    function updateRealTime() {
        plot.setData([getRandomData()]);
        plot.draw();

        var timeout;
        if (realtime === 'on') {
            timeout = setTimeout(updateRealTime, updateInterval);
        } else {
            clearTimeout(timeout);
        }
    }

    updateRealTime();

    $('#realtime').on('change', function () {
        realtime = this.checked ? 'on' : 'off';
        updateRealTime();
    });
    //====================================================================================================
	

});





function getRandomData() {
    if (data.length > 0) data = data.slice(1);

    while (data.length < totalPoints) {
        var prev = data.length > 0 ? data[data.length - 1] : 50,
            y = prev + Math.random() * 10 - 5;
        if (y < 0) {
            y = 0;
        } else if (y > 100) {
            y = 100;
        }

        data.push(y);
    }

    var res = [];
    for (var i = 0; i < data.length; ++i) {
        res.push([i, data[i]])
    }

    return res;
}