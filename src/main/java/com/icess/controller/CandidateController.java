package com.icess.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.activation.FileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.expression.EvaluationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.icess.entity.Candidate;
import com.icess.model.CustomCandidateResponse;
import com.icess.model.CustomCandidateSkill;
import com.icess.model.CustomTest;
import com.icess.service.CandidateService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/icess")
public class CandidateController {

	@Autowired
	private CandidateService candidateService;
	
	@Resource
	private Environment environment;
	private static String UPLOADED_FOLDER;
	
//	@RequestMapping(value = "/addEditCandidate", method = RequestMethod.POST,headers = "Accept=application/json;charset=UTF-8")
//	public ResponseEntity<Object> addEditCandidate(@RequestBody Candidate candidate) 
//			throws InterruptedException, EvaluationException, ExecutionException{
//		
//		Future<Boolean> returnValue = candidateService.addEditCandidate(candidate);
//		
//		if(returnValue.get()==null){
//            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<Object>(returnValue.get() , HttpStatus.OK);
//	}
	
	@RequestMapping(value = "/addEditCandidate", method = RequestMethod.POST,headers="Content-Type=multipart/form-data")
	public ResponseEntity<Object> addEditCandidate(@RequestParam("candidate") String candidate,@RequestPart("file") MultipartFile file,
			@RequestPart("userimg") MultipartFile userimg) 
			throws InterruptedException, EvaluationException, ExecutionException{
		Gson gson = new Gson();
		Candidate result = gson.fromJson(candidate, Candidate.class);
		Future<Candidate> returnValue = candidateService.addEditCandidate(result);
		 CustomCandidateResponse ccr= new CustomCandidateResponse();
		 if (file.isEmpty()) {
	            return new ResponseEntity("please select a file!", HttpStatus.OK);
	        }

	        try {

	           Path path= saveUploadedFiles(Arrays.asList(file),String.valueOf(result.getCandidateId()));
	           Path path1= saveUploadedFiles(Arrays.asList(userimg),String.valueOf(result.getCandidateId()));
	          /* return new ResponseEntity("Successfully uploaded - " +
	        		   path.getFileName()+" "+UPLOADED_FOLDER, new HttpHeaders(), HttpStatus.OK);*/
	           result.setUser(returnValue.get().getUser());
	           result.setCandidateId(returnValue.get().getCandidateId());
	           result.setUploadFile(""+path.getFileName());
	           result.setUploadPath(UPLOADED_FOLDER);
	           result.setUpload_img_file(""+path1.getFileName());
	           returnValue=  candidateService.addEditCandidate(result);
	           
	            ccr.setCandidateId(String.valueOf(returnValue.get().getCandidateId()));
	            ccr.setCandidateName(returnValue.get().getCandidateName());
	            ccr.setCandidatestatusFlag(String.valueOf(returnValue.get().getCandidatestatusFlag()));
	            ccr.setContact_No1(returnValue.get().getContact_No1());
	            ccr.setContact_No2(returnValue.get().getContact_No2());
	            ccr.setEmail(returnValue.get().getEmail());
	            ccr.setReg_date(String.valueOf(returnValue.get().getReg_date()));
	            ccr.setAddress(returnValue.get().getAddress());
	            ccr.setRoleId(String.valueOf(returnValue.get().getUser().getRole().getRoleId()));
	            ccr.setRoleName(returnValue.get().getUser().getRole().getRoleName());
	            List<CustomCandidateSkill> listccs= new ArrayList<CustomCandidateSkill>();
	            for(int i=0;i<returnValue.get().getCandidateskills().size();i++) {
	            	CustomCandidateSkill ccs= new CustomCandidateSkill();
	            	ccs.setIsDeleted(String.valueOf(returnValue.get().getCandidateskills().get(i).getIsDeleted()));
	            	ccs.setCandidateskillId(String.valueOf(returnValue.get().getCandidateskills().get(i).getCandidateskillId()));
	            	ccs.setSkillId(String.valueOf(returnValue.get().getCandidateskills().get(i).getSkillId()));
	            	ccs.setSubskillId(String.valueOf(returnValue.get().getCandidateskills().get(i).getSubskillId()));
	            	ccs.setSkillName(returnValue.get().getCandidateskills().get(i).getSkillName());
	            	ccs.setSubSkillName(returnValue.get().getCandidateskills().get(i).getSubSkillName());
	            	listccs.add(ccs);
	            }
	            ccr.setCandidateskills(listccs);
	         /*   ccr.setIsDeleted(String.valueOf(returnValue.get().getCandidateskills().get(0).getIsDeleted()));
	            ccr.setCandidateskillId(String.valueOf(returnValue.get().getCandidateskills().get(0).getCandidateskillId()));
	            ccr.setSkillId(String.valueOf(returnValue.get().getCandidateskills().get(0).getSkillId()));
	            ccr.setSubskillId(String.valueOf(returnValue.get().getCandidateskills().get(0).getSubskillId()));
	            ccr.setSkillName(returnValue.get().getCandidateskills().get(0).getSkillName());
	            ccr.setSubSkillName(returnValue.get().getCandidateskills().get(0).getSubSkillName());*/
	            
	            
	            ccr.setUserId(String.valueOf(returnValue.get().getUser().getUserId()));
	            ccr.setUserName(returnValue.get().getUser().getUserName());
	            ccr.setUploadFile(returnValue.get().getUploadFile());
	            ccr.setUploadPath(returnValue.get().getUploadPath());
	            ccr.setUpload_img_file(returnValue.get().getUpload_img_file());
	           
	            
	        } catch (IOException e) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
		
		if(returnValue.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(ccr , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getCandidateById/{candidateId}", method = RequestMethod.GET,headers="Accept=application/json")
	public ResponseEntity<Object> getUserById(@PathVariable int candidateId)
			throws InterruptedException, EvaluationException, ExecutionException{
		
		Future<Candidate> returnValue = candidateService.getCandidateById(candidateId);
		
		if(returnValue.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(returnValue.get() , HttpStatus.OK);
	}
	
	/*@RequestMapping(value = "/getAllCandidate", method = RequestMethod.GET,headers="Accept=application/json")
	public ResponseEntity<Object> getAllUser() 
    		throws InterruptedException, EvaluationException, ExecutionException {
		
		Future<List<Candidate>> returnValue = candidateService.getAllCandidate();
		
		if(returnValue.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(returnValue.get() , HttpStatus.OK);
	}*/
	
	
	@RequestMapping(value = "/getAllPendingtestCandidate", method = RequestMethod.GET,headers="Accept=application/json")
	public ResponseEntity<Object> getAllPendingtestCandidate() 
    		throws InterruptedException, EvaluationException, ExecutionException {
		
		Future<List<Candidate>> returnValue = candidateService.getAllPendingTestCandidate();
		
		if(returnValue.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(returnValue.get() , HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getAllPendingEvaluationTestCandidate", method = RequestMethod.GET,headers="Accept=application/json")
	public ResponseEntity<Object> getAllPendingEvaluationTestCandidate() 
    		throws InterruptedException, EvaluationException, ExecutionException {
		
		Future<List<CustomTest>> returnValue = candidateService.getAllPendingEvaluationTestCandidate();
		
		if(returnValue.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(returnValue.get() , HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getCandidate", method = RequestMethod.GET,headers="Accept=application/json")
	public ResponseEntity<Object> getCandidate(@RequestParam(required = false) int skillId , 
											   @RequestParam(required = false) int subSkillId , 
											   @RequestParam(required = false) String name , 
											   @RequestParam(required = false) String contactNo) 
    		throws InterruptedException, EvaluationException, ExecutionException {
		
		Future<List<Candidate>> returnValue = candidateService.getCandidateSearch(skillId, subSkillId, name, contactNo);
		
		if(returnValue.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(returnValue.get() , HttpStatus.OK);
	}
	
	/*suvayan18
	suvayan1*/
	
	   private Path saveUploadedFiles(List<MultipartFile> files,String userid) throws IOException {
	    	UPLOADED_FOLDER =environment.getRequiredProperty("upload.dir");
	    	Path path = null;
	    	for (MultipartFile file : files) {

	            if (file.isEmpty()) {
	                continue; //next pls
	            }

	            byte[] bytes = file.getBytes();
	            
	            path = Paths.get(UPLOADED_FOLDER+userid+file.getOriginalFilename());
	            Files.write(path, bytes);
	            
	        }
			return path;

	    }
	   
	   
	   @RequestMapping(value = "/getAllEvaluatedCandidate", method = RequestMethod.GET,headers="Accept=application/json")
		public ResponseEntity<Object> getAllEvaluatedCandidate() 
	    		throws InterruptedException, EvaluationException, ExecutionException {
			
			Future<List<CustomTest>> returnValue = candidateService.getAllEvaluatedCandidate();
			
			if(returnValue.get()==null){
	            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<Object>(returnValue.get() , HttpStatus.OK);
		}
	   
	   @RequestMapping(value = "/downloadFile/{fileName:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	   public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable String fileName, @RequestParam String path_url,HttpServletRequest request) throws IOException {
		   UPLOADED_FOLDER =environment.getRequiredProperty("upload.dir");
	       // ...
		   //File file = new File(UPLOADED_FOLDER + fileName);
		    String filePath = path_url + fileName;
	        Path path = Paths.get(filePath);
		    org.springframework.core.io.Resource resource = new UrlResource(path.toUri());

	       return ResponseEntity.ok()
	    		   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	               .contentType(MediaType.parseMediaType("application/octet-stream"))
	               .body(resource);
	   }
	   
	   @RequestMapping(value = "/Image/{fileName:.+}", method = RequestMethod.GET)
	   public ResponseEntity<byte[]> getImage(@PathVariable String fileName, @RequestParam String path_url) throws IOException {
		 
	        File file = new File(path_url + fileName);
	        byte[] image =new byte[(int) file.length()]; 
	        FileInputStream fis = new FileInputStream(file);
	        fis.read(image); //read file into bytes[]
	        fis.close();
	      /* return ResponseEntity.ok()
	               .header("Content-Disposition", "attachment; filename=" +file.getName())
	               .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
	               .body(image);*/
	       return ResponseEntity.ok()
	    		   //.contentType(MediaType.IMAGE_JPEG)
	    		   .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
	    		   .body(image);
	   }
	
}
