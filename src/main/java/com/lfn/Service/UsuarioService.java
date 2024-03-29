package com.lfn.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lfn.Entity.Usuario;
import com.lfn.Exception.EntityNotFoundException;
import com.lfn.Exception.PasswordInvalidException;
import com.lfn.Exception.UsernameUniqueViolationException;
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
		try {
		return usuarioRepository.save(usuario);
		}catch(org.springframework.dao.DataIntegrityViolationException ex) {
			throw new UsernameUniqueViolationException(
					String.format("Usuario {%s}ja cadastrado", usuario.getUsername()));
		}
	}

	@Transactional
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException
						(String.format("Usuario de id {%s} nao foi encontrado", id)));
	}

	@Transactional
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
		 if (!novaSenha.equals(confirmaSenha)) {
	            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
	        }

	        Usuario user = buscarPorId(id);
	        if (!user.getPassword().equals(senhaAtual)) {
	            throw new PasswordInvalidException("Sua senha não confere.");
	        }

	        user.setPassword(novaSenha);
	        return user;
	}

	@Transactional
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}

	
}
