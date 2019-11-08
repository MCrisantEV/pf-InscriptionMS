package mc.pf.ms.inscription.app.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "inscriptions")
public class Inscription {
	@Id
	private String id;
	private String alumno;
	private String line;
	private String course;
	private String estado;
}
