package com.jh.test.comments;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jh.board.comments.CommentsDAO;
import com.jh.board.comments.CommentsDTO;
import com.jh.util.DBConnector;

public class CommentsDAOTest {
	private CommentsDAO commentsDAO;
	private Connection con;
	public CommentsDAOTest() {
		// TODO Auto-generated constructor stub
		commentsDAO = new CommentsDAO();
	}
	@BeforeClass
	public static void start() {}
	
	@Before
	public void s() {
		try {
			con = DBConnector.getConnect();
			con.setAutoCommit(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void inserttest() throws Exception {
		CommentsDTO commentsDTO = new CommentsDTO();
		commentsDTO.setNum(20);
		commentsDTO.setWriter("JH");
		commentsDTO.setContents("TEST");
		int result = commentsDAO.insert(commentsDTO, con);
		assertEquals(1, result);
	
	}
	
	@After
	public void a() {
		try {
			//con.rollback();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void after() {
		
	}
}
