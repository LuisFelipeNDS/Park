package com.lfn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.lfn.Web.Dto.UsuarioCreateDto;
import com.lfn.Web.Dto.UsuarioResponseDto;
import com.lfn.Web.Exception.ErrorMesage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/resources/Sql/Usuario/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/resources/Sql/Usuario/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {
	
	@Autowired
	WebTestClient testClient;

	@Test
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
	
	@Test
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
	
	@Test
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
}
