package br.com.goodmann.contabilidadeapi.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:4200")
public abstract class BaseController<T, ID> {

	@Autowired
	private BaseService<T, ID> service;

	@PostMapping
	public ResponseEntity<T> create(@RequestBody T model) {
		return new ResponseEntity<T>(service.save(model), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<T> update(@PathVariable(required = true, name = "id") ID id, @RequestBody T model) {
		return new ResponseEntity<T>(service.save(model), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(required = true, name = "id") ID id) {
		this.service.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}")
	public ResponseEntity<T> findById(@PathVariable(required = true, name = "id") ID id) {
		return new ResponseEntity<T>(this.service.findById(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<T>> findAll() {
		return new ResponseEntity<List<T>>(this.service.findAll(), HttpStatus.OK);
	}

	public void setService(BaseService<T, ID> service) {
		this.service = service;
	}

}
