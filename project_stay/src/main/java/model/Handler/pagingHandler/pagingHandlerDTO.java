package model.Handler.pagingHandler;

public class pagingHandlerDTO {

		
		private int count;
		private int pageSize;
		private int pageNum;
		private int number;
		
		private int startRow;
		private int currentPage;
		private int pageCount; 
		private int pageBlock; 
		private int startPage; 
		private int endPage;
		
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
		public int getPageNum() {
			return pageNum;
		}
		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
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
