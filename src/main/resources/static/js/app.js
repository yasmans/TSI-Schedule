var triggerDisplayRules = function () {
    if ($('#filter-type').val() === 'room') {
        $('#filter-rooms').removeClass("hidden");
        $('#filter-groups').addClass("hidden");
        $('#filter-teachers').addClass("hidden");
    } else if ($('#filter-type').val() === 'teacher') {
        $('#filter-rooms').addClass("hidden");
        $('#filter-groups').addClass("hidden");
        $('#filter-teachers').removeClass("hidden");
    } else {
        $('#filter-rooms').addClass("hidden");
        $('#filter-groups').removeClass("hidden");
        $('#filter-teachers').addClass("hidden");
    }
};

$('#filter-type').on('change', function () {
    triggerDisplayRules();
});

function populateDropdowns() {
    $.ajax({
        url: '/referenceData'
    }).then(function (data) {
        var filter_rooms = $("#filter-value-room");
        var filter_teachers = $("#filter-value-teacher");
        var filter_groups = $("#filter-value-group");
        $.each(data.rooms, function () {
            filter_rooms.append($("<option />").val(this.id).text(this.name));
        });
        $.each(data.teachers, function () {
            filter_teachers.append($("<option />").val(this.id).text(this.name));
        });
        $.each(data.groups, function () {
            filter_groups.append($("<option />").val(this.id).text(this.name));
        });
        filter_rooms.selectpicker('refresh');
        filter_teachers.selectpicker('refresh');
        filter_groups.selectpicker('refresh');
    });
}

var getSiteURL = function () {
    if (!window.location.origin) {
        return window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port : '');
    } else {
        return window.location.origin;
    }
};

var searchEvents = function () {
    var type = $('#filter-type').val();
    var group = $('#filter-value-group').val();
    var room = $('#filter-value-room').val();
    var teacher = $('#filter-value-teacher').val();
    var group_value = '';
    var room_value = '';
    var teacher_value = '';
    var moment = window.moment();
    moment.date(1);
    moment.hour(12);
    var date_from = moment.format('YYYY-MM-DD');
    moment.add(5, 'month');
    var date_to = moment.format('YYYY-MM-DD');
    switch (type) {
        case 'group':
            group_value = group;
            break;
        case 'room':
            room_value = room;
            break;
        case 'teacher':
            teacher_value = teacher;
            break;
        default:
            return;
    }

    $.ajax({
        url: '/events',
        data: {
            lang: user_language,
            from: date_from,
            to: date_to,
            teachers: teacher_value,
            rooms: room_value,
            groups: group_value
        }
    }).then(function (data) {
        events = [];
        $.each(data, function () {
            events.push({
                title: $(this).attr('summary'),
                start: $(this).attr('timestamp')
            })
        });
        $('#calendar').fullCalendar('removeEvents');
        $('#calendar').fullCalendar('addEventSource', events);
        $('#calendar-url').val(getSiteURL() + '/calendar/' + date_from + '/' + date_to + '/calendar.ics?lang=' + user_language +
            '&groups=' + group_value + '&rooms=' + room_value + '&teachers=' + teacher_value);
    });

};

$(document).ready(function () {
    $('#calendar').fullCalendar({});
    $('#calendar-url').val('');
    triggerDisplayRules();
    populateDropdowns();
});
