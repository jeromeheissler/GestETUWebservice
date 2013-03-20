# This is a play app to manage mark in School.

## information
We have 2 part : 

- the web site, it can manage student, teacher, etc.
- we have a REST service with prefix url /api/ for external application would like to do read/edit some information.
 
All rest service are describe below.

## REST Service explanation

#### log into the webservice : 
Login is required if you want to edit some information on database. To be log you have to send a request POST to the url yoursite.com/api/login.

	- uri : 
	POST	/api/login
	- example curl : 
	curl --data "email=mymail@gmail.com&password=0000" http://localhost:9000/api/login
	
	- response body : 
	{"status":"400"} // if email and password do not lead to a connection
	{"status":"200","token":"YEvi9tqWKetymrIKYnmT"} // if you are log

#### log out the webservice : 
	- uri : 
	POST	/api/logout
	- example curl : 
	curl --data "t=YEvi9tqWKetymrIKYnmT" http://localhost:9000/api/logout
	
	- response body : 
	{"status":"200"}
	
#### authenticate request
For each request doing an action of insert, update, delete you need to send the token you get during the log in operation.
for example delete an student (we will explain it below) : 

	curl -X "DELETE" http://localhost:9000/api/student/514435224e08afa658f83d52 --data "t=YEvi9tqWKetymrIKYnmT"
	
If you miss send your token with the t parameter, or send a fake token you will get a :

	{"status":"403"}
	

#### work with student : 

##### getting student
You have 2 means to find student on database. Getting all student with the URI

	- uri : 
	GET		/api/student
	- example curl : 
	curl http://localhost:9002/api/student
	
	-response body : 
	"status":"200","content":[{"id":"51443fea4e08afa658f83d69","firstname":"Nicolas","lastname":"Rosado","numStu":"21004736","email":"rosado.nicolas@etu.univ-tours.fr","promotion":{…},"lstTest":[…]},{"id":"5149b6014e08a079dfda6231","firstname":"Thibault edtied","lastname":"De maison rouge","numStu":"21004737","email":null,"promotion":{…},"lstTest":[]}]}
	
Or one single student with : 
	
	- uri : 
	GET		/api/student/:id
	- example curl : 
	curl http://localhost:9002/api/student/51443fea4e08afa658f83d69
	
	-reponse body : 
	{"status":"200","content":{"id":"51443fea4e08afa658f83d69","firstname":"Nicolas","lastname":"Rosado","numStu":"21004736","email":"rosado.nicolas@etu.univ-tours.fr","promotion":{…},"lstTest":[…]}}
	


##### add student
When you want to add a student you have to send PUT request with on parameter the json representation of the student : 

	- uri :
	PUT 	/api/student
	- example curl : 
	curl -X "PUT" http://localhost:9002/api/student --data "t=Bz5YWSuWKUmMaZvcYcEK&student={\"firstname\":\"Thibault Arnaud2\",\"lastname\":\"De maison rouge\",\"numStu\":\"21004737\",\"email\":null,\"promotion\":{\"id\":\"51443e134e08afa658f83d64\",\"annee\":2013,\"label\":\"CP\"},\"lstTest\":[]}" 
	
	- response body
	{"status":"200", "id":"514435224e08afa658f83d52"}
	
##### delete student	
For delete student you have to send a DELETE request with 

	- uri
	DELETE 	/api/student/:id
	- example curl : 
	curl -X "DELETE" http://localhost:9000/api/student/514435224e08afa658f83d52 --data "t=YEvi9tqWKetymrIKYnmT"
	
	- response body
	{"status":"200"}

if the student you want to delete does not exist you will get the status :

	{"status":"404"}

##### edit student
To edit a student you have to send a POST request on the url /api/student/<id> to edit a specific student. This request need 2 parameters : the token and the json representation of the new student after edition.

	- uri
	POST	/api/student/:id
	- example curl : 
	curl -X "POST" http://localhost:9002/api/student/5149B6014E08A079DFDA6231 --data "t=Bz5YWSuWKUmMaZvcYcEK&student={\"firstname\":\"Thibault edtied\",\"lastname\":\"De maison rouge\",\"numStu\":\"21004737\",\"email\":null,\"promotion\":{\"annee\":1915,\"label\":\"CPVieux\"},\"lstTest\":[]}"
	
	 - response body
	{"status":"200"}

notice : 

- if the promotion does not existe on the database, it will be created during the edition.
- if the student does not exist you will get a status code 404

#### work with promotion : 

##### getting promotion
Like getting student you can get the description of one or more promotion : 

	- uri 
	GET		/api/promotion
	- example curl : 
	curl http://localhost:9002/api/promotion
	
	- response body : 
	{"status":"200","promotions":[{"id":"51443def4e08afa658f83d5d","annee":2013,"label":"DI"},{"id":"51443df64e08afa658f83d5e","annee":2012,"label":"DI"},{"id":"5149bfa24e08a079dfda6232","annee":1915,"label":"CPVieux"}]}
	
	- uri
	GET		/api/promotion/:id
	- example curl : 
	curl http://localhost:9002/api/promotion/51443def4e08afa658f83d5d
	
	- response body : 
	{"status":"200","promotion":{"id":"51443def4e08afa658f83d5d","annee":2013,"label":"DI","students":[{"id":"51443fea4e08afa658f83d69","firstname":"Nicolas","lastname":"Rosado","numStu":"21004736","email":"rosado.nicolas@etu.univ-tours.fr","promotion":{"id":"51443def4e08afa658f83d5d","annee":2013,"label":"DI"},"lstTest":[]}]}}

#### work with mark : 

##### getting mark
You can get information for one mark with the request : 
	
	- uri :
	GET		/api/mark/:id
	- example curl :
	curl http://localhost:9002/api/mark/51443fea4e08afa658f83d6a

	- response body
	{"id":"51443fea4e08afa658f83d6a","subject":{"id":"514435224e08afa658f83d53","name":"mathematical"},"note":4.0,"date":"03/25/13","teacher":{"id":"512b77a6c2e6d1ef72687f50","mail":"heissler.jerome@gmail.com","firstname":"Jérome","lastname":"Heissler","delete":false}} 


#### add mark

	- uri : 
	PUT 	/api/mark/:idstudent
	- example curl : 
	curl -X "PUT" http://localhost:9002/api/mark/51443fea4e08afa658f83d69 --data "t=AfOuioBXCtRYslLbYfSj&mark={\"subject\":{\"id\":\"514435224e08afa658f83d53\",\"name\":\"mathematical\"},\"note\":14.0,\"date\":\"03/25/13\"}"
	
	- response body
	{"status":"200", "id":"51443fea4e08afa658f83d6a"}

#### delete mark
	
	- uri : 
	DELETE 	/api/mark/:id
	- example curl : 
	curl -X "DELETE" http://localhost:9002/api/mark/51443fea4e08afa658f83d6a --data "t=AfOuioBXCtRYslLbYfSj"
	
#### edit mark

	-uri
	POST	/api/mark/:id
	- example curl : 
	curl -X "POST" http://localhost:9002/api/mark/5149cdcd4e08a079dfda6237 --data "t=AfOuioBXCtRYslLbYfSj&mark={\"subject\":{\"id\":\"514435224e08afa658f83d53\",\"name\":\"mathematical\"},\"note\":10.0,\"date\":\"03/25/13\"}"
	
	
	
	
	
	
	