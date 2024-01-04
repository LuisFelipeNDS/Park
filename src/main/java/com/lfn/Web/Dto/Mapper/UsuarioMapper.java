package com.lfn.Web.Dto.Mapper;

import org.modelmapper.ModelMapper;

import com.lfn.Entity.Usuario;
import com.lfn.Web.Dto.UsuarioCreateDto;

public class UsuarioMapper {
	
	public static Usuario toUsuario(UsuarioCreateDto createDto) {
		ModelMapper model = new ModelMapper();
		return model.map(createDto, Usuario.class);
	}

}
