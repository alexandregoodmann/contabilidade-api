package br.com.goodmann.contabilidadeapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
public abstract class BaseController<T, ID> {

	@Autowired
	private JpaRepository<T, ID> repo;

	@PostMapping
	public ResponseEntity<T> create(@RequestBody T model) {
		return new ResponseEntity<T>(repo.save(model), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<T> update(@RequestBody T model) {
		return new ResponseEntity<T>(repo.save(model), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable(required = true, name = "id") ID id) {
		this.repo.deleteById(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<T> findById(@PathVariable(required = true, name = "id") ID id) {
		try {
			Optional<T> model = this.repo.findById(id);
			if (model.isPresent()) {
				return new ResponseEntity<T>(model.get(), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@GetMapping
	public ResponseEntity<List<T>> findAll() {
		return new ResponseEntity<List<T>>(this.repo.findAll(), HttpStatus.OK);
	}

}
