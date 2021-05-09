package dtos;

public class MessageDto {
    public String code;
    public String message;
    public String token;
    public String expires;
    public String status;
    public String result;

    @Override
    public String toString() {
        return "MessageDto{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", expires='" + expires + '\'' +
                ", status='" + status + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
