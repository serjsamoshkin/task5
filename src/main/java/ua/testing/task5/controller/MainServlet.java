package ua.testing.task5.controller;

import ua.testing.task5.dataBase.UsersBase;
import ua.testing.task5.dataBase.util.DbInitializer;
import ua.testing.task5.model.entity.authentication.User;
import ua.testing.task5.model.entity.authentication.UserIsNotUnique;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainServlet",
        urlPatterns = {"/servlet"})
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Hello from servlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UsersBase usersBase = new UsersBase();

        DbInitializer initializer = new DbInitializer();
        initializer.setUserDbDemoData(usersBase);

        String userName = request.getParameter("name");
        String userPassword = request.getParameter("password");


        User user = new User();
        user.setUserName(userName);
        user.setPassword(userPassword);

        try {
            usersBase.setData(user);
        }catch (UserIsNotUnique e){
            request.setAttribute("incorrect_name", "Пользователь с таким именем уже зарегестрирован в системе");
            request.setAttribute("name_r", userName);
            request.getServletContext().getRequestDispatcher("/").forward(request, response);
        }

    }
}
