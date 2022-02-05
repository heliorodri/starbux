package com.heliorodri.starbux.domain.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heliorodri.starbux.domain.user_drink.UserDrink;
import com.heliorodri.starbux.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(targetEntity = User.class)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_drink_id", referencedColumnName = "id")
    private UserDrink userDrink;

    private Integer quantity;

}
