package model.WishList;

public class WishListDTO {
	private int wish_code;  // 찜코드
	private int user_code;  // 회원코드
	private int acc_code;  // 숙소코드
	private String user_email; // 유저 이메일
	private String acc_name;  // 숙소명
	private String acc_type;  // 숙소타입
	private String acc_address;  // 숙소주소
	private String acc_facility;  // 부대시설
	private String acc_checkinTime;  // 체크인 시간
	private String acc_checkoutTime;  // 체크아웃시간
	private int acc_chk_del;  // 숙소 소프트삭제 여부체크키
	private String acc_img;  // 숙소이미지
	
	
	public int getAcc_chk_del() {
		return acc_chk_del;
	}
	public void setAcc_chk_del(int acc_chk_del) {
		this.acc_chk_del = acc_chk_del;
	}
	public int getWish_code() {
		return wish_code;
	}
	public void setWish_code(int wish_code) {
		this.wish_code = wish_code;
	}
	public int getUser_code() {
		return user_code;
	}
	public void setUser_code(int user_code) {
		this.user_code = user_code;
	}
	public int getAcc_code() {
		return acc_code;
	}
	public void setAcc_code(int acc_code) {
		this.acc_code = acc_code;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getAcc_name() {
		return acc_name;
	}
	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}
	public String getAcc_type() {
		return acc_type;
	}
	public void setAcc_type(String acc_type) {
		this.acc_type = acc_type;
	}
	public String getAcc_address() {
		return acc_address;
	}
	public void setAcc_address(String acc_address) {
		this.acc_address = acc_address;
	}
	public String getAcc_facility() {
		return acc_facility;
	}
	public void setAcc_facility(String acc_facility) {
		this.acc_facility = acc_facility;
	}

	public String getAcc_checkinTime() {
		return acc_checkinTime;
	}
	public void setAcc_checkinTime(String acc_checkinTime) {
		this.acc_checkinTime = acc_checkinTime;
	}
	public String getAcc_checkoutTime() {
		return acc_checkoutTime;
	}
	public void setAcc_checkoutTime(String acc_checkoutTime) {
		this.acc_checkoutTime = acc_checkoutTime;
	}
	public String getAcc_img() {
		return acc_img;
	}
	public void setAcc_img(String acc_img) {
		this.acc_img = acc_img;
	}
	
	
}