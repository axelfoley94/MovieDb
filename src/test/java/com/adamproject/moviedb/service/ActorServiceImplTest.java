package com.adamproject.moviedb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adamproject.moviedb.entity.Actor;
import com.adamproject.moviedb.repository.ActorRepository;

class ActorServiceImplTest {

	@Captor
	private ArgumentCaptor<Long> idCaptor;
	
	private ActorRepository actorRepository = Mockito.mock(ActorRepository.class);
	
	@Test
	@DisplayName("Should find actor by Id")
	void testFindOne() {
		
		Actor testActor = new Actor(123L, "Tim Honks", null);
		when(actorRepository.findById(123L)).thenReturn(Optional.of(testActor));
		
		ActorService actorService = new ActorServiceImpl(actorRepository);
		
		
		verify(actorRepository).findById(idCaptor.capture());
		
		assertThat(actorService.findOne(123L).getId()).isEqualTo(testActor.getId());
		
	}
	
	

}
