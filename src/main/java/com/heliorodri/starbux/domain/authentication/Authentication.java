package com.heliorodri.starbux.domain.authentication;

import com.heliorodri.starbux.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "auth_user_cart")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private User user;

    private String token;

    public Authentication(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
    }
}
