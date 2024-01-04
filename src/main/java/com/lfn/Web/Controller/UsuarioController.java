package com.lfn.Web.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import com.lfn.Web.Dto.UsuarioCreateDto;
import com.lfn.Web.Dto.Mapper.UsuarioMapper;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;
	
	//public ModelMapper modelUsuarioDto = new ModelMapper();
	

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody UsuarioCreateDto createDto){
    	
    	//Tranforma um UsuarioCreateDto em Usuario
    	Usuario userConvertido = UsuarioMapper.toUsuario(createDto);
    	Usuario user = usuarioService.salvar(userConvertido);
    	
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
    
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
    	
    	List<Usuario> users = usuarioService.buscarTodos();
    	
    	return ResponseEntity.ok(users);
    }
}
