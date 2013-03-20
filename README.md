# This is a play app to manage mark in School.

## information
We have 2 part : 

- the web site, it can manage student, teacher, etc.
- we have a REST service with prefix url /api/ for external application would like to do read/edit some information.
 
All rest service are describe below.

## REST Service explanation

#### log into the webservice : 
Login is required if you want to edit some information on database. To be log you have to send a request POST to the url yoursite.com/api/login.

	URI : 
	POST	/api/login
	
	example curl : 
	curl --data "email=mymail@gmail.com&password=0000" http://localhost:9000/api/login
	
	response body : 
	{"status":"400"} // if email and password do not lead to a connection
	{"status":"200","token":"YEvi9tqWKetymrIKYnmT"} // if you are log
	
#### authenticate request
For each request doing an action of insert, update, delete you need to send the token you get during the log in operation.
for example delete an student (we will explain it below) : 

	curl -X "DELETE" http://localhost:9000/api/student/514435224e08afa658f83d52 --data "t=YEvi9tqWKetymrIKYnmT"
	
If you miss send your token with the t parameter, or send a fake token you will get a :

	{"status":"403"}
	

#### work with student : 

##### getting student
You have 2 means to find student on database. Getting all student with the URI

	GET		/api/student
	
Or one single student with : 
	
	GET		/api/student/:id
	


##### add student
When you want to add a student you have to send PUT request with on parameter the representation's json of the student : 

	URI :
	PUT 	/api/student
	
	example curl : 
	curl -X "PUT" http://localhost:9002/api/student --data "t=Bz5YWSuWKUmMaZvcYcEK&student={\"firstname\":\"Thibault Arnaud2\",\"lastname\":\"De maison rouge\",\"numStu\":\"21004737\",\"email\":null,\"promotion\":{\"id\":\"51443e134e08afa658f83d64\",\"annee\":2013,\"label\":\"CP\"},\"lstTest\":[]}" 
	
	response body
	{"status":"200"}
	
##### delete student	
For delete student you have to send a DELETE request with 

	DELETE 	/api/student/:id
	
	example curl : 
	curl -X "DELETE" http://localhost:9000/api/student/514435224e08afa658f83d52 --data "t=YEvi9tqWKetymrIKYnmT"
	
	response body
	{"status":"200"}

if the student you want to delete does not exist you will get the status :

	{"status":"404"}

##### edit student

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