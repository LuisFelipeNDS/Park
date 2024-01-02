package com.lfn.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lfn.Entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
