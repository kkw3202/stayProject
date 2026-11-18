package model.Handler.pagingHandler;

public class pagingHandlerDAO {

	public pagingHandlerDTO getPaging(String pageNum, int count, int pageSize, int pageBlock) {
		
		// pdto 인스턴스
		pagingHandlerDTO pdto = new pagingHandlerDTO();
		
		// Proc 페이지에서 받아온 pageNum이 null 일 경우(첫 처음 게시판 접속 혹은 전체목록)
		// pageNum이 없으면 1로 설정
		if(pageNum == null || pageNum.isEmpty()) {
			pageNum = "1";
		}
		
		// 현재 페이지의 번호 설정
		int currentPage = Integer.parseInt(pageNum);
		// 첫번째 줄 번호 구하는 공식 설정  (1페이지: (1-1)*10 = 0번째 / 2페이지: (2-1)*10 = 10번째)
		int startRow = (currentPage-1)*pageSize;
		// 페이지별 글 번호 설정
		int number = count - startRow;  // 총게시글 수 - 첫번째줄 번 
		// 총 25개 글인 경우 -> 1페이지 첫: 25 - 0 = 25번 
		/// 2페이지 첫: 25 - 10 = 15번
		
		// 총 페이지의 개수 계산 설정
		int pageCount = 0;
		// 페이징 시작번호 설정
		int startPage = 1;
		// 페이징 마지막 번 번호 설정
		int endPage = 0;
		
		if(count>0) {
			pageCount = count/pageSize + (count % pageSize == 0? 0:1);
			if(currentPage % pageBlock !=0) {
				startPage = (currentPage/pageBlock)*pageBlock + 1;
			}else {
				startPage = ((currentPage/pageBlock)-1)*pageBlock + 1;
			}
			// 첫 화면에 [1],[2],[3],[4],[5]는 5개, [6],[7],[8],[9],[10]은 10개 나열 설정
			// 5    =     1     +  5      - 1
			endPage = startPage + pageBlock-1 ;
			
			if(endPage > pageCount) {
				endPage = pageCount;
			}
		}
		
		// 구한 값들을 DTO에 값 담기
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