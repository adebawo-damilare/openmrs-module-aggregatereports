/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.dataquality.fragment.controller;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.openmrs.Encounter;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.openmrs.api.UserService;
import org.openmrs.module.dataquality.Misc;
import org.openmrs.module.dataquality.OTZPatient;
import org.openmrs.module.dataquality.api.DataqualityService;
import org.openmrs.module.dataquality.api.dao.ClinicalDaoHelper;
import org.openmrs.module.dataquality.api.dao.Database;
import org.openmrs.module.dataquality.api.dao.OTZDao;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lordmaul
 */

public class OtzFragmentController {
	
	OTZDao otzDao = new OTZDao();
	
	public void controller(FragmentModel model, @SpringBean("userService") UserService service) {
		
		try {
			
			/*URL url = this.getClass().getResource("otz_info.json");
			//System.out.println(url.getPath());
			File f = new File(url.getPath());
			JSONObject obj = new JSONObject(FileUtils.readFileToString(f));
			
			model.addAttribute("otz_info", obj);*/
			
			model.addAttribute("testing", "test");
			model.addAttribute("title", "OTZ");
			Database.initConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String sayHello() {
		return "Hello";
	}
	
	public String getAllEnrolledInOTZ(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            String ageType = request.getParameter("ageType");
            //System.out.println(ageType+"in fragmentController");
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            
            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;
           
            
            List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
            ////System.out.println("TotalSize "+allPatients.size());
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();
    }
	
	public String getAllFullDisc(HttpServletRequest request, HttpServletResponse response) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            //Database.initConnection();

            //System.out.println("start date time"+startDateTime);
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            
            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;
          
            
            List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZFullDisclosure(startDate, endDate);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithScheduledPickup6MonthsBefore(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             
            String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            DateTime sixMonthsAgoB = startDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
            String sixMonthsB = sixMonthsAgoB.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithScheduledPickup6MonthsBefore(startDate, endDate, sixMonthsB);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWhoKeptScheduledPickup6MonthsBefore(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWhoKeptScheduledPickup6MonthsBefore(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithGoodAdhScore6MonthsBefore(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            DateTime sixMonthsAgoB = startDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
            String sixMonthsB = sixMonthsAgoB.toString("yyyy'-'MM'-'dd");

            
            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithGoodAdhScore6MonthsBefore(startDate, endDate, sixMonthsB);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVL12MonthsBefore(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBefore(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVL12MonthsBeforeAndBelow200(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBeforeAndBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVL12MonthsBeforeAndBtw200AND1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBeforeAndBtw200AND1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVL6MonthsBefore(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBefore(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVL6MonthsBeforeAndBelow200(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBeforeAndBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVL6MonthsBeforeAndBtw200AND1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBeforeAndBtw200AND1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEligibleForMonthZeroVL(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEligibleForMonthZeroVL(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEligibleForMonthZeroVLWithSampleCollectedAtEnrollment(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEligibleForMonthZeroVLWithSampleCollectedAtEnrollment(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalWithBaseLineVLBelow1000AndMonthZeroVlBelow200(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove200(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithScheduledPickupAfter(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithScheduledPickupAfter(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWhoKeptScheduledPickupAfter(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWhoKeptScheduledPickupAfter(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithGoodAdhScoreAfter(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithGoodAdhScoreAfter(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledEligibleForVL(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVL(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledEligibleForVLWithSampleTaken(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTaken(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledEligibleForVLWithSampleTakenAndResult(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTakenAndResult(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledEligibleForVLWithSampleTakenAndResultBelow200(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTakenAndResultBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove200Below1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove200Below1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVLPast12Months(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12Months(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVLPast12MonthsResultBelow200(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVLPast12MonthsResultAbove200Below1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove200Below1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVLPast12MonthsResultAbove1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
            }
                }


            Map<String, String> dataMap = new HashMap<>();

            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVLPast12MonthsResultAbove1000CompletedEAC(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000CompletedEAC(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
            }
                }


            Map<String, String> dataMap = new HashMap<>();

            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVl(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVl(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
            }
                }


            Map<String, String> dataMap = new HashMap<>();

            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
        DateTime sixMonthsAgo = endDateTime.minusMonths(6);
        //Database.initConnection();

        
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

        int male0To10= 0;
        int male10To14=0;
        int male15To19=0;
        int male20To24=0; 
        int maleabove24=0;
        
        int female0To10= 0;
        int female10To14=0;
        int female15To19=0;
        int female20To24=0; 
        int femaleabove24=0;

        List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000(startDate, endDate, sixMonths);
        for(int i=0; i<allPatients.size(); i++)
        {
            if("curra".equals(ageType)){
                if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                {
                	if(allPatients.get(i).getCage() <10)
                	{
                		male0To10++;
                	}
                	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                    {
                        male10To14++;
                    }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                    {
                        male15To19++;
                    }
                    else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                    {
                        male20To24++;
                    }
                    else if(allPatients.get(i).getCage() > 24)
                    {
                        maleabove24++;
                    }
                }
                else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                {
                	if(allPatients.get(i).getCage() <10)
                	{
                		female0To10++;
                	}
                	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                    {
                        female10To14++;
                    }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                    {
                        female15To19++;
                    }
                    else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                    {
                        female20To24++;
                    }
                    else if(allPatients.get(i).getCage() > 24)
                    {
                       femaleabove24++;
                    }
                }
            }else{
                if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                {
                	if(allPatients.get(i).getAge() <10)
                	{
                		male0To10++;
                	}
                	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                    {
                        male10To14++;
                    }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                    {
                        male15To19++;
                    }
                    else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                    {
                        male20To24++;
                    }
                    else if(allPatients.get(i).getAge() > 24)
                    {
                        maleabove24++;
                    }
                }
                else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                {
                	if(allPatients.get(i).getAge() <10)
                	{
                		female0To10++;
                	}
                	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                    {
                        female10To14++;
                    }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                    {
                        female15To19++;
                    }
                    else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                    {
                        female20To24++;
                    }
                    else if(allPatients.get(i).getAge() > 24)
                    {
                       femaleabove24++;
                    }
                }   
            }
         }
        
        
        Map<String, String> dataMap = new HashMap<>();
       
        dataMap.put("male0To10",  male0To10+"");
        dataMap.put("male10To14",  male10To14+"");
        dataMap.put("male15To19",  male15To19+"");
        dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
        dataMap.put("maleabove24",  maleabove24+"");
        dataMap.put("female0To10",  female0To10+"");
        dataMap.put("female10To14",  female10To14+"");
        dataMap.put("female15To19",  female15To19+"");
        dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
        dataMap.put("femaleabove24",  femaleabove24+"");
        //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
        return new JSONObject(dataMap).toString();

}
	
	public String getTotalEnrolledWithSwitchTo2ndLine(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
        DateTime sixMonthsAgo = endDateTime.minusMonths(6);
        //Database.initConnection();

        
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

        int male0To10= 0;
        int male10To14=0;
        int male15To19=0;
        int male20To24=0; 
        int maleabove24=0;
        
        int female0To10= 0;
        int female10To14=0;
        int female15To19=0;
        int female20To24=0; 
        int femaleabove24=0;

        List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithSwitchTo2ndLine(startDate, endDate, sixMonths);
        for(int i=0; i<allPatients.size(); i++)
        {
            if("curra".equals(ageType)){
                if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                {
                	if(allPatients.get(i).getCage() <10)
                	{
                		male0To10++;
                	}
                	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                    {
                        male10To14++;
                    }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                    {
                        male15To19++;
                    }
                    else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                    {
                        male20To24++;
                    }
                    else if(allPatients.get(i).getCage() > 24)
                    {
                        maleabove24++;
                    }
                }
                else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                {
                	if(allPatients.get(i).getCage() <10)
                	{
                		female0To10++;
                	}
                	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                    {
                        female10To14++;
                    }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                    {
                        female15To19++;
                    }
                    else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                    {
                        female20To24++;
                    }
                    else if(allPatients.get(i).getCage() > 24)
                    {
                       femaleabove24++;
                    }
                }
            }else{
                if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                {
                	if(allPatients.get(i).getAge() <10)
                	{
                		male0To10++;
                	}
                	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                    {
                        male10To14++;
                    }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                    {
                        male15To19++;
                    }
                    else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                    {
                        male20To24++;
                    }
                    else if(allPatients.get(i).getAge() > 24)
                    {
                        maleabove24++;
                    }
                }
                else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                {
                	if(allPatients.get(i).getAge() <10)
                	{
                		female0To10++;
                	}
                	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                    {
                        female10To14++;
                    }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                    {
                        female15To19++;
                    }
                    else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                    {
                        female20To24++;
                    }
                    else if(allPatients.get(i).getAge() > 24)
                    {
                       femaleabove24++;
                    }
                }   
            }
         }
        
        
        Map<String, String> dataMap = new HashMap<>();
       
        dataMap.put("male0To10",  male0To10+"");
        dataMap.put("male10To14",  male10To14+"");
        dataMap.put("male15To19",  male15To19+"");
        dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
        dataMap.put("maleabove24",  maleabove24+"");
        dataMap.put("female0To10",  female0To10+"");
        dataMap.put("female10To14",  female10To14+"");
        dataMap.put("female15To19",  female15To19+"");
        dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
        dataMap.put("femaleabove24",  femaleabove24+"");
        //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
        return new JSONObject(dataMap).toString();

}
	
	public String getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove1000(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
        DateTime sixMonthsAgo = endDateTime.minusMonths(6);
        //Database.initConnection();

        
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

        int male0To10= 0;
        int male10To14=0;
        int male15To19=0;
        int male20To24=0; 
        int maleabove24=0;
        
        int female0To10= 0;
        int female10To14=0;
        int female15To19=0;
        int female20To24=0; 
        int femaleabove24=0;

        List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000(startDate, endDate, sixMonths);
        for(int i=0; i<allPatients.size(); i++)
        {
            if("curra".equals(ageType)){
                if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                {
                	if(allPatients.get(i).getCage() <10)
                	{
                		male0To10++;
                	}
                	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                    {
                        male10To14++;
                    }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                    {
                        male15To19++;
                    }
                    else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                    {
                        male20To24++;
                    }
                    else if(allPatients.get(i).getCage() > 24)
                    {
                        maleabove24++;
                    }
                }
                else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                {
                	if(allPatients.get(i).getCage() <10)
                	{
                		female0To10++;
                	}
                	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                    {
                        female10To14++;
                    }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                    {
                        female15To19++;
                    }
                    else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                    {
                        female20To24++;
                    }
                    else if(allPatients.get(i).getCage() > 24)
                    {
                       femaleabove24++;
                    }
                }
            }else{
                if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                {
                	if(allPatients.get(i).getAge() <10)
                	{
                		male0To10++;
                	}
                	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                    {
                        male10To14++;
                    }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                    {
                        male15To19++;
                    }
                    else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                    {
                        male20To24++;
                    }
                    else if(allPatients.get(i).getAge() > 24)
                    {
                        maleabove24++;
                    }
                }
                else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                {
                	if(allPatients.get(i).getAge() <10)
                	{
                		female0To10++;
                	}
                	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                    {
                        female10To14++;
                    }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                    {
                        female15To19++;
                    }
                    else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                    {
                        female20To24++;
                    }
                    else if(allPatients.get(i).getAge() > 24)
                    {
                       femaleabove24++;
                    }
                }   
            }
         }
        
        
        Map<String, String> dataMap = new HashMap<>();
       
        dataMap.put("male0To10",  male0To10+"");
        dataMap.put("male10To14",  male10To14+"");
        dataMap.put("male15To19",  male15To19+"");
        dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
        dataMap.put("maleabove24",  maleabove24+"");
        dataMap.put("female0To10",  female0To10+"");
        dataMap.put("female10To14",  female10To14+"");
        dataMap.put("female15To19",  female15To19+"");
        dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
        dataMap.put("femaleabove24",  femaleabove24+"");
        //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
        return new JSONObject(dataMap).toString();

}
	
	public String getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlBelow200(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalAYPLHIVEnrolledInOTZWhoComplete7(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
        DateTime sixMonthsAgo = endDateTime.minusMonths(6);
        //Database.initConnection();

        
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

        int male0To10= 0;
        int male10To14=0;
        int male15To19=0;
        int male20To24=0; 
        int maleabove24=0;
        
        int female0To10= 0;
        int female10To14=0;
        int female15To19=0;
        int female20To24=0; 
        int femaleabove24=0;

        List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZWhoComplete7(startDate, endDate);
        for(int i=0; i<allPatients.size(); i++)
        {
            if("curra".equals(ageType)){
                if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                {
                	if(allPatients.get(i).getCage() <10)
                	{
                		male0To10++;
                	}
                	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                    {
                        male10To14++;
                    }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                    {
                        male15To19++;
                    }
                    else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                    {
                        male20To24++;
                    }
                    else if(allPatients.get(i).getCage() > 24)
                    {
                        maleabove24++;
                    }
                }
                else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                {
                	if(allPatients.get(i).getCage() <10)
                	{
                		female0To10++;
                	}
                	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                    {
                        female10To14++;
                    }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                    {
                        female15To19++;
                    }
                    else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                    {
                        female20To24++;
                    }
                    else if(allPatients.get(i).getCage() > 24)
                    {
                       femaleabove24++;
                    }
                }
            }else{
                if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                {
                	if(allPatients.get(i).getAge() <10)
                	{
                		male0To10++;
                	}
                	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                    {
                        male10To14++;
                    }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                    {
                        male15To19++;
                    }
                    else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                    {
                        male20To24++;
                    }
                    else if(allPatients.get(i).getAge() > 24)
                    {
                        maleabove24++;
                    }
                }
                else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                {
                	if(allPatients.get(i).getAge() <10)
                	{
                		female0To10++;
                	}
                	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                    {
                        female10To14++;
                    }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                    {
                        female15To19++;
                    }
                    else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                    {
                        female20To24++;
                    }
                    else if(allPatients.get(i).getAge() > 24)
                    {
                       femaleabove24++;
                    }
                }   
            }
         }
        
        
        Map<String, String> dataMap = new HashMap<>();
       
        dataMap.put("male0To10",  male0To10+"");
        dataMap.put("male10To14",  male10To14+"");
        dataMap.put("male15To19",  male15To19+"");
        dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
        dataMap.put("maleabove24",  maleabove24+"");
        dataMap.put("female0To10",  female0To10+"");
        dataMap.put("female10To14",  female10To14+"");
        dataMap.put("female15To19",  female15To19+"");
        dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
        dataMap.put("femaleabove24",  femaleabove24+"");
            
        //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
        return new JSONObject(dataMap).toString();

}
	
	public String getTotalEnrolledWithSwitchTo3rdLine(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithSwitchTo3rdLine(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
                
	        //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
	        return new JSONObject(dataMap).toString();

	}
	
	public String getTotalEnrolledAndDiedAfter(HttpServletRequest request) {
	        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
	        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
                    String ageType = request.getParameter("ageType");
	        DateTime sixMonthsAgo = endDateTime.minusMonths(6);
	        //Database.initConnection();

	        
	        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
	        String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

	        int male0To10= 0;
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; 
            int maleabove24=0;
            
            int female0To10= 0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; 
            int femaleabove24=0;

	        List<OTZPatient> allPatients = otzDao.getTotalEnrolledAndDiedAfter(startDate, endDate);
	        for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
	        //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
	        return new JSONObject(dataMap).toString();

	}
	
	public String getTotalEnrolledAndOptedOutAfter(HttpServletRequest request) {
	        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
	        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
                    String ageType = request.getParameter("ageType");
	        DateTime sixMonthsAgo = endDateTime.minusMonths(6);
	        //Database.initConnection();

	        
	        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
	        String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

	        int male0To10=0;
	        int male10To14=0;
	        int male15To19=0;
	        int male20To24=0; int maleabove24=0;
	        int female0To10=0;
	        int female10To14=0;
	        int female15To19=0;
	        int female20To24=0; int femaleabove24=0;

	        List<OTZPatient> allPatients = otzDao.getTotalEnrolledAndOptedOutAfter(startDate, endDate);
	        for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
	        //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
	        return new JSONObject(dataMap).toString();

	}
	
	public String getTotalEnrolledAndTransitionedAfter(HttpServletRequest request) {
	        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
	        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
                    String ageType = request.getParameter("ageType");
	        DateTime sixMonthsAgo = endDateTime.minusMonths(6);
	        //Database.initConnection();

	        
	        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
	        String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

	        int male0To10=0;
	        int male10To14=0;
	        int male15To19=0;
	        int male20To24=0; int maleabove24=0;
	        int female0To10=0;
	        int female10To14=0;
	        int female15To19=0;
	        int female20To24=0; int femaleabove24=0;
                

	        List<OTZPatient> allPatients = otzDao.getTotalEnrolledAndTransitionedAfter(startDate, endDate);
               
	        for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
	        //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
	        return new JSONObject(dataMap).toString();

	}
	
	public String getTotalEnrolledAndTransferredOutAfter(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
                String ageType = request.getParameter("ageType");
        DateTime sixMonthsAgo = endDateTime.minusMonths(6);
        //Database.initConnection();

        
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

        int male0To10=0;
        int male10To14=0;
        int male15To19=0;
        int male20To24=0; int maleabove24=0;
        int female0To10=0;
        int female10To14=0;
        int female15To19=0;
        int female20To24=0; int femaleabove24=0;

        List<OTZPatient> allPatients = otzDao.getTotalEnrolledAndTransferredOutAfter(startDate, endDate);
        for(int i=0; i<allPatients.size(); i++)
        {
            if("curra".equals(ageType)){
                if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                {
                	if(allPatients.get(i).getCage() <10)
                	{
                		male0To10++;
                	}
                	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                    {
                        male10To14++;
                    }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                    {
                        male15To19++;
                    }
                    else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                    {
                        male20To24++;
                    }
                    else if(allPatients.get(i).getCage() > 24)
                    {
                        maleabove24++;
                    }
                }
                else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                {
                	if(allPatients.get(i).getCage() <10)
                	{
                		female0To10++;
                	}
                	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                    {
                        female10To14++;
                    }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                    {
                        female15To19++;
                    }
                    else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                    {
                        female20To24++;
                    }
                    else if(allPatients.get(i).getCage() > 24)
                    {
                       femaleabove24++;
                    }
                }
            }else{
                if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                {
                	if(allPatients.get(i).getAge() <10)
                	{
                		male0To10++;
                	}
                	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                    {
                        male10To14++;
                    }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                    {
                        male15To19++;
                    }
                    else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                    {
                        male20To24++;
                    }
                    else if(allPatients.get(i).getAge() > 24)
                    {
                        maleabove24++;
                    }
                }
                else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                {
                	if(allPatients.get(i).getAge() <10)
                	{
                		female0To10++;
                	}
                	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                    {
                        female10To14++;
                    }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                    {
                        female15To19++;
                    }
                    else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                    {
                        female20To24++;
                    }
                    else if(allPatients.get(i).getAge() > 24)
                    {
                       femaleabove24++;
                    }
                }   
            }
         }
        
        
        Map<String, String> dataMap = new HashMap<>();
       
        dataMap.put("male0To10",  male0To10+"");
        dataMap.put("male10To14",  male10To14+"");
        dataMap.put("male15To19",  male15To19+"");
        dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
        dataMap.put("maleabove24",  maleabove24+"");
        dataMap.put("female0To10",  female0To10+"");
        dataMap.put("female10To14",  female10To14+"");
        dataMap.put("female15To19",  female15To19+"");
        dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
        dataMap.put("femaleabove24",  femaleabove24+"");
        //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
        return new JSONObject(dataMap).toString();

}
	
	public String getTotalEnrolledAndLTFUAfter(HttpServletRequest request) {
        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
                String ageType = request.getParameter("ageType");
        DateTime sixMonthsAgo = endDateTime.minusMonths(6);
        //Database.initConnection();

        
        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
        String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

        int male0To10=0;
        int male10To14=0;
        int male15To19=0;
        int male20To24=0; int maleabove24=0;
        int female0To10=0;
        int female10To14=0;
        int female15To19=0;
        int female20To24=0; int femaleabove24=0;

        List<OTZPatient> allPatients = otzDao.getTotalEnrolledAndLTFUAfter(startDate, endDate);
        for(int i=0; i<allPatients.size(); i++)
        {
            if("curra".equals(ageType)){
                if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                {
                	if(allPatients.get(i).getCage() <10)
                	{
                		male0To10++;
                	}
                	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                    {
                        male10To14++;
                    }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                    {
                        male15To19++;
                    }
                    else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                    {
                        male20To24++;
                    }
                    else if(allPatients.get(i).getCage() > 24)
                    {
                        maleabove24++;
                    }
                }
                else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                {
                	if(allPatients.get(i).getCage() <10)
                	{
                		female0To10++;
                	}
                	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                    {
                        female10To14++;
                    }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                    {
                        female15To19++;
                    }
                    else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                    {
                        female20To24++;
                    }
                    else if(allPatients.get(i).getCage() > 24)
                    {
                       femaleabove24++;
                    }
                }
            }else{
                if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                {
                	if(allPatients.get(i).getAge() <10)
                	{
                		male0To10++;
                	}
                	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                    {
                        male10To14++;
                    }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                    {
                        male15To19++;
                    }
                    else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                    {
                        male20To24++;
                    }
                    else if(allPatients.get(i).getAge() > 24)
                    {
                        maleabove24++;
                    }
                }
                else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                {
                	if(allPatients.get(i).getAge() <10)
                	{
                		female0To10++;
                	}
                	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                    {
                        female10To14++;
                    }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                    {
                        female15To19++;
                    }
                    else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                    {
                        female20To24++;
                    }
                    else if(allPatients.get(i).getAge() > 24)
                    {
                       femaleabove24++;
                    }
                }   
            }
         }
        
        
        Map<String, String> dataMap = new HashMap<>();
       
        dataMap.put("male0To10",  male0To10+"");
        dataMap.put("male10To14",  male10To14+"");
        dataMap.put("male15To19",  male15To19+"");
        dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
        dataMap.put("maleabove24",  maleabove24+"");
        dataMap.put("female0To10",  female0To10+"");
        dataMap.put("female10To14",  female10To14+"");
        dataMap.put("female15To19",  female15To19+"");
        dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
        dataMap.put("femaleabove24",  femaleabove24+"");
        //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
        return new JSONObject(dataMap).toString();

}
	
	public String getTotalEnrolledAndExitedAfter(HttpServletRequest request) {
	        DateTime startDateTime = new DateTime(request.getParameter("startDate"));
	        DateTime endDateTime = new DateTime(request.getParameter("endDate"));
                    String ageType = request.getParameter("ageType");
	        DateTime sixMonthsAgo = endDateTime.minusMonths(6);
	        //Database.initConnection();

	        
	        String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
	        String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
	        String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

	        int male0To10=0;
	        int male10To14=0;
	        int male15To19=0;
	        int male20To24=0; int maleabove24=0;
	        int female0To10=0;
	        int female10To14=0;
	        int female15To19=0;
	        int female20To24=0; int femaleabove24=0;

	        List<OTZPatient> allPatients = otzDao.getTotalEnrolledAndExitedAfter(startDate, endDate);
	        for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getCage() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		male0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                    	if(allPatients.get(i).getAge() <10)
                    	{
                    		female0To10++;
                    	}
                    	else if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                        }
                    }   
                }
             }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male0To10",  male0To10+"");
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female0To10",  female0To10+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
	        //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
	        return new JSONObject(dataMap).toString();

	}
	
	public String getTxCurr(HttpServletRequest request) {
		DateTime startDateTime = new DateTime(request.getParameter("startDate"));
		DateTime endDateTime = new DateTime(request.getParameter("endDate"));
		DateTime sixMonthsAgo = endDateTime.minusMonths(6);
		//Database.initConnection();
		String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
		ClinicalDaoHelper clinicalDaoHelper = new ClinicalDaoHelper();
		List<Map<String, String>> activeAYPLHIV = clinicalDaoHelper.getActiveAYPLHIV(startDate, endDate);
		
		String json = new Gson().toJson(activeAYPLHIV);
		
		//return "hello";
		return json;
		
	}
	
	public String getTxCurr2(HttpServletRequest request) {
		DateTime startDateTime = new DateTime(request.getParameter("startDate"));
		DateTime endDateTime = new DateTime(request.getParameter("endDate"));
		DateTime sixMonthsAgo = endDateTime.minusMonths(6);
		//Database.initConnection();
		String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
		ClinicalDaoHelper clinicalDaoHelper = new ClinicalDaoHelper();
		List<Map<String, String>> activeAYPLHIV = clinicalDaoHelper.getActiveAYPLHIV3(startDate, endDate);
		
		String json = new Gson().toJson(activeAYPLHIV);
		
		//return "hello";
		return json;
		
	}
	
	public String getAYPLHIVEnrolled(HttpServletRequest request) {
		DateTime startDateTime = new DateTime(request.getParameter("startDate"));
		DateTime endDateTime = new DateTime(request.getParameter("endDate"));
		DateTime sixMonthsAgo = endDateTime.minusMonths(6);
		//Database.initConnection();
		String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
                
                
                
                
		OTZDao otzDao = new OTZDao();
                
		List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
		List<OTZPatient> allPatientsFullDisclosure = otzDao.getTotalAYPLHIVEnrolledInOTZFullDisclosure(startDate, endDate);
                
                //lets get those who exited 
                List<OTZPatient> allPatientsExited = otzDao.getTotalEnrolledAndExitedAfter(startDate, endDate);
                
                List<OTZPatient> allPatientsTransferred = otzDao.getTotalEnrolledAndTransferredOutAfter(startDate, endDate);
                
                List<OTZPatient> allPatientsDied = otzDao.getTotalEnrolledAndDiedAfter(startDate, endDate);
                
                List<OTZPatient> allPatientsOptedOut = otzDao.getTotalEnrolledAndOptedOutAfter(startDate, endDate);
                
                JSONObject quarters = Misc.getQuartersBetweenDates(startDate, endDate);
                
                //System.out.println("transferred "+allPatientsTransferred.size());
                
                
                Map<String, Object> data = new HashMap<>();
                data.put("quarters", quarters);
                data.put("patients", allPatients);
                data.put("allPatientsFullDisclosure", allPatientsFullDisclosure);
                data.put("allPatientsTransferred", allPatientsTransferred);
                data.put("allPatientsExited", allPatientsExited);
                data.put("allPatientsDied", allPatientsDied);
                data.put("allPatientsOptedOut", allPatientsOptedOut);
		String json = new Gson().toJson(data);
		         // //System.out.println("json++++++++++++++++++++++++++"+json);
		//return "hello";
		return json;
		
	}
	
	public String getTotalWhoCompleted(HttpServletRequest request) {
		DateTime startDateTime = new DateTime(request.getParameter("startDate"));
		DateTime endDateTime = new DateTime(request.getParameter("endDate"));
		DateTime sixMonthsAgo = endDateTime.minusMonths(6);
		//Database.initConnection();
		String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
                
                
                
                
		OTZDao otzDao = new OTZDao();
                
		List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
		
                JSONObject quarters = Misc.getQuartersBetweenDates(startDate, endDate);
                
                
                Map<String, Object> data = new HashMap<>();
                data.put("quarters", quarters);
                data.put("patients", allPatients);
		String json = new Gson().toJson(data);
		
		return json;
		
	}
	
	public Map<String, Object> getPatientsVLAccess(HttpServletRequest request) {
		DateTime startDateTime = new DateTime(request.getParameter("startDate"));
		DateTime endDateTime = new DateTime(request.getParameter("endDate"));
		//DateTime sixMonthsAgo = endDateTime.minusMonths(6);
                
                String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		//String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
                
                Map<String, Object> data = new HashMap<>();
                DateTime today = new DateTime();
                
                int monthsBetweenDates = Months.monthsBetween(startDateTime, today).getMonths();
                //for(int j=0; j<monthsBetweenDates; j +=6)
                for(int j=0; j <= monthsBetweenDates; j +=6)
                {
                    DateTime futureStartDateTime = startDateTime.plusMonths(j);
                    DateTime futureStartDateTime2 = startDateTime.plusMonths(j+6);
                    DateTime futureEndDateTime = endDateTime.plusMonths(j);
                    DateTime futureEndDateTime2 = endDateTime.plusMonths(j+6);
                    
                    DateTime sixMonthsAgoDateTime = futureStartDateTime.minusMonths(6);
                    String sixMonthsAgo = sixMonthsAgoDateTime.toString("yyyy'-'MM'-'dd");
                    DateTime sixMonthsAgoDateTime2 = futureStartDateTime2.minusMonths(6);
                    String sixMonthsAgo2 = sixMonthsAgoDateTime2.toString("yyyy'-'MM'-'dd");
                    
                    String futureStartDate = futureStartDateTime.toString("yyyy'-'MM'-'dd");
                    String futureEndDate = futureEndDateTime.toString("yyyy'-'MM'-'dd");
                    String futureStartDate2 = futureStartDateTime2.toString("yyyy'-'MM'-'dd");
                    String futureEndDate2 = futureEndDateTime2.toString("yyyy'-'MM'-'dd");
                    
                    int month = j;
                    if(j == 0)
                    {
                       // month = j-6;
                    }
                    List<OTZPatient> allPatients = otzDao.getTotalPtsEnrolledAndEligibleForVL3(startDate, endDate,  month);
                    //stopped here
                    
                    //quick
                    List<OTZPatient> allPatientsExited = otzDao.getTotalEnrolledAndExitedAfter(startDate, endDate);
                    List<OTZPatient> allPatientsTO = otzDao.getTotalEnrolledAndTransitionedAfter(startDate, endDate);
                    List<OTZPatient> allPatientsOptedOut = otzDao.getTotalEnrolledAndOptedOutAfter(startDate, endDate);
                    List<OTZPatient> allPatientsDied = otzDao.getTotalEnrolledAndDiedAfter(startDate, endDate);
                    List<OTZPatient> allPatientsIIT = otzDao.getTotalEnrolledAndLTFUAfter(startDate, endDate);
                    List<OTZPatient> allPatientsTransferred = otzDao.getTotalEnrolledAndTransferredOutAfter(startDate, endDate);
                    List<OTZPatient> allPatientsWhoCompleted = otzDao.getTotalAYPLHIVEnrolledInOTZWhoComplete7(startDate, endDate);
                    List<OTZPatient> allPatientsSwitchTo3rd = otzDao.getTotalEnrolledWithSwitchTo3rdLine(startDate, endDate, String.valueOf(month));
                    List<OTZPatient> allPatientsSwitchTo2nd = otzDao.getTotalEnrolledWithSwitchTo2ndLine(startDate, endDate, String.valueOf(month));
                    List<OTZPatient> allPatientsPast12MonthsResultAbove1000WithRepeatVl = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVl(startDate, endDate, String.valueOf(month));
                    List<OTZPatient> allPatientsPast12MonthsResultAbove1000WithRepeatVlBelow200 = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlBelow200(startDate, endDate, String.valueOf(month));
                    List<OTZPatient> allPatientsPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000 = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000(startDate, endDate, String.valueOf(month));
                     
                    
                    List<OTZPatient> patientsEligible = new ArrayList<>();
                    List<OTZPatient> patientsWithSample = new ArrayList<>();
                    List<OTZPatient> patientsWithResult = new ArrayList<>();
                    List<OTZPatient> patientsWithResultPast6Months = new ArrayList<>();
                    List<OTZPatient> patientsSuppressedPast6Months = new ArrayList<>();
                    List<OTZPatient> patientsUndetectablePast6Months = new ArrayList<>();
                    List<OTZPatient> patientsLLVPast6Months = new ArrayList<>();
                    

                    
                    List<OTZPatient> patientsWithResultPast12Months = new ArrayList<>();
                    List<OTZPatient> patientsSuppressedPast12Months = new ArrayList<>();
                    List<OTZPatient> patientsUndetectablePast12Months = new ArrayList<>();
                    List<OTZPatient> patientsLLVPast12Months = new ArrayList<>();
                    
                    List<OTZPatient> patientsWithResultPast12MonthsAbove1000 = new ArrayList<>();
                     
                    for(int i=0; i<allPatients.size(); i++)
                    {
                    	DateTime artStartDate = new DateTime(allPatients.get(i).getArtStartDate().substring(0, 10));
                        DateTime enrollmentDate = new DateTime(allPatients.get(i).getEnrollmentDate().substring(0, 10));
                        DateTime sampleCollectionDate = (allPatients.get(i).getPreviousSampleCollectionDate() != null) ? new DateTime(allPatients.get(i).getPreviousSampleCollectionDate().substring(0, 10)) : new DateTime();
                        DateTime newSampleCollectionDate = (allPatients.get(i).getSampleCollectionDate() != null) ? new DateTime(allPatients.get(i).getSampleCollectionDate().substring(0, 10)) : new DateTime();
                        float vlResult = allPatients.get(i).getPreviousViralLoad();
                        
                        
                        //we need the last day of the month for the enrollment date
                        int lastDateInCohortMonth = enrollmentDate.dayOfMonth().getMaximumValue();
                        
                        
                        DateTime enrollmentLastDate = enrollmentDate.dayOfMonth().withMaximumValue();
                        DateTime enrollmentFirstDate = enrollmentDate.dayOfMonth().withMinimumValue();//this is the first day of the month for the enrollment month
                        DateTime expectedSampleCollectionDateForMonth = enrollmentDate.plusMonths(j);
                        
                        int daysDifference = Days.daysBetween(sampleCollectionDate, expectedSampleCollectionDateForMonth).getDays();
                        //get number of months between previous sample collection date and expected sample collection date. This helps us to know if the patient is eligible
                        int monthsBetween = (sampleCollectionDate != null) ? Months.monthsBetween(sampleCollectionDate.dayOfMonth().withMinimumValue(), expectedSampleCollectionDateForMonth.dayOfMonth().withMaximumValue()).getMonths() : -1;
                        //get the number of months between the expected sample collection date and the actual sample collection date to know if sample was indeed collected
                        int monthsBetweenExpectedAndActual = (expectedSampleCollectionDateForMonth != null) ?  Months.monthsBetween(expectedSampleCollectionDateForMonth.dayOfMonth().withMinimumValue(), newSampleCollectionDate.dayOfMonth().withMaximumValue()).getMonths() : -1;
                        
                        int monthBetweenArtStartDateAndPeriod = Months.monthsBetween(artStartDate, enrollmentDate).getMonths();
                        
                        //sample was either npatientsWithResultPast6Monthsot taken or was taken at the right day. That means they are eliglble
                       
                        //if there is no result/sample collection and the person is up to six months on art or if the result is at least 6 months old and is suppressed
                        if( monthsBetween == -1 ||  (monthsBetween >= 6 && vlResult < 1000) ) {
                        //if(monthsBetween == -1) {
                        	patientsEligible.add(allPatients.get(i));
                        	//those with sample collection have to be a subset of these
                        	//if(monthsBetweenExpectedAndActual == 0 )//this approximates. I.e someone with sample collected at november 17 is added, even when the expected is say dec 6
                        	if((newSampleCollectionDate != null) && (expectedSampleCollectionDateForMonth.getMonthOfYear() == newSampleCollectionDate.getMonthOfYear() && expectedSampleCollectionDateForMonth.getYear() == newSampleCollectionDate.getYear()))
                            {
                                 patientsWithSample.add(allPatients.get(i));
                                 //for there to be result, sample must have been taken
                                if(allPatients.get(i).getViralLoad() != -1)
                                {
                                    patientsWithResult.add(allPatients.get(i));
                                   
                                }
                            }
                        	
                        }
                        
                        
                        
                      
                        
                        /*long monthsBetween = ChronoUnit.MONTHS.between(
                                LocalDate.parse(allPatients.get(i).getEnrollmentDate()).withDayOfMonth(1),
                                LocalDate.parse(allPatients.get(i).getSampleCollectionDate()).withDayOfMonth(1));*/
                       // //System.out.println(monthsBetween); //3

                        //check if there is a test is within the past 6 months
                        //if(monthsBetweenExpectedAndActual >= 0 && monthsBetweenExpectedAndActual <=6)
                        if(monthsBetween >= 0 && monthsBetween <=6)
                        {
                           
                            /*if(allPatients.get(i).getViralLoad() != -1)
                            {
                                patientsWithResultPast6Months.add(allPatients.get(i));//there is result within the past 6 months
                                if(allPatients.get(i).getViralLoad() < 1000)//the result is suppressed
                                {
                                    patientsSuppressedPast6Months.add(allPatients.get(i));
                                    if(allPatients.get(i).getViralLoad() <=50)
                                    {
                                        patientsUndetectablePast6Months.add(allPatients.get(i));
                                    }
                                    else{
                                        patientsLLVPast6Months.add(allPatients.get(i));
                                    }
                                }
                            }*/
                        	if(allPatients.get(i).getPreviousViralLoad() != -1)
                            {
                                patientsWithResultPast6Months.add(allPatients.get(i));//there is result within the past 6 months
                                if(allPatients.get(i).getPreviousViralLoad() < 1000)//the result is suppressed
                                {
                                    patientsSuppressedPast6Months.add(allPatients.get(i));
                                    if(allPatients.get(i).getPreviousViralLoad() <=50)
                                    {
                                        patientsUndetectablePast6Months.add(allPatients.get(i));
                                    }
                                    else{
                                        patientsLLVPast6Months.add(allPatients.get(i));
                                    }
                                }
                            }
                            
                            
                        }
                        //if(monthsBetweenExpectedAndActual >= 0 && monthsBetweenExpectedAndActual <=12)
                        if(monthsBetween >= 0 && monthsBetween<=12)
                        {
                        
                            /*if(allPatients.get(i).getViralLoad() != -1)
                            {
                                patientsWithResultPast12Months.add(allPatients.get(i));
                                if(allPatients.get(i).getViralLoad() < 1000)//the result is suppressed
                                {
                                    patientsSuppressedPast12Months.add(allPatients.get(i));
                                     if(allPatients.get(i).getViralLoad() <=50)
                                    {
                                        patientsUndetectablePast12Months.add(allPatients.get(i));
                                    }
                                    else{
                                        patientsLLVPast12Months.add(allPatients.get(i));
                                    }
                                    
                                }else{
                                    patientsWithResultPast12MonthsAbove1000.add(allPatients.get(i));//this is unsuppressed
                                }
                            }*/
                        	if(allPatients.get(i).getPreviousViralLoad() != -1)
                            {
                                patientsWithResultPast12Months.add(allPatients.get(i));
                                if(allPatients.get(i).getPreviousViralLoad() < 1000)//the result is suppressed
                                {
                                    patientsSuppressedPast12Months.add(allPatients.get(i));
                                     if(allPatients.get(i).getPreviousViralLoad() <=50)
                                    {
                                        patientsUndetectablePast12Months.add(allPatients.get(i));
                                    }
                                    else{
                                        patientsLLVPast12Months.add(allPatients.get(i));
                                    }
                                    
                                }else{
                                    patientsWithResultPast12MonthsAbove1000.add(allPatients.get(i));//this is unsuppressed
                                }
                            }
                            
                        }
                        
                        /*if(allPatients.get(i).getSampleCollectionDate() == null)
                        {
                            //no sample has been taken at all. The patient is eligible for month 6 then
                            patientsEligible.add(allPatients.get(i));
                        }
                        else if(monthsBetween ==j || (monthsBetween + 1) == j || (monthsBetween - 1) == j)
                        {
                            patientsWithSample.add(allPatients.get(i));//sample was taken at six months
                            patientsEligible.add(allPatients.get(i));
                            if(allPatients.get(i).getViralLoad() != 0)
                            {
                                patientsWithResult.add(allPatients.get(i));
                               
                            }

                        }*/

                    }
                    
                    
                    data.put("patientsEligible"+j, patientsEligible);
                    data.put("patientsWithSample"+j, patientsWithSample); 
                    data.put("patientsWithResult"+j, patientsWithResult);
                    data.put("patientsWithResultPast6Months"+j, patientsWithResultPast6Months);
                    data.put("patientsSuppressedPast6Months"+j, patientsSuppressedPast6Months);
                    data.put("patientsUndetectablePast6Months"+j, patientsUndetectablePast6Months);
                    data.put("patientsLLVPast6Months"+j, patientsLLVPast6Months);
                    data.put("patientsWithResultPast12Months"+j, patientsWithResultPast12Months);
                    data.put("patientsSuppressedPast12Months"+j, patientsSuppressedPast12Months);
                    data.put("patientsUndetectablePast12Months"+j, patientsUndetectablePast12Months);
                    data.put("patientsLLVPast12Months"+j, patientsLLVPast12Months);
                    data.put("patientsWithResultPast12MonthsAbove1000"+j, patientsWithResultPast12MonthsAbove1000);
                    
                    //quick
                    data.put("allPatientsPast12MonthsResultAbove1000WithRepeatVl"+j, allPatientsPast12MonthsResultAbove1000WithRepeatVl);
                    data.put("allPatientsPast12MonthsResultAbove1000WithRepeatVlBelow200"+j, allPatientsPast12MonthsResultAbove1000WithRepeatVlBelow200);
                    data.put("allPatientsPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000"+j, allPatientsPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000);
                    data.put("allPatientsSwitchTo2nd"+j, allPatientsSwitchTo2nd);
                    data.put("allPatientsSwitchTo3rd"+j, allPatientsSwitchTo3rd);
                    data.put("allPatientsWhoCompleted"+j, allPatientsWhoCompleted);
                    data.put("allPatientsTransferred"+j, allPatientsTransferred);
                    data.put("allPatientsIIT"+j, allPatientsIIT);
                    data.put("allPatientsDied"+j, allPatientsDied);
                    data.put("allPatientsOptedOut"+j, allPatientsOptedOut);
                    data.put("allPatientsTO"+j, allPatientsTO);
                    data.put("allPatientsExited"+j, allPatientsExited);
                    
                   
                    
                    
                    //get those who completed EAC
                    List<OTZPatient> allPatients2 = otzDao.getTotalEnrolledAndCompletedEACPast12Months(startDate, endDate,  j);
                    
                    List<OTZPatient> patientsWhoCompletedEACPast12Months = new ArrayList<>();
                    List<OTZPatient> suppressedPatientsPostEAC = new ArrayList<>();
                    //loop through and add accordingly
                    for(int i=0; i<allPatients2.size(); i++)
                    {
                        
                      
                        
                        
                        if(allPatients2.get(i).getViralLoad() != 0 && allPatients2.get(i).getViralLoad() >=1000)
                        {
                            patientsWhoCompletedEACPast12Months.add(allPatients2.get(i));
                        }
                        if(allPatients2.get(i).getViralLoad() != 0 && allPatients2.get(i).getViralLoad() <1000)
                        {
                            suppressedPatientsPostEAC.add(allPatients2.get(i));
                        }
                        
                    }
                    data.put("patientsWhoCompletedEACPast12Months"+j, patientsWhoCompletedEACPast12Months);
                    data.put("suppressedPatientsPostEAC"+j, suppressedPatientsPostEAC);
                    
                     //get those who completed EAC
                    List<OTZPatient> allPatients3 = otzDao.getTotalEnrolledWithVLPast12MonthsWithRepeatVl(startDate, endDate,  j);
                    
                    List<OTZPatient> patientsWithRepeatVl12Months = new ArrayList<>();
                    //loop through and add accordingly
                    for(int i=0; i<allPatients3.size(); i++)
                    {
                        patientsWithRepeatVl12Months.add(allPatients3.get(i));
                        
                    }
                    
                    data.put("patientsWithRepeatVl12Months"+j, patientsWithRepeatVl12Months);
                    
                    
                    
                    
                     //we could get adherence data here too
                    List<OTZPatient> allPatientsScheduled = otzDao.getTotalEnrolledWithScheduledPickupMonthN(startDate, endDate,  sixMonthsAgo, futureEndDate, j);
                    //List<OTZPatient> allPatientsScheduled = otzDao.getTotalEnrolledWithScheduledPickupMonthN2(startDate, endDate, j);
                    //List<OTZPatient> allPatientsScheduled = otzDao.getTotalEnrolledWithScheduledPickup6MonthsBeforeAndAfter(startDate, endDate, j,  sixMonthsAgo, futureEndDate2);
                    List<OTZPatient> allPatientsKept = otzDao.getTotalEnrolledWhoKeptScheduledPickupMonthN(startDate, endDate,  sixMonthsAgo, futureEndDate, j);
                    //List<OTZPatient> allPatientsKept = otzDao.getTotalEnrolledWhoKeptScheduledPickup6MonthsBeforeAndAfter(startDate, endDate,  j,  sixMonthsAgo, futureEndDate2);
                    List<OTZPatient> allPatientsGoodScore = otzDao.getTotalEnrolledWithGoodAdhScoreMonthN(startDate, endDate,  sixMonthsAgo, futureEndDate);
                    //List<OTZPatient> allPatientsGoodScore = otzDao.getTotalEnrolledWithGoodAdhScore6MonthsBeforeAndAfter(startDate, endDate,  j,  sixMonthsAgo, futureEndDate2);
                    
                    data.put("allPatientsScheduled"+j, allPatientsScheduled);
                    data.put("allPatientsKept"+j, allPatientsKept);
                    data.put("allPatientsGoodScore"+j, allPatientsGoodScore);
                    
                    
                    
                    // To include the current month block
                    /*
					if (j > monthsBetweenDates) {
                    break;
                    }
					*/
                   
                }
                
		//Database.initConnection();
		
                
              
                
                //lets get those who completed 7 modules
                
                 List<OTZPatient> allPatientsWhoCompleted = otzDao.getTotalAYPLHIVEnrolledInOTZWhoComplete7(startDate, endDate);
                 
                 List<OTZPatient> allPatientsIIT = otzDao.getTotalEnrolledAndLTFUAfter(startDate, endDate);
                 
                 List<OTZPatient> allPatientsTO = otzDao.getTotalEnrolledAndTransitionedAfter(startDate, endDate);
                
                //lets loop through and perform operations to get eligibility at month 6, 12 and 18
		
                JSONObject quarters = Misc.getQuartersBetweenDates(startDate, endDate);
                
                data.put("complete7Modules", allPatientsWhoCompleted);
                
                data.put("alPatientsIIT", allPatientsIIT);
                
                data.put("alPatientsTO", allPatientsTO);
               
                data.put("quarters", quarters);
                
              
		
                String json = new Gson().toJson(data);
		ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> dataToReturn =  mapper.readValue(json, Map.class);
            dataToReturn.put("quarters", ((HashMap)dataToReturn.get("quarters")).get("myHashMap"));
            return dataToReturn;
        } catch (Exception e) {
            
            e.printStackTrace();
            return new HashMap<>();
        }
		//return data;//json;
		
	}
	
	public Map<String, Object> getPatientsVLAccess2(String startDate, String endDate, String ageType) {
		DateTime startDateTime = new DateTime(startDate);
		DateTime endDateTime = new DateTime(endDate);
		//DateTime sixMonthsAgo = endDateTime.minusMonths(6);
                
        //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		//String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		//String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
                
                Map<String, Object> data = new HashMap<>();
                DateTime today = new DateTime();
                
                int monthsBetweenDates = Months.monthsBetween(startDateTime, today).getMonths();
                //for(int j=0; j<monthsBetweenDates; j +=6)
                for(int j=0; j <= monthsBetweenDates; j +=6)
                {
                    DateTime futureStartDateTime = startDateTime.plusMonths(j);
                    DateTime futureStartDateTime2 = startDateTime.plusMonths(j+6);
                    DateTime futureEndDateTime = endDateTime.plusMonths(j);
                    DateTime futureEndDateTime2 = endDateTime.plusMonths(j+6);
                    
                    DateTime sixMonthsAgoDateTime = futureStartDateTime.minusMonths(6);
                    String sixMonthsAgo = sixMonthsAgoDateTime.toString("yyyy'-'MM'-'dd");
                    DateTime sixMonthsAgoDateTime2 = futureStartDateTime2.minusMonths(6);
                    String sixMonthsAgo2 = sixMonthsAgoDateTime2.toString("yyyy'-'MM'-'dd");
                    
                    String futureStartDate = futureStartDateTime.toString("yyyy'-'MM'-'dd");
                    String futureEndDate = futureEndDateTime.toString("yyyy'-'MM'-'dd");
                    String futureStartDate2 = futureStartDateTime2.toString("yyyy'-'MM'-'dd");
                    String futureEndDate2 = futureEndDateTime2.toString("yyyy'-'MM'-'dd");
                    
                    int month = j;
                    if(j == 0)
                    {
                       // month = j-6;
                    }
                    List<OTZPatient> allPatients = otzDao.getTotalPtsEnrolledAndEligibleForVL3(startDate, endDate,  month);
                    //stopped here
                    
                    //quick
                    List<OTZPatient> allPatientsExited = otzDao.getTotalEnrolledAndExitedAfter(startDate, endDate);
                    List<OTZPatient> allPatientsTO = otzDao.getTotalEnrolledAndTransitionedAfter(startDate, endDate);
                    List<OTZPatient> allPatientsOptedOut = otzDao.getTotalEnrolledAndOptedOutAfter(startDate, endDate);
                    List<OTZPatient> allPatientsDied = otzDao.getTotalEnrolledAndDiedAfter(startDate, endDate);
                    List<OTZPatient> allPatientsIIT = otzDao.getTotalEnrolledAndLTFUAfter(startDate, endDate);
                    List<OTZPatient> allPatientsTransferred = otzDao.getTotalEnrolledAndTransferredOutAfter(startDate, endDate);
                    List<OTZPatient> allPatientsWhoCompleted = otzDao.getTotalAYPLHIVEnrolledInOTZWhoComplete7(startDate, endDate);
                    List<OTZPatient> allPatientsSwitchTo3rd = otzDao.getTotalEnrolledWithSwitchTo3rdLine(startDate, endDate, String.valueOf(month));
                    List<OTZPatient> allPatientsSwitchTo2nd = otzDao.getTotalEnrolledWithSwitchTo2ndLine(startDate, endDate, String.valueOf(month));
                    List<OTZPatient> allPatientsPast12MonthsResultAbove1000WithRepeatVl = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVl(startDate, endDate, String.valueOf(month));
                    List<OTZPatient> allPatientsPast12MonthsResultAbove1000WithRepeatVlBelow200 = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlBelow200(startDate, endDate, String.valueOf(month));
                    List<OTZPatient> allPatientsPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000 = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000(startDate, endDate, String.valueOf(month));
                     
                    
                    List<OTZPatient> patientsEligible = new ArrayList<>();
                    List<OTZPatient> patientsWithSample = new ArrayList<>();
                    List<OTZPatient> patientsWithResult = new ArrayList<>();
                    List<OTZPatient> patientsWithResultPast6Months = new ArrayList<>();
                    List<OTZPatient> patientsSuppressedPast6Months = new ArrayList<>();
                    List<OTZPatient> patientsUndetectablePast6Months = new ArrayList<>();
                    List<OTZPatient> patientsLLVPast6Months = new ArrayList<>();
                    

                    
                    List<OTZPatient> patientsWithResultPast12Months = new ArrayList<>();
                    List<OTZPatient> patientsSuppressedPast12Months = new ArrayList<>();
                    List<OTZPatient> patientsUndetectablePast12Months = new ArrayList<>();
                    List<OTZPatient> patientsLLVPast12Months = new ArrayList<>();
                    
                    List<OTZPatient> patientsWithResultPast12MonthsAbove1000 = new ArrayList<>();
                     
                    for(int i=0; i<allPatients.size(); i++)
                    {
                    	DateTime artStartDate = new DateTime(allPatients.get(i).getArtStartDate().substring(0, 10));
                        DateTime enrollmentDate = new DateTime(allPatients.get(i).getEnrollmentDate().substring(0, 10));
                        DateTime sampleCollectionDate = (allPatients.get(i).getPreviousSampleCollectionDate() != null) ? new DateTime(allPatients.get(i).getPreviousSampleCollectionDate().substring(0, 10)) : new DateTime();
                        DateTime newSampleCollectionDate = (allPatients.get(i).getSampleCollectionDate() != null) ? new DateTime(allPatients.get(i).getSampleCollectionDate().substring(0, 10)) : new DateTime();
                        float vlResult = allPatients.get(i).getPreviousViralLoad();
                        
                        
                        //we need the last day of the month for the enrollment date
                        int lastDateInCohortMonth = enrollmentDate.dayOfMonth().getMaximumValue();
                        
                        
                        DateTime enrollmentLastDate = enrollmentDate.dayOfMonth().withMaximumValue();
                        DateTime enrollmentFirstDate = enrollmentDate.dayOfMonth().withMinimumValue();//this is the first day of the month for the enrollment month
                        DateTime expectedSampleCollectionDateForMonth = enrollmentDate.plusMonths(j);
                        
                        int daysDifference = Days.daysBetween(sampleCollectionDate, expectedSampleCollectionDateForMonth).getDays();
                        //get number of months between previous sample collection date and expected sample collection date. This helps us to know if the patient is eligible
                        int monthsBetween = (sampleCollectionDate != null) ? Months.monthsBetween(sampleCollectionDate.dayOfMonth().withMinimumValue(), expectedSampleCollectionDateForMonth.dayOfMonth().withMaximumValue()).getMonths() : -1;
                        //get the number of months between the expected sample collection date and the actual sample collection date to know if sample was indeed collected
                        int monthsBetweenExpectedAndActual = (expectedSampleCollectionDateForMonth != null) ?  Months.monthsBetween(expectedSampleCollectionDateForMonth.dayOfMonth().withMinimumValue(), newSampleCollectionDate.dayOfMonth().withMaximumValue()).getMonths() : -1;
                        
                        int monthBetweenArtStartDateAndPeriod = Months.monthsBetween(artStartDate, enrollmentDate).getMonths();
                        
                        //sample was either npatientsWithResultPast6Monthsot taken or was taken at the right day. That means they are eliglble
                       
                        //if there is no result/sample collection and the person is up to six months on art or if the result is at least 6 months old and is suppressed
                        if( monthsBetween == -1 ||  (monthsBetween >= 6 && vlResult < 1000) ) {
                        //if(monthsBetween == -1) {
                        	patientsEligible.add(allPatients.get(i));
                        	//those with sample collection have to be a subset of these
                        	//if(monthsBetweenExpectedAndActual == 0 )//this approximates. I.e someone with sample collected at november 17 is added, even when the expected is say dec 6
                        	if((newSampleCollectionDate != null) && (expectedSampleCollectionDateForMonth.getMonthOfYear() == newSampleCollectionDate.getMonthOfYear() && expectedSampleCollectionDateForMonth.getYear() == newSampleCollectionDate.getYear()))
                            {
                                 patientsWithSample.add(allPatients.get(i));
                                 //for there to be result, sample must have been taken
                                if(allPatients.get(i).getViralLoad() != -1)
                                {
                                    patientsWithResult.add(allPatients.get(i));
                                   
                                }
                            }
                        	
                        }
                        
                        
                        
                      
                        
                        /*long monthsBetween = ChronoUnit.MONTHS.between(
                                LocalDate.parse(allPatients.get(i).getEnrollmentDate()).withDayOfMonth(1),
                                LocalDate.parse(allPatients.get(i).getSampleCollectionDate()).withDayOfMonth(1));*/
                       // //System.out.println(monthsBetween); //3

                        //check if there is a test is within the past 6 months
                        //if(monthsBetweenExpectedAndActual >= 0 && monthsBetweenExpectedAndActual <=6)
                        if(monthsBetween >= 0 && monthsBetween <=6)
                        {
                           
                            /*if(allPatients.get(i).getViralLoad() != -1)
                            {
                                patientsWithResultPast6Months.add(allPatients.get(i));//there is result within the past 6 months
                                if(allPatients.get(i).getViralLoad() < 1000)//the result is suppressed
                                {
                                    patientsSuppressedPast6Months.add(allPatients.get(i));
                                    if(allPatients.get(i).getViralLoad() <=50)
                                    {
                                        patientsUndetectablePast6Months.add(allPatients.get(i));
                                    }
                                    else{
                                        patientsLLVPast6Months.add(allPatients.get(i));
                                    }
                                }
                            }*/
                        	if(allPatients.get(i).getPreviousViralLoad() != -1)
                            {
                                patientsWithResultPast6Months.add(allPatients.get(i));//there is result within the past 6 months
                                if(allPatients.get(i).getPreviousViralLoad() < 1000)//the result is suppressed
                                {
                                    patientsSuppressedPast6Months.add(allPatients.get(i));
                                    if(allPatients.get(i).getPreviousViralLoad() <=50)
                                    {
                                        patientsUndetectablePast6Months.add(allPatients.get(i));
                                    }
                                    else{
                                        patientsLLVPast6Months.add(allPatients.get(i));
                                    }
                                }
                            }
                            
                            
                        }
                        //if(monthsBetweenExpectedAndActual >= 0 && monthsBetweenExpectedAndActual <=12)
                        if(monthsBetween >= 0 && monthsBetween<=12)
                        {
                        
                            /*if(allPatients.get(i).getViralLoad() != -1)
                            {
                                patientsWithResultPast12Months.add(allPatients.get(i));
                                if(allPatients.get(i).getViralLoad() < 1000)//the result is suppressed
                                {
                                    patientsSuppressedPast12Months.add(allPatients.get(i));
                                     if(allPatients.get(i).getViralLoad() <=50)
                                    {
                                        patientsUndetectablePast12Months.add(allPatients.get(i));
                                    }
                                    else{
                                        patientsLLVPast12Months.add(allPatients.get(i));
                                    }
                                    
                                }else{
                                    patientsWithResultPast12MonthsAbove1000.add(allPatients.get(i));//this is unsuppressed
                                }
                            }*/
                        	if(allPatients.get(i).getPreviousViralLoad() != -1)
                            {
                                patientsWithResultPast12Months.add(allPatients.get(i));
                                if(allPatients.get(i).getPreviousViralLoad() < 1000)//the result is suppressed
                                {
                                    patientsSuppressedPast12Months.add(allPatients.get(i));
                                     if(allPatients.get(i).getPreviousViralLoad() <=50)
                                    {
                                        patientsUndetectablePast12Months.add(allPatients.get(i));
                                    }
                                    else{
                                        patientsLLVPast12Months.add(allPatients.get(i));
                                    }
                                    
                                }else{
                                    patientsWithResultPast12MonthsAbove1000.add(allPatients.get(i));//this is unsuppressed
                                }
                            }
                            
                        }
                        
                        /*if(allPatients.get(i).getSampleCollectionDate() == null)
                        {
                            //no sample has been taken at all. The patient is eligible for month 6 then
                            patientsEligible.add(allPatients.get(i));
                        }
                        else if(monthsBetween ==j || (monthsBetween + 1) == j || (monthsBetween - 1) == j)
                        {
                            patientsWithSample.add(allPatients.get(i));//sample was taken at six months
                            patientsEligible.add(allPatients.get(i));
                            if(allPatients.get(i).getViralLoad() != 0)
                            {
                                patientsWithResult.add(allPatients.get(i));
                               
                            }

                        }*/

                    }
                    
                    
                    data.put("patientsEligible"+j, patientsEligible);
                    data.put("patientsWithSample"+j, patientsWithSample); 
                    data.put("patientsWithResult"+j, patientsWithResult);
                    data.put("patientsWithResultPast6Months"+j, patientsWithResultPast6Months);
                    data.put("patientsSuppressedPast6Months"+j, patientsSuppressedPast6Months);
                    data.put("patientsUndetectablePast6Months"+j, patientsUndetectablePast6Months);
                    data.put("patientsLLVPast6Months"+j, patientsLLVPast6Months);
                    data.put("patientsWithResultPast12Months"+j, patientsWithResultPast12Months);
                    data.put("patientsSuppressedPast12Months"+j, patientsSuppressedPast12Months);
                    data.put("patientsUndetectablePast12Months"+j, patientsUndetectablePast12Months);
                    data.put("patientsLLVPast12Months"+j, patientsLLVPast12Months);
                    data.put("patientsWithResultPast12MonthsAbove1000"+j, patientsWithResultPast12MonthsAbove1000);
                    
                    //quick
                    data.put("allPatientsPast12MonthsResultAbove1000WithRepeatVl"+j, allPatientsPast12MonthsResultAbove1000WithRepeatVl);
                    data.put("allPatientsPast12MonthsResultAbove1000WithRepeatVlBelow200"+j, allPatientsPast12MonthsResultAbove1000WithRepeatVlBelow200);
                    data.put("allPatientsPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000"+j, allPatientsPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000);
                    data.put("allPatientsSwitchTo2nd"+j, allPatientsSwitchTo2nd);
                    data.put("allPatientsSwitchTo3rd"+j, allPatientsSwitchTo3rd);
                    data.put("allPatientsWhoCompleted"+j, allPatientsWhoCompleted);
                    data.put("allPatientsTransferred"+j, allPatientsTransferred);
                    data.put("allPatientsIIT"+j, allPatientsIIT);
                    data.put("allPatientsDied"+j, allPatientsDied);
                    data.put("allPatientsOptedOut"+j, allPatientsOptedOut);
                    data.put("allPatientsTO"+j, allPatientsTO);
                    data.put("allPatientsExited"+j, allPatientsExited);
                    
                   
                    
                    
                    //get those who completed EAC
                    List<OTZPatient> allPatients2 = otzDao.getTotalEnrolledAndCompletedEACPast12Months(startDate, endDate,  j);
                    
                    List<OTZPatient> patientsWhoCompletedEACPast12Months = new ArrayList<>();
                    List<OTZPatient> suppressedPatientsPostEAC = new ArrayList<>();
                    //loop through and add accordingly
                    for(int i=0; i<allPatients2.size(); i++)
                    {
                        
                      
                        
                        
                        if(allPatients2.get(i).getViralLoad() != 0 && allPatients2.get(i).getViralLoad() >=1000)
                        {
                            patientsWhoCompletedEACPast12Months.add(allPatients2.get(i));
                        }
                        if(allPatients2.get(i).getViralLoad() != 0 && allPatients2.get(i).getViralLoad() <1000)
                        {
                            suppressedPatientsPostEAC.add(allPatients2.get(i));
                        }
                        
                    }
                    data.put("patientsWhoCompletedEACPast12Months"+j, patientsWhoCompletedEACPast12Months);
                    data.put("suppressedPatientsPostEAC"+j, suppressedPatientsPostEAC);
                    
                     //get those who completed EAC
                    List<OTZPatient> allPatients3 = otzDao.getTotalEnrolledWithVLPast12MonthsWithRepeatVl(startDate, endDate,  j);
                    
                    List<OTZPatient> patientsWithRepeatVl12Months = new ArrayList<>();
                    //loop through and add accordingly
                    for(int i=0; i<allPatients3.size(); i++)
                    {
                        patientsWithRepeatVl12Months.add(allPatients3.get(i));
                        
                    }
                    
                    data.put("patientsWithRepeatVl12Months"+j, patientsWithRepeatVl12Months);
                    
                    
                    
                    
                     //we could get adherence data here too
                    List<OTZPatient> allPatientsScheduled = otzDao.getTotalEnrolledWithScheduledPickupMonthN(startDate, endDate,  sixMonthsAgo, futureEndDate, j);
                    //List<OTZPatient> allPatientsScheduled = otzDao.getTotalEnrolledWithScheduledPickupMonthN2(startDate, endDate, j);
                    //List<OTZPatient> allPatientsScheduled = otzDao.getTotalEnrolledWithScheduledPickup6MonthsBeforeAndAfter(startDate, endDate, j,  sixMonthsAgo, futureEndDate2);
                    List<OTZPatient> allPatientsKept = otzDao.getTotalEnrolledWhoKeptScheduledPickupMonthN(startDate, endDate,  sixMonthsAgo, futureEndDate, j);
                    //List<OTZPatient> allPatientsKept = otzDao.getTotalEnrolledWhoKeptScheduledPickup6MonthsBeforeAndAfter(startDate, endDate,  j,  sixMonthsAgo, futureEndDate2);
                    List<OTZPatient> allPatientsGoodScore = otzDao.getTotalEnrolledWithGoodAdhScoreMonthN(startDate, endDate,  sixMonthsAgo, futureEndDate);
                    //List<OTZPatient> allPatientsGoodScore = otzDao.getTotalEnrolledWithGoodAdhScore6MonthsBeforeAndAfter(startDate, endDate,  j,  sixMonthsAgo, futureEndDate2);
                    
                    data.put("allPatientsScheduled"+j, allPatientsScheduled);
                    data.put("allPatientsKept"+j, allPatientsKept);
                    data.put("allPatientsGoodScore"+j, allPatientsGoodScore);
                    
                    
                    
                    // To include the current month block
                    /*
					if (j > monthsBetweenDates) {
                    break;
                    }
					*/
                   
                }
                
		//Database.initConnection();
		
                
              
                
                //lets get those who completed 7 modules
                
                 List<OTZPatient> allPatientsWhoCompleted = otzDao.getTotalAYPLHIVEnrolledInOTZWhoComplete7(startDate, endDate);
                 
                 List<OTZPatient> allPatientsIIT = otzDao.getTotalEnrolledAndLTFUAfter(startDate, endDate);
                 
                 List<OTZPatient> allPatientsTO = otzDao.getTotalEnrolledAndTransitionedAfter(startDate, endDate);
                
                //lets loop through and perform operations to get eligibility at month 6, 12 and 18
		
                JSONObject quarters = Misc.getQuartersBetweenDates(startDate, endDate);
                
                data.put("complete7Modules", allPatientsWhoCompleted);
                
                data.put("alPatientsIIT", allPatientsIIT);
                
                data.put("alPatientsTO", allPatientsTO);
               
                data.put("quarters", quarters);
                
              
		
                String json = new Gson().toJson(data);
		
		//return json;
        return data;
		
	}
	
	public String getPatientsVLCoverage(HttpServletRequest request) {
		DateTime startDateTime = new DateTime(request.getParameter("startDate"));
		DateTime endDateTime = new DateTime(request.getParameter("endDate"));
		DateTime sixMonthsAgo = endDateTime.minusMonths(6);
		//Database.initConnection();
		String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
                
                
                List<OTZPatient> patientsEligibleMonth6 = new ArrayList<>();
                List<OTZPatient> patientsWithSampleMonth6 = new ArrayList<>();
                
                List<OTZPatient> patientsEligibleMonth12 = new ArrayList<>();
                List<OTZPatient> patientsWithSampleMonth12 = new ArrayList<>();
                
                List<OTZPatient> patientsEligibleMonth18 = new ArrayList<>();
                List<OTZPatient> patientsWithSampleMonth18 = new ArrayList<>();
                
		OTZDao otzDao = new OTZDao();
                
                List<OTZPatient> allPatients = otzDao.getTotalPtsEnrolledAndEligibleForVL(startDate, endDate,  6);
                for(int i=0; i<allPatients.size(); i++)
                {
                    
                    DateTime enrollmentDate = new DateTime(allPatients.get(i).getEnrollmentDate().substring(0, 10));
                    DateTime sampleCollectionDate = (allPatients.get(i).getSampleCollectionDate() != null) ? new DateTime(allPatients.get(i).getSampleCollectionDate().substring(0, 10)) : new DateTime();
                    
                    int monthsBetween = Months.monthsBetween(enrollmentDate, sampleCollectionDate).getMonths();
                    /*long monthsBetween = ChronoUnit.MONTHS.between(
                            LocalDate.parse(allPatients.get(i).getEnrollmentDate()).withDayOfMonth(1),
                            LocalDate.parse(allPatients.get(i).getSampleCollectionDate()).withDayOfMonth(1));*/
                 
                    
                    if(allPatients.get(i).getSampleCollectionDate() == null)
                    {
                        //no sample has been taken at all. The patient is eligible for month 6 then
                        patientsEligibleMonth6.add(allPatients.get(i));
                    }
                    else if(monthsBetween ==6 || (monthsBetween + 1) == 6 || (monthsBetween - 1) == 6)
                    {
                        patientsWithSampleMonth6.add(allPatients.get(i));//sample was taken at six months
                        patientsEligibleMonth6.add(allPatients.get(i));
                    }
                    else if(monthsBetween == - 6 || (monthsBetween + 1) == -6 || (monthsBetween - 1) == -6){//that means sample was not taken at that time. lets check when the sample was taken if it is up to a year ago
                        patientsEligibleMonth6.add(allPatients.get(i));
                    }
                    
                    
                }

                
                List<OTZPatient> allPatients12 = otzDao.getTotalPtsEnrolledAndEligibleForVL(startDate, endDate,  12);
                for(int i=0; i<allPatients12.size(); i++)
                {
                    
                   DateTime enrollmentDate = new DateTime(allPatients12.get(i).getEnrollmentDate().substring(0, 10));
                    DateTime sampleCollectionDate = (allPatients12.get(i).getSampleCollectionDate() != null) ? new DateTime(allPatients12.get(i).getSampleCollectionDate().substring(0, 10)) : new DateTime();
                    
                    int monthsBetween = Months.monthsBetween(enrollmentDate, sampleCollectionDate).getMonths();
                  
                    
                    if(allPatients12.get(i).getSampleCollectionDate() == null)
                    {
                        //no sample has been taken at all. The patient is eligible for month 6 then
                        patientsEligibleMonth12.add(allPatients12.get(i));
                    }
                    else if(monthsBetween ==12 || (monthsBetween + 1) == 12 || (monthsBetween - 1) == 12)
                    {
                        patientsWithSampleMonth12.add(allPatients12.get(i));//sample was taken at six months
                        patientsEligibleMonth12.add(allPatients12.get(i));
                    }
                    else if(monthsBetween == - 12 || (monthsBetween + 1) == -12 || (monthsBetween - 1) == -12){//that means sample was not taken at that time. lets check when the sample was taken if it is up to a year ago
                        patientsEligibleMonth12.add(allPatients12.get(i));
                    }
                    
                    
                }
                
                List<OTZPatient> allPatients18 = otzDao.getTotalPtsEnrolledAndEligibleForVL(startDate, endDate,  18);
                for(int i=0; i<allPatients18.size(); i++)
                {
                    
                    DateTime enrollmentDate = new DateTime(allPatients18.get(i).getEnrollmentDate().substring(0, 10));
                    DateTime sampleCollectionDate = (allPatients18.get(i).getSampleCollectionDate() != null) ? new DateTime(allPatients18.get(i).getSampleCollectionDate().substring(0, 10)) : new DateTime();
                    
                    int monthsBetween = Months.monthsBetween(enrollmentDate, sampleCollectionDate).getMonths();
                   
                    
                    if(allPatients18.get(i).getSampleCollectionDate() == null)
                    {
                        //no sample has been taken at all. The patient is eligible for month 6 then
                        patientsEligibleMonth18.add(allPatients18.get(i));
                    }
                    else if(monthsBetween ==18 || (monthsBetween + 1) == 18 || (monthsBetween - 1) == 18)
                    {
                        patientsWithSampleMonth18.add(allPatients18.get(i));//sample was taken at six months
                        patientsEligibleMonth18.add(allPatients18.get(i));
                    }
                    else if(monthsBetween == - 18 || (monthsBetween + 1) == -18 || (monthsBetween - 1) == -18){//that means sample was not taken at that time. lets check when the sample was taken if it is up to a year ago
                        patientsEligibleMonth18.add(allPatients18.get(i));
                    }
                }
                
                
                
                
                
                //lets loop through and perform operations to get eligibility at month 6, 12 and 18
		
                JSONObject quarters = Misc.getQuartersBetweenDates(startDate, endDate);
                
                Map<String, Object> data = new HashMap<>();
                data.put("quarters", quarters);
                data.put("patientsEligibleMonth6", patientsEligibleMonth6);
                data.put("patientsEligibleMonth12", patientsEligibleMonth12);
                data.put("patientsEligibleMonth18", patientsEligibleMonth18);

                data.put("patientsWithSampleMonth6", patientsWithSampleMonth6);
                data.put("patientsWithSampleMonth12", patientsWithSampleMonth12);
                data.put("patientsWithSampleMonth18", patientsWithSampleMonth18);
		
                String json = new Gson().toJson(data);
		
		return json;
		
	}
}
