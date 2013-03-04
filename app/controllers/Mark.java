package controllers;

import play.data.DynamicForm;
import play.mvc.*;

import org.bson.types.ObjectId;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import models.StudentModel;
import models.SubjectModel;
import models.TeacherModel;
import models.TestModel;

import views.html.*;

public class Mark extends Controller {
	
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
			test.setStudent(stu);
			SubjectModel sub = null;
			if(subjectID.compareTo("null") != 0)	{
				ObjectId id = new ObjectId(subjectID);
				sub = SubjectModel.finder.byId(id);
			}
			if(sub != null)	{
				test.setSubject(sub);
			}else	{
				sub = new SubjectModel();
				sub.setName(subject);
				sub.insert();
			
				test.setSubject(sub);
			}
			test.insert();
			session().put("markadd", "true");
			
			//stu.addTest(test);
			//stu.update();
		} else	{
			session().put("markadd", "false");
		}
		return ok(dashboard.render());
	}
	
	public static Result subject()	{
		SubjectModel[] all = SubjectModel.finder.all().toArray(new SubjectModel[0]);
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ArrayNode ret = new ArrayNode(factory);
		for(SubjectModel subject : all)	{
			ObjectNode node = new ObjectNode(factory);
			node.put("id", subject.id().toString());
			node.put("name", subject.getName());
			ret.add(node);
		}
		return ok(ret);
	}
	
}
