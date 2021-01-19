package br.com.goodmann.contabilidadeapi.core;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;

public class BaseService<T, ID> {

	@Autowired
	private MongoRepository<T, ID> repo;

	public T save(T model) {
		return repo.save(model);
	}

	public void delete(@PathVariable(required = true, name = "id") ID id) {
		this.repo.deleteById(id);
	}

	public T findById(@PathVariable(required = true, name = "id") ID id) {
		Optional<T> model = this.repo.findById(id);
		if (!model.isPresent()) {
			throw new RuntimeException("Object not found ID: " + id);
		}
		return model.get();
	}

	public List<T> findAll() {
		return this.repo.findAll();
	}

}
