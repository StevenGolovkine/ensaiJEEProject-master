# ENSAI Java EE Project

## Steven Golovkine

- How to install and launch the project?

    1. Execute `mvn clean install` in a terminal
    2. Import it in eclipse
    3. Run `App.java` in the `business` package


- How to launch some databases to have persistence.

    1. Execute `sh ./runServer.sh` in a terminal and keep `dev` in the `EntityManagerHelper.java` class
    2. Or you can use a MySQL database by putting `mysql` in the `EntityManegerHelper.java` class to replace `dev`


- What to do with the Servlet Server?

The Servlet Server is open on the port 8080. So, at the beginning, you have to go at the web-page `http://localhost:8080/myapp/signin.html`. Then, you can sign in (if you already have an account) or sign up (if you do not have an account). Then, you can just follow the instruction.


- What to do with the Rest Server?

The App provides a restful server. So, it is possible to interact with the database and interpret a R Program without using a graphical interface. This Rest Server is open on the port 8081.

* Features of the Rest Server:

     &minus; Display all the R Programs in the database.
    
    `curl -X GET http://localhost:8081/myapp/rprogram/allrprograms`
	
	&minus; Display one program knowing his ID.	
  
	`curl -X GET http://localhost:8081/myapp/rprogram/{id}`

    &minus; Display all the programs with a given name (normally the author is different).
  
	`curl -X GET http://localhost:8081/myapp/rprogram/name/{program_name}`

    &minus; Display all the programs for a given author.
  
	`curl -X GET http://localhost:8081/myapp/rprogram/author/{author_name}`

    &minus; Add an author to the database.

	`curl -H "Content-Type:application/json" -X POST -d '{"name":"author_name", "password":"author_password"}' http://localhost:8081/myapp/rprogram/addAuthor`

    &minus; Add an RProgram to the database. (You just have to put the name of the author but it has to be registered on the database)
	
	`curl -H "Content-Type:application/json" -X POST -d '{"author":"{"name":"author_name"}, "name":"rprogram_name", "program":"rprogram"}' http://localhost:8081/myapp/rprogram/execute`

