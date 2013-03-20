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
	curl --data "email=mymail@gmail.com&password=0000" http://localhost:9000/api/login
	
	response body : 
	{"status":"400"} // if email and password do not lead to a connection
	{"status":"200","token":"YEvi9tqWKetymrIKYnmT"} // if you are log

#### work with student : 

##### getting student
You have 2 means to find student on database. Getting all student with the URI

	GET		/api/student
	
Or one single student with : 
	
	GET		/api/student/:id

##### adding student

	URI :
	PUT 	/api/student

For delete student you have to send a DELETE request with 

	DELETE 	/api/student/:id
	
	example curl : 
	curl -X "DELETE" http://localhost:9000/api/student/514435224e08afa658f83d52 --data "t=YEvi9tqWKetymrIKYnmT"
	
	response body
	{"status":"200"}
	
If you miss send your token with the t parameter you will get a :

	{"status":"403"}

else if the student you want to delete does not exist you will get the status :

	{"status":"404"}


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