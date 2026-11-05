package model.Handler.pagingHandler;

public class pagingHandlerDAO {

	public pagingHandlerDTO getPaging(String pageNum, int count, int pageSize, int pageBlock) {
		
		pagingHandlerDTO pdto = new pagingHandlerDTO();
		
		if(pageNum == null || pageNum.isEmpty()) {
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage-1)*pageSize;
		int number = count - startRow;
		
		int pageCount = 0;
		int startPage = 1;
		int endPage = 0;
		
		if(count>0) {
			
			pageCount = count/pageSize + (count % pageSize == 0? 0:1);
			
			if(currentPage % pageBlock !=0) {
				startPage = (currentPage/pageBlock)*pageBlock + 1;
			}else {
				startPage = ((currentPage/pageBlock)-1)*pageBlock + 1;
			}
			
			// 첫 화면에 [1],[2],[3],[4],[5]의 5를, [6],[7],[8],[9],[10]의 10을 담는 변수
			// 5    =     1     +  5      - 1
			endPage = startPage + pageBlock-1 ;
			
			if(endPage > pageCount) {
				endPage = pageCount;
			}
		}
		
		// 계산된 값들을 DTO에 모두 담기
		pdto.setPageSize(pageSize);
		pdto.setCount(count);
		pdto.setCurrentPage(currentPage);
		pdto.setStartRow(startRow);
		pdto.setNumber(number);
		pdto.setPageCount(pageCount);
		pdto.setPageBlock(pageBlock);
		pdto.setStartPage(startPage);
		pdto.setEndPage(endPage);
		
		return pdto;
	}
}