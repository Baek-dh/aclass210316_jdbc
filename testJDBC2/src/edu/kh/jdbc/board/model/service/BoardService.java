package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.vo.Board;

public class BoardService {

	private BoardDAO dao = new BoardDAO();

	/**
	 * 게시글 목록 조회 Service
	 * 
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard() throws Exception {
		Connection conn = getConnection();

		List<Board> boardList = dao.selectAllBoard(conn);

		// select를 수행했기 때문에 별도에 트랜잭션 처리 없이 바로 conn 반환
		close(conn);

		return boardList;
	}

	/**
	 * 게시글 상세 조회 Service
	 * 
	 * @param in
	 * @return bo
	 * @throws Exception
	 */
	public Board selectBoard(int in) throws Exception {
		Connection conn = getConnection();
		Board bo = dao.selectBoard(conn, in);
		close(conn);
		return bo;
	}

	/**
	 * 게시글 삽입 Service
	 * 
	 * @param input
	 * @param input2
	 * @return result
	 * @throws Exception
	 */
	public int insertBoard(String input, String input2) throws Exception {
		Connection conn = getConnection();
		int result = dao.insertBoard(conn, input, input2);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	/**
	 * 게시글 삭제 Service
	 * 
	 * @param boardNo
	 * @return result
	 */
	public int deleteBoard(int boardNo) throws Exception {

		Connection conn = getConnection();

		int result = dao.deleteBoard(conn, boardNo);

		if (result > 0)	commit(conn);
		else 			rollback(conn);

		close(conn);

		return result;
	}

}
