package mc.pf.ms.inscription.app.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import mc.pf.ms.inscription.app.models.Inscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InscriptionRepository extends ReactiveMongoRepository<Inscription, String> {
	
	public Flux<Inscription> findByAlumno(String alumno);
	
	public Mono<Inscription> findByAlumnoAndLineAndCourse(String alumno, String line, String course);
}
