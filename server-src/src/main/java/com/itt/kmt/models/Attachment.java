/*
 * 
 */

package com.itt.kmt.models;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a Attachment.
 *
 * @author Manoj Mewara
 */

@Data
@Getter
@Setter
public class Attachment implements Serializable {

    /** The id. */
    @Id
    private String id;

    /** The file name. */
    @NotBlank
    private String fileName;

    /** The file type. */
    @NotBlank
    private String fileType;
    
    /** The Url. */
    @NotBlank
    private String Url;

    /** The file size. */
    @NotBlank
    private long fileSize;

    /** The article id. */
    private String articleId;
}
