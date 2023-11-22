package io.github.darthpetter.domain.model.dto;

/**
 * Generic Data Transfer Object (DTO) representing an inner response.
 *
 * @param <T> The type of data stored in the response.
 */
public class InnerResponseDTO<T> {
    private boolean ok = true;
    private T data;
    private String message;
    private String code;

    /**
     * Gets the boolean indicating the success of the response.
     *
     * @return true if the response is successful, false otherwise.
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * Sets the boolean indicating the success of the response.
     *
     * @param ok The boolean to set for the response.
     */
    public void setOk(boolean ok) {
        this.ok = ok;
    }

    /**
     * Gets the data contained in the response.
     *
     * @return The data contained in the response.
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data for the response.
     *
     * @param data The data to set for the response.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Gets the message associated with the response.
     *
     * @return The message associated with the response.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message for the response.
     *
     * @param message The message to set for the response.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the code associated with the response.
     *
     * @return The code associated with the response.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code for the response.
     *
     * @param code The code to set for the response.
     */
    public void setCode(String code) {
        this.code = code;
    }
}
