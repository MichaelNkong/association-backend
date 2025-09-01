package com.culture.association_backend.repository;
import com.culture.association_backend.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

    @Repository
    public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String username);

        @Modifying
        @Transactional
        @Query(value = "INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
        void assignRoleToUser(@Param("userId") Long userId, @Param("roleId") Long roleId);
    }

