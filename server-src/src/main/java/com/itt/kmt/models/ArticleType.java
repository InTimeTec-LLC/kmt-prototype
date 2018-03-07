package com.itt.kmt.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;


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
     * article type.
     */
    private String type;
}
