package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import play.data.DynamicForm;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import org.bson.types.ObjectId;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import com.google.code.morphia.query.Query;

import models.StudentModel;
import models.SubjectModel;
import models.TeacherModel;
import models.TestModel;

import views.html.*;

public class Mark extends Controller {
	
	private static JsonNodeFactory factory = JsonNodeFactory.instance;
	
	public static Result markAdd()	{
		return ok(markAdd.render());
	}
	
	public static Result markAddForm()	{
		DynamicForm addMark = form().bindFromRequest();
		
		String stuid = addMark.field("student").value();
		String subject = addMark.field("subject").value();
		String subjectID = addMark.field("subjectID").value();
		String mark = addMark.field("mark").value();
		
		play.Logger.debug(mark);
		play.Logger.debug(subject);
		play.Logger.debug(subjectID);
		play.Logger.debug(stuid);
		
		TestModel test = new TestModel();
		test.setNote(Float.parseFloat(mark));
		test.setTeacher(TeacherModel.finder.byId(
						new ObjectId(session().get("idTeacher"))
				));
		StudentModel stu = StudentModel.finder.byId(new ObjectId(stuid));
		if(stu != null)	{
			SubjectModel sub = null;
			if(subjectID.compareTo("null") != 0)	{
				ObjectId id = new ObjectId(subjectID);
				sub = SubjectModel.finder.byId(id);
			}
			if(sub != null)	{
				test.setSubject(sub);
			}else	{
				sub = new SubjectModel();
				sub.setName(subject.toLowerCase().trim());
				sub.insert();
			
				test.setSubject(sub);
			}
			test.insert();
			session().put("markadd", "true");
			
			stu.addTest(test);
			stu.update();
		} else	{
			session().put("markadd", "false");
		}
		return ok(dashboard.render());
	}
	
	public static Result subject()	{
		SubjectModel[] all = SubjectModel.finder.all().toArray(new SubjectModel[0]);	
		ArrayNode ret = new ArrayNode(factory);
		for(SubjectModel subject : all)	{
			ObjectNode node = new ObjectNode(factory);
			node.put("id", subject.id().toString());
			node.put("name", subject.getName());
			ret.add(node);
		}
		return ok(ret);
	}
	
	public static Result saveMark()	{
		
		DynamicForm saveMark = form().bindFromRequest();
		
		String ids = saveMark.field("id").value();
		String subject = saveMark.field("subject").value();
		String mark = saveMark.field("mark").value();
		
		String[] idx = ids.split("-");
		ObjectNode node = new ObjectNode(factory);
		if(idx.length >= 2 && idx[1].compareTo(session().get("idTeacher")) == 0)	{
			TestModel test = TestModel.finder.byId(new ObjectId(idx[0]));
			if(test != null)	{
				 SubjectModel sub = SubjectModel.findByName(subject);
				 if(sub == null)	{
					 sub = new SubjectModel();
					 sub.setName(subject.toLowerCase().trim());
					 sub.insert();
				 }
				 test.setSubject(sub);
				 test.setNote(Float.parseFloat(mark));
				 test.update();
				 node.put("state", "success");
			}else	{
				node.put("state", "fail");
			}
		}else	{
			node.put("state", "fail");
		}
		return ok(node);
	}
	
	public static Result delMark()	{
		DynamicForm saveMark = form().bindFromRequest();
		String ids = saveMark.field("id").value();
		
		String[] idx = ids.split("-");
		ObjectNode node = new ObjectNode(factory);
		if(idx.length >= 2 && idx[1].compareTo(session().get("idTeacher")) == 0)	{
			TestModel test = TestModel.finder.byId(new ObjectId(idx[0]));
			if(test != null)	{
				StudentModel owner = StudentModel.findOwner(test);
				owner.delTest(test);
				owner.update();
				test.delete();
				node.put("state", "success");
			}else	{
				node.put("state", "fail");
			}
		}else	{
			node.put("state", "fail");
		}
		return ok(node);
	}
	
	public static Result importCSV()	{
		return ok(importCSV.render());
	}
	
	public static Result uploadCSV()	{
		MultipartFormData uploadForm = request().body().asMultipartFormData();
		List<FilePart> files = uploadForm.getFiles();
		ArrayNode ret = new ArrayNode(factory);
		for(FilePart file : files)	{
			ObjectNode node = new ObjectNode(factory);
			node.put("name", file.getFilename());
			
			File thefile = file.getFile();
			try {
				BufferedReader br = new BufferedReader(new FileReader(thefile));
				//lire la premiere ligne
			    String tmp = br.readLine();
			    String[] header = tmp.split(";");
			    if(header.length == 4 && header[0].equalsIgnoreCase("date") && header[1].equalsIgnoreCase("numstudent") &&
			       header[2].equalsIgnoreCase("subject") && header[3].equalsIgnoreCase("mark"))	{  	    
				    node.put("status", "success");
				    int nbData = 0;
				    
			    	//lire toute les ligne pour enregistrer les nom des substances 
				    while ( (tmp=br.readLine())!=null ) {
				    	String[] splitted = tmp.split(";");
						
				    	StudentModel student = StudentModel.findByStudentNumber(splitted[1]);
				    	
				    	if(student == null)	{
				    		student = new StudentModel();
				    		student.setNumStu(splitted[1]);
				    		student.insert();
				    	}
				    	
				    	SubjectModel sub = SubjectModel.findByName(splitted[2].toLowerCase());
				    	if(sub == null)	{
				    		sub = new SubjectModel();
				    		sub.setName(splitted[2].toLowerCase());
				    		sub.insert();
				    	}
				    	
				    	TestModel test = new TestModel();
				    	test.setNote(Float.parseFloat(splitted[3]));
				    	test.setSubject(sub);
				    	test.setDate(splitted[0]);
				    	test.setTeacher(TeacherModel.finder.byId(new ObjectId(session().get("idTeacher"))));
				    	test.insert();
				    	
				    	student.addTest(test);
				    	student.update();
				    	
				    	nbData ++;
					}
				    
				    node.put("dataInsert", nbData);
			    }else	{
			    	node.put("status", "fail");
			    	node.put("msg", "the header was not valid");
			    }
			    br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    	
			ret.add(node);
		}	
		ObjectNode jsonfiles = new ObjectNode(factory);
		jsonfiles.put("files", ret);
		return ok(jsonfiles);
	}
	
}
