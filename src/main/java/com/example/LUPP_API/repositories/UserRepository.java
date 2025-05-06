package com.example.LUPP_API.repositories;

import com.example.LUPP_API.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {  // Alterando de String para UUID
    UserDetails findByLogin(String login);
}