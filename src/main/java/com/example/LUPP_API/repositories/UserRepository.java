package com.example.LUPP_API.repositories;

import com.example.LUPP_API.domain.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {


}
