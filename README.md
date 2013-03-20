# This is a play app to manage mark in School.

## information
We have 2 part : 

- the web site, it can manage student, teacher, etc.
- we have a REST service with prefix url /api/ for external application would like to do read/edit some information.
 
All rest service are describe below.

## REST Service explanation

#### login to the webservice : 
Login is required if you want to edit some information on database. To be log you have to send a request POST to the url yoursite.com/api/login.

	URI : 
	POST	/api/login
	
	example curl : 
	curl --data "email=mymail@gmail.com&password=0000" http://localhost:9002/api/login
	
	response body : 
	{"status":"fail"} // if email and password do not lead to a connection
	{"status":"success","token":"YEvi9tqWKetymrIKYnmT"} // if you are log

#### work with student : 

##### adding student

	URI :
	PUT 	/api/student

bla bla

	DELETE 	/api/student/:id

balbla

	GET		/api/student/:id

balbla

	GET		/api/student

balbla

	POST	/api/student/:id
		

balbla

	GET		/api/promotion

balbla

	GET		/api/promotion/:id


balbla

	PUT 	/api/mark/

balbla

	DELETE 	/api/mark/:id

balbla

	GET		/api/mark/:id

balbla

	POST	/api/mark/:id