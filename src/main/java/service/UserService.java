package service;

import DAO.UserDAO;
import com.google.gson.Gson;
import model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserService {
    UserDAO userDAO = new UserDAO();


    public void getAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        List<User> userList = userDAO.getUsers();
        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        printWriter.write(gson.toJson(userList));
    }
    public void saveUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = request.getReader();
        User user = gson.fromJson(bufferedReader,User.class);
        userDAO.saveUser(user);
        PrintWriter out = response.getWriter();
        out.write(String.valueOf(user));
    }
}
