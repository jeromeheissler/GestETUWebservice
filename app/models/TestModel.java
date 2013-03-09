package models;

import java.io.IOException;

import org.bson.types.ObjectId;

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
	
	@Reference(lazy=true)
	private TeacherModel teacher;
	
	public ObjectId id()	{
		return id;
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
	
}
