package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
@AllArgsConstructor


public class User {
    private String id;
    private String name;
    private String email;
    private String address;



}

