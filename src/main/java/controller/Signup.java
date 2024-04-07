package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Signup
 */
//@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*//Enumeration
		response.setContentType("text/html");
		Enumeration<String>	ens=	request.getParameterNames();
		
		/*
		 * Enumeration is one of the adhoc cursor in collection framework
		 * this interface provides two main methods like
		 * boolean hasMoreElements()
		 * object nextElement()
		 * 
		 */
	/*PrintWriter pw=	response.getWriter();
		while (ens.hasMoreElements()) {
			String parameter_name=ens.nextElement();
			String value=request.getParameter(parameter_name);
			pw.println("<h3><font color ='blue'>"+parameter_name+" :"+value+"</font></h3>");
		}
		
		PrintWriter pw = response.getWriter();
		pw.print("hiiii");
		Enumeration<String> ens= request.getHeaderNames();
		
		while(ens.hasMoreElements()) {
			String headerName= ens.nextElement();
			String headerValue= request.getHeader(headerName);
			pw.println(headerName+"--->"+headerValue+"<br>");
			
		}*/
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("hello");
		
		response.setContentType("text/html");
		String firstName=request.getParameter("t1");
		String lastName=request.getParameter("t2");
		String address=request.getParameter("t3");
		//to read checkbox we need call
		//String[] getparametervalues(string parameterName)
		//:HttpServletRequest interface
		String[] qualifications=request.getParameterValues("t4");
		
		String  gender=request.getParameter("t5");
		String dob=request.getParameter("t6");
		String phone=request.getParameter("t7");
		String email=request.getParameter("t8");
		String na=request.getParameter("t9");
		
		
		
		String qualis=Arrays.toString(qualifications);
		//creating object of PrintWriter
		PrintWriter out=response.getWriter();
		out.println("<h2><font color='green'>Congratulations!!!!!"+firstName+" "+lastName+"</font></h3>");
		out.println("you are from "+ " "+address);
		out.println("your qualification:"+ " "+Arrays.toString(qualifications));
		out.println("your contact details is"+"<br>"+phone+"</br>"+" <br>"+email+"</br>");
		
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("driver loaded");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Sample", "root", "Sanket@1");
		System.out.println("connection done");
		PreparedStatement ps=con.prepareStatement("insert into CustomerWebTable values(?,?,?,?,?,?,?,?,?)");
		ps.setString(1, firstName);
		ps.setString(2, lastName);
		ps.setString(3, qualis);
		ps.setString(4, gender);
		ps.setLong(5, Long.parseLong(phone));
		LocalDate Id=LocalDate.parse(dob);
		
		java.sql.Date dob1= java.sql.Date.valueOf(Id);
		ps.setDate(6, dob1);
		ps.setString(7, email);
		ps.setString(8,na);
		ps.setString(9, address);
		int regStatus =ps.executeUpdate();
		if(regStatus>0) {
			
			out.println("<h1>Registration completed sucessfully!!!<h1>");
			String uname=firstName+" "+lastName;
			request.setAttribute("userKey", uname);
			
			//I want the user name in other servlet
			// i want implement servlet chaining
			/*
			 * 
			 * RequestDispatcher is an interface inside javaX.servelet package
			 * RequestDispactcher is used to dispatch the request from one servlet to
			 * another servlet to another servlet/jsp/Htnl page
			 * 
			 * RequestDispatcher is provides two important method
			 * void include(HttpServletRequest request , HttpServletResponse Response);
			 * ----> incase o the include method the response from the next servlet or second servlet
			 * does not invlude with  be included with first servlet or starter srvlet
			 */
			
			//getting the object of RequestDispatcher object
			RequestDispatcher rd=request.getRequestDispatcher("WelcomeServlet");
			
			rd.include(request, response);
		}
		
		
		
		
		
		
		
		}catch(Exception e)
		
		{
			System.out.println(e);
		}
	}

}

