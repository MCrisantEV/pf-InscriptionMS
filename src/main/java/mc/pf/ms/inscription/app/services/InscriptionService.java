package mc.pf.ms.inscription.app.services;

import mc.pf.ms.inscription.app.models.Inscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InscriptionService {

	public Mono<Inscription> save(Inscription inscription);

	public Mono<Inscription> updateEstado(String id, Inscription inscription);

	public Flux<Inscription> findAll();

	public Mono<Inscription> findId(String id);
	
	public Flux<Inscription> findAlumno(String alumno);
}
