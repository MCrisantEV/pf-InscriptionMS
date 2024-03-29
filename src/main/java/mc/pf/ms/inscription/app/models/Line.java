package mc.pf.ms.inscription.app.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
public class Line {
	@Id
	private String id;
	private String line;
	private List<Course> net;
}
