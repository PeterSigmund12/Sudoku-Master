package at.jku.se.sudokumaster;

/**
 * The type Cell.
 */
public class Cell {

    private Integer value;
    private String type;
    private Integer groupId;

    /**
     * Instantiates a new empty Cell.
     */
    public Cell(){

    }

    /**
     * Instantiates a new Cell with parameters.
     *
     * @param value   the value
     * @param type    the type
     * @param groupId the group id
     */
    public Cell(Integer value, String type, Integer groupId) {
        this.value = value;
        this.type = type;
        this.groupId = groupId;
    }

    /**
     * Instantiates a new Cell only with value.
     *
     * @param value the value
     */
    public Cell(Integer value) {
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * Gets type.
     * @implNote Type is for future use if there is a need to set a specific type for each cell.
     *           e.g Cell Type: PreFilled, Hint, PlayerInput
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets group id.
     *
     * @return the group id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * Sets group id.
     *
     * @param groupId the group id
     */
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
