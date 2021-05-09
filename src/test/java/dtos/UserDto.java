package dtos;

import java.util.Arrays;

public class UserDto {

    public String userID;
    public String username;
    public Book[] books;

    @Override
    public String toString() {
        return "UserDto{" +
                "userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", books=" + Arrays.toString(books) +
                '}';
    }
}
