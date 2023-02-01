package sg.edu.nus.iss.day22workshop.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RSVP {
    private Integer id;

    private String fullName; // maps with "full_name" from the database

    private String email;

    private Integer phone;

    private Date confirmationDate; // maps with "confirmation_date" from the database

    private String comments;
}
