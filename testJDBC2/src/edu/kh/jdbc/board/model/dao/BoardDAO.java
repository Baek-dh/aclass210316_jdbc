package edu.kh.jdbc.board.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.vo.Board;


public class BoardDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private Properties prop = null;
	
	public BoardDAO() { // 기본생성자
		try {
			
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("board-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 게시글 목록 조회 DAO
	 * @param conn
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard(Connection conn) throws Exception {
		
		List<Board> boardList = null;
		
		try {
			String sql = prop.getProperty("selectAllBoard");
			
			// sql 구문에 위치홀더(?)가 없으므로 Statement 객체를 활용할 예정
			stmt = conn.createStatement();
			
			// Statement : Connection을 통해 SQL을 수행하고 결과를 반환 받는 역할을 하는 객체
			rs = stmt.executeQuery(sql);
			
			boardList = new ArrayList<Board>();
			
			while(rs.next()) {
				
				//int boardNo = rs.getInt("BOARD_NO"); // 컬럼명 작성을 권장
				int boardNo = rs.getInt(1); // 조회 결과의 1번째 컬럼 값
				String boardTitle = rs.getString(2);
				Date createDt = rs.getDate(3);
				String memId = rs.getString(4);
				int readCount = rs.getInt(5);
				
				// 얻어온 컬럼값을 객체에 저장한 후 List에 추가
				boardList.add( new Board(boardNo, boardTitle, createDt, memId, readCount)   );
			}
			
		}finally {
			close(rs);
			close(stmt);
		}
		
		return boardList;
	}
	
	
}




