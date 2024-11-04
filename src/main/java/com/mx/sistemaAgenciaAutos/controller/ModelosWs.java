package com.mx.sistemaAgenciaAutos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.sistemaAgenciaAutos.model.Marcas;
import com.mx.sistemaAgenciaAutos.model.Modelos;
import com.mx.sistemaAgenciaAutos.service.ModelosImp;

@RestController
@RequestMapping(path = "ModelosWs")
@CrossOrigin
public class ModelosWs {
	@Autowired
	ModelosImp modeloImp;

	// http://localhost:9000/ModelosWs/listar
	@GetMapping(path = "listar")
	public List<Modelos> listar() {
		return modeloImp.listar();
	}

	// http://localhost:9000/ModelosWs/guardar
	@PostMapping(path = "guardar")
	public ResponseEntity<?> guardar(@RequestBody Modelos modelo) {
		String response = modeloImp.guardar(modelo);

		if (response.equals("idModeloExiste"))
			return new ResponseEntity<>("No se guardo, ese idModelo ya existe", HttpStatus.OK);
		else if (response.equals("nombreModExiste"))
			return new ResponseEntity<>("No se guardo, ese nombreModelo ya existe", HttpStatus.OK);
		else if (response.equals("idMarNoExiste"))
			return new ResponseEntity<>("No se guardo, ese idMarca no existe", HttpStatus.OK);
		else
			return new ResponseEntity<>(modelo, HttpStatus.CREATED);
	}
	
	
	// http://localhost:9000/ModelosWs/buscar
		@PostMapping(path = "buscar")
		public Modelos buscar(@RequestBody Modelos modelo) {
			return modeloImp.buscar(modelo.getId());
		}

	// http://localhost:9000/ModelosWs/buscar
	@PostMapping("/editar")
	public ResponseEntity<?> editar(@RequestBody Modelos modelo) {
		String respuesta = modeloImp.editar(modelo);

		if (respuesta.equals("idMarNoExiste")) {
			return new ResponseEntity<>("No se editó, el idMarca no existe", HttpStatus.NOT_FOUND);
		} else if (respuesta.equals("idModeloNoExiste")) {
			return new ResponseEntity<>("No se editó, el idModelo no existe", HttpStatus.NOT_FOUND);
		} else if (respuesta.equals("guardado")) {
			return new ResponseEntity<>(modelo, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error desconocido", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// http://localhost:9000/ModelosWs/eliminar
	@PostMapping(path = "eliminar")
	public ResponseEntity<String> eliminar(@RequestBody Modelos modelo) {
		boolean response = modeloImp.eliminar(modelo.getId());

		if (response == false)
			return new ResponseEntity<>("Ese registro no existe", HttpStatus.OK);
		else
			return new ResponseEntity<>("Se elimino con exito", HttpStatus.OK);
	}

}
