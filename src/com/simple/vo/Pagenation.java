package com.simple.vo;

public class Pagenation {

	private int rowsPerPage;
	private int pagesPerBlock;
	private int pageNo;
	private int totalRows;
	private int totalPages;
	private int totalBlocks;
	private int currentBlock;
	private int beginIndex;
	private int endIndex;
	private int beginPage;
	private int endPage;
	private String keyword;
	
	
	public Pagenation(int rowsPerPage, int pagesPerBlock, int pageNo, int totalRows, String keyword) {
		this.rowsPerPage = rowsPerPage;
		this.pagesPerBlock = pagesPerBlock;
		this.pageNo = pageNo;
		this.totalRows = totalRows;
		this.keyword = keyword;
		init();
	}
	
	private void init() {
		totalPages = (int) Math.ceil((double) totalRows/rowsPerPage);
		
		if (pageNo < 0 || pageNo > totalPages) {
			pageNo = 1;
		}
		beginIndex = (pageNo - 1)*rowsPerPage + 1;
		endIndex = pageNo*rowsPerPage;
		totalBlocks = (int) Math.ceil((double) totalPages/pagesPerBlock);
		currentBlock = (int) Math.ceil((double) pageNo/pagesPerBlock);
		beginPage = (currentBlock - 1)*pagesPerBlock + 1;
		endPage = currentBlock*pagesPerBlock;
		if (currentBlock == totalBlocks) {
			endPage = totalPages;
		}
	}
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public int getTotalBlocks() {
		return totalBlocks;
	}

	public void setTotalBlocks(int totalBlocks) {
		this.totalBlocks = totalBlocks;
	}

	public void setPagesPerBlock(int pagesPerBlock) {
		this.pagesPerBlock = pagesPerBlock;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setCurrentBlock(int currentBlock) {
		this.currentBlock = currentBlock;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getPagesPerBlock() {
		return pagesPerBlock;
	}
	
	public int getCurrentBlock() {
		return currentBlock;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public int getBeginIndex() {
		return beginIndex;
	}
	
	public int getEndIndex() {
		return endIndex;
	}
	
	public int getBeginPage() {
		return beginPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
	@Override
	public String toString() {
		return "Pagenation [rowsPerPage=" + rowsPerPage + ", pagesPerBlock=" + pagesPerBlock + ", pageNo=" + pageNo
				+ ", totalRows=" + totalRows + ", totalPages=" + totalPages + ", totalBlocks=" + totalBlocks
				+ ", currentBlock=" + currentBlock + ", beginIndex=" + beginIndex + ", endIndex=" + endIndex
				+ ", beginPage=" + beginPage + ", endPage=" + endPage + ", keyword=" + keyword + "]";
	}

	
	
	
}