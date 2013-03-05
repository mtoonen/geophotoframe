/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meine.gpf.stripes;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import nl.meine.gpf.SecurityRealm;
import nl.meine.gpf.entities.Gebruiker;
import nl.meine.gpf.entities.Role;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stripesstuff.stripersist.EntityTypeConverter;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Meine Toonen
 */
public class UserActionBean implements ActionBean {

    private static final Log log = LogFactory.getLog(UserActionBean.class);
    private ActionBeanContext context;
    private static final String OVERVIEW = "/WEB-INF/jsp/admin/users/usersoverview.jsp";
    private static final String EDIT = "/WEB-INF/jsp/admin/users/edituser.jsp";
    @Validate(converter = EntityTypeConverter.class)
    @ValidateNestedProperties({
        @Validate(field = "username", required = true, maxlength = 30, on = "save"),
        @Validate(field = "fullname", maxlength = 50, on = "save"),
        @Validate(field = "email", converter = EmailTypeConverter.class, maxlength = 50, on = "save"),
        @Validate(field = "phone", maxlength = 15, on = "save")
    })
    private Gebruiker user;
    @Validate(on = "save")
    private String password;
    @Validate(on = "save")
    private String password2;

    @DefaultHandler
    public Resolution view() {

        return new ForwardResolution(OVERVIEW);
    }

    public Resolution newUser() {

        return new ForwardResolution(EDIT);
    }

    public Resolution save() throws Exception {
        if (password.equals(password2)) {
            try {
                EntityManager em = Stripersist.getEntityManager();
                String salt = SecurityRealm.generateHexSalt(context.getRequest());
                String phrase = SecurityRealm.getHexSha1(salt, password);
                user.setPasswordhash(phrase);
                user.setPasswordsalt(salt);     
                
                
                Role role =(Role) em.createQuery("from Role where role = :r").setParameter("r", Role.USER).getSingleResult();
       
                Set<Role> roles = new HashSet<Role>();
                roles.add(role);
                user.setRoles(roles);
                em.persist(user);
                em.getTransaction().commit();
                context.getMessages().add(new SimpleMessage("validation.user.succeeded"));
            } catch (NoSuchAlgorithmException ex) {
                log.error("Cannot create password", ex);
                context.getValidationErrors().add("Cannot create password", new SimpleError("validation.user.passwordsNotEqual"));
            } catch (UnsupportedEncodingException ex) {
                log.error("Cannot create password", ex);
                context.getValidationErrors().add("password2", new SimpleError("validation.user.passwordsNotEqual"));
            }

        } else {
            context.getValidationErrors().add("password2", new SimpleError("validation.user.passwordsNotEqual"));
        }
        return new ForwardResolution(EDIT);
    }

    // <editor-fold desc="Getters and Setters">
    public Gebruiker getUser() {
        return user;
    }

    public void setUser(Gebruiker user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }
    // </editor-fold>
}
