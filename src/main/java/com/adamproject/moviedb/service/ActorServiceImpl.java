package com.adamproject.moviedb.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.adamproject.moviedb.entity.Actor;
import com.adamproject.moviedb.entity.Movie;
import com.adamproject.moviedb.repository.ActorRepository;

@Service
public class ActorServiceImpl implements ActorService {

	ActorRepository actorRepository;

	@Autowired
	public ActorServiceImpl(ActorRepository actorRepository) {
		this.actorRepository = actorRepository;
	}

	@Override
	public List<Actor> findAll() {
		
		return actorRepository.findAll();
	}

	@Override
	public Actor findOne(Long id) {
		
		return actorRepository.findById(id).orElse(null);
	}

	@Override
	public Actor findByActorName(String name) {
		
		return actorRepository.findByName(name).orElse(null);
	}

	@Override
	public Actor addActor(Actor actor) {
		
		return actorRepository.save(actor);
	}
	
	@Override
	public Iterable<Actor> addActors(List<Actor> actorList) {
		
		return actorRepository.saveAll(actorList);
	}

	@Override
	public Page<Actor> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		 Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
		 Sort.by(sortField).descending();
		 
		 Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

		 return actorRepository.findAll(pageable);
	}
	

	@Override
	public Page<Actor> findPaginatedContaining(int pageNo, int pageSize, String name) {
		Pageable pageable = PageRequest.of(pageNo-1 , pageSize);
		return actorRepository.findByNameContainingAllIgnoreCase(name,pageable);
	}


	

	
	
	
	
}
