package controller;

import domain.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.*;

public class moreInfoCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(InfoBank.getUserByUsername(request.getParameter("user")) != null) {

            request.setAttribute("user", InfoBank.getUserByUsername(request.getParameter("user")));

        }
        else
            request.setAttribute("HACK", "کاربر مورد نظر در سیستم موجود نیست");


        String HouseID;

        if(request.getAttribute("house_id") != null)
            HouseID = (String)request.getAttribute("house_id");
        else
            HouseID = request.getParameter("house_id");

        try{
            if(InfoBank.estate.getHouseById(HouseID) != null)
                request.setAttribute("house", InfoBank.estate.getHouseById(HouseID));

            else if(InfoBank.estate.getRealEstateHouseById(HouseID) != null){
                JSONObject obj = new JSONObject(Tools.GETreq(
                        "http://acm.ut.ac.ir/khaneBeDoosh/house/" +HouseID));

                JSONObject data = obj.getJSONObject("data");

                String id = data.getString("id");
                int area = data.getInt("area");
                String buildType = data.getString("buildingType");
                String address = data.getString("address");
                int deal = data.getInt("dealType");
                String phone = data.getString("phone");
                String desc = data.getString("description");
                String expire = data.getString("expireTime");
                String image = data.getString("imageURL");
                JSONObject price = data.getJSONObject("price");
                int sellP = 0;
                int rentP = 0;
                int baseP = 0;

                if (price.has("sellPrice")) {
                    sellP = price.getInt("sellPrice");
                } else {
                    rentP = price.getInt("rentPrice");
                    baseP = price.getInt("basePrice");
                }

                request.setAttribute("house", new House(id, area, buildType, address, image
                        , deal, baseP, rentP, sellP, phone, desc, expire, InfoBank.getUsers().get(1)));

            }else {
                request.setAttribute("HACK", "خانه ای با شناسه ی داده شده موجود نمی باشد");

            }


            request.getRequestDispatcher("moreInfo.jsp").forward(request, response);

        }catch (Exception e){
            request.setAttribute("ERROR"," sent from moreInfoCtrl.java");
            request.getRequestDispatcher("moreInfo.jsp").forward(request, response);
        }
    }
}
