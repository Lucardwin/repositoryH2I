package it.h2i.idservice.accountablemodel.DTO;

public class AddDTO {

	private String success;
	private String first_parameter;
	private String second_parameter;
	private String third_parameter;
	private String four_parameter;
	
	
	
	
	public String getFour_parameter() {
		return four_parameter;
	}




	public void setFour_parameter(String four_parameter) {
		this.four_parameter = four_parameter;
	}




	public AddDTO() {}

	public AddDTO(String success,String f) {
		this.success=success;
		first_parameter = f;
	
		
	}
	public AddDTO(String success,String f, String t) {
		this.success=success;
		first_parameter = f;
		second_parameter = t;
		
	
		
	}
	
	
	
	
	
	public AddDTO(String success,String f, String s, String t, String fo) {
		this.success=success;
		first_parameter = f;
		second_parameter = s;
		third_parameter = t;
		four_parameter=fo;
		
		
	}
	
	
	
	
	
	
	
	public String getSuccess() {
		return success;
	}




	public void setSuccess(String success) {
		this.success = success;
	}




	public String getFirst_parameter() {
		return first_parameter;
	}
	public void setFirst_parameter(String first_parameter) {
		this.first_parameter = first_parameter;
	}
	public String getSecond_parameter() {
		return second_parameter;
	}
	public void setSecond_parameter(String second_parameter) {
		this.second_parameter = second_parameter;
	}
	public String getThird_parameter() {
		return third_parameter;
	}
	public void setThird_parameter(String third_parameter) {
		this.third_parameter = third_parameter;
	}
	
	
	
	
	
	
	
	
	
	
}
