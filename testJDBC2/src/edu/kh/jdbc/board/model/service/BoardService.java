package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.vo.Board;

public class BoardService {

	private BoardDAO dao = new BoardDAO();

	
	
	/** 게시글 목록 조회 Service
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard() throws Exception{
		Connection conn = getConnection();
		
		List<Board> boardList = dao.selectAllBoard(conn);
		
		// select를 수행했기 때문에 별도에 트랜잭션 처리 없이 바로 conn 반환
		close(conn); 
		
		return boardList;
	}
	
	
	
}
