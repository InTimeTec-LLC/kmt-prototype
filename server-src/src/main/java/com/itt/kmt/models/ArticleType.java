package com.itt.kmt.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;


=======
>>>>>>> Article new schema changes
/**
 * This class represents a Article Type.
 *
 * @author Rohan Sahu
 */

@Data
public class ArticleType implements Serializable {


    /**
     * unique identifier.
     */
    @Id
    private String id;

    /**
     * user role.
     */
    private String type;
    private String type;
=======
>>>>>>> Article new schema changes
}
