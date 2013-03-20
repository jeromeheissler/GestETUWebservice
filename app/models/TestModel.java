package models;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

import leodagdag.play2morphia.Model;

public class TestModel extends Model {

	public static Finder<ObjectId, TestModel> finder = new Finder<ObjectId, TestModel>(ObjectId.class, TestModel.class);
	
	@Id
	private ObjectId id;
	@Reference(lazy=true)
	private SubjectModel subject;
	private float note;
	
	private String date;
	
	@Reference(lazy=true)
	private TeacherModel teacher;
	
	@JsonProperty
	public String id()	{
		if(id == null)
			return null;
		return id.toString();
	}

	public SubjectModel getSubject() {
		return subject;
	}

	public void setSubject(SubjectModel subject) {
		this.subject = subject;
	}

	public float getNote() {
		return note;
	}

	public void setNote(float note) {
		this.note = note;
	}

	public TeacherModel getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherModel teacher) {
		this.teacher = teacher;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
