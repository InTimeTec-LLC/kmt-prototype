
package com.itt.test.kmt.controllers;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.LifecycleUtils;
import org.apache.shiro.util.ThreadState;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.AfterClass;

/**
 * The Class AbstractShiroTest.
 */
public abstract class AbstractShiroTest {

    /** The subject thread state. */
    private static ThreadState subjectThreadState;

    /**
     * Instantiates a new abstract shiro test.
     */
    public AbstractShiroTest() {

    }

    /**
     * Sets the subject.
     *
     * @param subject the new subject
     */
    protected void setSubject(final Subject subject) {

        clearSubject();
        subjectThreadState = createThreadState(subject);
        subjectThreadState.bind();
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    protected Subject getSubject() {

        return SecurityUtils.getSubject();
    }

    /**
     * Creates the thread state.
     *
     * @param subject the subject
     * @return the thread state
     */
    protected ThreadState createThreadState(final Subject subject) {

        return new SubjectThreadState(subject);
    }

    /**
     * Clears Shiro's thread state, ensuring the thread remains clean for future
     * test execution.
     */
    protected void clearSubject() {

        doClearSubject();
    }

    /**
     * Do clear subject.
     */
    private static void doClearSubject() {

        if (subjectThreadState != null) {
            subjectThreadState.clear();
            subjectThreadState = null;
        }
    }

    /**
     * Sets the security manager.
     *
     * @param securityManager the new security manager
     */
    protected static void setSecurityManager(final SecurityManager securityManager) {

        SecurityUtils.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);
    }

    /**
     * Gets the security manager.
     *
     * @return the security manager
     */
    protected static DefaultWebSecurityManager getSecurityManager() {

        return (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
    }

    /**
     * Tear down shiro.
     */
    @AfterClass
    public static void tearDownShiro() {

        doClearSubject();
        try {
            DefaultWebSecurityManager securityManager = getSecurityManager();
            LifecycleUtils.destroy(securityManager);
        } catch (UnavailableSecurityManagerException e) {
            e.printStackTrace();
        }
        setSecurityManager(null);
    }
}
