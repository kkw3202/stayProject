package model.Handler.pagingHandler;

public class pagingHandlerDAO {

	public pagingHandlerDTO getPaging(String pageNum, int count, int pageSize, int pageBlock) {
		
		// pdto 인스턴스
		pagingHandlerDTO pdto = new pagingHandlerDTO();
		
		// Proc 서블릿에서 받아온 pageNum이 null 인 경우(맨 처음에 게시판 열때 혹은 전체보기)
		// pageNum을 문자 1로 정함
		if(pageNum == null || pageNum.isEmpty()) {
			pageNum = "1";
		}
		
		// 현재 페이지 변수 지정
		int currentPage = Integer.parseInt(pageNum);
		// 첫번째 행 변수 담는 변수 공식  (1페이지: (1-1)*10 = 0행부터 / 2페이지: (2-1)*10 = 10행부터)
		int startRow = (currentPage-1)*pageSize;
		// 순번시작 값 변수 공식
		int number = count - startRow;  // 총게시글 수 - 첫번째행 값 
		// 총 25개 글일 때 -> 1페이지 첫글: 25 - 0 = 25번 
		/// 2페이지 첫글: 25 - 10 = 15번
		
		// 총 페이지 개수 변수 설정
		int pageCount = 0;
		// 페이징 시작값 변수 설정
		int startPage = 1;
		// 페이징 마지막 값 변수 설정
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