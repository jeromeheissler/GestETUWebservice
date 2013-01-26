package models;


import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

import leodagdag.play2morphia.Model;

public class StudentModel extends Model {

	@Id
	private ObjectId id;
	
	//prenom
	private String firstname;
	//nom
	private String lastname;
	private String numStu;
	
	@Reference
	private PromotionModel promotion;
	@Reference
	private List<TestModel> lstTest;
	
	public static Finder<ObjectId, StudentModel> finder = new Finder<ObjectId, StudentModel>(ObjectId.class, StudentModel.class);
	
	public StudentModel()	{
		lstTest = new ArrayList<TestModel>();
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getNumStu() {
		return numStu;
	}
	public void setNumStu(String numStu) {
		this.numStu = numStu;
	}
	public PromotionModel getPromotion() {
		return promotion;
	}
	public void setPromotion(PromotionModel promotion) {
		this.promotion = promotion;
	}
	public List<TestModel> getLstTest() {
		return lstTest;
	}
	public void addTest(TestModel test) {
		this.lstTest.add(test);
	}
	public void delTest(TestModel test)	{
		int nbBoucle = lstTest.size();
		while(nbBoucle > 0)	{
			if(lstTest.get(nbBoucle-1).id().equals(test.id()))	{
				lstTest.remove(nbBoucle-1);
				break;
			}
			nbBoucle--;
		}
	}
	
	
}
