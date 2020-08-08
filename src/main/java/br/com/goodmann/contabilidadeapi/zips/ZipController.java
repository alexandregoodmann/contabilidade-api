package br.com.goodmann.contabilidadeapi.zips;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZipController {

	@Autowired
	private ZipRepository zip;

	@GetMapping("/findAll")
	public List<Zip> findAll() {
		return this.zip.findAll();
	}
}
