package com.adamproject.moviedb.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.adamproject.moviedb.entity.Actor;

public interface ActorService {
	List<Actor> findAll();
	Actor findOne(Long id);
	Actor findByActorName(String name);
	Actor addActor(Actor actor);
	Iterable<Actor> addActors(List<Actor> actorList);
	Page<Actor> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	Page<Actor> findPaginatedContaining(int pageNo, int pageSize, String name);
}
