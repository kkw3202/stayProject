package model.User;

public class MyBookingDTO {
	// 예약관련 
	private int book_code;  // 예약번호
	private String book_number;  // 예약번호
	private String book_name;  // 예약자명
	private int user_code;  // 회원번호
	private int room_code;  // 객실번호
	private String book_checkin_date;  // (예정) 체크인 날짜
	private String book_checkout_date;  // (예정) 체크아웃 날짜
	private String book_date;  // 예매결제일
	private String book_cancel_date;  // 예매취소일
	private int book_chk_status;  // 예약상태체크(0:예약완료 / 1: 예약취소)

	// 객실관련
	private String room_name;  // 객실명
	private int room_price;  // 객실가격

	// 숙소관련
	private int acc_code;  // 숙소코드
	private String acc_type;  // 숙소유형
	private String acc_name;  // 숙소명
	private String acc_address;  // 숙소주소
	private String acc_checkinTime;  // 숙소입실시간
	private String acc_checkoutTime;  // 숙소퇴실시간
	private String acc_img;  // 숙소 이미지
	
	// 리뷰관련
	private int rev_code;  // 리뷰 코드 (작성햇는지 유무 파악)
	private String rev_created_datetime;  // 리뷰 작성시간
	private int rev_chk_del;  // 리뷰 삭제했는지 유무체크
	
	// 회원관련
	private String user_email;  // 회원 이메일
	
	// 리뷰 작성 가능한지 유무 체크 변수 (퇴실예정체크아웃날짜) 보다 더 뒤에만 리뷰 작성가능
	private int can_review;  // 리뷰 작성 가능 변수

	public int getBook_code() {
		return book_code;
	}

	public void setBook_code(int book_code) {
		this.book_code = book_code;
	}

	public String getBook_number() {
		return book_number;
	}

	public void setBook_number(String book_number) {
		this.book_number = book_number;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public int getUser_code() {
		return user_code;
	}

	public void setUser_code(int user_code) {
		this.user_code = user_code;
	}

	public int getRoom_code() {
		return room_code;
	}

	public void setRoom_code(int room_code) {
		this.room_code = room_code;
	}

	public String getBook_checkin_date() {
		return book_checkin_date;
	}

	public void setBook_checkin_date(String book_checkin_date) {
		this.book_checkin_date = book_checkin_date;
	}

	public String getBook_checkout_date() {
		return book_checkout_date;
	}

	public void setBook_checkout_date(String book_checkout_date) {
		this.book_checkout_date = book_checkout_date;
	}

	public String getBook_date() {
		return book_date;
	}

	public void setBook_date(String book_date) {
		this.book_date = book_date;
	}

	public String getBook_cancel_date() {
		return book_cancel_date;
	}

	public void setBook_cancel_date(String book_cancel_date) {
		this.book_cancel_date = book_cancel_date;
	}

	public int getBook_chk_status() {
		return book_chk_status;
	}

	public void setBook_chk_status(int book_chk_status) {
		this.book_chk_status = book_chk_status;
	}

	public int getAcc_code() {
		return acc_code;
	}

	public void setAcc_code(int acc_code) {
		this.acc_code = acc_code;
	}

	public String getAcc_type() {
		return acc_type;
	}

	public void setAcc_type(String acc_type) {
		this.acc_type = acc_type;
	}

	public String getAcc_name() {
		return acc_name;
	}

	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}

	public String getAcc_address() {
		return acc_address;
	}

	public void setAcc_address(String acc_address) {
		this.acc_address = acc_address;
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

	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public int getRoom_price() {
		return room_price;
	}

	public void setRoom_price(int room_price) {
		this.room_price = room_price;
	}

	public int getRev_code() {
		return rev_code;
	}

	public void setRev_code(int rev_code) {
		this.rev_code = rev_code;
	}

	public String getRev_created_datetime() {
		return rev_created_datetime;
	}

	public void setRev_created_datetime(String rev_created_datetime) {
		this.rev_created_datetime = rev_created_datetime;
	}

	public int getRev_chk_del() {
		return rev_chk_del;
	}

	public void setRev_chk_del(int rev_chk_del) {
		this.rev_chk_del = rev_chk_del;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public int getCan_review() {
		return can_review;
	}

	public void setCan_review(int can_review) {
		this.can_review = can_review;
	}
	
	
}
