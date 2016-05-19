package lv.tsi.calendar.domain;

public class SearchBean {

    private String filterType;
    private Integer groupValue;
    private Integer teacherValue;
    private Integer roomValue;

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public Integer getGroupValue() {
        return groupValue;
    }

    public void setGroupValue(Integer groupValue) {
        this.groupValue = groupValue;
    }

    public Integer getTeacherValue() {
        return teacherValue;
    }

    public void setTeacherValue(Integer teacherValue) {
        this.teacherValue = teacherValue;
    }

    public Integer getRoomValue() {
        return roomValue;
    }

    public void setRoomValue(Integer roomValue) {
        this.roomValue = roomValue;
    }
}
