package com.itt.kmt.models;

<<<<<<< e228bf1867c95ddf1315bdd0522a560b7c87b6a5
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
<<<<<<< e228bf1867c95ddf1315bdd0522a560b7c87b6a5

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
=======
public class ArticleType {
>>>>>>> Article new schema changes
}
