package controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import models.ApiTokenModel;
import models.PromotionModel;
import models.StudentModel;
import models.TeacherModel;
import models.TestModel;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import play.data.DynamicForm;
import play.mvc.*;
import util.Hash;

public class Api extends Controller {
	
	public static enum STATUSCODE	{
		SUCCESS(200), FAIL(400), NOTFOUND(404), FORBIDDEN(403);
		
		private int code;
		private STATUSCODE(int code){
			this.code = code;
		}
		public String toString(){return ""+code;}
	}

	private static ObjectMapper mapper = new ObjectMapper();
	private static JsonNodeFactory factory = JsonNodeFactory.instance;

	/**
	 * LOGIN
	 */
	
	public static Result login()	{
		DynamicForm signinForm = form().bindFromRequest();	
		String email = signinForm.field("email").value();
		
		ObjectNode node = new ObjectNode(factory);
		try {
			String password = Hash.encode(signinForm.field("password").value(), "SHA-256");

			TeacherModel teacher = TeacherModel.findByLoginHashedPass(email, password);
			if(teacher == null)	{
				StudentModel stu = StudentModel.findByLoginHashedPass(email, password);
				if(stu == null)
					node.put("status", STATUSCODE.FAIL.toString());
				else	{
					node.put("status", STATUSCODE.SUCCESS.toString());
					node.put("token", ApiTokenModel.generateNewToken(stu.id()));
					node.put("type", "student");
				}
			}else	{
				node.put("status", STATUSCODE.SUCCESS.toString());
				node.put("token", ApiTokenModel.generateNewToken(teacher.id()));
				node.put("type", "teacher");
			}
		} catch (NoSuchAlgorithmException e1) {
			node.put("status", STATUSCODE.FAIL.toString());
		}
		return ok(node);
	}
	
	public static Result logout()	{
		DynamicForm signinForm = form().bindFromRequest();	
		String token = signinForm.field("t").value();
		ObjectNode node = new ObjectNode(factory);
		if(ApiTokenModel.asToken(token))	{
			ApiTokenModel.deleteToken(token);
		}
		node.put("status", STATUSCODE.SUCCESS.toString());
		return ok(node);
	}
	
	/**
	 *  Work with student
	 */
	
	public static Result addStudent() throws JsonParseException, JsonMappingException, IOException {
		DynamicForm form = form().bindFromRequest();	
		String token = form.field("t").value();
		String json = form.field("student").value();
		
		ObjectNode node = new ObjectNode(factory);
		if(ApiTokenModel.asToken(token))	{
			StudentModel student = mapper.readValue(json, StudentModel.class);
			student.insert();
			node.put("status", STATUSCODE.SUCCESS.toString());
			node.put("id", student.id());
		}else	{
			node.put("status", STATUSCODE.FORBIDDEN.toString());
		}
		return ok(node);
	}

	public static Result delStudent(String id) {
		DynamicForm signinForm = form().bindFromRequest();	
		String token = signinForm.field("t").value();
		play.Logger.debug(token);
		ObjectNode node = new ObjectNode(factory);
		
		if(ApiTokenModel.asToken(token))	{
			StudentModel student = StudentModel.finder.byId(new ObjectId(id));
			if(student != null)	{
				student.delete();
				node.put("status", STATUSCODE.SUCCESS.toString());
			} else {
				node.put("status", STATUSCODE.NOTFOUND.toString());
				node.put("msg", "student not found");
			}
		}else	{
			node.put("status", STATUSCODE.FORBIDDEN.toString());
		}
		return ok(node);
	}

	public static Result getStudent(String id) throws JsonGenerationException, JsonMappingException, IOException {
		if(id.compareTo("") == 0)	{
			StudentModel[] all = StudentModel.finder.all().toArray(new StudentModel[0]);
			ArrayNode ret = new ArrayNode(factory);
			for(StudentModel student : all)	{
				ret.add(mapper.convertValue(student, ObjectNode.class));
			}
			ObjectNode node = new ObjectNode(factory);
			node.put("status", STATUSCODE.SUCCESS.toString());
			node.put("content", ret);
			return ok(node);
		}else	{
			StudentModel student = StudentModel.finder.byId(new ObjectId(id));
			
			ObjectNode node = new ObjectNode(factory);
			if(student != null)	{
				node.put("status", STATUSCODE.SUCCESS.toString());
				node.put("content", mapper.convertValue(student, ObjectNode.class));
			}else	{
				node.put("status", STATUSCODE.NOTFOUND.toString());
			}
			return ok(node);
		}
	}

	public static Result editStudent(String id) throws JsonParseException, JsonMappingException, IOException {
		ObjectNode node = new ObjectNode(factory);
		
		DynamicForm form = form().bindFromRequest();
		String token = form.field("t").value();
		String json = form.field("student").value();
		
		if(ApiTokenModel.asToken(token))	{
			StudentModel stuedited = StudentModel.finder.byId(new ObjectId(id));
			if(stuedited != null)	{
				StudentModel student = mapper.readValue(json, StudentModel.class);
				stuedited.setEmail(student.getEmail());
				stuedited.setFirstname(student.getFirstname());
				stuedited.setLastname(student.getLastname());
				stuedited.setNumStu(student.getNumStu());
				
				if(student.getPromotion().id() == null)	{
					student.getPromotion().insert();
				}
				
				stuedited.setPromotion(student.getPromotion());
				stuedited.update();
				
				node.put("status", STATUSCODE.SUCCESS.toString());
			}else	{	
				node.put("status", STATUSCODE.NOTFOUND.toString());
			}
		}else	{
			node.put("status", STATUSCODE.FORBIDDEN.toString());
		}
		
		
		return ok(node);
	}

	
	/**
	 * Work with promotion
	 */
	
	public static Result getPromotion(String id) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectNode node = new ObjectNode(factory);
		if(id.compareTo("") == 0)	{
			PromotionModel[] all = PromotionModel.finder.all().toArray(new PromotionModel[0]);
			ArrayNode ret = new ArrayNode(factory);
			for(PromotionModel promo : all)	{
				ret.add(mapper.convertValue(promo, ObjectNode.class));
			}
			node.put("status", STATUSCODE.SUCCESS.toString());
			node.put("promotions", ret);	
		}else	{
			PromotionModel promo = PromotionModel.finder.byId(new ObjectId(id));
			
			
			ObjectNode promotionObj = mapper.convertValue(promo, ObjectNode.class);			
			
			ArrayNode lstStudent = new ArrayNode(factory);
			List<StudentModel> all = StudentModel.findWithPromotion(promo);
			for(StudentModel stu : all)	{
				lstStudent.add(mapper.convertValue(stu,ObjectNode.class));
			}
			promotionObj.put("students", lstStudent);
			
			node.put("status", STATUSCODE.SUCCESS.toString());
			node.put("promotion", promotionObj);	
		}	
		return ok(node);
	}

	/**
	 * Work with mark
	 */
	
	public static Result getMark(String id) throws JsonGenerationException, JsonMappingException, IOException {
		TestModel test = TestModel.finder.byId(new ObjectId(id));
		
		ObjectNode node = new ObjectNode(factory);
		if(test != null)	{
			node.put("status", STATUSCODE.SUCCESS.toString());
			node.put("content", mapper.convertValue(test, ObjectNode.class));
		}else	{
			node.put("status", STATUSCODE.NOTFOUND.toString());
		}
		return ok(node);
	}
	
	public static Result addMark(String studentid) throws JsonParseException, JsonMappingException, IOException {
		DynamicForm form = form().bindFromRequest();	
		String token = form.field("t").value();
		String json = form.field("mark").value();
		
		ObjectNode node = new ObjectNode(factory);
		if(ApiTokenModel.asToken(token))	{
			String idteacher = ApiTokenModel.getSessionId(token);
			TestModel test = mapper.readValue(json, TestModel.class);
			test.setTeacher(TeacherModel.finder.byId(new ObjectId(idteacher)));
			test.insert();
			
			StudentModel stu = StudentModel.finder.byId(new ObjectId(studentid));
			stu.addTest(test);
			stu.update();
			node.put("status", STATUSCODE.SUCCESS.toString());
			node.put("id", test.id());
		}else	{
			node.put("status", STATUSCODE.FORBIDDEN.toString());
		}
		return ok(node);
	}

	public static Result delMark(String id) {
		DynamicForm signinForm = form().bindFromRequest();	
		String token = signinForm.field("t").value();

		ObjectNode node = new ObjectNode(factory);
		
		if(ApiTokenModel.asToken(token))	{
			TestModel test = TestModel.finder.byId(new ObjectId(id));
			if(test != null)	{
				StudentModel stu = StudentModel.findOwner(test);
				stu.delTest(test);
				stu.update();
				
				test.delete();
				node.put("status", STATUSCODE.SUCCESS.toString());
			} else {
				node.put("status", STATUSCODE.NOTFOUND.toString());
				node.put("msg", "student not found");
			}
		}else	{
			node.put("status", STATUSCODE.FORBIDDEN.toString());
		}
		return ok(node);
	}

	public static Result editMark(String id) throws JsonParseException, JsonMappingException, IOException {
		ObjectNode node = new ObjectNode(factory);
		
		DynamicForm form = form().bindFromRequest();
		String token = form.field("t").value();
		String json = form.field("mark").value();
		TestModel testedited = TestModel.finder.byId(new ObjectId(id));
		if(ApiTokenModel.asToken(token) && testedited.getTeacher().id().compareTo(ApiTokenModel.getSessionId(token)) == 0)	{
			if(testedited != null)	{
				TestModel test = mapper.readValue(json, TestModel.class);
				testedited.setDate(test.getDate());
				testedited.setNote(test.getNote());
				if(test.getSubject().id() == null)
					test.getSubject().insert();
				testedited.setSubject(test.getSubject());
				testedited.update();
				
				node.put("status", STATUSCODE.SUCCESS.toString());
			}else {
				node.put("status", STATUSCODE.NOTFOUND.toString());
			}
		}else	{
			node.put("status", STATUSCODE.FORBIDDEN.toString());
		}
		
		
		return ok(node);
	}

}
