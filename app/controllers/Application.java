package controllers;

import java.security.NoSuchAlgorithmException;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import models.PromotionModel;
import models.TeacherModel;

import play.*;
import play.data.DynamicForm;
import play.mvc.*;

import util.Hash;
import views.html.*;

public class Application extends Controller {

	public static Result index(boolean formError) {
		if(session().containsKey("idTeacher"))
			return redirect(routes.Application.dashboard());
		
		return ok(index.render(formError));
	}
	
	public static Result logout()	{
		session().clear();
		return redirect(routes.Application.index(false));
	}
	
	public static Result signin()	{
		DynamicForm signinForm = form().bindFromRequest();
		
		String email = signinForm.field("Email").value();
		try {
			String password = Hash.encode(signinForm.field("Password").value(), "SHA-256");

			TeacherModel teacher = TeacherModel.findByLoginHashedPass(email, password);
			if(teacher == null)
				return redirect(routes.Application.index(true));
			else	{
				session().put("idTeacher", teacher.id().toString());
				return redirect(routes.Application.dashboard());
			}
		} catch (NoSuchAlgorithmException e1) {
			return redirect(routes.Application.index(true));
		}
	}
	
	public static Result signupForm()	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputFirstname = signupForm.field("inputFirstname").value();
		String inputLastname = signupForm.field("inputLastname").value();
		String inputEmail = signupForm.field("inputEmail").value();
		try {
			String inputPassword = signupForm.field("inputPassword").value();
			inputPassword = Hash.encode(inputPassword, "SHA-256");
			
			TeacherModel teacher = new TeacherModel();
			teacher.setFirstname(inputFirstname);
			teacher.setLastname(inputLastname);
			teacher.setMail(inputEmail);
			teacher.setPass(inputPassword);
			teacher.insert();
			session().put("idTeacher", teacher.id().toString());
		
		} catch (NoSuchAlgorithmException e) {
			return redirect(routes.Application.index(true));
		}
		
		return redirect(routes.Application.dashboard());
	}
	
	public static Result signup()	{
		if(session().containsKey("idTeacher"))
			return redirect(routes.Application.dashboard());
		
		return ok(signup.render());
	}
	
	public static Result dashboard()	{
		if(!session().containsKey("idTeacher"))
			return redirect(routes.Application.index(false));
		
		return ok(dashboard.render());
	}
	
	public static Result promotion()	{
		PromotionModel[] all = PromotionModel.finder.all().toArray(new PromotionModel[0]);
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ArrayNode ret = new ArrayNode(factory);
		for(PromotionModel promo : all)	{
			ObjectNode node = new ObjectNode(factory);
			node.put("id", promo.id().toString());
			node.put("annee", promo.getAnnee());
			node.put("name", promo.getLabel());
			ret.add(node);
		}
		return ok(ret);
	}
	
	public static Result addPromotion()	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputName = signupForm.field("name").value();
		String inputAnnee = signupForm.field("annee").value();
		PromotionModel promo = new PromotionModel();
		promo.setAnnee(Integer.parseInt(inputAnnee));
		promo.setLabel(inputName);
		promo.insert();
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);
		node.put("state", "success");
		node.put("id", promo.id().toString());
		return ok(node);
	}
	
	public static Result delPromotion()	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputid = signupForm.field("id").value();
		PromotionModel promo = PromotionModel.finder.byId(new ObjectId(inputid));
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);
		if(promo != null)	{
			promo.delete();
			node.put("state", "success");
		}else	{
			node.put("state", "fail");
		}
		return ok(node);
	}
	
	public static Result editPromotion()	{
		DynamicForm signupForm = form().bindFromRequest();
		String inputid = signupForm.field("id").value();
		String inputname = signupForm.field("name").value();
		String inputannee = signupForm.field("annee").value();
		PromotionModel promo = PromotionModel.finder.byId(new ObjectId(inputid));
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode node = new ObjectNode(factory);
		if(promo != null)	{
			promo.setAnnee(Integer.parseInt(inputannee));
			promo.setLabel(inputname);
			promo.update();
			node.put("state", "success");
		}else	{
			node.put("state", "fail");
		}
		return ok(node);
	}
}
