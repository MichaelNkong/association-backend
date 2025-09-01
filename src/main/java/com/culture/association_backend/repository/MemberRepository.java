package com.culture.association_backend.repository;

import com.culture.association_backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET is_active =:userActive WHERE id = :userId", nativeQuery = true)
    void activateMember(@Param("userId") Long userId, @Param("userActive") String userActive);
}