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
import com.lfn.Web.Exception.ErrorMesage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Usuarios", description = "Contem todas as operacoes relativas aos recursos para cadastro, edicao e leitura de um usuario.")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;
	
	//public ModelMapper modelUsuarioDto = new ModelMapper();
	

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @Operation(summary = "Criar um novo usuario", description = "Recurso para criar um novo usuario"
    		, responses = {@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso"
    		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class)))
    		,@ApiResponse(responseCode = "409", description = "Usuario ja cadastrado no sistema", content = @Content(mediaType = "application/json"
    		, schema = @Schema(implementation = ErrorMesage.class)))
    		,@ApiResponse(responseCode = "422", description = "Recurso nao processado por dados invalidos", content = @Content(mediaType = "application/json"
    	    , schema = @Schema(implementation = ErrorMesage.class)))})
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto){
    	
    	//Tranforma um UsuarioCreateDto em Usuario
    	Usuario userConvertido = UsuarioMapper.toUsuario(createDto);
    	Usuario user = usuarioService.salvar(userConvertido);
    	
    	return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }
    
    @Operation(summary = "Recuperar um usuario pelo ID", description = "Recuperar um usuario pelo ID"
    		, responses = {@ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso"
    		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class)))
    		,@ApiResponse(responseCode = "404", description = "Recurso nao encontrado", content = @Content(mediaType = "application/json"
    	    , schema = @Schema(implementation = ErrorMesage.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
    	
    	Usuario user = usuarioService.buscarPorId(id);
    	
    	return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }
    

    @Operation(summary = "Atualizar senha", description = "Atualizar senha"
    		, responses = {@ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"
    		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)))
    		,@ApiResponse(responseCode = "404", description = "Recurso nao encontrado", content = @Content(mediaType = "application/json"
    	    , schema = @Schema(implementation = ErrorMesage.class)))
    		,@ApiResponse(responseCode = "400", description = "Senha nao confere", content = @Content(mediaType = "application/json"
    		, schema = @Schema(implementation = ErrorMesage.class)))
    		,@ApiResponse(responseCode = "422", description = "Campos invalidos ou mal formatado", content = @Content(mediaType = "application/json"
    		, schema = @Schema(implementation = ErrorMesage.class)))
    		})
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@Valid @PathVariable Long id,@RequestBody UsuarioSenhaDto dto){
    	
    	 Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
         return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Recuperar todos os usuarios", description = "Recuperar todos os usuarios cadastrados"
    		, responses = {@ApiResponse(responseCode = "200", description = "Usuarios recuperados com sucesso"
    		, content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class))))})
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
    	
    	List<Usuario> users = usuarioService.buscarTodos();
    	
    	return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
