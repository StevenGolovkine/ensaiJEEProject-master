package business;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class which manages an author of a RScript
 * @author steven
 *
 */
@Entity
@Table(name = "Author")
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true)
	private String name;
	
	private String password;
	
	@OneToMany(mappedBy = "author")
	private Set<RProgram> rprograms;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<RProgram> getRprograms() {
		return rprograms;
	}

	public void setRprograms(Set<RProgram> rprograms) {
		this.rprograms = rprograms;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
