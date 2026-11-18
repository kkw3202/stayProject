package model.Handler.pagingHandler;

public class pagingHandlerDTO {
		// 게시글 총 개수 관련
		private int count;  // 총 게시글 수  (총 225개 게시글) 
		private int pageSize;  // 한 화면에서 보여줄 게시글의 개수 제한(우리 룰 : 10개)
		private int startRow;  // 한 화면에서 보여줄 첫번째에 위치한 글 번호 (endRow를 사용하지 않고 =>  startRow + pageSize로 계산예정) 
		private int number;  // 페이지별 글 번호값 (총 글 225에서 1페이지일 때 첫 글번호는 225번)
		
		// 페이징 관련 정보
		private int currentPage;  // pageNum 대체. 현재 화면에 출력되고 있는 페이징 넘버 값 (페이징의 첫번째줄에서 [1][2][3][4][5] 중 3번째일 경우, 3이 됨)
		private int pageCount;  // 전체 게시글의 총 보여주기 위해서 필요한 총 페이지의 개수(마지막 페이지 번호) => 총 225개 / 한 페이지당 10개씩 = 총 23페이지
		private int pageBlock;  // 페이징 단위 (우리는 5개씩 구성)
		private int startPage;  // 현재 페이지에 있는 페이징의 시작값 ([1][2][3][4][5] => 첫번째는 1 / 두번째 페이지는 6 / 세번째 페이지는 11)
		private int endPage;  // 현재 페이지에 있는 페이징의 마지막값 ([1][2][3][4][5] => 첫번째는 5 / 다음은 페이지 10 / 세번째 페이지는 15)
		
		public int getCurrentPage() {
			return currentPage;
		}
		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public int getStartRow() {
			return startRow;
		}
		public void setStartRow(int startRow) {
			this.startRow = startRow;
		}
		public int getPageCount() {
			return pageCount;
		}
		public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
		}
		public int getPageBlock() {
			return pageBlock;
		}
		public void setPageBlock(int pageBlock) {
			this.pageBlock = pageBlock;
		}
		public int getStartPage() {
			return startPage;
		}
		public void setStartPage(int startPage) {
			this.startPage = startPage;
		}
		public int getEndPage() {
			return endPage;
		}
		public void setEndPage(int endPage) {
			this.endPage = endPage;
		}
}