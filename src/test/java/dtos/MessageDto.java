package dtos;

public class MessageDto {
    public String code;
    public String message;

    @Override
    public String toString() {
        return "MessageDto{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
