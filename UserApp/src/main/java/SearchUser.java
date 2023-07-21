import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchUser")
public class SearchUser extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			String name = req.getParameter("name");

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userapp", "root", "Agrahari$@13");
			PreparedStatement p = con.prepareStatement("select *from users where name like ?");
			p.setString(1, "%" + name + "%");
			ResultSet rs = p.executeQuery();
			out.print("<!DOCTYPE html>");
			out.print("<html>");
			out.print("<head>");
			out.print("<title>User App</title>");
			out.print("</head>");
			out.print("<body>");
			ServletContext ctx = getServletContext();
			String appName = ctx.getInitParameter("appName");
			out.print("<h1 style='text-align:center; background-color:grey;'>" + appName + "</h1>");
			out.print("<hr>");
			boolean flag = true;
			while (rs.next()) {
				flag = false;
				String n = rs.getString("name");
				String e = rs.getString("email");
				String phone = rs.getString("phone");
				int a = rs.getInt("age");
				String gen = rs.getString("gender");
				String add = rs.getString("address");
				out.print(
						"<div style='background-color:orange; border:5px solid black; margin: 20px; padding:10px; '>");
				out.print("<p>Name: " + n + "</p>");
				out.print("<p>Email: " + e + "</p>");
				out.print("<p>Phone Number: " + phone + "</p>");
				out.print("<p>Age: " + a + "</p>");
				out.print("<p>Gender: " + gen + "</p>");
				out.print("<p>Address: " + add + "</p>");
				out.print("</div>");
			}
			if (flag) {
				out.print("<h2 style='color:red; text-decoration-line:line-through;'>No Such User Exists!</h2>");
			}
			out.print("</body>");
			out.print("</html>");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
