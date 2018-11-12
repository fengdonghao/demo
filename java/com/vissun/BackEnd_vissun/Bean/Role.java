package com.vissun.BackEnd_vissun.Bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Role  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;
    private String roleName;
   /* @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "UserRole", joinColumns = { @JoinColumn(name = "RoleId") }, inverseJoinColumns ={@JoinColumn(name = "userId") })
    private List<User> userList;*/

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String rolenNme) {
        this.roleName = roleName;
    }


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
