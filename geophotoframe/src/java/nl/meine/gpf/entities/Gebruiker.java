package nl.meine.gpf.entities;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import nl.meine.gpf.SecurityRealm;

@Entity
public class Gebruiker implements Principal {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique=true, nullable=false)
    private String username;
    private String passwordsalt;
    private String passwordhash;
    private String fullname;
    private String email;
    @ManyToMany
    @JoinTable(joinColumns=@JoinColumn(name="gebruiker"), inverseJoinColumns=@JoinColumn(name="role"))
    private Set<Role> roles = new HashSet();
    

    public void changePassword(HttpServletRequest request, String pw) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String salt = SecurityRealm.generateHexSalt(request);
        String hash = SecurityRealm.getHexSha1(salt, pw);
        setPasswordsalt(salt);
        setPasswordhash(hash);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordsalt() {
        return passwordsalt;
    }

    public void setPasswordsalt(String passwordSalt) {
        this.passwordsalt = passwordSalt;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    /* Principal implementatie */
    public String getName() {
        return getUsername();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isBeheerder() {
        return isInRole(Role.ADMIN);
    }
    
    public boolean isInRole(String roleName) {
        for(Iterator it = getRoles().iterator(); it.hasNext();) {
            Role r = (Role)it.next();
            if(r.getRole().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
