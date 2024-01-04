package com.lfn.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lfn.Entity.Usuario;
import com.lfn.Repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	
	public UsuarioService(UsuarioRepository usuarioRepository) {
		
		this.usuarioRepository = usuarioRepository;
		
	}

	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		return usuarioRepository.save(usuario);
	}

	@Transactional
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
	}

	@Transactional
	public Usuario editarSenha(Long id, String password) {
		
		Usuario user = buscarPorId(id);
		user.setPassword(password);
		
		return user;
	}

	@Transactional
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}

	
}
