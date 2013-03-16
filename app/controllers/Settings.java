package controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.bson.types.ObjectId;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import models.PromotionModel;
import models.StudentModel;
import models.TeacherModel;
import play.mvc.Controller;
import play.data.DynamicForm;
import play.mvc.Result;

import util.Hash;
import views.html.*;

public class Settings extends Controller {

	public static Result account()	{
		TeacherModel me = TeacherModel.finder.byId(new ObjectId(session().get("idTeacher")));
		
		return ok(account.render(me));
	}
	
	public static Result administration()	{
		List<TeacherModel> all = TeacherModel.finder.all();
		TeacherModel me = TeacherModel.finder.byId(new ObjectId(session().get("idTeacher")));
		
		return ok(administration.render(me, all));
	}
	
	public static Result addTeacher() throws NoSuchAlgorithmException	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputMail = signupForm.field("mail").value();
		String inputFirst = signupForm.field("firstname").value();
		String inputLast = signupForm.field("lastname").value();
		TeacherModel teacher = new TeacherModel();
		teacher.setFirstname(inputFirst);
		teacher.setLastname(inputLast);
		teacher.setMail(inputMail);
		teacher.setPass(Hash.encode(inputFirst.toLowerCase()+"-"+inputLast.toLowerCase(), "SHA-256"));
		teacher.insert();
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);
		node.put("state", "success");
		node.put("id", teacher.id());
		return ok(node);
	}
	
	public static Result delTeacher()	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputid = signupForm.field("id").value();
		TeacherModel teacher = TeacherModel.finder.byId(new ObjectId(inputid));
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);
		if(teacher != null)	{
			teacher.setDelete(true);
			teacher.setMail("");
			teacher.update();
			
			node.put("state", "success");
		}else	{
			node.put("state", "fail");
		}
		return ok(node);
	}
	
	public static Result resetPassword() throws NoSuchAlgorithmException	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputid = signupForm.field("id").value();
		TeacherModel teacher = TeacherModel.finder.byId(new ObjectId(inputid));
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);
		if(teacher != null)	{
			teacher.setPass(Hash.encode(teacher.getFirstname().toLowerCase()+"-"+teacher.getLastname().toLowerCase(), "SHA-256"));
			teacher.update();
			
			node.put("state", "success");
		}else	{
			node.put("state", "fail");
		}
		return ok(node);
	}
	
	public static Result saveTeacher()	{
		DynamicForm signupForm = form().bindFromRequest();

		TeacherModel teacher = TeacherModel.finder.byId(new ObjectId(session().get("idTeacher")));

		String inputMail = signupForm.field("mail").value();
		String inputFirst = signupForm.field("firstname").value();
		String inputLast = signupForm.field("lastname").value();
	
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);

		if(teacher != null)	{
			teacher.setMail(inputMail);
			teacher.setFirstname(inputFirst);
			teacher.setLastname(inputLast);
			teacher.update();
			
			node.put("state", "success");
		}else	{
			node.put("state", "fail");
		}
		return ok(node);
	}
	
	public static Result savePassword() throws NoSuchAlgorithmException	{
		DynamicForm signupForm = form().bindFromRequest();

		TeacherModel teacher = TeacherModel.finder.byId(new ObjectId(session().get("idTeacher")));

		String inputpassword1 = signupForm.field("inputpassword1").value();
		String inputpassword2 = signupForm.field("inputpassword2").value();
		String inputpassword3 = signupForm.field("inputpassword3").value();
	
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);

		if(teacher != null && inputpassword2.compareTo(inputpassword3) == 0)	{
			if(teacher.getMail().compareTo(Hash.encode(inputpassword1, "SHA-256")) == 0)	{
				teacher.setMail(Hash.encode(inputpassword2, "SHA-256"));
			}
			teacher.update();
			
			node.put("state", "success");
		}else	{
			node.put("state", "fail");
		}
		return ok(node);
	}
}
