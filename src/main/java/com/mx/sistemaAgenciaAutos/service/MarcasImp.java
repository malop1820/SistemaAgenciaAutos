package com.mx.sistemaAgenciaAutos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.sistemaAgenciaAutos.dao.MarcasDao;
import com.mx.sistemaAgenciaAutos.model.Marcas;

@Service
public class MarcasImp {

	@Autowired
	MarcasDao marcasDao;

	@Transactional(readOnly = true)
	public List<Marcas> listar() {
		return marcasDao.findAll();
	}

	// Al guardar validar que el id y nombre no se repita, guardar
	@Transactional
	public String guardar(Marcas marca) {
		// Ciclo
		// condicion
		// bandera
		String respuesta = "";
		boolean bandera = false;
		for (Marcas m : marcasDao.findAll()) {
			if (m.getId().equals(marca.getId())) {
				bandera = true;
				respuesta = "idExiste";
				break;
			} else if (m.getNombre().equals(marca.getNombre())) {
				bandera = true;
				respuesta = "nombrExiste";
				break;
			}
		}

		if (bandera == false) {
			marcasDao.save(marca);
			respuesta = "guardado";
		}

		return respuesta;

	}

	@Transactional(readOnly = true)
	public Marcas buscar(Long id) {
		return marcasDao.findById(id).orElse(null);
	}

	// Validar que el idExista para editar
	@Transactional
	public boolean editar(Marcas marca) {

		boolean bandera = false;

		for (Marcas m : marcasDao.findAll()) {
			if (m.getId().equals(marca.getId())) {
				marcasDao.save(marca);
				bandera = true;
				break;
			}
		}
		return bandera;
	}

	// Validar que el idExista para eliminar
	@Transactional
	public boolean eliminar(Long id) {
		boolean bandera = false;
		for (Marcas m : marcasDao.findAll()) {
			if (m.getId().equals(id)) {
				marcasDao.deleteById(id);
				// marcasDao.delete(m);
				bandera = true;
				break;
			}
		}
		return bandera;
	}

}
