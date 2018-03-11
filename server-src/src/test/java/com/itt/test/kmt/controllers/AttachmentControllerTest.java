
package com.itt.test.kmt.controllers;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.itt.kmt.models.Attachment;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.AttachmentService;
import com.itt.utility.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class AttachmentControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebMvc

/** The Constant log. */

/** The Constant log. */
@Slf4j
public class AttachmentControllerTest extends AbstractShiroTest {

    /** The mock mvc. */
    @Autowired
    private MockMvc mockMvc;

    /** The attachment service. */
    @MockBean
    private AttachmentService attachmentService;

    /** The content type. */
    private MediaType contentType = new MediaType("application", "json", Charset.forName("UTF-8"));

    /** The ctx. */
    @Autowired
    private WebApplicationContext ctx;

    /** The subject under test. */
    private Subject subjectUnderTest;

    /** The mock session. */
    private MockHttpSession mockSession;

    /** The wac. */
    @Autowired
    private WebApplicationContext wac;

    /** The Constant CONVERTOR. */
    public static final int CONVERTOR = 1024;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp()
        throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                                 .build();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                                      .build();
        DefaultWebSecurityManager securityManger =
            Mockito.mock(DefaultWebSecurityManager.class, RETURNS_DEEP_STUBS);
        ThreadContext.bind(securityManger);
        // 1. Create a mock authenticated Subject instance for the test to run:
        subjectUnderTest = new Subject.Builder((DefaultWebSecurityManager) getSecurityManager()).buildSubject();

        mockSession = new MockHttpSession(
            ctx.getServletContext(), subjectUnderTest.getSession()
                                                     .getId()
                                                     .toString());
        // 2. Bind the subject to the current thread:
        setSubject(subjectUnderTest);
    }

    @Test
    public void uploadAttachment()
        throws Exception {

        MockMultipartFile uploadfile =
            new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        ResponseMsg responseMsg = new ResponseMsg(Boolean.TRUE, "filename123.txt");
        Attachment attachment = new Attachment();
        attachment.setFileName("filename.txt");
        attachment.setFileSize(CONVERTOR);
        attachment.setFileType("txt");
        attachment.setArticleId("5aa3b12d348c287cbc62120d");
        attachment.setId("5aa3b1e96f4811c3c0108b84");
        attachment.setUrl("api/attachments/filename.txt");

        when(attachmentService.storeUploadedFiles(uploadfile)).thenReturn(responseMsg);
        when(attachmentService.saveAttachment(any(Attachment.class))).thenReturn(attachment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.fileUpload("/attachments/")
                                                              .file(uploadfile)
                                                              .param("fileType", "txt")
                                                              .param("fileName", "filename.txt");

        // Act
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        // Assert
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.success.message", is(Constants.ATTACHMENT_ADDED_MESSAGE)))
                     .andExpect(jsonPath("$.success.status", is(Boolean.TRUE)));

        verify(attachmentService, times(1)).storeUploadedFiles(uploadfile);
        verify(attachmentService, times(1)).saveAttachment(any(Attachment.class));
    }

    @Test
    public void uploadAttachmentError()
        throws Exception {

        MockMultipartFile uploadfile =
            new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        ResponseMsg responseMsg = new ResponseMsg(Boolean.FALSE, "some problem occured");
        Attachment attachment = new Attachment();
        attachment.setFileName("filename.txt");
        attachment.setFileSize(CONVERTOR);
        attachment.setFileType("txt");
        attachment.setArticleId("5aa3b12d348c287cbc62120d");
        attachment.setId("5aa3b1e96f4811c3c0108b84");
        attachment.setUrl("api/attachments/filename.txt");

        when(attachmentService.storeUploadedFiles(uploadfile)).thenReturn(responseMsg);
        when(attachmentService.saveAttachment(any(Attachment.class))).thenReturn(attachment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.fileUpload("/attachments/")
                                                              .file(uploadfile)
                                                              .param("fileType", "txt")
                                                              .param("fileName", "filename.txt");

        // Act
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        // Assert
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.success.message", is("some problem occured")))
                     .andExpect(jsonPath("$.success.status", is(Boolean.FALSE)));

        //verify(attachmentService, times(1)).storeUploadedFiles(uploadfile);
    }

    @Test
    public void downloadAttachment()
        throws Exception {

        Attachment attachment = new Attachment();
        attachment.setFileName("filename.txt");
        attachment.setFileSize(CONVERTOR);
        attachment.setFileType("txt");
        attachment.setArticleId("5aa3b12d348c287cbc62120d");
        attachment.setId("5aa3b1e96f4811c3c0108b84");
        attachment.setUrl("api/attachments/filename.txt");

        // Arrange
        when(attachmentService.getAttachmentByID("5aa3b1e96f4811c3c0108b84")).thenReturn(attachment);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/attachments/5aa3b1e96f4811c3c0108b84")
                                                              .accept(MediaType.APPLICATION_JSON);

        // Act
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        // Assert
        resultActions.andExpect(status().isBadRequest());

        verify(attachmentService, times(1)).getAttachmentByID("5aa3b1e96f4811c3c0108b84");
    }

    /**
     * Delete attachment by id.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteAttachmentById()
        throws Exception {

        // Arrange
        when(attachmentService.delete("5aa3b1e96f4811c3c0108b84")).thenReturn(Boolean.TRUE);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/attachments/5aa3b1e96f4811c3c0108b84")
                                                              .accept(MediaType.APPLICATION_JSON);

        // Act
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        // Assert
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.success.message", is(Constants.ATTACHMENT_DELETED_MESSAGE)))
                     .andExpect(jsonPath("$.success.status", is(Boolean.TRUE)));

        verify(attachmentService, times(1)).delete("5aa3b1e96f4811c3c0108b84");
    }

    @Test
    public void deleteAttachmentErrorById()
        throws Exception {

        // Arrange
        when(attachmentService.delete("5aa3b1e96f4811c3c0108b84")).thenReturn(Boolean.FALSE);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/attachments/5aa3b1e96f4811c3c0108b84")
                                                              .accept(MediaType.APPLICATION_JSON);

        // Act
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        // Assert
        resultActions.andExpect(status().isOk())
                     .andExpect(
                         jsonPath("$.success.message", is("some problem occured while deleting attachment")))
                     .andExpect(jsonPath("$.success.status", is(Boolean.FALSE)));

        verify(attachmentService, times(1)).delete("5aa3b1e96f4811c3c0108b84");
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {

    }
}
