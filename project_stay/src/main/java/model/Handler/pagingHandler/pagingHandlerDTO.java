package model.Handler.pagingHandler;

public class pagingHandlerDTO {
		// 게시글 행 관련 변수
		private int count;  // 총 게시글 수  (예 225개 게시글) 
		private int pageSize;  // 한 화면에서 보여질 게시글의 개수를 지정(우리 조 : 10개)
		private int startRow;  // 한 화면에서 행의 첫번째행 변수를 담는 변수 (endRow는 사용하지 않음 =>  startRow + pageSize로 계산예정) 
		private int number;  // 가상 순번 시작값 (총 글 225개면 1페이지 맨 윗글은 225번)
		
		// 페이징 관련 변수
		private int currentPage;  // pageNum 대체. 현재 화면에 보여지고 있는 페이징 넘버 값 (페이징의 첫페이지에서 [1][2][3][4][5] 중 3번째인 경우, 3이 됨)
		private int pageCount;  // 전체 게시글을 다 보여주기 위해 필요한 총 페이지 개수(마지막 페이지 번호) => 글 225개 / 한 페이지 10개씩 = 총 23페이지
		private int pageBlock;  // 페이징 구간 (우리는 5개로 정함)
		private int startPage;  // 현재 보여지고 있는 페이징의 시작값 ([1][2][3][4][5] => 첫페이지 1 / 두번째 페이지 6 / 세번째 페이지 11)
		private int endPage;  // 현재 보여지고 있는 페이징의 마지막값 ([1][2][3][4][5] => 첫페이지 5 / 마지막 페이지 10 / 세번째 페이지 15)
		
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
