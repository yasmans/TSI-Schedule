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
    }).then(function(data) {
        var filter_rooms = $("#filter-value-room");
        var filter_teachers = $("#filter-value-teacher");
        var filter_groups = $("#filter-value-group");
        $.each(data.rooms, function() {
            filter_rooms.append($("<option />").val(this.id).text(this.name));
        });
        $.each(data.teachers, function() {
            filter_teachers.append($("<option />").val(this.id).text(this.name));
        });
        $.each(data.groups, function() {
            filter_groups.append($("<option />").val(this.id).text(this.name));
        });
        filter_rooms.selectpicker('refresh');
        filter_teachers.selectpicker('refresh');
        filter_groups.selectpicker('refresh');
    });
}

var searchEvents= function() {
    var type = $('#filter-type').val();
    var group = $('#filter-value-group').val();
    var room = $('#filter-value-room').val();
    var teacher = $('#filter-value-teacher').val();
    var group_value = '';
    var room_value = '';
    var teacher_value = '';
    var date_from = '2016-03-01';
    var date_to = '2016-07-01';
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
        default: return;
    }

    $.ajax({
        url: '/events',
        data: {
            lang: 'lv',
            from: date_from,
            to: date_to,
            teachers: teacher_value,
            rooms: room_value,
            groups: group_value
        }
    }).then(function(data) {
        events = [];
        $.each(data, function() {
            events.push({
                title: $(this).attr('summary'),
                start: $(this).attr('timestamp')
            })
        });
        $('#calendar').fullCalendar('removeEvents');
        $('#calendar').fullCalendar('addEventSource', events );
        $('#calendar-url').val('Create URL creator');
    });

};

$(document).ready(function () {
    $('#calendar').fullCalendar({

    });

    triggerDisplayRules();
    populateDropdowns();
});
