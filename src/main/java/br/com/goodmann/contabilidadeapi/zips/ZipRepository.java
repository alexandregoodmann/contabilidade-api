package br.com.goodmann.contabilidadeapi.zips;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ZipRepository extends MongoRepository<Zip, String> {

}
