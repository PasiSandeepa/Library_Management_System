package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Student {
    private String username;
    private String email;
    private String password;
    private String address;

}
