package io.github.darthpetter.domain.model.dto;

/**
 * Data Transfer Object (DTO) representing header information.
 */
public class HeaderNameDTO {
    private int order;
    private String label;
    private String variable;

    /**
     * Gets the order of the header.
     *
     * @return The order of the header.
     */
    public int getOrder() {
        return order;
    }

    /**
     * Sets the order of the header.
     *
     * @param order The order to be set for the header.
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Gets the label of the header.
     *
     * @return The label of the header.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of the header.
     *
     * @param label The label to be set for the header.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the variable associated with the header.
     *
     * @return The variable associated with the header.
     */
    public String getVariable() {
        return variable;
    }

    /**
     * Sets the variable associated with the header.
     *
     * @param variable The variable to be set for the header.
     */
    public void setVariable(String variable) {
        this.variable = variable;
    }
}
