package com.lfn;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.lfn.Web.Dto.UsuarioCreateDto;
import com.lfn.Web.Dto.UsuarioResponseDto;
import com.lfn.Web.Dto.UsuarioSenhaDto;
import com.lfn.Web.Exception.ErrorMesage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/resources/Sql/Usuario/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/resources/Sql/Usuario/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {
	
	@Autowired
	WebTestClient testClient;

	public void createUser_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201() {
		
		UsuarioResponseDto  responseBody = testClient
				.post()
				.uri("api/v1/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioCreateDto("tody@email.com", "123456"))
				.exchange().expectStatus().isCreated()
				.expectBody(UsuarioResponseDto.class)
				.returnResult().getResponseBody();
				
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("tody@email.com");
		org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");
	}	
	
	public void createUser_ComUsernameInvalido_RetornarErrorMessageStatus422() {
		
		ErrorMesage  responseBody = testClient
				.post()
				.uri("api/v1/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioCreateDto("", "123456"))
				.exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				.post()
				.uri("api/v1/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioCreateDto("tody@", "123456"))
				.exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				.post()
				.uri("api/v1/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioCreateDto("tody@email", "123456"))
				.exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	}
	
	public void createUser_ComPasswordInvalido_RetornarErrorMessageStatus422() {
		
		ErrorMesage  responseBody = testClient
				.post()
				.uri("api/v1/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioCreateDto("tody@email.com", ""))
				.exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				.post()
				.uri("api/v1/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioCreateDto("tody@", "12345"))
				.exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				.post()
				.uri("api/v1/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioCreateDto("tody@email", "1234562"))
				.exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	}	
	
	public void createUser_ComUsernameRepetido_RetornarErrorMessageStatus409() {
		
		ErrorMesage  responseBody = testClient
				.post()
				.uri("api/v1/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioCreateDto("ana@email.com", "123456"))
				.exchange().expectStatus().isEqualTo(409)
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
				
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isNotNull();
		
	}	
	
	@Test
	public void buscarUsuario_comIdExistente_RetornarUsuarioComStatus200() {
		
		UsuarioResponseDto  responseBody = testClient
				.get()
				.uri("api/v1/usuarios/100")
				.exchange().expectStatus().isOk()
				.expectBody(UsuarioResponseDto.class)
				.returnResult().getResponseBody();
				
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
		org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@email.com");
		org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");
	}	
	
	@Test
	public void buscarUsuario_comIdInexistente_RetornarErrorMessageComStatus404() {

		
		ErrorMesage  responseBody = testClient
				.get()
				.uri("api/v1/usuarios/0")
				.exchange().expectStatus().isNotFound()
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
	}	
	
	@Test
	public void editarSenha_ComDadosInvalidos_RetornarStatus204() {
		
		testClient
				.patch()
				.uri("api/v1/usuarios/100")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
				.exchange().expectStatus().isNoContent();
	}	
	
	@Test
	public void editarSenha_comIdInexistente_RetornarErrorMessageComStatus404() {

		
		ErrorMesage  responseBody = testClient
				.patch()
				.uri("api/v1/usuarios/0")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
				.exchange().expectStatus().isNotFound()
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
	}	
	
	public void editarSenha_comCamposInvalidos_RetornarErrorMessageComStatus422() {
		
		
		ErrorMesage  responseBody = testClient
				.patch()
				.uri("api/v1/usuarios/100")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioSenhaDto("", "", ""))
				.exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				.patch()
				.uri("api/v1/usuarios/100")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioSenhaDto("12345", "12345", "12345"))
				.exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				.patch()
				.uri("api/v1/usuarios/100")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioSenhaDto("1234567", "1234567", "1234567"))
				.exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	}	
	
	public void editarSenha_ComSenhaInvalida_RetornarErrorMessageComStatus400() {
		
		
		ErrorMesage  responseBody = testClient
				.patch()
				.uri("api/v1/usuarios/100")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioSenhaDto("123456", "123455", "000000"))
				.exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
		
		responseBody = testClient
				.patch()
				.uri("api/v1/usuarios/100")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioSenhaDto("000000", "123456", "123456"))
				.exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMesage.class)
				.returnResult().getResponseBody();
		
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(4);
		
	}	
	
	 @Test
	    public void listarUsuarios_SemQualquerParametro_RetornarListaDeUsuariosComStatus200() {
	        List<UsuarioResponseDto> responseBody = testClient
	                .get()
	                .uri("/api/v1/usuarios")
	                .exchange()
	                .expectStatus().isOk()
	                .expectBodyList(UsuarioResponseDto.class)
	                .returnResult().getResponseBody();

	        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(3);
	    }
}
