package controllers;


import java.util.List;

import models.PromotionModel;
import models.StudentModel;

import org.bson.types.ObjectId;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Student extends Controller {
	
	public static Result student()	{
		List<StudentModel> all = StudentModel.finder.all();
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ArrayNode ret = new ArrayNode(factory);
		for(StudentModel student : all)	{
			ObjectNode node = new ObjectNode(factory);
			node.put("id", student.id().toString());
			node.put("firstname", student.getFirstname());
			node.put("lastname", student.getLastname());
			node.put("numstu", student.getNumStu());
			node.put("mail", student.getEmail());
			PromotionModel promo = student.getPromotion();
			if(promo != null)
				node.put("promo", promo.getLabel()+" "+promo.getAnnee());
			ret.add(node);
		}
		return ok(ret);
	}
	
	public static Result loadOneStudent(String id)	{
		StudentModel student = StudentModel.finder.byId(new ObjectId(id));
		int status;
		if( student != null) {
			status = 200;
		}else	{
			status = 404;
		}
		return ok(studentDescription.render(status, student));
	}
	
	public static Result studentAdd()	{
		PromotionModel[] lst = PromotionModel.finder.all().toArray(new PromotionModel[0]);
		return ok(studentAdd.render(lst));
	}
	
	public static Result studentAddForm()	{
		DynamicForm signupForm = form().bindFromRequest();
		  
		String inputfirstname = signupForm.field("firstname").value();
		String inputlastname = signupForm.field("lastname").value();
		String inputnumber = signupForm.field("number").value();
		String inputmail = signupForm.field("mail").value();
		String inputpromotion = signupForm.field("promotion").value();
		
		PromotionModel promo = PromotionModel.finder.byId(new ObjectId(inputpromotion));
		if(promo != null)	{
			StudentModel stu = new StudentModel();
			stu.setFirstname(inputfirstname);
			stu.setLastname(inputlastname);
			stu.setNumStu(inputnumber);
			stu.setEmail(inputmail);
			
			stu.setPromotion(promo);
			
			stu.insert();

			session().put("statusadd", "true");
		}else	{
			session().put("statusadd", "false");
		}
		return ok(dashboard.render());
	}
	
	public static Result delStudent()	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputid = signupForm.field("id").value();
		StudentModel stu = StudentModel.finder.byId(new ObjectId(inputid));
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);
		if(stu != null)	{
			stu.delete();
			node.put("state", "success");
		}else	{
			node.put("state", "fail");
		}
		return ok(node);
	}
	
	public static Result editStudent()	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputid = signupForm.field("id").value();
		String inputfirstname = signupForm.field("firstname").value();
		String inputlastname = signupForm.field("lastname").value();
		String inputnumstu = signupForm.field("numstu").value();
		String inputmail = signupForm.field("mail").value();
		StudentModel stu = StudentModel.finder.byId(new ObjectId(inputid));
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);
		if(stu != null)	{
			stu.setFirstname(inputfirstname);
			stu.setLastname(inputlastname);
			stu.setNumStu(inputnumstu);
			stu.setEmail(inputmail);
			stu.update();
			node.put("state", "success");
		}else	{
			node.put("state", "fail");
		}
		return ok(node);
	}
}
