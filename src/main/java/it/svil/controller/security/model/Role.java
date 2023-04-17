package it.svil.controller.security.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Role {

    @Id
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<MyUser> users = new ArrayList<>();

    public Role(String id){
        this.name = id;
    }

    public Role() {

    }
}
