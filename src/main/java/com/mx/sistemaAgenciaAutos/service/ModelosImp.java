package com.mx.sistemaAgenciaAutos.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.sistemaAgenciaAutos.dao.MarcasDao;
import com.mx.sistemaAgenciaAutos.dao.ModelosDao;
import com.mx.sistemaAgenciaAutos.model.Marcas;
import com.mx.sistemaAgenciaAutos.model.Modelos;

@Service
public class ModelosImp {

	@Autowired
	ModelosDao modeloDao;

	@Autowired
	MarcasDao marcaDao;

	@Transactional(readOnly = true)
	public List<Modelos> listar() {
		return modeloDao.findAll();
	}

	// Validar: idModelo, nombre no exista, idMarca exista, guardar
	@Transactional
	public String guardar(Modelos modelo) {

		// Ciclo...Marcas ---- Empezar a validar esto que idMarca exista
		// Condicon
		// bandera

		// Ciclo....Modelos
		// Condicion
		// Bandera

		// Ciclos anidados 2

		String respuesta = "";
		boolean banderaMar = false;
		boolean banderaMod = false;

		for (Marcas m : marcaDao.findAll()) {

			if (m.getId().equals(modelo.getMarca().getId())) {
				// Que existe el idMarca
				banderaMar = true;

				for (Modelos mod : modeloDao.findAll()) {

					if (mod.getId().equals(modelo.getId())) {
						// Que idModelo ya existe
						respuesta = "idModeloExiste";
						banderaMod = true;
						break;
					} else if (mod.getNombre().equals(modelo.getNombre())) {
						// Que nombreModelo ya existe
						respuesta = "nombreModExiste";
						banderaMod = true;
						break;
					}
				}
				break;
			}
		}

		// idMarcaNoexiste
		if (banderaMar == false) {
			respuesta = "idMarNoExiste";
			banderaMod = true;
		} else if (banderaMod == false) {
			modeloDao.save(modelo);
			respuesta = "guardado";
		}

		return respuesta;
	}

	@Transactional(readOnly = true)
	public Modelos buscar(Long id) {
		return modeloDao.findById(id).orElse(null);
	}

	// Que el idModelo exista y el idMarcaExista
	@Transactional
	public String editar(Modelos modelo) {
		// Verificar si la marca existe
		boolean marcaExiste = marcaDao.findById(modelo.getMarca().getId()).isPresent();

		if (!marcaExiste) {
			return "idMarNoExiste";
		}

		boolean modeloExiste = modeloDao.findById(modelo.getId()).isPresent();

		if (!modeloExiste) {
			return "idModeloNoExiste";
		}

		modeloDao.save(modelo);
		return "guardado";
	}
	public boolean eliminar(Long id) {
		boolean bandera = false;
		for (Modelos m : modeloDao.findAll()) {
			if (m.getId().equals(id)) {
				modeloDao.deleteById(id);
				//modeloDao.delete(m);
				bandera = true;
				break;
			}
		}
		return bandera;
	}
}