This is a play app to manage mark in School.

We have 2 part : 
 - the web site, it can manage student, teacher, etc...
 - we have a REST service with prefix url /api/ for external application would like to do read/edit some information
 
All rest service are describe here : 

POST	/api/login
		
PUT 	/api/student
DELETE 	/api/student/:id
GET		/api/student/:id
GET		/api/student
POST	/api/student/:id
		
GET		/api/promotion
GET		/api/promotion/:id

PUT 	/api/mark/
DELETE 	/api/mark/:id
GET		/api/mark/:id
POST	/api/mark/:id