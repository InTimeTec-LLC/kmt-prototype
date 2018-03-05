
package com.itt.kmt.jwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.ApplicationContextProvider;
import com.itt.kmt.models.User;
import com.itt.kmt.services.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Class MyRealm.
 */
@Controller
public class MyRealm extends AuthorizingRealm {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(MyRealm.class);

    /**
     * Instantiates a new my realm.
     */

    /*
     * (non-Javadoc)
     * @see
     * org.apache.shiro.realm.AuthenticatingRealm#supports(org.apache.shiro.
     * authc.AuthenticationToken)
     */
    @Override
    public boolean supports(final AuthenticationToken token) {

        return token instanceof JWTToken;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache
     * .shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {

        System.out.println("reading permission matrix");
        ObjectMapper objectMapper = new ObjectMapper();
        Set<String> permissions = new HashSet<String>();

        String email = JWTUtil.getemail(principals.toString());
        ApplicationContext ctx = ApplicationContextProvider.getContext();
        UserService userService = ctx.getBean(UserService.class);
        User user = userService.getUserByEmail(email);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String role = user.getUserRole();



            try {
                List<PermissionMatrix> permissionMatrix = objectMapper.readValue(
                        new File(getClass().getClassLoader().getResource("permissionsMatrix.json").getFile()), new TypeReference<List<PermissionMatrix>>() {
                        });
                for (PermissionMatrix permission : permissionMatrix) {
                    if (role.equals(permission.getRole())) {
                        permissions.addAll(permission.getPermissions());
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            simpleAuthorizationInfo.addRole(role);
            simpleAuthorizationInfo.addStringPermissions(permissions);

            return simpleAuthorizationInfo;
        }

    /*
     * (non-Javadoc)
     * @see
     * org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.
     * apache.shiro.authc.AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken auth)
        throws AuthenticationException {

        ApplicationContext ctx = ApplicationContextProvider.getContext();
        UserService userService = ctx.getBean(UserService.class);
        String token = (String) auth.getCredentials();

        /*if (token != null && !token.isEmpty() && token.startsWith("Bearer")) {
            token = token.substring(token.indexOf("Bearer") + 7, token.length());
        } else {
            throw new AuthenticationException("Invalid Token");
        }*/

        String email = JWTUtil.getemail(token);
        if (email == null) {
            throw new AuthenticationException("token invalid");
        }

        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (!JWTUtil.verify(token, email, user.getPassword())) {
            throw new AuthenticationException("Invalid Token");
        }
        AuthenticationInfo authenticationInfo = null;
        try {
            // token = "Bearer " + token;
            authenticationInfo = new SimpleAuthenticationInfo(token, token, "my_realm");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return authenticationInfo;
    }
}
