package jaxrs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import business.Author;
import business.RProgram;
import jpa.EntityManagerHelper;


/**
 * Class which provides the restful service.
 * @author steven
 *
 */
@Path("/rprogram")
public class RestR {
	
	@GET
	public Response getStatus() {
		return Response.status(Response.Status.OK).entity("JO").build();
	}
		
	@GET
	@Path("/allrprograms")
	@Produces(MediaType.TEXT_PLAIN)
	public List<RProgram> getAllRPrograms(){
		List<RProgram> result = EntityManagerHelper.getEntityManager().createQuery("SELECT e FROM RProgram AS e").getResultList();
		return result;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public RProgram getRProgram(@PathParam("id") int id){
		RProgram result = new RProgram();
		result = (RProgram) EntityManagerHelper.getEntityManager().createQuery("SELECT e FROM RProgram AS e WHERE e.id = '" + id + "'").getSingleResult();
		return result;
	}
	
	@GET
	@Path("/name/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public List<RProgram> getRProgramByName(@PathParam("name") String name){
		List<RProgram> result = EntityManagerHelper.getEntityManager().createQuery("SELECT e FROM RProgram AS e WHERE e.name = '" + name + "'").getResultList();
		return result;
	}
	
	@GET
	@Path("/author/{author}")
	@Produces(MediaType.TEXT_PLAIN)
	public List<RProgram> getRProgramByAuthor(@PathParam("author") String author){
		Author a = (Author)EntityManagerHelper.getEntityManager().createQuery("SELECT a FROM Author AS a WHERE a.name ='" + author + "'").getSingleResult();
		List<RProgram> result = EntityManagerHelper.getEntityManager().createQuery("SELECT e FROM RProgram AS e WHERE e.author = '" + a.getId() + "'").getResultList();
		return result;
	}
	
	@POST
	@Path("/addAuthor")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Author addAuthor(Author author){
		Author result = new Author();
		
		result.setName(author.getName());
		result.setPassword(author.getPassword());
		
		EntityTransaction t = EntityManagerHelper.getEntityManager().getTransaction();
		t.begin();
		EntityManagerHelper.getEntityManager().persist(result);
		t.commit();
		
		return result;
	}
	
	@POST
	@Path("/execute")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public RProgram addRProgram(RProgram rprogram){
		RProgram result = new RProgram();
		
		Author a = (Author)EntityManagerHelper.getEntityManager().createQuery("SELECT a FROM Author AS a WHERE a.name ='" + rprogram.getAuthor().getName() + "'").getSingleResult();
		
		result.setAuthor(a);
		result.setName(rprogram.getName());
		result.setProgram(rprogram.getProgram());
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("Renjin");
		
		if (engine == null) {
			throw new RuntimeException("Renjin Script Engine not found on the classpath.");
		}
		
		engine.getContext().setWriter(new PrintWriter(buffer));

		try {
			engine.eval(result.getProgram());

			EntityTransaction t = EntityManagerHelper.getEntityManager().getTransaction();
			t.begin();
			result.setResult(buffer.toString());
			EntityManagerHelper.getEntityManager().persist(result);
			t.commit();

		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
