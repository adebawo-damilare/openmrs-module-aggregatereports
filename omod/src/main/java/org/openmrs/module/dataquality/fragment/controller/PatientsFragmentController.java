/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.dataquality.fragment.controller;

import org.openmrs.module.dataquality.api.dao.ClinicalDaoHelper;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
//import liquibase.util.csv.opencsv.CSVWriter;
import com.opencsv.CSVWriter;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import org.joda.time.DateTime;
import org.openmrs.module.dataquality.Constants;
//import org.openmrs.module.dataquality.api.CohortBuilder;
import org.openmrs.module.dataquality.api.DataqualityService;
import org.openmrs.module.dataquality.api.dao.LabDao;
import org.openmrs.module.dataquality.api.dao.PharmacyDao;

public class PatientsFragmentController {
	
	DataqualityService dataQualityService = Context.getService(DataqualityService.class);
	
	ClinicalDaoHelper clinicalDaoHelper = new ClinicalDaoHelper();
	
	PharmacyDao pharmacyDao = new PharmacyDao();
	
	LabDao labDao = new LabDao();
	
	public void controller(FragmentModel model, HttpServletRequest request) {
		
		int type = Integer.parseInt(request.getParameter("type"));
		DateTime endDateTime = new DateTime(new Date());
		DateTime startDateTime = endDateTime.minusYears(100);
		String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		
		model.addAttribute("type", type);
		if (type == Constants.ACTIVE_DOCUMENTED_EDUCATIONAL_STATUS_COHORT) {
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
			} else {
				
				startDateTime = new DateTime("1990-01-01");
				endDateTime = new DateTime(new Date());
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			List<Map<String, String>> patientData = clinicalDaoHelper.getActivePtsWithoutWithEducationalStatus(startDate,
			    endDate);
			model.addAttribute("data", patientData);
			
			model.addAttribute("title", "Active patients without a documented educational status ");
			//return totalActiveWithDocEducationalStatus+"";
		} else if (type == Constants.ACTIVE_DOCUMENTED_MARITAL_STATUS_COHORT) {
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
			} else {
				
				startDateTime = new DateTime("1990-01-01");
				endDateTime = new DateTime(new Date());
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			List<Map<String, String>> patientData = clinicalDaoHelper.getActivePtsWithoutMaritalStatus(startDate, endDate);
			model.addAttribute("data", patientData);
			model.addAttribute("title", "Active patients without a documented marital status ");
			
		} else if (type == Constants.ACTIVE_DOCUMENTED_OCCUPATIONAL_STATUS_COHORT) {
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
			} else {
				
				startDateTime = new DateTime("1990-01-01");
				endDateTime = new DateTime(new Date());
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			List<Map<String, String>> patientData = clinicalDaoHelper
			        .getActivePtsWithoutOccupationStatus(startDate, endDate);
			model.addAttribute("data", patientData);
			
			model.addAttribute("data", patientData);
			
			model.addAttribute("title", "Active patients without a documented occupational status ");
			
		} else if (type == Constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_DOB) {
			
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " without documented age and/or Date of Birth ";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months without documented age and/or Date of Birth ";
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			List<Map<String, String>> patientData = clinicalDaoHelper.getPtsStartedOnARTWithoutDocDob(startDate, endDate);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			model.addAttribute("data", patientData);
			
			model.addAttribute("title", title);
			
		} else if (type == Constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_SEX) {
			System.out.println("5");
			
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " without without a documented gender  ";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months without a documented gender  ";
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			List<Map<String, String>> patientData = clinicalDaoHelper.getPtsStartedOnARTWithoutDocGender(startDate, endDate);
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			model.addAttribute("data", patientData);
			
			model.addAttribute("title", title);
		} else if (type == Constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_DATECONFIRMED_POSITIVE) {
			
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " without without a documented date of HIV diagnosis   ";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months without a documented date of HIV diagnosis   ";
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			List<Map<String, String>> patientData = clinicalDaoHelper.getPtsStartedOnARTWithoutDocHIVDiagnosisDate(
			    startDate, endDate);
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			model.addAttribute("data", patientData);
			
			model.addAttribute("title", title);
			
		} else if (type == Constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_HIVENROLLMENT) {
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " without  a documented  HIV Enrollment  ";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months without a documented  HIV Enrollment  ";
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			List<Map<String, String>> patientData = clinicalDaoHelper.getPtsStartedOnARTWithoutDocHIVEnrollmentDate(
			    startDate, endDate);
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			model.addAttribute("data", patientData);
			
			model.addAttribute("title", title);
			
		}
		
		else if (type == Constants.DOCUMENTED_ART_START_DATE_ARV_PICKUP_COHORT) {
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " without a documented Drug pickup ";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months without a documented Drug pickup  ";
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			List<Map<String, String>> patientData = clinicalDaoHelper
			        .getPtsStartedOnARTWithDocDrugPickup(startDate, endDate);
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			model.addAttribute("data", patientData);
			
			model.addAttribute("title", title);
		} else if (type == Constants.STARTED_ART_LAST_6MONTHS_DOCUMENTED_CD4_COUNT) {
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " without documented  CD4 Count";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months without documented  CD4 Count ";
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			//System.out.println(startDate);
			//System.out.println(endDate);
			
			List<Map<String, String>> patientData = clinicalDaoHelper.getPtsStartedOnARTWithDocCd4(startDate, endDate);
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("data", patientData);
			model.addAttribute("title", title);
			
		} else if (type == Constants.NEWLY_STARTED_ON_ART_WITH_DOCUMENTED_LGA) {
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " without a registered address/LGA of residence  ";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months without a without registered address/LGA of residence  ";
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			List<Map<String, String>> patientData = clinicalDaoHelper
			        .getPtsStartedOnARTWithoutDocAddress(startDate, endDate);
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			model.addAttribute("data", patientData);
			
			model.addAttribute("title", title);
			
		} else if (type == Constants.CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_WEIGH) {
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " that had no documented weight  ";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months that had no documented weight  ";
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			List<Map<String, String>> patientData = clinicalDaoHelper.getPtsWithClinicVisitWithoutDocWeight(startDate,
			    endDate);
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("data", patientData);
			model.addAttribute("title", title);
			
		} else if (type == Constants.PEDIATRIC_CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_MUAC) {
			
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " that had no documented MUAC  ";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months that had no documented MUAC  ";
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			List<Map<String, String>> patientData = clinicalDaoHelper
			        .getPtsWithClinicVisitWithoutDocMuac(startDate, endDate);
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("data", patientData);
			model.addAttribute("title", title);
			
		}
		
		else if (type == Constants.CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_WHO) {
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " that had no documented WHO clinical stage  ";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months that had no documented WHO clinical stage  ";
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			List<Map<String, String>> patientData = clinicalDaoHelper.getPtsWithClinicVisitWithoutDocStaging(startDate,
			    endDate);
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("data", patientData);
			model.addAttribute("title", title);
		}
		
		else if (type == Constants.CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_TB_STATUS) {
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " that had no documented TB status ";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months that had no documented TB status  ";
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			List<Map<String, String>> patientData = clinicalDaoHelper.getPtsWithClinicVisitWithoutDocTBStatus(startDate,
			    endDate);
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("data", patientData);
			model.addAttribute("title", title);
			
		} else if (type == Constants.LAST_ARV_PHARMACY_PICKUP_WITH_DURATION) {
			System.out.println("15");
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				model.addAttribute("title",
				    "Proportion of patients without a documented ART regimen duration in the last drug refill visit");
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = new DateTime("1990-01-01");
				model.addAttribute("title",
				    "Proportion of patients without a documented ART regimen duration in the last drug refill visit");
			}
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			List<Map<String, String>> patientData = pharmacyDao.getAllPtsWithoutLastPickupDuration(startDate, endDate);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("data", patientData);
			
		} else if (type == Constants.LAST_ARV_PHARMACY_PICKUP_WITH_QUANTITY) {
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				model.addAttribute("title",
				    "Proportion of patients without a documented ART regimen quantity in the last drug refill visit as at "
				            + endDate);
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = new DateTime("1990-01-01");
				model.addAttribute("title",
				    "Proportion of patients without a documented ART regimen quantity in the last drug refill visit");
			}
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			List<Map<String, String>> patientData = pharmacyDao.getAllPtsWithoutLastPickupQuantity(startDate, endDate);
			model.addAttribute("data", patientData);
			
		} else if (type == Constants.LAST_ARV_PHARMACY_PICKUP_WITH_REGIMEN) {
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				model.addAttribute("title",
				    "Proportion of patients without documented ART regimen in the last drug refill visit as at " + endDate);
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = new DateTime("1990-01-01");
				model.addAttribute("title",
				    "Proportion of patients without documented ART regimen in the last drug refill visit");
			}
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			List<Map<String, String>> patientData = pharmacyDao.getAllPtsWithLastPickupNoRegimen(startDate, endDate);
			model.addAttribute("data", patientData);
			
		} else if (type == Constants.LAST_ARV_PHARMACY_PICKUP_WITH_DURATION_MORETHAN180DAYS) {
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				model.addAttribute("title",
				    "Proportion of patients with ART regimen duration  more than six(6) months in the last drug refill visit between "
				            + startDate + " and " + endDate);
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = new DateTime("1990-01-01");
				startDate = startDateTime.toString("yyyy'-'MM'-'dd");
				endDate = endDateTime.toString("yyyy'-'MM'-'dd");
				model.addAttribute("title",
				    "Proportion of patients with ART regimen duration  more than six(6) months in the last drug refill visit");
			}
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			List<Map<String, String>> patientData = pharmacyDao.getAllPtsWithLastPickupQtyMoreThan180(startDate, endDate);
			model.addAttribute("data", patientData);
			
		}
		
		else if (type == Constants.VIRAL_LOAD_ELIGIBLE_WITH_DOCUMENTED_RESULT) {
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				model.addAttribute("title",
				    "Proportion of eligible patients without documented Viral Load results done between  " + startDate
				            + " and " + endDate);
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(12);
				
				model.addAttribute("title",
				    "Proportion of eligible patients without documented Viral Load results done in the last one year");
			}
			
			DateTime startDateTime2 = startDateTime.minusMinutes(12);
			DateTime endDateTime2 = endDateTime.minusMonths(6);
			
			String startDate2 = startDateTime2.toString("yyyy'-'MM'-'dd");
			String endDate2 = endDateTime2.toString("yyyy'-'MM'-'dd");
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			List<Map<String, String>> patientData = labDao
			        .getAllPtsEligibleForVLWithoutResult(endDate2, startDate2, endDate);
			model.addAttribute("data", patientData);
			
		}
		
		else if (type == Constants.VIRAL_LOAD_RESULT_WITH_SAMPLE_COLLECTION_DATE) {
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				model.addAttribute("title",
				    "Proportion of patients with Viral Load result that had no documented specimen collection date between "
				            + startDate + " and " + endDate);
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = new DateTime("1990-01-01");
				model.addAttribute("title",
				    "Proportion of patients with Viral Load result that had no documented specimen collection date ");
			}
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			List<Map<String, String>> patientData = labDao.getAllPtsWithVlResultWithoutCollectionDate(startDate, endDate);
			
			model.addAttribute("data", patientData);
			
		} else if (type == Constants.SAMPLE_SENT_WITH_SAMPLE_RECEIVED_AT_PCR) {
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				model.addAttribute("title",
				    "Proportion of patients with Viral Load result that had no documented date received at PCR lab date between "
				            + startDate + " and " + endDate);
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = new DateTime("1990-01-01");
				model.addAttribute("title",
				    "Proportion of patients with Viral Load result that had no documented date received at PCR lab ");
			}
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			//List<Map<String, String>> patientData = labDao.getAllPtsWithVlResultAndNoSampleSentDate(startDate, endDate);
			
			List<Map<String, String>> patientData = labDao.getAllPtsWithVlResultAndNoSampleReceivedDate(startDate, endDate);
			model.addAttribute("data", patientData);
			
		} else if (type == Constants.CLINIC_VISIT_LAST_6MONTHS_WITH_FUNCTIONAL_STATUS) {
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " that had no documented functional status ";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months that had no documented functional status  ";
			}
			
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			
			List<Map<String, String>> patientData = clinicalDaoHelper.getPtsWithClinicVisitWithoutDocFunctionalStatus(
			    startDate, endDate);
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("data", patientData);
			model.addAttribute("title", title);
			
		}
		
		else if (type == Constants.STARTED_ART_LAST_6MONTHS_WITH_INITIAL_REGIMEN) {
			System.out.println("23");
			
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				title = "Proportion of patients newly started on ART between " + startDate + " and " + endDate
				        + " without initial ART regimen ";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months without initial ART regimen ";
			}
			List<Map<String, String>> patientData = clinicalDaoHelper.getPtsStartedOnARTWithoutInitialRegimen(startDate,
			    endDate);
			model.addAttribute("data", patientData);
			model.addAttribute("title", title);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
		}
		
		else if (type == Constants.CLINIC_VISIT_LAST_6MONTHS_DOCUMENTED_NEXT_APPOINTMENT_DATE) {
			String title = "";
			
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				title = "Proportion of patients  started on ART in between " + startDate + " and " + endDate
				        + " that had no documented next scheduled appointment date";
			} else {
				endDateTime = new DateTime(new Date());
				startDateTime = endDateTime.minusMonths(6);
				title = "Proportion of patients newly started on ART in the last 6 months that had no documented next scheduled appointment date ";
			}
			startDate = startDateTime.toString("yyyy'-'MM'-'dd");
			endDate = endDateTime.toString("yyyy'-'MM'-'dd");
			List<Map<String, String>> patientData = clinicalDaoHelper
			        .getPtsWithClinicVisitDocNextAppDate(startDate, endDate);
			
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("data", patientData);
			model.addAttribute("title", title);
			
		}
		
		else if (type == Constants.DOCUMENTED_EXIT_REASON_INACTIVE_COHORT) {
			if (request.getParameter("startDate") != null && !request.getParameter("startDate").equalsIgnoreCase("")) {
				startDateTime = new DateTime(request.getParameter("startDate"));
				endDateTime = new DateTime(request.getParameter("endDate"));
				model.addAttribute("title", "Proportion of all inactive patients without a documented exit reason ");
			} else {
				startDateTime = new DateTime(new Date());
				endDateTime = new DateTime("1990-01-01");
				model.addAttribute("title", "Proportion of all inactive patients without a documented exit reason ");
			}
			
			List<Map<String, String>> patientData = clinicalDaoHelper.getInactiveActivePtsWithDocReason(startDate, endDate);
			model.addAttribute("data", patientData);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
		}
		
		model.addAttribute("constants", Constants.class);
		
	}
}
