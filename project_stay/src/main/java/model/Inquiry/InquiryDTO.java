package model.Inquiry;

public class InquiryDTO {
	
	private int inq_code;
	private String inq_title;
	private String user_name;
	private String inq_created_datetime;
	private int inq_chk_status;
	
	public int getInq_code() {
		return inq_code;
	}
	public void setInq_code(int inq_code) {
		this.inq_code = inq_code;
	}
	public String getInq_title() {
		return inq_title;
	}
	public void setInq_title(String inq_title) {
		this.inq_title = inq_title;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getInq_created_datetime() {
		return inq_created_datetime;
	}
	public void setInq_created_datetime(String inq_created_datetime) {
		this.inq_created_datetime = inq_created_datetime;
	}
	public int getInq_chk_status() {
		return inq_chk_status;
	}
	public void setInq_chk_status(int inq_chk_status) {
		this.inq_chk_status = inq_chk_status;
	}
	
	
}
