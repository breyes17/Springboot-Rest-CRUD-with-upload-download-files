package com.springrest.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springrest.demo.entity.BasicInfo;
import com.springrest.demo.service.BasicInfoService;

@RestController
@RequestMapping("/api")
public class BasicInfoController {

	@Autowired
	private BasicInfoService basicInfoService;
	
	@PostMapping("/info")
	public ResponseEntity<?> saveInfo(@RequestParam("formData") String data,
									@RequestParam("attachedFile") MultipartFile file) throws IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		BasicInfo bi = new BasicInfo();
		
		bi = mapper.readValue(data, BasicInfo.class);
		bi.setFileName(file.getOriginalFilename());
		bi.setFileNameByte(file.getBytes());
		bi.setFileNameType(file.getContentType());
		
		basicInfoService.saveBasicInfo(bi);
		
		return new ResponseEntity<>(bi, HttpStatus.OK);
	}
	
	@GetMapping("/info")
	public ResponseEntity<?> getAllBasicInfo(){
		return new ResponseEntity<>(basicInfoService.getAllBasicInfo(), HttpStatus.OK);
	}
	
	@GetMapping("/info/download/{id}/{filename:.+}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("id") int id, @PathVariable("filename") String fileName){
		
		BasicInfo bi = new BasicInfo();
		bi = basicInfoService.getBasicInfoById(id);

		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(bi.getFileNameType()))
								.header(HttpHeaders.CONTENT_DISPOSITION, "attachement:filename=\""+fileName+"\"")
								.body(new ByteArrayResource(bi.getFileNameByte()));
	}
	
	@DeleteMapping("/info/delete/{id}")
	public ResponseEntity<?> deleteBasicInfoById(@PathVariable int id){
		basicInfoService.deleteBasicInfo(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/info")
	public ResponseEntity<?> updateBasicInfo(@RequestParam("formData") String data, 
												@RequestParam(value="attachedFile" , required=false) MultipartFile file) throws IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		BasicInfo bi = new BasicInfo();
		
		bi = mapper.readValue(data, BasicInfo.class);
		
		if(file != null) {
			bi.setFileName(file.getOriginalFilename());
			bi.setFileNameByte(file.getBytes());
			bi.setFileNameType(file.getContentType());
		}
		
		basicInfoService.saveBasicInfo(bi);
		
		return new ResponseEntity<>(bi, HttpStatus.OK);
	}
}
