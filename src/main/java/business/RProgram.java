package business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class which manages a RProgram
 * @author steven
 *
 */
@Entity
@Table(name = "RProgram")
public class RProgram {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(length=2000)
	private String program;
	
	@Column(length=2000)
	private String result;

	@ManyToOne(optional=true)
	@JoinColumn(name="author")
	private Author author;
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public String getProgram() {
		return program;
	}
	
	public void setProgram(String program) {
		this.program = program;
	}

	@Override
	public String toString() {
		return "Name = " + name + ", Author = " + author + "\n Program = " + program + "\n Result = " + result + "\n\n";
	}
	
	
}
