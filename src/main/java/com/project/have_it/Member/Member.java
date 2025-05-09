package com.project.have_it.Member;

import com.project.have_it.comment.Comment;
import com.project.have_it.good.Good;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    public String username;

    @Column(length = 200)
    public String password;

    @Column(length = 200, name="display_name")
    public String displayName;

//    @OneToMany(mappedBy = "member")
//    List<Comment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member")
//    List<Good> goods = new ArrayList<>();
}
