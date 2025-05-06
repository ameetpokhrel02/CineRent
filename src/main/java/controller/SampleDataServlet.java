package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;
import DAO.MovieDAO;

@WebServlet("/sample-data")
public class SampleDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Add Sample Movie Data</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
        out.println("h1, h2 { color: #333; }");
        out.println(".error { color: red; }");
        out.println(".success { color: green; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Add Sample Movie Data</h1>");
        
        MovieDAO dao = new MovieDAO();
        
        try {
            // Check if movie table already has data
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cinerent?useSSL=false", "root", "");
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM movie");
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                out.println("<p>Movie table already has " + rs.getInt(1) + " records.</p>");
                out.println("<p>Do you want to add more sample data?</p>");
                out.println("<form method='post'>");
                out.println("<input type='submit' value='Add Sample Data'>");
                out.println("</form>");
            } else {
                out.println("<p>Movie table is empty. Adding sample data...</p>");
                addSampleData(dao, out);
            }
            
            conn.close();
        } catch (Exception e) {
            out.println("<p class='error'>Error: " + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
            
            out.println("<p>Attempting to add sample data anyway...</p>");
            addSampleData(dao, out);
        }
        
        out.println("<p><a href='diagnostic'>Go to Diagnostic Page</a></p>");
        out.println("<p><a href='home.jsp'>Go to Home Page</a></p>");
        out.println("</body>");
        out.println("</html>");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Add Sample Movie Data</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
        out.println("h1, h2 { color: #333; }");
        out.println(".error { color: red; }");
        out.println(".success { color: green; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Add Sample Movie Data</h1>");
        
        MovieDAO dao = new MovieDAO();
        addSampleData(dao, out);
        
        out.println("<p><a href='diagnostic'>Go to Diagnostic Page</a></p>");
        out.println("<p><a href='home.jsp'>Go to Home Page</a></p>");
        out.println("</body>");
        out.println("</html>");
    }
    
    private void addSampleData(MovieDAO dao, PrintWriter out) {
        try {
            // Sample movie 1
            Movie movie1 = new Movie(
                "Avengers: Endgame",
                "Action, Adventure, Sci-Fi",
                2019,
                8.4f,
                "12.99",
                "181 min",
                "https://www.youtube.com/embed/TcMBFSGVi1c"
            );
            movie1.setOverview("After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.");
            movie1.setPosterPath("https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg");
            
            // Sample movie 2
            Movie movie2 = new Movie(
                "The Dark Knight",
                "Action, Crime, Drama",
                2008,
                9.0f,
                "9.99",
                "152 min",
                "https://www.youtube.com/embed/EXeTwQWrcwY"
            );
            movie2.setOverview("When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.");
            movie2.setPosterPath("https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg");
            
            // Sample movie 3
            Movie movie3 = new Movie(
                "Inception",
                "Action, Adventure, Sci-Fi",
                2010,
                8.8f,
                "10.99",
                "148 min",
                "https://www.youtube.com/embed/YoHD9XEInc0"
            );
            movie3.setOverview("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.");
            movie3.setPosterPath("https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg");
            
            // Add movies to database
            boolean success1 = dao.addMovie(movie1);
            boolean success2 = dao.addMovie(movie2);
            boolean success3 = dao.addMovie(movie3);
            
            if (success1 && success2 && success3) {
                out.println("<p class='success'>Successfully added sample movies to the database!</p>");
            } else {
                out.println("<p class='error'>Failed to add some sample movies.</p>");
                out.println("<p>Movie 1: " + (success1 ? "Success" : "Failed") + "</p>");
                out.println("<p>Movie 2: " + (success2 ? "Success" : "Failed") + "</p>");
                out.println("<p>Movie 3: " + (success3 ? "Success" : "Failed") + "</p>");
            }
        } catch (Exception e) {
            out.println("<p class='error'>Error adding sample data: " + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
    }
}
