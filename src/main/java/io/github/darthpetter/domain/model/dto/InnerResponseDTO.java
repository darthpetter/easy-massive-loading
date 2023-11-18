package io.github.darthpetter.domain.model.dto;

public class InnerResponseDTO<T> {
    private boolean ok = true;
    private T data;
    private String message;
    private String code;

    /**
     * @return boolean return the ok
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * @param ok the ok to set
     */
    public void setOk(boolean ok) {
        this.ok = ok;
    }

    /**
     * @return <T> return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * @return String return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return String return the code
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
