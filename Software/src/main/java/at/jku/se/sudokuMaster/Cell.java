package at.jku.se.sudokuMaster;

public class Cell {

    private Integer value;
    private String type;
    private Integer groupId;
    public Cell(){

    }
    public Cell(Integer value, String type, Integer groupId) {
        this.value = value;
        this.type = type;
        this.groupId = groupId;
    }
    public Cell(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "value=" + value +
                ", type='" + type + '\'' +
                ", groupId=" + groupId +
                '}';
    }
}
