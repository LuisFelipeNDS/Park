package com.lfn.Web.Dto.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.lfn.Entity.Usuario;
import com.lfn.Web.Dto.UsuarioCreateDto;
import com.lfn.Web.Dto.UsuarioResponseDto;

public class UsuarioMapper {
	
	public static Usuario toUsuario(UsuarioCreateDto createDto) {
		return new ModelMapper().map(createDto, Usuario.class);
	}
	
	public static UsuarioResponseDto toDto(Usuario usuario) {
		
		//A variavel role tera como valor oque vier depois de ROLE_ (Ira recortar oque vier dpois de ROLE_)
		String role = usuario.getRole().name().substring("ROLE_".length());
		
		PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>(){
			@Override
			public void configure() {
				map().setRole(role);
			}
		};
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(props);
		
		return mapper.map(usuario, UsuarioResponseDto.class);
	}
	
	public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios){
		return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
	}
}
