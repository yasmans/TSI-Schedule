package lv.tsi.calendar.domain;

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
        return id == null ? -1 : id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment == null ? "" : comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp * 1000;
    }

    public String getTeacher() {
        return teacher == null ? "" : teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRooms() {
        return rooms == null ? "" : rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getGroups() {
        return groups == null ? "" : groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        if (!getName().isEmpty()) {
            sb.append(getName());
        }
        if (!getTeacher().isEmpty()) {
            if (sb.length() > 0) {
                sb.append(DELIMITER);
            }
            sb.append(getTeacher());
        }
        if (!getGroups().isEmpty()) {
            if (sb.length() > 0) {
                sb.append(DELIMITER);
            }
            sb.append(getGroups());
        }
        return sb.toString();
    }
}
