/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.meine.gpf.entities;

import javax.persistence.*;

@Entity
public class Role {
    public static final String ADMIN = "admin";
    public static final String USER = "user";
    
    @Id
    private Integer id;
    
    private String role;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    public static Role findByName(String role) throws Exception {
        return null;
/*        
        EntityManager em = MyEMFDatabase.getEntityManager(MyEMFDatabase.MAIN_EM);
        Role r = null;
        try {
            r = (Role)em.createQuery("from Role where role = :role")
                    .setParameter("role", role)
                    .getSingleResult();

        } catch(NoResultException nre) {
            // ...
        }
        return r;
        */
    }
}
