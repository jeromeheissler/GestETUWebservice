package models;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

import leodagdag.play2morphia.Model;

public class TestModel extends Model {

	@Id
	private ObjectId id;
	@Reference
	private SubjectModel subject;
	private float note;
	@Reference
	private StudentModel student;
	
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

	public StudentModel getStudent() {
		return student;
	}

	public void setStudent(StudentModel student) {
		this.student = student;
	}
	
}
