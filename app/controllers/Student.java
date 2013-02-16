package controllers;

import models.PromotionModel;
import models.StudentModel;

import org.bson.types.ObjectId;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.studentAdd;

public class Student extends Controller {
	
	public static Result student()	{
		StudentModel[] all = StudentModel.finder.all().toArray(new StudentModel[0]);
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ArrayNode ret = new ArrayNode(factory);
		for(StudentModel student : all)	{
			ObjectNode node = new ObjectNode(factory);
			node.put("id", student.id().toString());
			node.put("firstname", student.getFirstname());
			node.put("lastname", student.getLastname());
			ret.add(node);
		}
		return ok(ret);
	}
	
	public static Result studentAdd()	{
		return ok(studentAdd.render());
	}
	
	public static Result studentAddForm()	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputid = signupForm.field("id").value();
		String inputname = signupForm.field("name").value();
		String inputannee = signupForm.field("annee").value();
		PromotionModel promo = PromotionModel.finder.byId(new ObjectId(inputid));
		
		return ok();
	}
}
