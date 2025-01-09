/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.openmrs.api.UserService;
import org.openmrs.module.dataquality.Misc;
import org.openmrs.module.dataquality.OTZPatient;
import org.openmrs.module.dataquality.OTZKeyValue;
import org.openmrs.module.dataquality.api.dao.ClinicalDaoHelper;
import org.openmrs.module.dataquality.api.dao.Database;
import org.openmrs.module.dataquality.api.dao.OTZDao;
import org.openmrs.module.dataquality.fragment.controller.OtzFragmentController;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ihvn
 */
public class OtzreportsummaryFragmentController {
	
	OTZDao otzDao = new OTZDao();
	
	OtzFragmentController otzFragmentController = new OtzFragmentController();
	
	public void controller(FragmentModel model, @SpringBean("userService") UserService service, HttpServletRequest request) {
		
		try {
			
			/*URL url = this.getClass().getResource("otz_info.json");
			//System.out.println(url.getPath());
			File f = new File(url.getPath());
			JSONObject obj = new JSONObject(FileUtils.readFileToString(f));
			
			model.addAttribute("otz_info", obj);*/
			if (request.getParameter("formattedMonthLength") != null) {
				int formattedMonthLength = Integer.parseInt(request.getParameter("formattedMonthLength"));
				model.addAttribute("formattedMonthLength", formattedMonthLength);
			} else {
				// Code to handle the case when formattedMonthLength is not defined
				model.addAttribute("formattedMonthLength", 0);
			}
			model.addAttribute("testing", "test");
			model.addAttribute("title", "OTZ");
			model.addAttribute("formattedMonthLength", 3);
			//model.addAttribute("formattedMonthLength", formattedMonthLength);
			//Database.initConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String sayHello() {
		return "Hello";
	}
	
	public Map<String, String> getAllEnrolledInOTZCurr(HttpServletRequest request) {
		
		DateTime startDateTime = new DateTime(request.getParameter("startDate"));
		DateTime endDateTime = new DateTime(request.getParameter("endDate"));
		String ageType = request.getParameter("ageType");
		//System.out.println(ageType+"in fragmentController");
		//Database.initConnection();
		
		String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		
		int male10To14_Active = 0;
		int male15To19_Active = 0;
		int male20To24_Active = 0;
		int maleabove24_Active = 0;
		
		int female10To14_Active = 0;
		int female15To19_Active = 0;
		int female20To24_Active = 0;
		int femaleabove24_Active = 0;
		
		List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
		if (allPatients == null) {
			System.out.println("empty set returned");
		}
		for (int i = 0; i < allPatients.size(); i++) {
			try {
				if ("curra".equals(ageType)) {
					if (allPatients.get(i).getGender().equalsIgnoreCase("M")
					        || allPatients.get(i).getGender().equalsIgnoreCase("Male")) {
						if (allPatients.get(i).getCage() >= 10 && allPatients.get(i).getCage() <= 14) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								male10To14_Active++;
							}
						} else if (allPatients.get(i).getCage() >= 15 && allPatients.get(i).getCage() <= 19) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								male15To19_Active++;
							}
						} else if (allPatients.get(i).getCage() >= 20 && allPatients.get(i).getCage() <= 24) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								male20To24_Active++;
							}
						} else if (allPatients.get(i).getCage() > 24) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								maleabove24_Active++;
							}
						}
					} else if (allPatients.get(i).getGender().equalsIgnoreCase("F")
					        || allPatients.get(i).getGender().equalsIgnoreCase("Female")) {
						if (allPatients.get(i).getCage() >= 10 && allPatients.get(i).getCage() <= 14) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								female10To14_Active++;
							}
						} else if (allPatients.get(i).getCage() >= 15 && allPatients.get(i).getCage() <= 19) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								female15To19_Active++;
							}
						} else if (allPatients.get(i).getCage() >= 20 && allPatients.get(i).getCage() <= 24) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								female20To24_Active++;
							}
						} else if (allPatients.get(i).getCage() > 24) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								femaleabove24_Active++;
							}
						}
					}
				} else {
					if (allPatients.get(i).getGender().equalsIgnoreCase("M")
					        || allPatients.get(i).getGender().equalsIgnoreCase("Male")) {
						if (allPatients.get(i).getAge() >= 10 && allPatients.get(i).getAge() <= 14) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								male10To14_Active++;
							}
						} else if (allPatients.get(i).getAge() >= 15 && allPatients.get(i).getAge() <= 19) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								male15To19_Active++;
							}
						} else if (allPatients.get(i).getAge() >= 20 && allPatients.get(i).getAge() <= 24) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								male20To24_Active++;
							}
						} else if (allPatients.get(i).getAge() > 24) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								maleabove24_Active++;
							}
						}
					} else if (allPatients.get(i).getGender().equalsIgnoreCase("F")
					        || allPatients.get(i).getGender().equalsIgnoreCase("Female")) {
						if (allPatients.get(i).getAge() >= 10 && allPatients.get(i).getAge() <= 14) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								female10To14_Active++;
							}
						} else if (allPatients.get(i).getAge() >= 15 && allPatients.get(i).getAge() <= 19) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								female15To19_Active++;
							}
						} else if (allPatients.get(i).getAge() >= 20 && allPatients.get(i).getAge() <= 24) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								female20To24_Active++;
							}
						} else if (allPatients.get(i).getAge() > 24) {
							
							if (allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")) {
								femaleabove24_Active++;
							}
						}
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Map<String, String> dataMap = new HashMap<>();
        dataMap.put("male10To14_Active", male10To14_Active + "");
        dataMap.put("male15To19_Active", male15To19_Active + "");
        dataMap.put("male20To24_Active", male20To24_Active + "");
        dataMap.put("maleabove24_Active", maleabove24_Active + "");
        dataMap.put("female10To14_Active", female10To14_Active + "");
        dataMap.put("female15To19_Active", female15To19_Active + "");
        dataMap.put("female20To24_Active", female20To24_Active + "");
        dataMap.put("femaleabove24_Active", femaleabove24_Active + "");
    
		
		//dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
		return dataMap;//new JSONObject(dataMap).toString();
	}
	
	public String getAllEnrolledInOTZ(HttpServletRequest request) {

            if (request.getParameter("startDate") == null || 
            request.getParameter("endDate") == null ||
            request.getParameter("ageType") == null) {
                System.out.println("Required parameters are missing");
            }

            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            String ageType = request.getParameter("ageType");
            //System.out.println(ageType+"in fragmentController");
            //Database.initConnection();

            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

           
            
            List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
            if (allPatients == null) {
                System.out.println("empty set returned");
            }
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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
           
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); 
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); 
            dataMap.put("femaleabove24",  femaleabove24+"");

            

            
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();
    }
	
	public Map<String, String> getAllEnrolledInOTZ(String startDate, String endDate, String ageType) {

            if (startDate == null || 
            endDate == null ||
            ageType == null) {
                System.out.println("Required parameters are missing");
            }

            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);
            
            //System.out.println(ageType+"in fragmentController");
            //Database.initConnection();

            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            int male10To14_Active=0;
            int male15To19_Active=0;
            int male20To24_Active=0; 
            int maleabove24_Active=0;
            
            int female10To14_Active=0;
            int female15To19_Active=0;
            int female20To24_Active=0; 
            int femaleabove24_Active=0;
           
            
            List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
            if (allPatients == null) {
                System.out.println("empty set returned");
            }
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            male10To14++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                male10To14_Active++;
                            }
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            male15To19++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                male15To19_Active++;
                            }
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            male20To24++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                male20To24_Active++;
                            }
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                            maleabove24++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                maleabove24_Active++;
                            }
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
                        {
                            female10To14++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                female10To14_Active++;
                            }
                        }else if(allPatients.get(i).getCage() >=15 && allPatients.get(i).getCage() <=19)
                        {
                            female15To19++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                female15To19_Active++;
                            }
                        }
                        else if(allPatients.get(i).getCage() >=20 && allPatients.get(i).getCage() <=24)
                        {
                            female20To24++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                female20To24_Active++;
                            }
                        }
                        else if(allPatients.get(i).getCage() > 24)
                        {
                           femaleabove24++;
                           if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                femaleabove24_Active++;
                            }
                        }
                    }
                }else{
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            male10To14++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                male10To14_Active++;
                            }
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            male15To19++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                male15To19_Active++;
                            }
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            male20To24++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                male20To24_Active++;
                            }
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                            maleabove24++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                maleabove24_Active++;
                            }
                        }
                    }
                    else if(allPatients.get(i).getGender().equalsIgnoreCase("F") || allPatients.get(i).getGender().equalsIgnoreCase("Female"))
                    {
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
                        {
                            female10To14++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                female10To14_Active++;
                            }
                        }else if(allPatients.get(i).getAge() >=15 && allPatients.get(i).getAge() <=19)
                        {
                            female15To19++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                female15To19_Active++;
                            }
                        }
                        else if(allPatients.get(i).getAge() >=20 && allPatients.get(i).getAge() <=24)
                        {
                            female20To24++;
                            if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                female20To24_Active++;
                            }
                        }
                        else if(allPatients.get(i).getAge() > 24)
                        {
                           femaleabove24++;
                           if(allPatients.get(i).getArtStatus().equalsIgnoreCase("Active")){
                                femaleabove24_Active++;
                            }
                        }
                    }   
                }
            }
            
            
            Map<String, String> dataMap = new HashMap<>();
           
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); 
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); 
            dataMap.put("femaleabove24",  femaleabove24+"");

            dataMap.put("male10To14_Active",  male10To14_Active+"");
            dataMap.put("male15To19_Active",  male15To19_Active+"");
            dataMap.put("male20To24_Active",  male20To24_Active+""); 
            dataMap.put("maleabove24_Active",  maleabove24_Active+"");
            dataMap.put("female10To14_Active",  female10To14_Active+"");
            dataMap.put("female15To19_Active",  female15To19_Active+"");
            dataMap.put("female20To24_Active",  female20To24_Active+""); 
            dataMap.put("femaleabove24_Active",  femaleabove24_Active+"");

            
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;
    }
	
	public String getAllFullDisc(HttpServletRequest request, HttpServletResponse response) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            //Database.initConnection();

            //System.out.println("start date time"+startDateTime);
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
          
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;
          
            
            List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZFullDisclosure(startDate, endDate);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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
           
            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalEnrolledWithScheduledPickup6MonthsBefore(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            DateTime sixMonthsAgoB = startDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
            String sixMonthsB = sixMonthsAgoB.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithScheduledPickup6MonthsBefore(startDate, endDate, sixMonthsB);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public Map<String, String> getTotalEnrolledWithScheduledPickup6MonthsBefore(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);     
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            DateTime sixMonthsAgoB = startDateTime.minusMonths(6);
            //Database.initConnection();

            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
            String sixMonthsB = sixMonthsAgoB.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithScheduledPickup6MonthsBefore(startDate, endDate, sixMonthsB);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public Map<String, String> getTotalEnrolledWhoKeptScheduledPickup6MonthsBefore(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWhoKeptScheduledPickup6MonthsBefore(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return dataMap;

    }
	
	public Map<String, String> getTotalEnrolledWhoKeptScheduledPickup6MonthsBefore2(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);             
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWhoKeptScheduledPickup6MonthsBefore(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public Map<String, String> getTotalEnrolledWithGoodAdhScore6MonthsBefore(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            DateTime sixMonthsAgoB = startDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
            String sixMonthsB = sixMonthsAgoB.toString("yyyy'-'MM'-'dd");

            
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithGoodAdhScore6MonthsBefore(startDate, endDate, sixMonthsB);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return dataMap;//new JSONObject(dataMap).toString();

    }
	
	public Map<String, String> getTotalEnrolledWithGoodAdhScore6MonthsBefore2(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);             
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            DateTime sixMonthsAgoB = startDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");
            String sixMonthsB = sixMonthsAgoB.toString("yyyy'-'MM'-'dd");

            
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithGoodAdhScore6MonthsBefore(startDate, endDate, sixMonthsB);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public String getTotalEnrolledWithVL12MonthsBefore(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBefore(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public Map<String, String> getTotalEnrolledWithVL12MonthsBefore(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);             
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBefore(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public String getTotalEnrolledWithVL12MonthsBeforeAndBelow200(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBeforeAndBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public Map<String, String> getTotalEnrolledWithVL12MonthsBeforeAndBelow200(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);             
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBeforeAndBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public String getTotalEnrolledWithVL12MonthsBeforeAndBtw200AND1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBeforeAndBtw200AND1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public Map<String, String> getTotalEnrolledWithVL12MonthsBeforeAndBtw200AND1000(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);             
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBeforeAndBtw200AND1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap; 
    }
	
	public Map<String, String> getTotalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return dataMap;

    }
	
	public Map<String, String> getTotalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000V2(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);             
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public String getTotalEnrolledWithVL6MonthsBefore(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBefore(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public Map<String, String> getTotalEnrolledWithVL6MonthsBefore(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);             
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBefore(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public Map<String, String> getTotalEnrolledWithVL6MonthsBeforeAndBelow200(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBeforeAndBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return dataMap;

    }
	
	public Map<String, String> getTotalEnrolledWithVL6MonthsBeforeAndBelow200V2(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);            
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBeforeAndBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public String getTotalEnrolledWithVL6MonthsBeforeAndBtw200AND1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBeforeAndBtw200AND1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public Map<String, String> getTotalEnrolledWithVL6MonthsBeforeAndBtw200AND1000(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);       
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBeforeAndBtw200AND1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public String getTotalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public Map<String, String> getTotalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);            
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public Map<String, String> getTotalEligibleForMonthZeroVL(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEligibleForMonthZeroVL(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return dataMap;

    }
	
	public Map<String, String> getTotalEligibleForMonthZeroVL2(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);             
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEligibleForMonthZeroVL(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public Map<String, String> dnt(HttpServletRequest request) {
            
            Map<String, String> dataMap = new HashMap<>();
            List<OTZKeyValue> OTZKeyValues = otzDao.dnt();
            for(int i=0; i<OTZKeyValues.size(); i++)
            {

                dataMap.put(OTZKeyValues.get(i).getKeyString(),  OTZKeyValues.get(i).getValueString()+"");
                
            }      
            
            return dataMap;
    }
	
	public Map<String, String> getTotalEligibleForMonthZeroVLWithSampleCollectedAtEnrollment(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEligibleForMonthZeroVLWithSampleCollectedAtEnrollment(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return dataMap;

    }
	
	public Map<String, String> getTotalEligibleForMonthZeroVLWithSampleCollectedAtEnrollment2(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);            
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEligibleForMonthZeroVLWithSampleCollectedAtEnrollment(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public Map<String, String> getTotalWithBaseLineVLBelow1000AndMonthZeroVlBelow200(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return dataMap;

    }
	
	public Map<String, String> getTotalWithBaseLineVLBelow1000AndMonthZeroVlBelow200V2(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);            
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public String getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove200(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public Map<String, String> getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove200(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);             
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public String getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public Map<String, String> getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000(String startDate, String endDate, String ageType) {
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);            
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            //String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            //String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            //return new JSONObject(dataMap).toString();
            return dataMap;

    }
	
	public String getTotalEnrolledWithScheduledPickupAfter(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));             String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithScheduledPickupAfter(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWhoKeptScheduledPickupAfter(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithGoodAdhScoreAfter(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVL(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTaken(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTakenAndResult(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTakenAndResultBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove200Below1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12Months(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove200Below1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000CompletedEAC(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVl(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlBelow200(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove1000(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithSwitchTo2ndLine(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalEnrolledWithSwitchTo3rdLine(startDate, endDate, sixMonths);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
            //dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
            return new JSONObject(dataMap).toString();

    }
	
	public String getTotalAYPLHIVEnrolledInOTZWhoComplete7(HttpServletRequest request) {
            DateTime startDateTime = new DateTime(request.getParameter("startDate"));
            DateTime endDateTime = new DateTime(request.getParameter("endDate"));
            String ageType = request.getParameter("ageType");
            DateTime sixMonthsAgo = endDateTime.minusMonths(6);
            //System.out.println(ageType+"in getTotalAYPLHIVEnrolledInOTZWhoComplete7");
            //Database.initConnection();

            
            String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
            String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
            String sixMonths = sixMonthsAgo.toString("yyyy'-'MM'-'dd");

           
            int male10To14=0;
            int male15To19=0;
            int male20To24=0; int maleabove24=0;
            int female10To14=0;
            int female15To19=0;
            int female20To24=0; int femaleabove24=0;

            List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZWhoComplete7(startDate, endDate);
            for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

            dataMap.put("male10To14",  male10To14+"");
            dataMap.put("male15To19",  male15To19+"");
            dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
            dataMap.put("female10To14",  female10To14+"");
            dataMap.put("female15To19",  female15To19+"");
            dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

	        int male10To14=0;
	        int male15To19=0;
	        int male20To24=0; int maleabove24=0;
	        int female10To14=0;
	        int female15To19=0;
	        int female20To24=0; int femaleabove24=0;

	        List<OTZPatient> allPatients = otzDao.getTotalEnrolledAndTransferredOutAfter(startDate, endDate);
	        for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

	        dataMap.put("male10To14",  male10To14+"");
	        dataMap.put("male15To19",  male15To19+"");
	        dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
	        dataMap.put("female10To14",  female10To14+"");
	        dataMap.put("female15To19",  female15To19+"");
	        dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

	        int male10To14=0;
	        int male15To19=0;
	        int male20To24=0; int maleabove24=0;
                
	        int female10To14=0;
	        int female15To19=0;
	        int female20To24=0; int femaleabove24=0;
               

	        List<OTZPatient> allPatients = otzDao.getTotalEnrolledAndLTFUAfter(startDate, endDate);
	        for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

	        dataMap.put("male10To14",  male10To14+"");
	        dataMap.put("male15To19",  male15To19+"");
	        dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
                dataMap.put("maleabove24",  maleabove24+"");
	        dataMap.put("female10To14",  female10To14+"");
	        dataMap.put("female15To19",  female15To19+"");
	        dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
                dataMap.put("femaleabove24",  femaleabove24+"");
                //System.out.println("From OTZ Frag");
                //System.out.println(dataMap);
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

	        int male10To14=0;
	        int male15To19=0;
	        int male20To24=0; int maleabove24=0;
	        int female10To14=0;
	        int female15To19=0;
	        int female20To24=0; int femaleabove24=0;

	        List<OTZPatient> allPatients = otzDao.getTotalEnrolledAndDiedAfter(startDate, endDate);
	        for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

	        dataMap.put("male10To14",  male10To14+"");
	        dataMap.put("male15To19",  male15To19+"");
	        dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
	        dataMap.put("female10To14",  female10To14+"");
	        dataMap.put("female15To19",  female15To19+"");
	        dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

	        int male10To14=0;
	        int male15To19=0;
	        int male20To24=0; int maleabove24=0;
	        int female10To14=0;
	        int female15To19=0;
	        int female20To24=0; int femaleabove24=0;

	        List<OTZPatient> allPatients = otzDao.getTotalEnrolledAndOptedOutAfter(startDate, endDate);
	        for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

	        dataMap.put("male10To14",  male10To14+"");
	        dataMap.put("male15To19",  male15To19+"");
	        dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
	        dataMap.put("female10To14",  female10To14+"");
	        dataMap.put("female15To19",  female15To19+"");
	        dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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

	        int male10To14=0;
	        int male15To19=0;
	        int male20To24=0; int maleabove24=0;
                
	        int female10To14=0;
	        int female15To19=0;
	        int female20To24=0; int femaleabove24=0;
                

	        List<OTZPatient> allPatients = otzDao.getTotalEnrolledAndTransitionedAfter(startDate, endDate);
               
	        for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

	        dataMap.put("male10To14",  male10To14+"");
	        dataMap.put("male15To19",  male15To19+"");
	        dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
                dataMap.put("maleabove24",  maleabove24+"");
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

	        int male10To14=0;
	        int male15To19=0;
	        int male20To24=0; int maleabove24=0;
	        int female10To14=0;
	        int female15To19=0;
	        int female20To24=0; int femaleabove24=0;

	        List<OTZPatient> allPatients = otzDao.getTotalEnrolledAndExitedAfter(startDate, endDate);
	        for(int i=0; i<allPatients.size(); i++)
            {
                if("curra".equals(ageType)){
                    if(allPatients.get(i).getGender().equalsIgnoreCase("M") || allPatients.get(i).getGender().equalsIgnoreCase("Male"))
                    {
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
                        if(allPatients.get(i).getCage() >=10 && allPatients.get(i).getCage() <=14)
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
                        if(allPatients.get(i).getAge() >=10 && allPatients.get(i).getAge() <=14)
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

	        dataMap.put("male10To14",  male10To14+"");
	        dataMap.put("male15To19",  male15To19+"");
	        dataMap.put("male20To24",  male20To24+""); dataMap.put("maleabove24",  maleabove24+"");
	        dataMap.put("female10To14",  female10To14+"");
	        dataMap.put("female15To19",  female15To19+"");
	        dataMap.put("female20To24",  female20To24+""); dataMap.put("femaleabove24",  femaleabove24+"");
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
                for(int j=0; j<monthsBetweenDates; j +=6)
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
                        DateTime enrollmentDate = new DateTime(allPatients.get(i).getEnrollmentDate().substring(0, 10));
                        DateTime sampleCollectionDate = (allPatients.get(i).getPreviousSampleCollectionDate() != null) ? new DateTime(allPatients.get(i).getPreviousSampleCollectionDate().substring(0, 10)) : new DateTime();
                        DateTime newSampleCollectionDate = (allPatients.get(i).getSampleCollectionDate() != null) ? new DateTime(allPatients.get(i).getSampleCollectionDate().substring(0, 10)) : new DateTime();

                        
                        //we need the last day of the month for the enrollment date
                        int lastDateInCohortMonth = enrollmentDate.dayOfMonth().getMaximumValue();
                        
                        
                        
                        DateTime expectedSampleCollectionDateForMonth = enrollmentDate.plusMonths(j);
                        
                        int daysDifference = Days.daysBetween(sampleCollectionDate, expectedSampleCollectionDateForMonth).getDays();
                        //get number of months between previous sample collection date and expected sample collection date. This helps us to know if the patient is eligible
                        int monthsBetween = Months.monthsBetween(sampleCollectionDate, expectedSampleCollectionDateForMonth).getMonths();
                        
                        //get the number of months between the expected sample collection date and the actual sample collection date to know if sample was indeed collected
                        int monthsBetweenExpectedAndActual = Months.monthsBetween(expectedSampleCollectionDateForMonth, newSampleCollectionDate).getMonths();
                        
                        
                        
                        //sample was either npatientsWithResultPast6Monthsot taken or was taken at the right day. That means they are eliglble
                        
                        //patient may not be eligible if the sample is not collected as when due
                        if(monthsBetween >= 6 ) {
                        	patientsEligible.add(allPatients.get(i));//once they have been on ART for up to six months before the month of investigation
                        }
                        ////System.out.println(monthsBetween+"---"+j);
                        
                        
                        if(monthsBetweenExpectedAndActual == 0  && newSampleCollectionDate != null)//due to data entry errors, there are cases where a patient has vl result but no sample collection date. So we need to also check for sample collection date too
                        {
                             patientsWithSample.add(allPatients.get(i));
                             //for there to be result, sample must have been taken
                            if(allPatients.get(i).getViralLoad() != -1)
                            {
                                patientsWithResult.add(allPatients.get(i));
                               
                            }
                        }
                        
                        /*if((daysDifference >= 0 && daysDifference <=7) || (daysDifference <= 0 && daysDifference >=-7))//was taken 
                        {
                             patientsWithSample.add(allPatients.get(i));
                             
                             //for there to be result, sample has to be taken
                            if(allPatients.get(i).getViralLoad() != 0)
                            {
                                patientsWithResult.add(allPatients.get(i));
                               
                            }
                             
                        }*/
                        
                        
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
                    //List<OTZPatient> allPatientsScheduled = otzDao.getTotalEnrolledWithScheduledPickup6MonthsBeforeAndAfter(startDate, endDate, j,  sixMonthsAgo, futureEndDate2);
                    List<OTZPatient> allPatientsKept = otzDao.getTotalEnrolledWhoKeptScheduledPickupMonthN(startDate, endDate,  sixMonthsAgo, futureEndDate, j);
                    //List<OTZPatient> allPatientsKept = otzDao.getTotalEnrolledWhoKeptScheduledPickup6MonthsBeforeAndAfter(startDate, endDate,  j,  sixMonthsAgo, futureEndDate2);
                    List<OTZPatient> allPatientsGoodScore = otzDao.getTotalEnrolledWithGoodAdhScoreMonthN(startDate, endDate,  sixMonthsAgo, futureEndDate);
                    //List<OTZPatient> allPatientsGoodScore = otzDao.getTotalEnrolledWithGoodAdhScore6MonthsBeforeAndAfter(startDate, endDate,  j,  sixMonthsAgo, futureEndDate2);
                    
                    data.put("allPatientsScheduled"+j, allPatientsScheduled);
                    data.put("allPatientsKept"+j, allPatientsKept);
                    data.put("allPatientsGoodScore"+j, allPatientsGoodScore);
                    
                    
                    
                   
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
                
              
            Map<String, Object> dataToReturn = new HashMap<>() ;
            try{
                ObjectMapper objectMapper = new ObjectMapper();
                dataToReturn = objectMapper.readValue(data.toString(), Map.class);
            }catch(Exception e)
            {

            }
               //String json = new Gson().toJson(data);
		return dataToReturn;
		//return data;
		
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
	
	@RequestMapping(method = RequestMethod.POST)
    @ResponseBody
	public void getAllEnrolledInOTZ2(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("ageType") String ageType, HttpServletResponse response) {
        
		System.out.println("ageType " + ageType);
		System.out.println("startDate: " + startDate);
		System.out.println("endDate- " + endDate);


		// String startDat = startDateTime.toString("yyyy'-'MM'-'dd");
	    // String endDat = endDateTime.toString("yyyy'-'MM'-'dd");



        DateTime beginDate = new DateTime(startDate);
        if (beginDate.getYear() < 2019) {
            beginDate = new DateTime(2019, 1, 1, 0, 0);
        }
        DateTime finishDate = new DateTime(endDate);

        System.out.println("Begin date: " + beginDate.toString());
        System.out.println("Finish date: " + finishDate.toString());

        List<MonthRange> monthsData = getStartAndEndDates(beginDate.toDate(), finishDate.toDate());
        //i want to sys out monthsData
        System.out.println("Months data: ");
        for (MonthRange mr : monthsData) {
            System.out.println(mr.toString());
        }

      
        List<Map<String, Object>> formattedMonthsData = getFormattedMonthsData(monthsData);
        // i want to sys out formattedMonthsData
        System.out.println("Formatted months data: ");
        for (Map<String, Object> mr : formattedMonthsData) {
            System.out.println(mr.toString());
        }

        
        
		
		// Get your data
		List<OTZPatient> allPatients = otzDao.getTotalAYPLHIVEnrolledInOTZ(startDate, endDate);
		
		System.out.println("allPatients " + allPatients.size());
		
		// Create workbook using Apache POI
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("OTZ Data");
		int rowNum = 0;
		// Create headers
		Row headerRow = sheet.createRow(rowNum);
		headerRow.createCell(0).setCellValue("Implementing Partner");
		headerRow.createCell(1).setCellValue("State");
		headerRow.createCell(2).setCellValue("LGA");
		headerRow.createCell(3).setCellValue("Facility name");
		headerRow.createCell(4).setCellValue("DATIM Code");
		headerRow.createCell(5).setCellValue("Cohort Month (MM/YYYY)");
		headerRow.createCell(6).setCellValue("Sex");
		headerRow.createCell(7).setCellValue("Age Band");
		headerRow.createCell(8).setCellValue("# of AYPLHIV currently on ART in the supported facility");
		headerRow.createCell(9).setCellValue("# of AYPLHIV enrolled in OTZ in the cohort month");
		headerRow.createCell(10).setCellValue(
		    "# of OTZ members with scheduled drug pick-up appointment in the last six months prior to enrolment on OTZ");
		headerRow.createCell(11).setCellValue(
		    "# of OTZ members who kept their drug pick-up appointment in the last six months prior to enrolment on OTZ");
		headerRow.createCell(12).setCellValue(
		    "# of OTZ members with good drug adherence score in the last six months prior to enrolment on OTZ");
		headerRow.createCell(13).setCellValue(
		    "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ");
		headerRow
		        .createCell(14)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result less than 200 c/ml");
		headerRow
		        .createCell(15)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result between 200 to less than 1000 c/ml");
		headerRow
		        .createCell(16)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result greater than or equal to 1000 c/ml");
		headerRow.createCell(17).setCellValue(
		    "# of AYPLHIV in OTZ with VL results at baseline within the last 6 months at enrolment into OTZ");
		headerRow
		        .createCell(18)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL less than 200 c/ml");
		headerRow
		        .createCell(19)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL result is between 200 to less than 1000 c/ml");
		headerRow
		        .createCell(20)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL greater than or equal to 1000 c/ml");
		headerRow
		        .createCell(21)
		        .setCellValue(
		            "# of AYPLHIV in OTZ without baseline VL results or with baseline VL result less than 1000 c/ml and eligible for month zero VL sample collection");
		headerRow
		        .createCell(22)
		        .setCellValue(
		            "# of AYPLHIV in OTZ enrolled in the cohort month and eligible for month zero VL sample collection whose VL samples were taken (at month zero)");
		headerRow
		        .createCell(23)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with baseline VL results less than 1000 c/ml whose VL result for sample collected at month zero is less than 200 c/ml");
		headerRow
		        .createCell(24)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with baseline VL results less 1000 c/ml whose VL result for sample collected at month zero is betwen 200 to less than 1000 c/ml");
		headerRow
		        .createCell(25)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with baseline VL results less 1000 c/ml  whose VL result for sample collected at month zero is greater than or equal to 1000 c/ml");
		headerRow.createCell(26).setCellValue("Follow up");
		headerRow.createCell(27).setCellValue(
		    "# of OTZ members with scheduled drug pick-up appointment in the follow up period");
		headerRow.createCell(28).setCellValue(
		    "# of OTZ members who kept their drug pick-up appointment in the follow up period");
		headerRow.createCell(29).setCellValue("# of OTZ members with good drug adherence score in the follow up period");
		headerRow
		        .createCell(30)
		        .setCellValue(
		            "# of AYPLHIV in OTZ who were eligible for routine VL test during the follow up period i.e. No VL result for the 6-month period prior to the beginning of the reporting period");
		headerRow.createCell(31).setCellValue("# of AYPLHIV in OTZ whose samples were taken for routine VL test");
		headerRow.createCell(32).setCellValue(
		    "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period");
		headerRow
		        .createCell(33)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period less than 200 copies/ml");
		headerRow
		        .createCell(34)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period between 200 to less than 1000 copies/ml");
		headerRow
		        .createCell(35)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period greater than or equal to 1000 copies/ml");
		headerRow.createCell(36).setCellValue(
		    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months");
		headerRow
		        .createCell(37)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ  in the cohort month with VL result within the last 12 months less than 200 copies/ml");
		headerRow
		        .createCell(38)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months is between 200 to less than 1000 copies/ml");
		headerRow
		        .createCell(39)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ  in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml");
		headerRow
		        .createCell(40)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml and completed EAC");
		headerRow
		        .createCell(41)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml who have repeat VL result");
		headerRow
		        .createCell(42)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml whose repeat VL result is less than 200 copies/ml");
		headerRow
		        .createCell(43)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml whose repeat VL result is between 200 to less than 1000 copies/ml");
		headerRow
		        .createCell(44)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml whose repeat VL result is greater than or equal to 1000 copies/ml");
		headerRow.createCell(45).setCellValue("# switched to second line ART");
		headerRow.createCell(46).setCellValue("# switched to third line ART");
		headerRow.createCell(47).setCellValue("# of OTZ members who have completed the 7 AYP modules");
		headerRow.createCell(48).setCellValue("# transferred out during the follow up period");
		headerRow.createCell(49).setCellValue("# Lost to follow up during the follow up period");
		headerRow.createCell(50).setCellValue("# reported dead during the follow up period");
		headerRow.createCell(51).setCellValue("# that opted out of OTZ during the follow up period");
		headerRow.createCell(52).setCellValue(
		    "# aged 20-24 years and transitioned to adult care during the follow up  period");
		headerRow.createCell(53).setCellValue("# that exited OTZ during the follow up period");
		// Add more headers as needed
		// call dnt
        Map<String, String> facilityDataMap = new HashMap<>();
        List<OTZKeyValue> otzKeyValues = otzDao.dnt();
        
        for (OTZKeyValue kv : otzKeyValues) {
            facilityDataMap.put(kv.getKeyString(), kv.getValueString());
        }


        
        String[] rowNames = {"m10to14", "m15to19", "m20to24", "f10To14", "f15To19", "f20To24"};

        Map<String, Row> rows = new LinkedHashMap<>();

        
        for (int jsgroovar = 1; jsgroovar <= formattedMonthsData.size(); jsgroovar++) {

            //get month year string
            Map<String, Object> formattedMonthsDataItr = formattedMonthsData.get(jsgroovar - 1);
            String startDateStr = (String) formattedMonthsDataItr.get("startDate");
            DateTime newDateFromStart = new DateTime(startDateStr);         
            String monthYearStringg = monthsName[newDateFromStart.getMonthOfYear() - 1] + " " + (newDateFromStart.getYear() + 1900);
            // i want to sys out startDateStr, jsgroovar, newDateFromStart, monthYearStringg, everything on a single line
            System.out.println(startDateStr + " " + jsgroovar + " " + newDateFromStart + " " + monthYearStringg);
            //get month year string

            //this block dynamically builds row maps and access each row from the map
            /*
            for (int i = 0; i < rowNames.length; i++) {
                String dynamicRowName = rowNames[i] + "_" + jsgroovar + "_" + rowNum++;
                rows.put(dynamicRowName, sheet.createRow(rowNum));
            }
             */
                
            // Access each row from the map and create cells
            /*
            for (Map.Entry<String, Row> entry : rows.entrySet()) {

                Row row = entry.getValue();
                String rowname = entry.getKey();
                row.createCell(0).setCellValue(rowname);

                row.createCell(5).setCellValue(monthYearStringg);

                // ...
            }
            */
            //this block dynamically builds row maps and access each row from the map


            Row m10to14 = sheet.createRow(++rowNum);
            m10to14.createCell(0).setCellValue(facilityDataMap.get("Parner_Name"));
            m10to14.createCell(1).setCellValue(facilityDataMap.get("Facility_State"));
            m10to14.createCell(2).setCellValue(facilityDataMap.get("Facility_LGA"));
            m10to14.createCell(3).setCellValue(facilityDataMap.get("Facility_Name"));
            m10to14.createCell(4).setCellValue(facilityDataMap.get("DATIM_Code"));

            Date startD = monthsData.get(jsgroovar - 1).startDate;
            //calculateDates(startD, jsgroovar);
            
        }
         

        /*
        Row m10to14 = sheet.createRow(++rowNum);
		m10to14.createCell(0).setCellValue(facilityDataMap.get("Parner_Name"));
		m10to14.createCell(1).setCellValue(facilityDataMap.get("Facility_State"));
		m10to14.createCell(2).setCellValue(facilityDataMap.get("Facility_LGA"));
		m10to14.createCell(3).setCellValue(facilityDataMap.get("Facility_Name"));
		m10to14.createCell(4).setCellValue(facilityDataMap.get("DATIM_Code"));
		 */ 
        
        
        /*
		// Populate data
		int rowNum = 2;
		for (OTZPatient patient : allPatients) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(patient.getPatientId());
			row.createCell(1).setCellValue(patient.getGender());
			row.createCell(2).setCellValue(patient.getCage());
			// Add more cells as needed
		}
		 */


		// Set response headers for file download
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=OTZ_Report.xlsx");
        response.setHeader("Cache-Control", "no-cache");

        try (ServletOutputStream out = response.getOutputStream()) {
            workbook.write(out);
            out.flush();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@RequestMapping(method = RequestMethod.POST)
    @ResponseBody
	public void getAllEnrolledInOTZ3(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("ageType") String ageType, HttpServletResponse response) {
        
		System.out.println("ageType " + ageType);
		System.out.println("startDate: " + startDate);
		System.out.println("endDate- " + endDate);


		// String startDat = startDateTime.toString("yyyy'-'MM'-'dd");
	    // String endDat = endDateTime.toString("yyyy'-'MM'-'dd");



        DateTime beginDate = new DateTime(startDate);
        if (beginDate.getYear() < 2019) {
            beginDate = new DateTime(2019, 1, 1, 0, 0);
        }
        DateTime finishDate = new DateTime(endDate);

        System.out.println("Begin date: " + beginDate.toString());
        System.out.println("Finish date: " + finishDate.toString());

        List<MonthRange> monthsData = getStartAndEndDates(beginDate.toDate(), finishDate.toDate());
        //i want to sys out monthsData
        System.out.println("Months data: ");
        for (MonthRange mr : monthsData) {
            System.out.println(mr.toString());
        }

      
        List<Map<String, Object>> formattedMonthsData = getFormattedMonthsData(monthsData);
        // i want to sys out formattedMonthsData
        System.out.println("Formatted months data: ");
        for (Map<String, Object> mr : formattedMonthsData) {
            System.out.println(mr.toString());
        }

        
 
		
		// Create workbook using Apache POI
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("OTZ Data");
		int rowNum = 0;
		// Create headers
		Row headerRow = sheet.createRow(rowNum);
		headerRow.createCell(0).setCellValue("Implementing Partner");
		headerRow.createCell(1).setCellValue("State");
		headerRow.createCell(2).setCellValue("LGA");
		headerRow.createCell(3).setCellValue("Facility name");
		headerRow.createCell(4).setCellValue("DATIM Code");
		headerRow.createCell(5).setCellValue("Cohort Month (MM/YYYY)");
		headerRow.createCell(6).setCellValue("Sex");
		headerRow.createCell(7).setCellValue("Age Band");
		headerRow.createCell(8).setCellValue("# of AYPLHIV currently on ART in the supported facility");
		headerRow.createCell(9).setCellValue("# of AYPLHIV enrolled in OTZ in the cohort month");
		headerRow.createCell(10).setCellValue(
		    "# of OTZ members with scheduled drug pick-up appointment in the last six months prior to enrolment on OTZ");
		headerRow.createCell(11).setCellValue(
		    "# of OTZ members who kept their drug pick-up appointment in the last six months prior to enrolment on OTZ");
		headerRow.createCell(12).setCellValue(
		    "# of OTZ members with good drug adherence score in the last six months prior to enrolment on OTZ");
		headerRow.createCell(13).setCellValue(
		    "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ");
		headerRow
		        .createCell(14)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result less than 200 c/ml");
		headerRow
		        .createCell(15)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result between 200 to less than 1000 c/ml");
		headerRow
		        .createCell(16)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result greater than or equal to 1000 c/ml");
		headerRow.createCell(17).setCellValue(
		    "# of AYPLHIV in OTZ with VL results at baseline within the last 6 months at enrolment into OTZ");
		headerRow
		        .createCell(18)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL less than 200 c/ml");
		headerRow
		        .createCell(19)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL result is between 200 to less than 1000 c/ml");
		headerRow
		        .createCell(20)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL greater than or equal to 1000 c/ml");
		headerRow
		        .createCell(21)
		        .setCellValue(
		            "# of AYPLHIV in OTZ without baseline VL results or with baseline VL result less than 1000 c/ml and eligible for month zero VL sample collection");
		headerRow
		        .createCell(22)
		        .setCellValue(
		            "# of AYPLHIV in OTZ enrolled in the cohort month and eligible for month zero VL sample collection whose VL samples were taken (at month zero)");
		headerRow
		        .createCell(23)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with baseline VL results less than 1000 c/ml whose VL result for sample collected at month zero is less than 200 c/ml");
		headerRow
		        .createCell(24)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with baseline VL results less 1000 c/ml whose VL result for sample collected at month zero is betwen 200 to less than 1000 c/ml");
		headerRow
		        .createCell(25)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with baseline VL results less 1000 c/ml  whose VL result for sample collected at month zero is greater than or equal to 1000 c/ml");
		headerRow.createCell(26).setCellValue("Follow up");
		headerRow.createCell(27).setCellValue(
		    "# of OTZ members with scheduled drug pick-up appointment in the follow up period");
		headerRow.createCell(28).setCellValue(
		    "# of OTZ members who kept their drug pick-up appointment in the follow up period");
		headerRow.createCell(29).setCellValue("# of OTZ members with good drug adherence score in the follow up period");
		headerRow
		        .createCell(30)
		        .setCellValue(
		            "# of AYPLHIV in OTZ who were eligible for routine VL test during the follow up period i.e. No VL result for the 6-month period prior to the beginning of the reporting period");
		headerRow.createCell(31).setCellValue("# of AYPLHIV in OTZ whose samples were taken for routine VL test");
		headerRow.createCell(32).setCellValue(
		    "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period");
		headerRow
		        .createCell(33)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period less than 200 copies/ml");
		headerRow
		        .createCell(34)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period between 200 to less than 1000 copies/ml");
		headerRow
		        .createCell(35)
		        .setCellValue(
		            "# of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period greater than or equal to 1000 copies/ml");
		headerRow.createCell(36).setCellValue(
		    "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months");
		headerRow
		        .createCell(37)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ  in the cohort month with VL result within the last 12 months less than 200 copies/ml");
		headerRow
		        .createCell(38)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months is between 200 to less than 1000 copies/ml");
		headerRow
		        .createCell(39)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ  in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml");
		headerRow
		        .createCell(40)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml and completed EAC");
		headerRow
		        .createCell(41)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml who have repeat VL result");
		headerRow
		        .createCell(42)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml whose repeat VL result is less than 200 copies/ml");
		headerRow
		        .createCell(43)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml whose repeat VL result is between 200 to less than 1000 copies/ml");
		headerRow
		        .createCell(44)
		        .setCellValue(
		            "# of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml whose repeat VL result is greater than or equal to 1000 copies/ml");
		headerRow.createCell(45).setCellValue("# switched to second line ART");
		headerRow.createCell(46).setCellValue("# switched to third line ART");
		headerRow.createCell(47).setCellValue("# of OTZ members who have completed the 7 AYP modules");
		headerRow.createCell(48).setCellValue("# transferred out during the follow up period");
		headerRow.createCell(49).setCellValue("# Lost to follow up during the follow up period");
		headerRow.createCell(50).setCellValue("# reported dead during the follow up period");
		headerRow.createCell(51).setCellValue("# that opted out of OTZ during the follow up period");
		headerRow.createCell(52).setCellValue(
		    "# aged 20-24 years and transitioned to adult care during the follow up  period");
		headerRow.createCell(53).setCellValue("# that exited OTZ during the follow up period");
		// Add more headers as needed
		// call dnt
        Map<String, String> facilityDataMap = new HashMap<>();
        List<OTZKeyValue> otzKeyValues = otzDao.dnt();
        
        for (OTZKeyValue kv : otzKeyValues) {
            facilityDataMap.put(kv.getKeyString(), kv.getValueString());
        }

        String[] rowNames = {"male10To14", "male15To19", "male20To24", "female10To14", "female15To19", "female20To24"};
        
        Map<String, Row> rows = new LinkedHashMap<>();

        Date today = new Date();
        for (int jsgroovar = 1; jsgroovar <= formattedMonthsData.size(); jsgroovar++) {

            //get month year string
            Map<String, Object> formattedMonthsDataItr = formattedMonthsData.get(jsgroovar - 1);
            String startDateStr = (String) formattedMonthsDataItr.get("startDate");
            String endDateStr = (String) formattedMonthsDataItr.get("endDate");
            DateTime newDateFromStart = new DateTime(startDateStr);         
            String monthYearStringg = monthsName[newDateFromStart.getMonthOfYear() - 1] + "-" + String.valueOf(newDateFromStart.getYear());

            


            System.out.println(startDateStr + " " + jsgroovar + " " + newDateFromStart + " " + monthYearStringg);
            
    
            Map<String, String> totalEnrolledWithScheduledPickup6MonthsBefore = getTotalEnrolledWithScheduledPickup6MonthsBefore(startDateStr, endDateStr, ageType);
            Map<String, String> allEnrolledInOTZ= getAllEnrolledInOTZ(startDateStr, endDateStr, ageType);
            Map<String, String> totalEnrolledWhoKeptScheduledPickup6MonthsBefore= getTotalEnrolledWhoKeptScheduledPickup6MonthsBefore2(startDateStr, endDateStr, ageType);
            Map<String, String> totalEnrolledWithGoodAdhScore6MonthsBefore= getTotalEnrolledWithGoodAdhScore6MonthsBefore2(startDateStr, endDateStr, ageType);
            Map<String, String> totalEnrolledWithVL12MonthsBefore= getTotalEnrolledWithVL12MonthsBefore(startDateStr, endDateStr, ageType);
            Map<String, String> totalEnrolledWithVL12MonthsBeforeAndBelow200= getTotalEnrolledWithVL12MonthsBeforeAndBelow200(startDateStr, endDateStr, ageType);
            Map<String, String> totalEnrolledWithVL12MonthsBeforeAndBtw200AND1000= getTotalEnrolledWithVL12MonthsBeforeAndBtw200AND1000(startDateStr, endDateStr, ageType);
            Map<String, String> totalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000= getTotalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000V2(startDateStr, endDateStr, ageType);
            Map<String, String> totalEnrolledWithVL6MonthsBefore= getTotalEnrolledWithVL6MonthsBefore(startDateStr, endDateStr, ageType);
            Map<String, String> totalEnrolledWithVL6MonthsBeforeAndBelow200= getTotalEnrolledWithVL6MonthsBeforeAndBelow200V2(startDateStr, endDateStr, ageType);
            Map<String, String> totalEnrolledWithVL6MonthsBeforeAndBtw200AND1000= getTotalEnrolledWithVL6MonthsBeforeAndBtw200AND1000(startDateStr, endDateStr, ageType);
            Map<String, String> totalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000= getTotalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000(startDateStr, endDateStr, ageType);
            Map<String, String> totalEligibleForMonthZeroVL= getTotalEligibleForMonthZeroVL2(startDateStr, endDateStr, ageType);
            Map<String, String> totalEligibleForMonthZeroVLWithSampleCollectedAtEnrollment= getTotalEligibleForMonthZeroVLWithSampleCollectedAtEnrollment2(startDateStr, endDateStr, ageType);
            Map<String, String> totalWithBaseLineVLBelow1000AndMonthZeroVlBelow200= getTotalWithBaseLineVLBelow1000AndMonthZeroVlBelow200V2(startDateStr, endDateStr, ageType);
            Map<String, String> totalWithBaseLineVLBelow1000AndMonthZeroVlAbove200= getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove200(startDateStr, endDateStr, ageType);
            Map<String, String> totalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000= getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000(startDateStr, endDateStr, ageType);
            Map<String, Object> vlAccessResults = otzFragmentController.getPatientsVLAccess(startDate, endDate, ageType);

            
            //System.out.println("VL Access Results: " + vlAccessResults);
            /*
            System.out.println("VL Access Results2: ");
            for (Map.Entry<String, Object> entry : vlAccessResults.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            */
            
            List<OTZPatient> patientstozs = (List<OTZPatient>) vlAccessResults.get("patientsEligible" + 0);
            for(OTZPatient patientoz : patientstozs) {
                String enrollmentDate = patientoz.getEnrollmentDate();
                System.out.println("ozzzzzzzzzzzzzzzzzzzzzzzz: " + enrollmentDate);
                //String artStatus = patient.getArt_status();
                // Access other OTZPatient properties as needed
            }


            //System.out.println("totalEnrolledWithScheduledPickup6MonthsBefore: " + totalEnrolledWithScheduledPickup6MonthsBefore);


            //this block dynamically builds row maps and access each row from the map
            
             //String gender = "", ageCategory = "";

            for (int i = 0; i < rowNames.length; i++) {
                String dynamicRowName = rowNames[i] + "_" + jsgroovar + "_" + rowNum++;


                Row currentRow = sheet.createRow(rowNum);
                rows.put(dynamicRowName, currentRow);
                
                currentRow.createCell(0).setCellValue(facilityDataMap.get("Parner_Name"));
                currentRow.createCell(1).setCellValue(facilityDataMap.get("Facility_State")); 
                currentRow.createCell(2).setCellValue(facilityDataMap.get("Facility_LGA"));
                currentRow.createCell(3).setCellValue(facilityDataMap.get("Facility_Name"));
                currentRow.createCell(4).setCellValue(facilityDataMap.get("DATIM_Code"));
                currentRow.createCell(5).setCellValue(monthYearStringg);
                currentRow.createCell(6).setCellValue(determineGender(rowNames[i]));
                currentRow.createCell(7).setCellValue(determineCategory(rowNames[i]));
                currentRow.createCell(9).setCellValue(allEnrolledInOTZ.get(rowNames[i]));
                currentRow.createCell(10).setCellValue(totalEnrolledWithScheduledPickup6MonthsBefore.get(rowNames[i]));
                currentRow.createCell(11).setCellValue(totalEnrolledWhoKeptScheduledPickup6MonthsBefore.get(rowNames[i]));
                currentRow.createCell(12).setCellValue(totalEnrolledWithGoodAdhScore6MonthsBefore.get(rowNames[i]));
                currentRow.createCell(13).setCellValue(totalEnrolledWithVL12MonthsBefore.get(rowNames[i]));
                currentRow.createCell(14).setCellValue(totalEnrolledWithVL12MonthsBeforeAndBelow200.get(rowNames[i]));
                currentRow.createCell(15).setCellValue(totalEnrolledWithVL12MonthsBeforeAndBtw200AND1000.get(rowNames[i]));
                currentRow.createCell(16).setCellValue(totalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000.get(rowNames[i]));
                currentRow.createCell(17).setCellValue(totalEnrolledWithVL6MonthsBefore.get(rowNames[i]));
                currentRow.createCell(18).setCellValue(totalEnrolledWithVL6MonthsBeforeAndBelow200.get(rowNames[i]));
                currentRow.createCell(19).setCellValue(totalEnrolledWithVL6MonthsBeforeAndBtw200AND1000.get(rowNames[i]));
                currentRow.createCell(20).setCellValue(totalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000.get(rowNames[i]));
                currentRow.createCell(21).setCellValue(totalEligibleForMonthZeroVL.get(rowNames[i]));
                currentRow.createCell(22).setCellValue(totalEligibleForMonthZeroVLWithSampleCollectedAtEnrollment.get(rowNames[i]));
                currentRow.createCell(23).setCellValue(totalWithBaseLineVLBelow1000AndMonthZeroVlBelow200.get(rowNames[i]));
                currentRow.createCell(24).setCellValue(totalWithBaseLineVLBelow1000AndMonthZeroVlAbove200.get(rowNames[i]));
                currentRow.createCell(25).setCellValue(totalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000.get(rowNames[i]));
                currentRow.createCell(26).setCellValue("month 6");
                currentRow.createCell(27).setCellValue("mth6");
                currentRow.createCell(28).setCellValue("mth6");
                currentRow.createCell(29).setCellValue("mth6");
                currentRow.createCell(30).setCellValue("mth6");
                currentRow.createCell(31).setCellValue("mth6");
                currentRow.createCell(32).setCellValue("mth6");
                currentRow.createCell(33).setCellValue("mth6");
                currentRow.createCell(34).setCellValue("mth6");
                currentRow.createCell(35).setCellValue("mth6");
                currentRow.createCell(36).setCellValue("mth6");

                
            }
            

            //get date in date format for monthdiff
            Date date_newDateFromStart = new Date(newDateFromStart.getMillis());
            int monthDifference = monthDiff(date_newDateFromStart, today);
            for (int k = 6; k <= monthDifference; k += 6) {
                if(k==6){
                    
                }else if(k>6){
                    for (int m = 0; m < rowNames.length; m++) {
                        // Dynamically add more rows
                    String dynamicRowName2 = rowNames[m] + "_" + jsgroovar + "_" + rowNum++;
                    Row additionalRow = sheet.createRow(rowNum);
                    rows.put(dynamicRowName2, additionalRow);

                    additionalRow.createCell(0).setCellValue(facilityDataMap.get("Parner_Name"));
                    additionalRow.createCell(1).setCellValue(facilityDataMap.get("Facility_State")); 
                    additionalRow.createCell(2).setCellValue(facilityDataMap.get("Facility_LGA"));
                    additionalRow.createCell(3).setCellValue(facilityDataMap.get("Facility_Name"));
                    additionalRow.createCell(4).setCellValue(facilityDataMap.get("DATIM_Code"));
                    additionalRow.createCell(5).setCellValue(monthYearStringg);
                    additionalRow.createCell(6).setCellValue(determineGender(rowNames[m]));
                    additionalRow.createCell(7).setCellValue(determineCategory(rowNames[m]));
                    additionalRow.createCell(9).setCellValue("");
                    additionalRow.createCell(10).setCellValue("");
                    additionalRow.createCell(11).setCellValue("");
                    additionalRow.createCell(12).setCellValue("");
                    additionalRow.createCell(13).setCellValue("");
                    additionalRow.createCell(14).setCellValue("");
                    additionalRow.createCell(15).setCellValue("");
                    additionalRow.createCell(16).setCellValue("");
                    additionalRow.createCell(17).setCellValue("");
                    additionalRow.createCell(18).setCellValue("");
                    additionalRow.createCell(19).setCellValue("");
                    additionalRow.createCell(20).setCellValue("");
                    additionalRow.createCell(21).setCellValue("");
                    additionalRow.createCell(22).setCellValue("");
                    additionalRow.createCell(23).setCellValue("");
                    additionalRow.createCell(24).setCellValue("");
                    additionalRow.createCell(25).setCellValue("");
                    additionalRow.createCell(26).setCellValue("month " + k);
                    additionalRow.createCell(27).setCellValue("zz");
                    additionalRow.createCell(28).setCellValue("zz");
                    additionalRow.createCell(29).setCellValue("zz");
                    additionalRow.createCell(30).setCellValue("zz");
                    additionalRow.createCell(31).setCellValue("zz");
                    additionalRow.createCell(32).setCellValue("zz");
                    additionalRow.createCell(33).setCellValue("zz");
                    additionalRow.createCell(34).setCellValue("zz");
                    additionalRow.createCell(35).setCellValue("zz");
                    additionalRow.createCell(36).setCellValue("zz");

                    }
                }
            }
            
            //this block dynamically builds row maps and access each row from the map


            /*
            Row m10to14 = sheet.createRow(++rowNum);
            m10to14.createCell(0).setCellValue(facilityDataMap.get("Parner_Name"));
            m10to14.createCell(1).setCellValue(facilityDataMap.get("Facility_State"));
            m10to14.createCell(2).setCellValue(facilityDataMap.get("Facility_LGA"));
            m10to14.createCell(3).setCellValue(facilityDataMap.get("Facility_Name"));
            m10to14.createCell(4).setCellValue(facilityDataMap.get("DATIM_Code"));
            Date startD = monthsData.get(jsgroovar - 1).startDate;
            //calculateDates(startD, jsgroovar);
            */
            
        }
         

        /*
        Row m10to14 = sheet.createRow(++rowNum);
		m10to14.createCell(0).setCellValue(facilityDataMap.get("Parner_Name"));
		m10to14.createCell(1).setCellValue(facilityDataMap.get("Facility_State"));
		m10to14.createCell(2).setCellValue(facilityDataMap.get("Facility_LGA"));
		m10to14.createCell(3).setCellValue(facilityDataMap.get("Facility_Name"));
		m10to14.createCell(4).setCellValue(facilityDataMap.get("DATIM_Code"));
		 */ 
        
        
        /*
		// Populate data
		int rowNum = 2;
		for (OTZPatient patient : allPatients) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(patient.getPatientId());
			row.createCell(1).setCellValue(patient.getGender());
			row.createCell(2).setCellValue(patient.getCage());
			// Add more cells as needed
		}
		 */


		// Set response headers for file download
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=OTZ_Report.xlsx");
        response.setHeader("Cache-Control", "no-cache");

        try (ServletOutputStream out = response.getOutputStream()) {
            workbook.write(out);
            out.flush();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private List<MonthRange> getStartAndEndDates(Date startMonth, Date endMonth) {
            List<MonthRange> months = new ArrayList<>();
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startMonth);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentYear = calendar.get(Calendar.YEAR);
            
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endMonth);
            
            while (currentMonth <= endCalendar.get(Calendar.MONTH) || currentYear < endCalendar.get(Calendar.YEAR)) {
                Calendar startDate = Calendar.getInstance();
                startDate.set(currentYear, currentMonth, 1);
                
                Calendar endDate = Calendar.getInstance();
                endDate.set(currentYear, currentMonth + 1, 0);
                
                MonthRange monthRange = new MonthRange();
                monthRange.setMonth(currentMonth + 1);
                monthRange.setStartDate(startDate.getTime());
                monthRange.setEndDate(endDate.getTime());
                months.add(monthRange);
                
                currentMonth++;
                if (currentMonth > 11) {
                    currentMonth = 0;
                    currentYear++;
                }
            }
            
            return months;
    }
	
	private class MonthRange {
		
		private int month;
		
		private Date startDate;
		
		private Date endDate;
		
		public void setMonth(int month) {
			this.month = month;
		}
		
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
	}
	
	private List<Map<String, Object>> getFormattedMonthsData(List<MonthRange> monthsData) {
            List<Map<String, Object>> formattedMonths = new ArrayList<>();
            
            for (int i = 0; i < monthsData.size(); i++) {
                Calendar startCal = Calendar.getInstance();
                startCal.setTime(monthsData.get(i).startDate);
                
                int year = startCal.get(Calendar.YEAR);
                int month = monthsData.get(i).month;
                int day = startCal.get(Calendar.DATE);
                
                Calendar endCal = Calendar.getInstance();
                endCal.setTime(monthsData.get(i).endDate);
                int eday = endCal.get(Calendar.DATE);
                
                String formattedDate = String.format("%d-%02d-%02d", year, month, day);
                String eformattedDate = String.format("%d-%02d-%02d", year, month, eday);
                
                Map<String, Object> monthData = new HashMap<>();
                monthData.put("month", i + 1);
                monthData.put("startDate", formattedDate);
                monthData.put("endDate", eformattedDate);
                
                formattedMonths.add(monthData);
            }
            
            return formattedMonths;
    }
	
	final String[] monthsName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	
	public String determineGender(String rowName) {
		if (rowName.toLowerCase().contains("female")) {
			return "Female";
		} else if (rowName.toLowerCase().contains("male")) {
			return "Male";
		} else {
			return "Unknown";
		}
	}
	
	public String determineCategory(String rowName) {
		if (rowName.toLowerCase().contains("10to14")) {
			return "10-14 yrs";
		} else if (rowName.toLowerCase().contains("15to19")) {
			return "15-19 yrs";
		} else if (rowName.toLowerCase().contains("20to24")) {
			return "20-24 yrs";
		} else {
			return "Unknown";
		}
	}
	
	// Helper method to calculate months difference
	private int monthDiff(Date startDate, Date endDate) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		
		int diffYear = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);
		
		return diffMonth;
	}
	
}
