package lv.tsi.schedule.domain;

import java.util.Date;
import java.util.Set;

public class Event {

    private Integer id;
    private String name;
    private String comment;
    private String type;
    private Date time;
    private Set<ReferenceData> teachers;
    private Set<ReferenceData> rooms;
    private Set<ReferenceData> groups;

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Set<ReferenceData> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<ReferenceData> teachers) {
        this.teachers = teachers;
    }

    public Set<ReferenceData> getRooms() {
        return rooms;
    }

    public void setRooms(Set<ReferenceData> rooms) {
        this.rooms = rooms;
    }

    public Set<ReferenceData> getGroups() {
        return groups;
    }

    public void setGroups(Set<ReferenceData> groups) {
        this.groups = groups;
    }
}
