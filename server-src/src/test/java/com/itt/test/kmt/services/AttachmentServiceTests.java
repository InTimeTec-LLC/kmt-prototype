
package com.itt.test.kmt.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.itt.kmt.models.Attachment;
import com.itt.kmt.repositories.AttachmentRepository;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.AttachmentService;
import com.itt.test_category.ServicesTests;
import com.itt.test_data.AttachmentTestDataRepository;

@PropertySource("classpath:application.properties")
@Category(ServicesTests.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class AttachmentServiceTests {

    @Autowired
    private AttachmentTestDataRepository attachmentTestDataRepository;

    @MockBean
    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentService attachmentService;
    public static final int CONVERTOR = 1024;

    @Value("${attachments.path}")
    private String attachmentPath;

    @Before
    public final void setUp() {

    }

    @Test
    public final void saveAttachment()
        throws Exception {

        // Arrange
        Attachment attachment = attachmentTestDataRepository.getAttachments()
                                                            .get("attachment-1");

        when(attachmentRepository.save(attachment)).thenReturn(attachment);

        // Act
        Attachment createdAttachment = attachmentService.saveAttachment(attachment);

        // Assert
        assertEquals(createdAttachment.getArticleId(), attachment.getArticleId());
        assertEquals(createdAttachment.getFileName(), attachment.getFileName());
        assertEquals(createdAttachment.getFileSize(), attachment.getFileSize());
        assertEquals(createdAttachment.getFileType(), attachment.getFileType());
        verify(attachmentRepository, times(1)).save(attachment);
    }

    @Test
    public final void getAttachmentByID()
        throws Exception {

        // Arrange
        Attachment attachment = attachmentTestDataRepository.getAttachments()
                                                            .get("attachment-1");

        when(attachmentRepository.findOne(attachment.getId())).thenReturn(attachment);

        Attachment dbAttachment = attachmentService.getAttachmentByID(attachment.getId());

        // Assert
        assertEquals(dbAttachment.getArticleId(), attachment.getArticleId());
        assertEquals(dbAttachment.getFileName(), attachment.getFileName());
        assertEquals(dbAttachment.getFileSize(), attachment.getFileSize());
        assertEquals(dbAttachment.getFileType(), attachment.getFileType());
        verify(attachmentRepository, times(1)).findOne(attachment.getId());
    }

    @Test
    public final void updateAttachment()
        throws Exception {

        // Arrange
        Attachment attachment = attachmentTestDataRepository.getAttachments()
                                                            .get("attachment-1");

        when(attachmentRepository.findOne(attachment.getId())).thenReturn(attachment);
        when(attachmentRepository.save(attachment)).thenReturn(attachment);

        Attachment dbAttachment = attachmentService.updateAttachment(attachment.getId(), attachment);

        assertEquals(dbAttachment.getArticleId(), attachment.getArticleId());
        assertEquals(dbAttachment.getFileName(), attachment.getFileName());
        assertEquals(dbAttachment.getFileSize(), attachment.getFileSize());
        assertEquals(dbAttachment.getFileType(), attachment.getFileType());

        verify(attachmentRepository, times(1)).findOne(attachment.getId());
        verify(attachmentRepository, times(1)).save(attachment);
    }

    @Test
    public final void deleteAttachment()
        throws Exception {

        // Arrange
        Attachment attachment = attachmentTestDataRepository.getAttachments()
                                                            .get("attachment-1");
        when(attachmentRepository.findOne(attachment.getId())).thenReturn(attachment);

        boolean isDeleted = attachmentService.delete(attachment.getId());

        assertEquals(isDeleted, Boolean.TRUE);

        verify(attachmentRepository, times(1)).findOne(attachment.getId());
    }

    @Test
    public final void validateFileSize()
        throws Exception {

        boolean isValid = attachmentService.validateFileSize(CONVERTOR);

        assertEquals(isValid, Boolean.TRUE);
    }

    @Test
    public final void validateFileTypes()
        throws Exception {

        String fileType = "jpg";

        boolean isValid = attachmentService.validateFileTypes(fileType);

        assertEquals(isValid, Boolean.TRUE);
    }

    @Test
    public final void getArticleAttachments()
        throws Exception {

        // Arrange
        Attachment attachment1 = attachmentTestDataRepository.getAttachments()
                                                             .get("attachment-1");
        Attachment attachment2 = attachmentTestDataRepository.getAttachments()
                                                             .get("attachment-2");

        List<Attachment> attachments = new ArrayList<Attachment>();
        attachments.add(attachment1);
        attachments.add(attachment2);
        when(attachmentRepository.findByArticleId(any(String.class))).thenReturn(attachments);

        List<Attachment> retrivedList = attachmentService.getArticleAttachments("5aa3c92e6f48113e288a7fda");

        assertEquals(retrivedList.size(), attachments.size());

        verify(attachmentRepository, times(1)).findByArticleId("5aa3c92e6f48113e288a7fda");
    }

    @Test
    public void storeUploadedFiles()
        throws Exception {

        MockMultipartFile uploadfile =
            new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());

        ResponseMsg responseMsg = attachmentService.storeUploadedFiles(uploadfile);
        if (responseMsg.getStatus()) {
            File file = new File(attachmentPath + responseMsg.getMessage());
            file.delete();
        }

   //     assertEquals(responseMsg.getStatus(), Boolean.TRUE);

    }
}
