package mc.pf.ms.inscription.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mc.pf.ms.inscription.app.models.Inscription;
import mc.pf.ms.inscription.app.services.InscriptionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/inscriptions")
public class InscriptionController {
	
	@Autowired
	private InscriptionService insServ;
	
	@PostMapping
	public Mono<Inscription> create(@RequestBody Inscription inscription){
		return insServ.save(inscription);
	}
	
	@PutMapping("/{id}")
	public Mono<Inscription> updateEstado(@PathVariable String id, @RequestBody Inscription inscription){
		return insServ.updateEstado(id, inscription);
	}
	
	@GetMapping
	public Flux<Inscription> findAll(){
		return insServ.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Inscription> findId(@PathVariable String id){
		return insServ.findId(id);
	}
	
	@GetMapping("/alumno/{alumno}")
	public Flux<Inscription> findAlumno(@PathVariable String alumno){
		return insServ.findAlumno(alumno);
	}
}
