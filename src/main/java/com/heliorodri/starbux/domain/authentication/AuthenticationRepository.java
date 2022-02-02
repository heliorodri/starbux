package com.heliorodri.starbux.domain.authentication;

import com.heliorodri.starbux.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

    Authentication findByUser(User user);
    Authentication findByToken(String token);

}
