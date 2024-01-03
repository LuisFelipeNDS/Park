package com.lfn.Web.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lfn.Entity.Usuario;
import com.lfn.Service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
    	
    	Usuario user = usuarioService.salvar(usuario);
    	
    	return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
    	
    	Usuario user = usuarioService.buscarPorId(id);
    	
    	return ResponseEntity.ok(user);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> updatePassword(@PathVariable Long id,@RequestBody Usuario usuario){
    	
    	Usuario user = usuarioService.editarSenha(id, usuario.getPassword());
    	
    	return ResponseEntity.ok(user);
    }
}
