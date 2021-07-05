package com.adamproject.moviedb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import com.adamproject.moviedb.entity.Actor;
import com.adamproject.moviedb.entity.Movie;
import com.adamproject.moviedb.service.ActorService;

@Controller
public class ActorController {
	
	private boolean isBlockLower(int pageNo) {
		return (pageNo <= 1) ? true : false;
	}
	
	private boolean isBlockUpper(int pageNo,int totalPage) {
		return (pageNo >= totalPage) ? true : false;
	}
	
	private String checkWall(int pageNo,int totalPage) {
		String status;
		if(pageNo <= 1) status="DOWN";
		else if(pageNo >= totalPage) status="UP";
		else status="OK";
		
		return status;
	}
	
	
	ActorService actorService;

	@Autowired
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
	}	

	@GetMapping("/newActor")
	public String newActor(Model model) {
		
		Actor newActor = new Actor();
	
		model.addAttribute("newActor",newActor);	

		return "newActor";
	}
	
	@PostMapping("/newActor")
	public String newActorPost(Actor newActor,RedirectAttributes redirAttrs) {

		actorService.addActor(newActor);
		redirAttrs.addFlashAttribute("sikeresFeltoltes", "");
		return "redirect:/newActor";
	}
	
	
	@GetMapping("/actors")
	public String ActorPaginated(
			@RequestParam(value = "pageNo", defaultValue = "1") String pageNo,
	        @RequestParam(value = "sortField", defaultValue =  "id") String sortField,
	        @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
	        Model model) {
		
			int pageSize = 10;

		Page<Actor> page = actorService.findPaginated(Integer.parseInt(pageNo), pageSize, sortField, sortDir);
	
		List<Actor> actorList = page.getContent();
		
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("currentPage",Integer.parseInt(pageNo));
		model.addAttribute("actorList",actorList);
		
		return "actors";
	}
	
	@GetMapping("/actor")
	public String getActor(@RequestParam String id, Model model) {
		Actor actor = actorService.findOne(Long.parseLong(id));
		
		
		model.addAttribute("actor",actor);
		
		return "actorDetails";
		
	}
	
	@GetMapping("/select2Actors")
	@ResponseBody
    public List<Actor> szallitok(@RequestParam(value = "q",required = false) String query) {
        
    	List<Actor> actorList = actorService.findAll();
    	
    	if (StringUtils.isEmpty(query)) {
    		return actorList;
        }

    	return actorList.stream().filter(sze -> sze.getName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());    
    }

}
