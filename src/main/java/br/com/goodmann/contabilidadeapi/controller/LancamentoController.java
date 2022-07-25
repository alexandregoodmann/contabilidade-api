package br.com.goodmann.contabilidadeapi.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Lancamento;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/lancamentos")
public class LancamentoController extends BaseController<Lancamento, Integer> {

	@PostMapping("/uploadFile")
	public void uploadFile(@RequestParam("file") MultipartFile file) throws FileNotFoundException, IOException {
		System.out.println("sincronas: " + file);
		File files = new File("src/main/resources/targetFile.tmp");

		try (OutputStream os = new FileOutputStream(files)) {
			os.write(file.getBytes());
		}
		/*
		 * String fileName = fileStorageService.storeFile(file);
		 * 
		 * String fileDownloadUri =
		 * ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
		 * .path(fileName).toUriString();
		 * 
		 * return new UploadFileResponse(fileName, fileDownloadUri,
		 * file.getContentType(), file.getSize());
		 */
	}
}
