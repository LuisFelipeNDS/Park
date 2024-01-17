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
import com.lfn.Web.Dto.UsuarioResponseDto;
import com.lfn.Web.Dto.UsuarioSenhaDto;
import com.lfn.Web.Dto.Mapper.UsuarioMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;
	
	//public ModelMapper modelUsuarioDto = new ModelMapper();
	

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto){
    	
    	//Tranforma um UsuarioCreateDto em Usuario
    	Usuario userConvertido = UsuarioMapper.toUsuario(createDto);
    	Usuario user = usuarioService.salvar(userConvertido);
    	
    	return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
    	
    	Usuario user = usuarioService.buscarPorId(id);
    	
    	return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@Valid @PathVariable Long id,@RequestBody UsuarioSenhaDto dto){
    	
    	Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
    	
    	return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
    	
    	List<Usuario> users = usuarioService.buscarTodos();
    	
    	return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
