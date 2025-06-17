package com.canopy.numbers.served.deprecated;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.canopy.shared.data.User;

public interface UserRepositoryDeprecated extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);
}
