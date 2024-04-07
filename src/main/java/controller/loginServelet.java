package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class loginServelet
 */
//@WebServlet("/loginServelet")
public class loginServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public loginServelet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//void setcotentType(string str) this is method of httpServeletRequest
		//The above method is used to set the response type 
		response.setContentType("text/html");
		String emailId=request.getParameter("t1");
		String mobileNumber=request.getParameter("t2");
		
		int loginStatus=getlogin(emailId,mobileNumber);
		if(loginStatus==1)
		{
			response.sendRedirect("loginSuccess.html");
		}else
		{
			response.sendRedirect("loginfail.html");
		}
		
		
	}
	//close the doPost()
	//we can define our user defined method
	public int getlogin(String userName,String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

	        // Open a connection
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Sample", "root", "Sanket@1");
	        PreparedStatement ps=con.prepareStatement("select email_id,phone_num from CustomerWebTable where email_id=? and phone_num=?");
	        
	        ps.setString(1, userName);
	        ps.setString(2, password);
	        
	        ResultSet rs=ps.executeQuery();
	        if(rs.next()) {
	        	
	        	String un=rs.getString(1);
	        	String pw=rs.getString(2);
	        	if(un.equalsIgnoreCase(userName)&& pw.equals(password))
	        	{
	        		return 1;
	        	}
	        }
		} catch(Exception e)
		{
			System.out.println(e);
		}
		return 0;
	}

	
	

}
