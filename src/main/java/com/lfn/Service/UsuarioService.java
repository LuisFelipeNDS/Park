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
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
		
		if(!novaSenha.equals(confirmaSenha)) {
			throw new RuntimeException("Sua senha não confere com a confirmacao de senha");
		}
		
		Usuario user = buscarPorId(id);
		
		if(!user.getPassword().equals(senhaAtual)) {
			throw new RuntimeException("Sua senha não confere");
		}
		
		user.setPassword(novaSenha);
		
		return user;
	}

	@Transactional
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}

	
}
