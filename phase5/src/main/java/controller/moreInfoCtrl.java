package controller;

import domain.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import org.json.*;

public class moreInfoCtrl extends HttpServlet {

    static String extractPostRequestBody(HttpServletRequest request) throws IOException {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
        return "";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out =new PrintWriter (new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String res = "default", user = "";


        JSONObject json = new JSONObject((String)request.getAttribute("body"));
        String house_id = json.getString("house_id");

        user = (String) request.getAttribute("user");

        if(user == null)
            user = "";

        if(InfoBank.getUserByUsername(user) != null) {
            //  request.setAttribute("user", InfoBank.getUserByUsername(request.getParameter("user")));
        }

        else if( user.equals("") == false)  {
            //request.setAttribute("HACK", "کاربر مورد نظر در سیستم موجود نیست")
            response.setStatus(400);
            System.out.println("{\"result\":\"bad request\",\"message\":\"invalid user\"}");
            out.println("{\"result\":\"bad request\",\"message\":\"invalid user\"}");
            return;
        }

        String HouseID;

        if(request.getAttribute("house_id") != null)
            HouseID = (String)request.getAttribute("house_id");
        else
            HouseID = house_id;
        JSONObject obj;

        try{
            if(InfoBank.estate.getHouseById(HouseID) != null) {

                response.setStatus(200);
                obj = Tools.IndHouse2JSON(InfoBank.estate.getHouseById(HouseID));

                Integer isPaid = 0;
                if (user.equals("") == false && InfoBank.getUserByUsername(user).searchHouse(InfoBank.estate.getHouseById(HouseID)))
                    isPaid =1 ;

                obj.put("result","ok");

                obj.getJSONObject("data").put("isPaid",isPaid);

                System.out.println(obj.toString());
                out.println(obj.toString());
                /* request.setAttribute("house", InfoBank.estate.getHouseById(HouseID));*/
            }
            else {
                /*    request.setAttribute("HACK", "خانه ای با شناسه ی داده شده موجود نمی باشد");*/
                System.out.println("{\"result\" : \"I'm here\"}");
                out.println("{\"result\" : \"I'm here\"}");
            }


        }catch (Exception e){
            System.out.println(e.getClass().getName() + " : " + e.getMessage());
            out.println("{\"result\" : \"" + res +"\"}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}