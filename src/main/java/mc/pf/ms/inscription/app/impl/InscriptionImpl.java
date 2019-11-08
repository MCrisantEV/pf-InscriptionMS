package mc.pf.ms.inscription.app.impl;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import mc.pf.ms.inscription.app.models.Course;
import mc.pf.ms.inscription.app.models.Inscription;
import mc.pf.ms.inscription.app.models.Line;
import mc.pf.ms.inscription.app.repositories.InscriptionRepository;
import mc.pf.ms.inscription.app.services.InscriptionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InscriptionImpl implements InscriptionService {

	@Autowired
	@Qualifier("lines")
	private WebClient wcLines;

	@Autowired
	@Qualifier("courses")
	private WebClient wcCourses;

	@Autowired
	private InscriptionRepository insRep;

	@Override
	public Mono<Inscription> save(Inscription inscription) {
		return wcLines.get().uri("/" + inscription.getLine()).accept(APPLICATION_JSON).retrieve().bodyToMono(Line.class)
				.flatMap(dbl -> {

					String priority = null;

					for (Course c : dbl.getNet()) {
						if (c.getCourse().equals(inscription.getCourse())) {
							priority = c.getPriority();
						}
					}

					if (priority == null) {
						inscription.setAlumno(null);
						inscription.setCourse("Error, No existe curso en la linea de carrera");
						inscription.setLine(null);
						inscription.setEstado(null);
						return Mono.just(inscription);
					} else {
						if (priority.equals("")) {
							 return insRep.findByAlumnoAndLineAndCourse(inscription.getAlumno(), inscription.getLine(),
									inscription.getCourse()).map(db -> {
										inscription.setAlumno("Error, Ya esta registrado este curso");
										inscription.setCourse(null);
										inscription.setLine(null);
										inscription.setEstado(null);
										return inscription;
									}).switchIfEmpty(insRep.save(inscription));
						} else {
							//Verificar si el curso prioritario esta aprobado o lo tengo registrado
							return insRep.findByAlumnoAndLineAndCourse(inscription.getAlumno(), inscription.getLine(),
									priority).flatMap(db -> {
										if(db.getEstado().equals("Aprobado")) {
											return insRep.save(inscription);
										}else {
											inscription.setAlumno("Error, El Curso requerido tiene que estar Aprobado");
											inscription.setCourse(null);
											inscription.setLine(null);
											inscription.setEstado("Estado Actual del Curso: "+db.getEstado());
											return Mono.just(inscription);
										}
									}).switchIfEmpty(
										Mono.just("").map(er -> {
											inscription.setAlumno("Error, Aun no esta registrado, ni aprobado en el curso requerido");
											inscription.setCourse(null);
											inscription.setLine(null);
											inscription.setEstado(null);
											return inscription;
										})
									);
						}

					}
				}).switchIfEmpty(Mono.just("").map(er -> {
					inscription.setAlumno(null);
					inscription.setCourse(null);
					inscription.setLine("Error, No existe Linea de carrera");
					inscription.setEstado(null);
					return inscription;
				}));
	}

	@Override
	public Mono<Inscription> updateEstado(String id, Inscription inscription) {
		return insRep.findById(id).flatMap(db -> {
			inscription.setId(db.getId());
			inscription.setAlumno(db.getAlumno());
			inscription.setLine(db.getLine());
			inscription.setCourse(db.getCourse());
			return insRep.save(inscription);
		}).switchIfEmpty(Mono.just("").map(er -> {
			inscription.setAlumno("Error, No existe el registro");
			inscription.setCourse(null);
			inscription.setLine(null);
			inscription.setEstado(null);
			return inscription;
		}));
	}

	@Override
	public Flux<Inscription> findAll() {
		return insRep.findAll();
	}

	@Override
	public Mono<Inscription> findId(String id) {
		return insRep.findById(id);
	}

	@Override
	public Flux<Inscription> findAlumno(String alumno) {
		return insRep.findByAlumno(alumno);
	}

}
