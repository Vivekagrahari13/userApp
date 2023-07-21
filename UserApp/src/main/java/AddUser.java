import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		PrintWriter out = null;
		try {
			out = arg1.getWriter();
			String email = arg0.getParameter("email");
			String name = arg0.getParameter("name");
			String phone = arg0.getParameter("phone");
			int age = Integer.parseInt(arg0.getParameter("age"));
			String gender = arg0.getParameter("gender");
			String address = arg0.getParameter("address");

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userapp", "root", "Agrahari$@13");
			PreparedStatement p = con.prepareStatement(
					"insert into users (email, name, phone, age, gender, address) values(?,?,?,?,?,?)");
			ServletContext ctx = getServletContext();
			String appName = ctx.getInitParameter("appName");
			out.print("<html>");
			out.print("<head>");
			out.print("<title>User App</title>");
			out.print("</head>");
			out.print("<body>");
			out.print("<h1>" + appName + "</h1>");
			out.print("<hr>");

			try {
				p.setString(1, email);
				p.setString(2, name);
				p.setString(3, phone);
				p.setInt(4, age);
				p.setString(5, gender);
				p.setString(6, address);
				p.executeUpdate();
				out.print("User Data Added Successfully!!");
			} catch (SQLIntegrityConstraintViolationException e) {
				out.print("<p style='color:red;'> User Already Exist! </p>");
			}
			out.print("</body>");
			out.print("</html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.close();
	}
}
