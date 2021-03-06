package com.rxl.socialbooks.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rxl.socialbooks.domain.Comentario;
import com.rxl.socialbooks.service.ComentarioService;

@RestController
@RequestMapping( value ="/livros/{id}/comentarios")
public class ComentariosResources {
	
	@Autowired
	private ComentarioService comentarioService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> adicionarComentario(@PathVariable("id") Long livroId, @RequestBody Comentario comentario) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		comentario.setUsuario(auth.getName());
		comentario = comentarioService.salvarComentario(livroId, comentario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable("id") Long livroId){
		List<Comentario> comentarios = comentarioService.listarComentarios(livroId);
		return ResponseEntity.status(HttpStatus.OK).body(comentarios);
	}
}
