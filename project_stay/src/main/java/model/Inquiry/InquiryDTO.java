package model.Inquiry;

public class InquiryDTO {
	// 二쇱꽍�씠�빞
	private int inq_code;
	private String inq_title;
	private String user_name;  // 회占쏙옙占싱몌옙
	private String inq_created_datetime;
	private int inq_chk_status;  // 占썰변占쏙옙占쏙옙 체크키
	private int inq_ref;  // 占쏙옙 占쌓뤄옙
	private int inq_re_step;  // 占쏙옙 占쏙옙占쏙옙
	private int user_code;  // 회占쏙옙占쌘듸옙
	private String inq_content;  // 占쏙옙 占쏙옙占쏙옙
	private int inq_chk_del;  // 占쏙옙占쏙옙占쏙옙占쏙옙체크키 (占쏙옙占쏙옙트占쏙옙占쏙옙)
	private String user_email;
	
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public int getInq_chk_del() {
		return inq_chk_del;
	}
	public void setInq_chk_del(int inq_chk_del) {
		this.inq_chk_del = inq_chk_del;
	}
	public String getInq_content() {
		return inq_content;
	}
	public void setInq_content(String inq_content) {
		this.inq_content = inq_content;
	}
	public int getInq_ref() {
		return inq_ref;
	}
	public void setInq_ref(int inq_ref) {
		this.inq_ref = inq_ref;
	}
	public int getInq_re_step() {
		return inq_re_step;
	}
	public void setInq_re_step(int inq_re_step) {
		this.inq_re_step = inq_re_step;
	}
	public int getUser_code() {
		return user_code;
	}
	public void setUser_code(int user_code) {
		this.user_code = user_code;
	}
	
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
