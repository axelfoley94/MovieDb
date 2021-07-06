package com.adamproject.moviedb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adamproject.moviedb.entity.Actor;
import com.adamproject.moviedb.repository.ActorRepository;

@ExtendWith(MockitoExtension.class)
class ActorServiceImplTest {

//	@Captor
//	private ArgumentCaptor<Long> idCaptor;
	
	@Mock
	private ActorRepository actorRepository;
	
	ActorService actorService;
	
	@BeforeEach
	void setUp() {
		//actorRepository = Mockito.mock(ActorRepository.class); v2
		actorService = new ActorServiceImpl(actorRepository);
	}
	
	@Test
	@DisplayName("Should find actor")
	void testFindOne() {
		
		Actor testActor = new Actor(123L, "Tom Hanks", null);
		Mockito.when(actorRepository.findById(123L))
			.thenReturn(Optional.of(testActor));
		
		//verify(actorRepository).findById(idCaptor.capture());
		
		assertThat(actorService.findOne(123L)).isEqualTo(testActor);
		
	}
	
	 @Test
	 @DisplayName("Should save an actor")
	  void delete() throws Exception {
		Actor testActor = new Actor(123L, "Tom Hanks", null);
	    actorService.addActor(testActor);
	    Mockito.verify(actorRepository).save(testActor);
	  }

}
