
package com.itt.kmt.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itt.kmt.models.Attachment;
import com.itt.kmt.repositories.AttachmentRepository;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.utility.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class that acts as an intermediary between controller and the
 * database for all basic CRUD operations. The business logic should reside in
 * service class.
 * 
 * @author Manoj Mewara
 */
@Service
@PropertySource("classpath:application.properties")

/** The Constant log. */

/** The Constant log. */
@Slf4j

public class AttachmentService {

    /**
     * Instance of the basic Repository implementation.
     */
    @Autowired
    private AttachmentRepository attachmentRepository;

    /** The valid file types. */
    @Value("${attachments.type}")
    private String validFileTypes;

    /** The uploaded folder. */
    @Value("${attachments.path}")
    private String attachmentPath;

    /** The max allowed file size. */
    @Value("${attachments.maxallowed.filesize}")
    private int maxAllowedFileSize;

    /** The Constant convertor. */
    public static final int CONVERTOR = 1024;
    
    /**
     * Save attachment.
     *
     * @param attachment the attachment
     * @return the attachment
     */
    public Attachment saveAttachment(final Attachment attachment) {

        return attachmentRepository.save(attachment);
    }

    /**
     * Gets the attachment by ID.
     *
     * @param id the id
     * @return the attachment by ID
     */
    public Attachment getAttachmentByID(final String id) {

        return attachmentRepository.findOne(id);
    }

    /**
     * Update attachment.
     *
     * @param id the id
     * @param updatedAttachment the updated attachment
     * @return the attachment
     */
    public Attachment updateAttachment(final String id, final Attachment updatedAttachment) {

        Attachment attachment = attachmentRepository.findOne(id);
        if (attachment == null) {
            throw new RuntimeException(Constants.ATTACHMENT_NOT_FOUND_ERROR_MESSAGE);
        }
        return attachmentRepository.save(updateAttachment(attachment, updatedAttachment));
    }

    /**
     * Update attachment with article id.
     *
     * @param attachments the attachments
     * @param articleId the article id
     */
    public void updateAttachmentWithArticleId(final List<Attachment> attachments, final String articleId) {

        try {
            for (Attachment attachment : attachments) {
                Attachment dbAttachment = getAttachmentByID(attachment.getId());
                dbAttachment.setArticleId(articleId);
                updateAttachment(dbAttachment.getId(), dbAttachment);
            }
        } catch (Exception ex) {
            log.error("an exception was thrown: ", ex);
        }
    }

    /**
     * Delete attachment with article id.
     *
     * @param articleId the article id
     */
    public void deleteAttachmentWithArticleId(final String articleId) {

        try {
            List<Attachment> attachments = getArticleAttachments(articleId);
            for (Attachment attachment : attachments) {
                delete(attachment.getId());
            }
        } catch (Exception ex) {
            log.error("an exception was thrown: ", ex);
        }
    }

    /**
     * Gets the article attachments.
     *
     * @param articleId the article id
     * @return the article attachments
     */
    public List<Attachment> getArticleAttachments(final String articleId) {

        List<Attachment> attachments = attachmentRepository.findByArticleId(articleId);
        for (Attachment attachment : attachments) {
            attachment.setUrl("api/attachments/" + attachment.getId());
        }
        return attachments;
    }

    /**
     * Update attachment.
     *
     * @param attachment the attachment
     * @param updatedAttachment the updated attachment
     * @return the attachment
     */
    public Attachment updateAttachment(final Attachment attachment, final Attachment updatedAttachment) {

        if (updatedAttachment.getFileName() != null && !updatedAttachment.getFileName()
                                                                         .isEmpty()) {
            attachment.setFileName(updatedAttachment.getFileName());
        }
        if (updatedAttachment.getFileSize() != 0) {
            attachment.setFileSize(updatedAttachment.getFileSize());
        }
        if (updatedAttachment.getFileType() != null) {
            attachment.setFileType(updatedAttachment.getFileType());
        }
        if (updatedAttachment.getArticleId() != null && !updatedAttachment.getArticleId()
                                                                          .isEmpty()) {
            attachment.setArticleId(updatedAttachment.getArticleId());
        }
        if (updatedAttachment.getUrl() != null && updatedAttachment.getUrl()
                                                                   .isEmpty()) {
            attachment.setUrl(updatedAttachment.getUrl());
        }

        return attachment;
    }

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     */
    public boolean delete(final String id) {

        boolean isDeleted = false;
        Attachment attachment = attachmentRepository.findOne(id);

        if (attachment == null) {
            throw new RuntimeException(Constants.ATTACHMENT_NOT_FOUND_ERROR_MESSAGE);
        }

        try {
            File file = new File(attachmentPath + attachment.getFileName());
            file.delete();
            // delete from database
            attachmentRepository.delete(id);
            isDeleted = true;
        } catch (Exception ex) {
            log.error("an exception was thrown: ", ex);
        }

        return isDeleted;
    }

    /**
     * Save uploaded files.
     *
     * @param file the file
     * @return the response msg
     * @throws IOException Signals that an I/O exception has occurred.
     */
    // save file
    public ResponseMsg storeUploadedFiles(final MultipartFile file)
        throws IOException {

        ResponseMsg responseMsg = null;
        if (file.isEmpty()) {
            responseMsg = new ResponseMsg(Boolean.FALSE, Constants.NO_ATTACHMENT_UPLOADED_ERROR_MESSAGE);
            return responseMsg;
        }

        try {
            long timeStamp = System.currentTimeMillis();
            String fileName = file.getOriginalFilename();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + timeStamp + "." + fileExtension;

            if (!validateFileTypes(fileExtension)) {
                responseMsg = new ResponseMsg(Boolean.FALSE, Constants.ATTACHMENT_SUPPORTED_FORMAT_ERROR_MESSAGE);
                return responseMsg;
            }

            if (!validateFileSize(file.getSize())) {
                responseMsg = new ResponseMsg(Boolean.FALSE, Constants.ATTACHMENT_SIZE_ERROR_MESSAGE);
                return responseMsg;
            }

            log.info("Uploaded file name : " + fileName);
            log.info("Uploaded file size : " + file.getSize());
            log.info("Uploaded file type : " + file.getContentType());

            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachmentPath + fileName);
            Files.write(path, bytes);

            responseMsg = new ResponseMsg(Boolean.TRUE, fileName);

        } catch (Exception ex) {
            responseMsg = new ResponseMsg(Boolean.FALSE, "an exception was thrown:" + ex.getMessage());
            log.error("an exception was thrown: ", ex);
        }
        return responseMsg;
    }

    /**
     * Validate file types.
     *
     * @param fileExtension the file extension
     * @return true, if successful
     */
    public boolean validateFileTypes(final String fileExtension) {

        boolean isValid = false;
        List<String> validTypeList = new ArrayList<String>(Arrays.asList(validFileTypes.split("\\s*,\\s*")));
        if (validTypeList.contains(fileExtension.toLowerCase())) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Validate file size.
     *
     * @param fileSize the file size
     * @return true, if successful
     */
    public boolean validateFileSize(final long fileSize) {
        
        boolean isValid = false;
        if (fileSize > 0) {
            // converting bytes to mb
            long fileSizeInMB = (fileSize / CONVERTOR) / CONVERTOR;
            if (fileSizeInMB < maxAllowedFileSize) {
                isValid = true;
            }
        }

        return isValid;

    }

}
