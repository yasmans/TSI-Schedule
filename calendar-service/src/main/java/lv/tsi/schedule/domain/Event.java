package lv.tsi.schedule.domain;

public class Event {

    private static final String DELIMITER = " | ";
    private Integer id;
    private String name;
    private String comment;
    private String type;
    private long timestamp;
    private String teacher;
    private String rooms;
    private String groups;

    public Event() {
    }

    public Event(Integer id, String name, String comment, long timestamp, String teacher, String rooms, String groups) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.timestamp = timestamp;
        this.teacher = teacher;
        this.rooms = rooms;
        this.groups = groups;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        if (rooms != null && !rooms.isEmpty()) {
            sb.append(rooms);
        }
        if (teacher != null && !teacher.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(DELIMITER);
            }
            sb.append(teacher);
        }
        if (name != null && !name.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(DELIMITER);
            }
            sb.append(name);
        }
        if (groups != null && !groups.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(DELIMITER);
            }
            sb.append(groups);
        }
        if (comment != null && !comment.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(DELIMITER);
            }
            sb.append(comment);
        }
        return sb.toString();
    }
}
