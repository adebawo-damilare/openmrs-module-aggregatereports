<% 

ui.decorateWith("appui", "standardEmrPage") %>
<%
ui.includeJavascript("dataquality", "myAjax.js")
ui.includeJavascript("dataquality", "popper.js")

%>

<%


ui.includeJavascript("dataquality", "jquery-3.3.1.js")
ui.includeJavascript("dataquality", "jquery.dataTables.min.js")
ui.includeJavascript("dataquality", "datatables.min.js")
ui.includeJavascript("dataquality", "buttons.flash.min.js")
ui.includeJavascript("dataquality", "bootstrap.min.js")
//ui.includeJavascript("dataquality", "bootstrap-datepicker.js")
ui.includeJavascript("dataquality", "jquery-ui.js")
ui.includeCss("dataquality", "bootstrap.min.css")

ui.includeCss("dataquality", "jquery-ui.css")
//ui.includeCss("dataquality", "bootstrap-datepicker.css")
ui.includeJavascript("dataquality", "jszip.min.js")
ui.includeJavascript("dataquality", "pdfmake.min.js")
ui.includeJavascript("dataquality", "vfs_fonts.js")
ui.includeJavascript("dataquality", "buttons.html5.min.js")
ui.includeJavascript("dataquality", "buttons.print.min.js")
ui.includeJavascript("dataquality", "table2excel.js")
ui.includeJavascript("dataquality", "table2csv.min.js")
//ui.includeJavascript("dataquality", "xlsx.core.min.js")
												   
ui.includeCss("dataquality", "buttons.dataTables.min.css")
ui.includeCss("dataquality", "jquery.dataTables.min.css")
ui.includeCss("dataquality", "myStyle.css")

def id = config.id
def months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"] as String[];

int i=1;
int year = Calendar.getInstance().get(Calendar.YEAR);
%>

<%=ui.resourceLinks()%>
<style>
    .table thead th
    {
        vertical-align: middle !important;
    }
</style>

<div class="container">
    
    <h2>OTZ Report</h2>
    

    <div class="row">
  <div class="col-7"><img width="500" src="${ ui.resourceLink("dataquality", "images/OTZ_bg.gif") }" /></div>
  <div class="col-5">

    
    
    
     <fieldset>
        <legend>Filters</legend>
        <div style="text-align: right">
            <div style="display: none; text-align: left;">
                    <fieldset>
                        <legend style="font-weight: bold; font-size: 13pt;"><span style="display: inline-block; width: 400px;">Display by:</span></legend>
                        <strong><input type="radio" name="agedisplaytype" value="aaeradio" class="aaeradio" />Age at Enrollment</strong>
                        <strong><input type="radio" name="agedisplaytype" value="caradio" class="caradio" />Current Age</strong>
                    </fieldset>

            </div>
        </div>
        
        <div class="row">
            <div class="col-sm-6 col-md-12">
                <label class="cohortDesc" style="display: none">Search By Cohort</label><br /><br />
            </div>    
        </div>
        <div class="row">
            <div class="col-sm-6 col-md-3" style="display: none">
                <strong><input type="radio" name="cohortType" value="monthly" class="cohortSelector" />Monthly</strong>
            </div>
            <div class="col-sm-6 col-md-3" style="display: none"> 
                <strong><input type="radio" name="cohortType" value="quarterlycy" class="cohortSelector" />Quarterly (CY)</strong>
            </div>
            <div class="col-sm-6 col-md-3" style="display: none"> 
                <strong><input type="radio" name="cohortType" value="quarterly" class="cohortSelector" />Quarterly (FY)</strong>
            </div>
            <div class="col-sm-6 col-md-3" style="display: none">
                <strong><input type="radio" name="cohortType"  value="dateRange" class="cohortSelector" />Date Range</strong>
            </div>
            
        </div><br />
        
        <div class="row hidden cohortArea" id="monthly">
            
           <label class="col-sm-6 col-md-2 " ><strong>Month</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
                <select name="month" id="month" class="form-control">
                    <option value="">Select Month</option>
                    <% for(int j=0; j<months.length; j++){%>
                        <option value="<%= j+1 %>"><%= months[j] %></option>
                    <% }%>
                </select>
            </div>
            
            <label class="col-sm-6 col-md-2 "><strong>Year</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
                <select name="monthYear" id="monthYear" class="form-control">
              <% for(int k=year; k>=2000; k--){%>
                 <option value="<%= k %>"><%= k %></option>
                    <% }%>
                </select>
            </div>
            
            
            <div class="col-sm-6 col-md-2">
                <button class="button" id="filterOTZMonthly">Filter</button>
            </div>
        </div>
        <div class="row hidden cohortArea" id="quarterlycy">
            
           <label class="col-sm-6 col-md-2 " ><strong>Quarter</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <select name="quartercy" id="quartercy" class="form-control">
                   <option value="">Select Quarter</option>
                   <option value="1">Quarter 1</option>
                   <option value="2">Quarter 2</option>
                   <option value="3">Quarter 3</option>
                   <option value="4">Quarter 4</option>
                   
                </select>
            </div>
            
            <label class="col-sm-6 col-md-2 "><strong>Year</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <select name="quarterYearcy" id="quarterYearcy" class="form-control">
              <% for(int k=year; k>=2000; k--){%>
                 <option value="<%= k %>">CY <%= k %></option>
                    <% }%>
                </select>
            </div>
            
            
            <div class="col-sm-6 col-md-2">
                <button class="button" id="filterOTZQuarterlycy">Filter</button>
            </div>
        </div>
        <div class="row hidden cohortArea" id="quarterly">
            
           <label class="col-sm-6 col-md-2 " ><strong>Quarter</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <select name="quarter" id="quarter" class="form-control">
                   <option value="">Select Quarter</option>
                   <option value="1">Quarter 1</option>
                   <option value="2">Quarter 2</option>
                   <option value="3">Quarter 3</option>
                   <option value="4">Quarter 4</option>
                   
                </select>
            </div>
            
            <label class="col-sm-6 col-md-2 "><strong>Year</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <select name="quarterYear" id="quarterYear" class="form-control">
              <% for(int k=year; k>=2000; k--){%>
                 <option value="<%= k %>">FY <%= k %></option>
                    <% }%>
                </select>
            </div>
            
            
            <div class="col-sm-6 col-md-2">
                <button class="button" id="filterOTZQuarterly">Filter</button>
            </div>
        </div>
            <div class="row cohortArea" id="dateRange" style="padding-left: 50px;">
            <div class="row">
           <label><strong>Start Date</strong></label>
           </div>
           <div class="row">
            
               <input type="text" class="form-control date" id="startDate" name="startDate" style="width: 270px;"/>
            
            </div>
            <div class="row" style="margin-top: 10px;">
            <label><strong>End Date</strong></label>
            </div>
            <div class="row">
               <input type="text" class="form-control date" id="endDate" name="endDate" style="width: 270px;"/>
            </div>
            
            
           
            
            <div class="row" style="margin-top: 10px;">
                <div class="col text-right">
                    
                    <button class="button loadingViewButton confirm" id="filterOTZ"><i class="icon-play"></i>&nbsp; Run</button>
                </div>
            </div>
           
        </div>
    </fieldset>
    </div>
</div>    
    
    <br/>
    
    <div style="display: block">
        <div id="OTZReport" style="display: block">OTZ Report</div>
    </div>
      
    
      
      <nav>
        <div class="nav nav-tabs" id="nav-tab" role="tablist" style="display: none">
          <a class="nav-item nav-link active" id="nav-summary-tab" data-toggle="tab" href="#nav-summary" role="tab" aria-controls="nav-summary" aria-selected="true">OTZ Reports</a>
          <a class="nav-item nav-link" id="nav-optimal-tab" data-toggle="tab" href="#nav-optimal" role="tab" aria-controls="nav-optimal" aria-selected="false">OTZ Dashboard</a>
        </div>
      </nav>
      <div class="tab-content" id="nav-tabContent" style="display: none">
            <div class="tab-pane fade show active" id="nav-summary" role="tabpanel" aria-labelledby="nav-summary-tab">
                <%
                if (formattedMonthLength) {
                %>
                    <!-- ${ ui.includeFragment("dataquality", "otzdetails/otzreportsummary", [title:title, formattedMonthLength:formattedMonthLength]) } -->
                <%
                }
                %>
            </div>
        <div class="tab-pane fade" id="nav-optimal" role="tabpanel" aria-labelledby="nav-optimal-tab">${ ui.includeFragment("dataquality", "otzdetails/otzdashboard", [title:title]) }</div>

      </div>
      
      
      <ul class='custom-menu'>
  <li data-action="first">First thing</li>
  <li data-action="second">Second thing</li>
  <li data-action="third">Third thing</li>
</ul>
  <div class="modal fade" id="detailsModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document" >
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalTitle">Details</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

        
    <script src="/<%= ui.contextPath();%>/ms/uiframework/resource/dataquality/amcharts/amcharts/amcharts.js"></script>
    <script src="/<%= ui.contextPath();%>/ms/uiframework/resource/dataquality/amcharts/amcharts/serial.js"></script>
    <script src="/<%= ui.contextPath();%>/ms/uiframework/resource/dataquality/amcharts/amcharts/pie.js" type="text/javascript"></script>
    <script src="/<%= ui.contextPath();%>/ms/uiframework/resource/dataquality/amcharts/amcharts/amstock.js" type="text/javascript"></script>

    <script type="text/javascript">
    
    jq = jQuery;
    
    var lastDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
   
    
      var totalReportCount = 13;    
      var currReportCount = 0;
                 
      var totalPtsOnArt = 0;
      var totalPedsOnArt = 0;
      var totalAdultsTestedPositive = 0;
      var totalPedsTestedPositive = 0;
      var totalTransferred = 0;
      var totalCompleted7 = 0;
      var totalOptedOut = 0;
      var totalTransitioned = 0;
      var totalExited = 0;
      var totalLTFU = 0;
      var totalDead = 0;
      var startDate = "";
      var endDate = "";
      var ageTyp="enrola";
      console.log("log on load")
      console.log(${formattedMonthLength})
      var formattedMonthLength = ${formattedMonthLength};
      console.log("created")
      console.log(formattedMonthLength)
      console.log("from groovy")
      console.log(${formattedMonthLength})
      var initialDate = "";
      var indicatorDescription = {};

      
      jq(document).ready(function(e){      
      jq.get("/<%= ui.contextPath();%>/ms/uiframework/resource/dataquality/otz_info.json", function(data){
            indicatorDescription = data;
        })
      
        jq(".otzInfo").click(function(e){
        
            var key = jq(this).attr("data-key");
            var data = indicatorDescription[key];

            var title = data["title"];
            var description = data["description"];
            var source = data["source"];
            var calculation = data["calculation"];
            jq("#otzIndicatorTitle").html(title)
            jq("#otzIndicatorDescription").html(description)
            jq("#otzIndicatorSource").html(source)
            jq("#otzIndicatorCalculation").html(calculation)
        })
        jq('input[name=agedisplaytype]').change(function(){
        if(jq(this).val()=='aaeradio'){
        ageTyp="enrola";
        }
        if(jq(this).val()=='caradio'){
        ageTyp="curra";
        }
        
        });
        jq(".cohortSelector").change(function(){
            var selVal = jq(this).val();
            
            //monthly
            //quarterly
            //dateRange
            //if(selVal=='monthly'){
            //    console.log("Monthly selected");
            //}
            
            
            jq(".cohortArea").addClass("hidden");
            jq("#"+selVal).removeClass("hidden");
            var cDesc = jq(".cohortDesc").html();
            if(selVal=='monthly'){jq(".cohortDesc").html("Search by Monthly Cohort");}
            if(selVal=='quarterlycy'){jq(".cohortDesc").html("Search by Quarterly Cohort - Calendar Year");}
            if(selVal=='quarterly'){jq(".cohortDesc").html("Search by Quarterly Cohort - Fiscal Year");}
            if(selVal=='dateRange'){jq(".cohortDesc").html("Search by Cohort - Custom Date Range");}
            console.log("Selected value"+selVal);
        });
        
        jq("#quartercy").change(function(){
            var selValu = jq(this).val();

            if(selValu==1){jq(".cohortDesc").html("Search by Quarterly Cohort - Calendar Year (Jan - Mar)");}
            if(selValu==2){jq(".cohortDesc").html("Search by Quarterly Cohort - Calendar Year (Apr - Jun)");}
            if(selValu==3){jq(".cohortDesc").html("Search by Quarterly Cohort - Calendar Year (Jul - Sept)");}
            if(selValu==4){jq(".cohortDesc").html("Search by Quarterly Cohort - Calendar Year (Oct - Dec)");}
            console.log("Selected value"+selValu);
        });
        
        jq("#quarter").change(function(){
            var selValu = jq(this).val();

            if(selValu==1){jq(".cohortDesc").html("Search by Quarterly Cohort - FY (Oct - Dec)");}
            if(selValu==2){jq(".cohortDesc").html("Search by Quarterly Cohort - FY (Jan - Mar)");}
            if(selValu==3){jq(".cohortDesc").html("Search by Quarterly Cohort - FY (Apr - Jun)");}
            if(selValu==4){jq(".cohortDesc").html("Search by Quarterly Cohort - FY (Jul - Sept)");}
            console.log("Selected value"+selValu);
        });
        
        
        
        jq(".date").unbind("datepicker");
        jq('.date').datepicker({
              dateFormat: 'yy-mm-dd',
              changeYear: true,
              changeMonth:true,
              yearRange: "-30:+0",
              autoclose: true
          });

         // setupDatePickerPositioner();

        //ensure that the toggle button is closed

          
          
        jq("#filterOTZQuarterlycy").click(function(){
            var quartercy = jq("#quartercy").val() ;
            var yearcy = jq("#quarterYearcy").val();
           
            console.log("Quarter: "+quartercy);
            console.log("Year: "+yearcy);
            
            var startDateEndDate = getDateRangeForQuartercy(quartercy, yearcy)
            
            startDate = startDateEndDate[0];
            endDate = startDateEndDate[1];
            
            console.log("Start: "+startDate);
            console.log("End: "+endDate);

            
            getOTZData();
           
        
        });
        
        
        jq("#filterOTZQuarterly").click(function(){
            var quarter = jq("#quarter").val() ;
            var year = jq("#quarterYear").val();
           
            console.log("Quarter: "+quarter);
            console.log("Year: "+year);
            
            var startDateEndDate = getDateRangeForQuarter(quarter, year)
            
            startDate = startDateEndDate[0];
            endDate = startDateEndDate[1];
            
            console.log("Start: "+startDate);
            console.log("End: "+endDate);

            
            getOTZData();
           
        
        });

        
        
        jq("#filterOTZMonthly").click(function(){
            var month = jq("#month").val() ;
            var year = jq("#monthYear").val();
            var lastDay = lastDays[month-1];
            
            console.log("Month: "+month);
            console.log("Year: "+year);
            
            if(month == 2 && leapYear(year))
            {
                lastDay = 29;
            }
            
            var monthString = (month > 9) ? month : "0"+month;
            startDate = year+"-"+monthString+"-"+"01"
            endDate = year+"-"+monthString+"-"+lastDay;
            
            console.log("Start: "+startDate);
            console.log("End: "+endDate);
            
            getOTZData();
           
        
        });
          
          
        jq("#filterOTZ").click(function(e){
        
                    
            startDate = jq("#startDate").val();
            endDate = jq("#endDate").val();
            
            if (startDate === "" || endDate === "") {
                return
            }
            jq("#filterOTZ").addClass('hidden');
            console.log("77777777777777777777777777777777777777777777777777777777777777777");
            console.log("Start: "+startDate);
            console.log("End: "+endDate);
            
            /////////////////////////////////////////////////here we go/////////////////////////////////////////////////
            const beginDate = new Date(startDate);
            console.log("begindate before 2019", beginDate);
            if (beginDate.getFullYear() < 2019) {
                beginDate.setFullYear(2019, 0, 1);
            }
            console.log("begindate before 2019 reset", beginDate);
            const finishDate = new Date(endDate);



                        
            function getStartAndEndDates(startMonth, endMonth) {
            const months = [];

            let currentMonth = startMonth.getMonth();
            let currentYear = startMonth.getFullYear();

            while (currentMonth <= endMonth.getMonth() || currentYear < endMonth.getFullYear()) {
                const startDate = new Date(currentYear, currentMonth, 1);
                const endDate = new Date(currentYear, currentMonth + 1, 0);
                
                months.push({
                month: currentMonth + 1,
                startDate: startDate,
                endDate: endDate
                });

                // Increment month and year
                currentMonth++;
                if (currentMonth > 11) {
                currentMonth = 0;
                currentYear++;
                }
            }

            return months;
            }


            const monthsData = getStartAndEndDates(beginDate, finishDate);
            console.log("monthsData", monthsData);


            // Iterate over the array using a for loop
            // for (let i = 0; i < monthsData.length; i++) {
            // console.log("Month: " + monthsData[i].month);
                // let year = monthsData[i].startDate.getFullYear();
                // let month = monthsData[i].month;
                // let day = monthsData[i].startDate.getDate();
                // let formattedDate = year + "-" + ("0" + month).slice(-2) + "-" + ("0" + day).slice(-2);
                // console.log("Start Date: " + formattedDate);
// 
                // let eday = monthsData[i].endDate.getDate();
                // let eformattedDate = year + "-" + ("0" + month).slice(-2) + "-" + ("0" + eday).slice(-2);
                // console.log("End Date: " + eformattedDate);
//             
            // console.log("Start Day: " + monthsData[i].startDate.getDate());
            // console.log("End Day: " + monthsData[i].endDate.getDate());
            // console.log("Year: " + monthsData[i].startDate.getFullYear());
            // console.log('----------------------');
            // }





            function getFormattedMonthsData(monthsData) {
            const formattedMonths = [];

            
                // Iterate over the array using a for loop
                for (let i = 0; i < monthsData.length; i++) {
                    
                    let year = monthsData[i].startDate.getFullYear();
                    let month = monthsData[i].month;
                    let day = monthsData[i].startDate.getDate();
                    let formattedDate = year + "-" + ("0" + month).slice(-2) + "-" + ("0" + day).slice(-2);
                    

                    let eday = monthsData[i].endDate.getDate();
                    let eformattedDate = year + "-" + ("0" + month).slice(-2) + "-" + ("0" + eday).slice(-2);
                
                    formattedMonths.push({
                        month: i+1,
                        startDate: formattedDate,
                        endDate: eformattedDate
                    });
            
                }

            

            

            return formattedMonths;
            }

            const callgetFormattedMonthsData = getFormattedMonthsData(monthsData);
            console.log("callgetFormattedMonthsData", callgetFormattedMonthsData);
            formattedMonthLength = callgetFormattedMonthsData.length;
            console.log("formattedMonthLength", formattedMonthLength);

           
            // Initialize the HTML content variable
            
            let newContent3 =``;
            let newContent = `
    <div class="container">
    <table id="tab2export" class="table dataTable">
        <thead>
            
             <tr>
                <th  style="text-align:center" align="middle">Implementing Partner</th>
                <th  style="text-align:center" align="middle">State</th>
                <th  style="text-align:center" align="middle">LGA</th>
                <th  style="text-align:center" align="middle">Facility name</th>
                <th  style="text-align:center" align="middle">DATIM Code</th>
                <th  style="text-align:center" align="middle">Cohort Month (MM/YYYY)</th>
                <th  style="text-align:center" align="middle">Sex</th>
                <th  style="text-align:center" align="middle">Age Band</th>
                <th># of AYPLHIV currently on ART in the supported facility</th>
                <th># of AYPLHIV enrolled in OTZ in the cohort month</th>
                <th># of OTZ members with scheduled drug pick-up appointment in the last six months prior to enrolment on OTZ</th>
                <th># of OTZ members who kept their drug pick-up appointment in the last six months prior to enrolment on OTZ</th>
                <th># of OTZ members with good drug adherence score in the last six months prior to enrolment on OTZ</th>
                <th># of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ</th>
                <th># of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result less than 200 c/ml</th>
                <th># of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result between 200 to less than 1000 c/ml</th>
                <th># of AYPLHIV in OTZ with baseline VL results (VL within the last 12 months) at enrolment into OTZ and VL result greater than or equal to 1000 c/ml</th>
<th># of AYPLHIV in OTZ with VL results at baseline within the last 6 months at enrolment into OTZ</th>
<th># of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL less than 200 c/ml</th>
<th># of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL result is between 200 to less than 1000 c/ml</th>
<th># of AYPLHIV in OTZ with VL result at baseline within the last 6 months at enrolment into OTZ and VL greater than or equal to 1000 c/ml</th>
<th># of AYPLHIV in OTZ without baseline VL results or with baseline VL result less than 1000 c/ml and eligible for month zero VL sample collection</th>
<th># of AYPLHIV in OTZ enrolled in the cohort month and eligible for month zero VL sample collection whose VL samples were taken (at month zero)</th>
<th># of AYPLHIV in OTZ with baseline VL results less than 1000 c/ml whose VL result for sample collected at month zero is less than 200 c/ml</th>
<th># of AYPLHIV in OTZ with baseline VL results less 1000 c/ml whose VL result for sample collected at month zero is betwen 200 to less than 1000 c/ml</th>
<th># of AYPLHIV in OTZ with baseline VL results less 1000 c/ml  whose VL result for sample collected at month zero is greater than or equal to 1000 c/ml</th>
<th>Follow up</th>
<th># of OTZ members with scheduled drug pick-up appointment in the follow up period</th>
<th># of OTZ members who kept their drug pick-up appointment in the follow up period</th>
<th># of OTZ members with good drug adherence score in the follow up period</th>
<th># of AYPLHIV in OTZ who were eligible for routine VL test during the follow up period i.e. No VL result for the 6-month period prior to the beginning of the reporting period</th>
<th># of AYPLHIV in OTZ whose samples were taken for routine VL test</th>
<th># of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period</th>
<th># of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period less than 200 copies/ml</th>
<th># of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period between 200 to less than 1000 copies/ml</th>
<th># of AYPLHIV in OTZ with result for sample taken for routine VL test during the follow up period greater than or equal to 1000 copies/ml</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months</th>
<th># of AYPLHIV enrolled in OTZ  in the cohort month with VL result within the last 12 months less than 200 copies/ml</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months is between 200 to less than 1000 copies/ml</th>
<th># of AYPLHIV enrolled in OTZ  in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml and completed EAC</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml who have repeat VL result</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml whose repeat VL result is less than 200 copies/ml</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml whose repeat VL result is between 200 to less than 1000 copies/ml</th>
<th># of AYPLHIV enrolled in OTZ in the cohort month with VL result within the last 12 months greater than or equal to 1000 copies/ml whose repeat VL result is greater than or equal to 1000 copies/ml</th>
<th># switched to second line ART</th>
<th># switched to third line ART</th>
<th># of OTZ members who have completed the 7 AYP modules</th>
<th># transferred out during the follow up period</th>
<th># Lost to follow up during the follow up period</th>
<th># reported dead during the follow up period</th>
<th># that opted out of OTZ during the follow up period</th>
<th># aged 20-24 years and transitioned to adult care during the follow up  period</th>
<th># that exited OTZ during the follow up period</th>
</tr>
            
        </thead>
        <tbody>`;




            function getMonthAndYear(currentMonth, currentYear) {

            const monthNames = [
                "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"
            ];

            const monthName = monthNames[currentMonth];
            const result = `\${monthName} \${currentYear}`;

            return result;
        }



function calculateDates(startDateTime, jsgroovar) {
    const today = new Date();
  
    const monthsBetweenDates = Math.floor((today - startDateTime) / (1000 * 60 * 60 * 24 * 30));
    console.log("monthsBetweenDates", monthsBetweenDates);
        
    let currentMonth = startDateTime.getMonth();
    let currentYear = startDateTime.getFullYear();
    
    let currentMonthRevert = startDateTime.getMonth();
    let currentYearRevert = startDateTime.getFullYear();
    const getMaYRevert = getMonthAndYear(currentMonthRevert, currentYearRevert);
    
    for (let jf = 12; jf <= monthsBetweenDates; jf += 6) {
        currentMonth+=6;
        
            const quotient = Math.floor(currentMonth / 12);
            const remainder = currentMonth % 12;
            
        const getMaY = getMonthAndYear(remainder, currentYear+quotient);
        console.log("month:", jf);
        let newContent2 =``;
    
        for (let agecccccc = 0; agecccccc<6; agecccccc++){

            let ageBand = agecccccc === 0 ? "M10To14_" :
                                agecccccc === 1 ? "M15To19_" :
                                agecccccc === 2 ? "M20To24_" :
                                agecccccc === 3 ? "F10To14_" :
                                agecccccc === 4 ? "F15To19_" :
                                "F20To24_";
            let var2 = ageBand.startsWith("M") ? "Male" : "Female";
            let var3 = ageBand.substring(1, ageBand.indexOf("T")) + "-" + ageBand.substring(ageBand.indexOf("o") + 1, ageBand.indexOf("_"));

            const rowContent = `
        <tr>
            <td class="loadingView num4"  id="parner_Name\${ageBand}\${jsgroovar}"></td>
            <td class="loadingView num4"  id="facilityState\${ageBand}\${jsgroovar}"></td>
            <td class="loadingView num4"  id="facilityLGA\${ageBand}\${jsgroovar}"></td>
            <td class="loadingView num4"  id="facilityName\${ageBand}\${jsgroovar}"></td>
            <td class="loadingView num4"  id="facilityDATIMCode"></td>
            <td class="loadingView num4"  id="monthYearr\${ageBand}\${jsgroovar}">\${getMaYRevert}</td>
            <td  style="text-align:center" align="middle">\${var2}</td>
            <td  style="text-align:center" align="middle">\${var3} yrs</td>
            <td class="loadingView num4"  id="AYPLHIVCurrent\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolled\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledWithApp6MtPrior\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledKeptAppPrior\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledGoodAdhPrior\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledBaselineResult\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledBaselineResult6Mt\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledEligibleMtZ\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledEligibleMtZSample\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledB1000MZ200\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            <td class="loadingView num4"  id="totalEnrolledB1000MZ1000\${ageBand}\${jsgroovar}" style="background-color: gray;"></td>
            
            <td class="loadingView num4"  id="followwup">month \${jf}</td>
            <td class="loadingView num4"  id="scheduledPickupFU\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="scheduledKeptPickupFU\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="goodAdhFU\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="eligibleFU\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="samplesTaken\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="samplesTakenResult\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="samplesTakenResult200\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="samplesTakenResult200To1000\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="samplesTakenResult1000\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="resultPast12Mt\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="resultPast12Mt200\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="resultPast12Mt200To100\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="resultPast12Mt1000\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="resultPast12Mt1000EAC\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="resultPast12Mt1000Repeat\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="resultPast12Mt1000Repeat200\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="switchTo2nd\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="switchTo3rd\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="completed7\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="transferredOut\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="ltfu\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="dead\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="optedOut\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="transitioned\${ageBand}\${jsgroovar}month\${jf}"></td>
            <td class="loadingView num4"  id="exited\${ageBand}\${jsgroovar}month\${jf}"></td>
        </tr>
        `;

        newContent2 += rowContent;

        }
        
        newContent += newContent2;

        // To include the current month block
        /*
        if (jf > monthsBetweenDates) {
          break;
        }
        */
      }
  }
  




for (let jsgroovar = 1; jsgroovar <= formattedMonthLength; jsgroovar++) {
    newContent += `
        <tr>
                <td class="loadingView num4"  id="parner_NameM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityStateM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityLGAM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityNameM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityDATIMCode"></td>
                <td class="loadingView num4"  id="monthYearrM10To14_\${jsgroovar}"></td>
                <td  style="text-align:center" align="middle">Male</td>
                <td  style="text-align:center" align="middle">10-14 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200M10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000M10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000M10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200M10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000M10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000M10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleM10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200M10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000M10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000M10To14_\${jsgroovar}"></td>

                <td class="loadingView num4"  id="followwup">month 6</td>
                <td class="loadingView num4"  id="scheduledPickupFUM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="goodAdhFUM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="eligibleFUM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResultM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult200M10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000M10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult1000M10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12MtM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt200M10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt200To100M10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000M10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200M10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000M10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000M10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="switchTo2ndM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="switchTo3rdM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="completed7M10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="transferredOutM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="ltfuM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="deadM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="optedOutM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="transitionedM10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="exitedM10To14_\${jsgroovar}month6"></td>
            </tr>
            <tr>
                <td class="loadingView num4"  id="parner_NameM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityStateM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityLGAM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityNameM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityDATIMCode"></td>
                <td class="loadingView num4"  id="monthYearrM15To19_\${jsgroovar}"></td>
                <td  style="text-align:center" align="middle">Male</td>
                <td  style="text-align:center" align="middle">15-19 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200M15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000M15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000M15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200M15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000M15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000M15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleM15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200M15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000M15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000M15To19_\${jsgroovar}"></td>

                <td class="loadingView num4"  id="followwup">month 6</td>
                <td class="loadingView num4"  id="scheduledPickupFUM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="goodAdhFUM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="eligibleFUM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResultM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult200M15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000M15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult1000M15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12MtM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt200M15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt200To100M15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000M15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200M15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000M15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000M15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="switchTo2ndM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="switchTo3rdM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="completed7M15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="transferredOutM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="ltfuM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="deadM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="optedOutM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="transitionedM15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="exitedM15To19_\${jsgroovar}month6"></td>
            </tr>
            <tr>
                <td class="loadingView num4"  id="parner_NameM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityStateM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityLGAM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityNameM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityDATIMCode"></td>
                <td class="loadingView num4"  id="monthYearrM20To24_\${jsgroovar}"></td>
                <td  style="text-align:center" align="middle">Male</td>
                <td  style="text-align:center" align="middle">20-24 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200M20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000M20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000M20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200M20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000M20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000M20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleM20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200M20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000M20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000M20To24_\${jsgroovar}"></td>

                <td class="loadingView num4"  id="followwup">month 6</td>
                <td class="loadingView num4"  id="scheduledPickupFUM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="goodAdhFUM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="eligibleFUM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResultM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult200M20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000M20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult1000M20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12MtM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt200M20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt200To100M20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000M20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200M20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000M20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000M20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="switchTo2ndM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="switchTo3rdM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="completed7M20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="transferredOutM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="ltfuM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="deadM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="optedOutM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="transitionedM20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="exitedM20To24_\${jsgroovar}month6"></td>
            </tr>
            
            <tr>
                <td class="loadingView num4"  id="parner_NameF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityStateF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityLGAF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityNameF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityDATIMCode"></td>
                <td class="loadingView num4"  id="monthYearrF10To14_\${jsgroovar}"></td>
                <td  style="text-align:center" align="middle">Female</td>
                <td  style="text-align:center" align="middle">10-14 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200F10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000F10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000F10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200F10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000F10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000F10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleF10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200F10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000F10To14_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000F10To14_\${jsgroovar}"></td>

                <td class="loadingView num4"  id="followwup">month 6</td>
                <td class="loadingView num4"  id="scheduledPickupFUF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="goodAdhFUF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="eligibleFUF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResultF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult200F10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000F10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult1000F10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12MtF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt200F10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt200To100F10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000F10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200F10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000F10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000F10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="switchTo2ndF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="switchTo3rdF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="completed7F10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="transferredOutF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="ltfuF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="deadF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="optedOutF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="transitionedF10To14_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="exitedF10To14_\${jsgroovar}month6"></td>
            </tr>
            <tr>
                <td class="loadingView num4"  id="parner_NameF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityStateF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityLGAF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityNameF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityDATIMCode"></td>
                <td class="loadingView num4"  id="monthYearrF15To19_\${jsgroovar}"></td>
                <td  style="text-align:center" align="middle">Female</td>
                <td  style="text-align:center" align="middle">15-19 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200F15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000F15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000F15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200F15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000F15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000F15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleF15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200F15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000F15To19_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000F15To19_\${jsgroovar}"></td>

                <td class="loadingView num4"  id="followwup">month 6</td>
                <td class="loadingView num4"  id="scheduledPickupFUF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="goodAdhFUF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="eligibleFUF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResultF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult200F15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000F15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult1000F15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12MtF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt200F15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt200To100F15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000F15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200F15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000F15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000F15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="switchTo2ndF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="switchTo3rdF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="completed7F15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="transferredOutF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="ltfuF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="deadF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="optedOutF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="transitionedF15To19_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="exitedF15To19_\${jsgroovar}month6"></td>
            </tr>
            <tr>
                <td class="loadingView num4"  id="parner_NameF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityStateF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityLGAF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityNameF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="facilityDATIMCode"></td>
                <td class="loadingView num4"  id="monthYearrF20To24_\${jsgroovar}"></td>
                <td  style="text-align:center" align="middle">Female</td>
                <td  style="text-align:center" align="middle">20-24 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200F20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000F20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000F20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200F20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000F20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000F20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleF20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200F20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000F20To24_\${jsgroovar}"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000F20To24_\${jsgroovar}"></td>

                <td class="loadingView num4"  id="followwup">month 6</td>
                <td class="loadingView num4"  id="scheduledPickupFUF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="goodAdhFUF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="eligibleFUF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResultF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult200F20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000F20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="samplesTakenResult1000F20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12MtF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt200F20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt200To100F20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000F20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200F20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000F20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000F20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="switchTo2ndF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="switchTo3rdF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="completed7F20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="transferredOutF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="ltfuF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="deadF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="optedOutF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="transitionedF20To24_\${jsgroovar}month6"></td>
                <td class="loadingView num4"  id="exitedF20To24_\${jsgroovar}month6"></td>
            </tr>
    `;




  const startD = new Date(monthsData[jsgroovar - 1].startDate);
  calculateDates(startD, jsgroovar);

}

newContent3 = `
            
            <tr>
                <td class="loadingView num4"  id="parner_ended_NameF20To24ended">ended</td>
                <td class="loadingView num4"  id="facility_ended_StateF20To24"></td>
                <td class="loadingView num4"  id="facility_ended_LGAF20To24"></td>
                <td class="loadingView num4"  id="facility_ended_NameF20To24"></td>
                <td class="loadingView num4"  id="facilityDATIMCode"></td>
                <td class="loadingView num4"  id="monthYearrF20To24"></td>
                <td  style="text-align:center" align="middle_">Female</td>
                <td  style="text-align:center" align="middle_">20-24 yrs</td>
                <td class="loadingView num4"  id="AYPLHIVCurrentF20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledF20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledWithApp6MtPriorF20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledKeptAppPriorF20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledGoodAdhPriorF20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultF20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultBelow200F20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult200To1000F20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResultAbove1000F20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtF20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow200F20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBt200To1000F20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledBaselineResult6MtBelow1000Gt1000F20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZF20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledEligibleMtZSampleF20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200F20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ200To1000F20To24"></td>
                <td class="loadingView num4"  id="totalEnrolledB1000MZ1000F20To24"></td>
                <td class="loadingView num4"  id="followwup">Follow up</td>
                <td class="loadingView num4"  id="scheduledPickupFUF20To24"></td>
                <td class="loadingView num4"  id="scheduledKeptPickupFUF20To24"></td>
                <td class="loadingView num4"  id="goodAdhFUF20To24"></td>
                <td class="loadingView num4"  id="eligibleFUF20To24"></td>
                <td class="loadingView num4"  id="samplesTakenF20To24"></td>
                <td class="loadingView num4"  id="samplesTakenResultF20To24"></td>
                <td class="loadingView num4"  id="samplesTakenResult200F20To24"></td>
                <td class="loadingView num4"  id="samplesTakenResult200To1000F20To24"></td>
                <td class="loadingView num4"  id="samplesTakenResult1000F20To24"></td>
                <td class="loadingView num4"  id="resultPast12MtF20To24"></td>
                <td class="loadingView num4"  id="resultPast12Mt200F20To24"></td>
                <td class="loadingView num4"  id="resultPast12Mt200To100F20To24"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000F20To24"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000EACF20To24"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000RepeatF20To24"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200F20To24"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat200To1000F20To24"></td>
                <td class="loadingView num4"  id="resultPast12Mt1000Repeat1000F20To24"></td>
                <td class="loadingView num4"  id="switchTo2ndF20To24"></td>
                <td class="loadingView num4"  id="switchTo3rdF20To24"></td>
                <td class="loadingView num4"  id="completed7F20To24"></td>
                <td class="loadingView num4"  id="transferredOutF20To24"></td>
                <td class="loadingView num4"  id="ltfuF20To24"></td>
                <td class="loadingView num4"  id="deadF20To24"></td>
                <td class="loadingView num4"  id="optedOutF20To24"></td>
                <td class="loadingView num4"  id="transitionedF20To24"></td>
                <td class="loadingView num4"  id="exitedF20To24"></td>
            </tr>
        </tbody>
    </table>
</div>
`;


            // Set the new content to the #OTZReport div
            document.getElementById("OTZReport").innerHTML = newContent + newContent3;
            
                
                //return
           
            


            //window.open("otzreportsummary.page?formattedMonthLength="+formattedMonthLength);


            /////////////////////////////////////////////////here we go/////////////////////////////////////////////////
            console.log("for loop log")
            console.log(callgetFormattedMonthsData)
            callgetFormattedMonthsData.forEach(function(item) {
                if (item.month==1){
                    initialDate = item.startDate;
                }
                console.log("what date here")
                console.log(item.month);
                startDate = item.startDate;
                endDate = item.endDate;
                console.log(startDate);
                console.log(endDate);
                console.log(initialDate);
                getOTZData(item);
            });

            //getOTZData();
            
            
        });
        
        
         jq(".otzDetails").click(function(e){
              //startDate = jq("#startDate").val();
              //endDate = jq("#endDate").val();
              var subSet = jq(this).attr("data-subset");
              var type = jq(this).attr("data-type");
              window.open("otzdetails.page?type="+type+"&subset="+subSet+"&startDate="+startDate+"&endDate="+endDate, "_blank");
          })
          
      
        jq(".getInformation").click(function(e){
              var key = jq(this).attr("data-key");//get the key
              
              //get information
              var description = information[key]["description"];
              var dataElements = information[key]["dataElements"]
              var numerator = information[key]["numerator"]
              var denominator = information[key]["denominator"]
              var minimumExpectation = information[key]["minimumExpectation"]
              
              
              //add information
              jq("#description").html(description);
              jq("#dataElements").html(dataElements);
              jq("#numerator").html(numerator);
              jq("#denominator").html(denominator);
              jq("#minimumExpectation").html(minimumExpectation);
              
        
          });
      });
      
        
      
    function updateProgress()
    {   
       currReportCount++;
       
       var progress = (currReportCount/totalReportCount * 100).toFixed(1);
       
       jq("#progressBar").css("width", progress+"%");
       jq("#progressBar").attr("aria-valuenow", progress);
       jq("#progressText").html(progress+"% complete");
       
    }
    
     var datatableObj ;
    function createDatatable()
    {
    
    
     datatableObj = jq(".dataTable").DataTable({
             pageLength: 100,
             "lengthMenu": [[50, 100, 250, 500, -1], [50, 100, 250, 500, "All"]],
             "columnDefs": [
                { "searchable": false, "targets": [0] }  // Disable search on first and last columns
              ],
              dom: 'lfBrtip',
                buttons: [
                    'copy', 
                   // 'csv', 
                    {
                        extend: 'csv',
                        title:'<%= title; %>',
                        exportOptions: {
                            columns: [0,1,2]
                        }
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'excel',
                        title:'<%= title; %>',
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'pdf',
                        title:'<%= title; %>',
                        //messageTop: '<%= title; %>'
                    },
                    {
                        extend: 'print',
                        title:'<%= title; %>',
                        //messageTop: '<%= title; %>'
                    },
                    //'pdf', 
                    //'print'
                ]

                });
       } 
       createDatatable();
       



       // Function to summarize patients based on age group and gender
       function summarizePatients(patients) {
            const summary = {
                '10-14': { F: 0, M: 0 },
                '15-19': { F: 0, M: 0 },
                '20-24': { F: 0, M: 0 }
            };

            patients.forEach(patient => {
                const ageGroup = getAgeGroup(patient.age);
                if (ageGroup !== 'Unknown') {
                    if (patient.gender === 'F') {
                        summary[ageGroup].F++;
                    } else if (patient.gender === 'M') {
                        summary[ageGroup].M++;
                    }
                }
            });

            return summary;
            }


            // Function to summarize patients based on age group and gender
       function summarizePatients200(patients) {
            const summary = {
                '10-14': { F: 0, M: 0 },
                '15-19': { F: 0, M: 0 },
                '20-24': { F: 0, M: 0 }
            };

            patients.forEach(patient => {
                const ageGroup = getAgeGroup(patient.age);

                if (patient.viralLoad < 200) {
                    if (ageGroup !== 'Unknown') {
                        if (patient.gender === 'F') {
                            summary[ageGroup].F++;
                        } else if (patient.gender === 'M') {
                            summary[ageGroup].M++;
                        }
                    }
                }


            });

            return summary;
            }

            // Function to summarize patients based on age group and gender
       function summarizePatients200to999(patients) {
            const summary = {
                '10-14': { F: 0, M: 0 },
                '15-19': { F: 0, M: 0 },
                '20-24': { F: 0, M: 0 }
            };

            patients.forEach(patient => {
                const ageGroup = getAgeGroup(patient.age);

                if (patient.viralLoad >= 200 && patient.viralLoad < 1000) {
                    if (ageGroup !== 'Unknown') {
                        if (patient.gender === 'F') {
                            summary[ageGroup].F++;
                        } else if (patient.gender === 'M') {
                            summary[ageGroup].M++;
                        }
                    }
                }


            });

            return summary;
            }

            // Function to summarize patients based on age group and gender
       function summarizePatients1000(patients) {
            const summary = {
                '10-14': { F: 0, M: 0 },
                '15-19': { F: 0, M: 0 },
                '20-24': { F: 0, M: 0 }
            };

            patients.forEach(patient => {
                const ageGroup = getAgeGroup(patient.age);

                if (patient.viralLoad >= 1000) {
                    if (ageGroup !== 'Unknown') {
                        if (patient.gender === 'F') {
                            summary[ageGroup].F++;
                        } else if (patient.gender === 'M') {
                            summary[ageGroup].M++;
                        }
                    }
                }


            });

            return summary;
            }
            

            // Function to determine age group based on cage
            function getAgeGroup(age) {
            if (age >= 10 && age <= 14) {
                return '10-14';
            } else if (age >= 15 && age <= 19) {
                return '15-19';
            } else if (age >= 20 && age <= 24) {
                return '20-24';
            } else {
                return 'Unknown';
            }
            }




            
       
   function getOTZData(month)
   {


        let startDate = month.startDate;
        let endDate = month.endDate;
        let currMonth = month.month;
        
        
        console.log("currMonth", currMonth)
             currReportCount = 0;
            //show the progress area
            jq("#progressArea").removeClass("hidden");
            
            jq("#progressArea").removeClass("hidden");
            
            jq(".loadingViewButton").html('Loading...');
            jq(".loadingViewButton").attr("disabled", true);
            
           
            console.log("#####################################################"+ageTyp);
            myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getAllEnrolledInOTZ") }').then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledM10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledM15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledM20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledMabove24_"+currMonth).html(maleabove24)
                
                 jq("#totalEnrolledF10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledF15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledF20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledFabove24_"+currMonth).html(femaleabove24)
                
               
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledTotal_"+currMonth).html(total)
                
                
                //lets set some cards
                setCardValues(data);;
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getAllFullDisc") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                console.log("disagregation++++++++++++++++++++++++++++++++++++++++"+response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
          
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
               
                
                jq("#totalFullDiscM10To14_"+currMonth).html(male1014)
                jq("#totalFullDiscM15To19_"+currMonth).html(male1519)
                jq("#totalFullDiscM20To24_"+currMonth).html(male2024)
                jq("#totalFullDiscMabove24_"+currMonth).html(maleabove24)
                
                 jq("#totalFullDiscF10To14_"+currMonth).html(female1014)
                jq("#totalFullDiscF15To19_"+currMonth).html(female1519);
                jq("#totalFullDiscF20To24_"+currMonth).html(female2024)
                jq("#totalFullDiscFabove24_"+currMonth).html(femaleabove24)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalFullDiscTotal_"+currMonth).html(total)
                console.log("total disclosed", total);
                
                renderFullDisclosure(totalEnrolled, total);
                //lets set some cards
                //setCardValues(data);;
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithScheduledPickup6MonthsBefore") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledWithApp6MtPriorM10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledWithApp6MtPriorM15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledWithApp6MtPriorM20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledWithApp6MtPriorMabove24_"+currMonth).html(maleabove24)
                
                 jq("#totalEnrolledWithApp6MtPriorF10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledWithApp6MtPriorF15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledWithApp6MtPriorF20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledWithApp6MtPriorFabove24_"+currMonth).html(femaleabove24)
                
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledWithApp6MtPriorTotal_"+currMonth).html(total)
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWhoKeptScheduledPickup6MonthsBefore") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledKeptAppPriorM10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledKeptAppPriorM15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledKeptAppPriorM20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledKeptAppPriorMabove24_"+currMonth).html(maleabove24)
                
                 jq("#totalEnrolledKeptAppPriorF10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledKeptAppPriorF15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledKeptAppPriorF20To24_"+currMonth).html(female2024);
                jq("#totalEnrolledKeptAppPriorFabove24_"+currMonth).html(femaleabove24);
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledKeptAppPriorTotal_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithGoodAdhScore6MonthsBefore") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledGoodAdhPriorM10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledGoodAdhPriorM15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledGoodAdhPriorM20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledGoodAdhPriorMabove24_"+currMonth).html(maleabove24)
                
                 jq("#totalEnrolledGoodAdhPriorF10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledGoodAdhPriorF15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledGoodAdhPriorF20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledGoodAdhPriorFabove24_"+currMonth).html(femaleabove24)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledGoodAdhPriorTotal_"+currMonth).html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVL12MonthsBefore") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledBaselineResultM10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledBaselineResultM15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledBaselineResultM20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledBaselineResultMabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledBaselineResultF10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledBaselineResultF15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledBaselineResultF20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledBaselineResultFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledBaselineResultTotal_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVL12MonthsBeforeAndBelow200") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledBaselineResultBelow200M10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledBaselineResultBelow200M15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledBaselineResultBelow200M20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledBaselineResultBelow200Mabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledBaselineResultBelow200F10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledBaselineResultBelow200F15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledBaselineResultBelow200F20To24_"+currMonth).html(female2024);
                jq("#totalEnrolledBaselineResultBelow200Fabove24_"+currMonth).html(femaleabove24);
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledBaselineResultBelow200Total_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVL12MonthsBeforeAndBtw200AND1000") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledBaselineResult200To1000M10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledBaselineResult200To1000M15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledBaselineResult200To1000M20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledBaselineResult200To1000Mabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledBaselineResult200To1000F10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledBaselineResult200To1000F15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledBaselineResult200To1000F20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledBaselineResult200To1000Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledBaselineResult200To1000Total_"+currMonth).html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVL12MonthsBeforeAndAboveOrEqual1000") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledBaselineResultAbove1000M10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledBaselineResultAbove1000M15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledBaselineResultAbove1000M20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledBaselineResultAbove1000Mabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledBaselineResultAbove1000F10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledBaselineResultAbove1000F15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledBaselineResultAbove1000F20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledBaselineResultAbove1000Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledBaselineResultAbove1000Total_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVL6MonthsBefore") }');
            })

            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledBaselineResult6MtM10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledBaselineResult6MtM15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledBaselineResult6MtM20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledBaselineResult6MtMabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledBaselineResult6MtF10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledBaselineResult6MtF15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledBaselineResult6MtF20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledBaselineResult6MtFabove24_"+currMonth).html(femaleabove24)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledBaselineResult6MtTotal_"+currMonth).html(total)
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVL6MonthsBeforeAndBelow200") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledBaselineResult6MtBelow200M10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledBaselineResult6MtBelow200M15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledBaselineResult6MtBelow200M20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledBaselineResult6MtBelow200Mabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledBaselineResult6MtBelow200F10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledBaselineResult6MtBelow200F15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledBaselineResult6MtBelow200F20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledBaselineResult6MtBelow200Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledBaselineResult6MtBelow200Total_"+currMonth).html(total)
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVL6MonthsBeforeAndBtw200AND1000") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledBaselineResult6MtBt200To1000M10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledBaselineResult6MtBt200To1000M15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledBaselineResult6MtBt200To1000M20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledBaselineResult6MtBt200To1000Mabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledBaselineResult6MtBt200To1000F10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledBaselineResult6MtBt200To1000F15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledBaselineResult6MtBt200To1000F20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledBaselineResult6MtBt200To1000Fabove24_"+currMonth).html(femaleabove24)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledBaselineResult6MtBt200To1000Total_"+currMonth).html(total)
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVL6MonthsBeforeAndAboveOrEqual1000") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000M10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000M15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000M20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000Mabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000F10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000F15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000F20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledBaselineResult6MtBelow1000Gt1000Total_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEligibleForMonthZeroVL") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledEligibleMtZM10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledEligibleMtZM15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledEligibleMtZM20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledEligibleMtZMabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledEligibleMtZF10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledEligibleMtZF15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledEligibleMtZF20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledEligibleMtZFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledEligibleMtZTotal_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEligibleForMonthZeroVLWithSampleCollectedAtEnrollment") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledEligibleMtZSampleM10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledEligibleMtZSampleM15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledEligibleMtZSampleM20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledEligibleMtZSampleMabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledEligibleMtZSampleF10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledEligibleMtZSampleF15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledEligibleMtZSampleF20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledEligibleMtZSampleFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledEligibleMtZSampleTotal_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalWithBaseLineVLBelow1000AndMonthZeroVlBelow200") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledB1000MZ200M10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledB1000MZ200M15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledB1000MZ200M20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledB1000MZ200Mabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledB1000MZ200F10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledB1000MZ200F15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledB1000MZ200F20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledB1000MZ200Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledB1000MZ200Total_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove200") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledB1000MZ200To1000M10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledB1000MZ200To1000M15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledB1000MZ200To1000M20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledB1000MZ200To1000Mabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledB1000MZ200To1000F10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledB1000MZ200To1000F15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledB1000MZ200To1000F20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledB1000MZ200To1000Fabove24_"+currMonth).html(femaleabove24)
                
                
                 var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledB1000MZ200To1000Total_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalWithBaseLineVLBelow1000AndMonthZeroVlAbove1000") }');
            })

            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#totalEnrolledB1000MZ1000M10To14_"+currMonth).html(male1014)
                jq("#totalEnrolledB1000MZ1000M15To19_"+currMonth).html(male1519)
                jq("#totalEnrolledB1000MZ1000M20To24_"+currMonth).html(male2024)
                jq("#totalEnrolledB1000MZ1000Mabove24_"+currMonth).html(maleabove24)
                
                jq("#totalEnrolledB1000MZ1000F10To14_"+currMonth).html(female1014)
                jq("#totalEnrolledB1000MZ1000F15To19_"+currMonth).html(female1519);
                jq("#totalEnrolledB1000MZ1000F20To24_"+currMonth).html(female2024)
                jq("#totalEnrolledB1000MZ1000Fabove24_"+currMonth).html(femaleabove24)
                
                 var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#totalEnrolledB1000MZ1000Total_"+currMonth).html(total)

                
                //retired                
                //return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithScheduledPickupAfter") }');
                return myAjax({startDate:startDate, endDate:endDate}, "otz/getPatientsVLAccess.action");
            })


            .then(function(response){
                
            var data = JSON.parse(response);

            var today = new Date();
            var bDate = new Date(startDate);
            var monthDifference = monthDiff(bDate, today);//get months between 
            
            
            console.log("what does the trend data looki likeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            console.log("data",data);
            
            
            for(var i=6; i<=monthDifference; i += 6){
                    
                var mthTitle = (i == 0 ) ? "Baseline": "month"+i;
                console.log("mthTitle: ", mthTitle)

                var allPatientsScheduled = data["allPatientsScheduled"+i];
                var allPatientsKept = data["allPatientsKept"+i];
                var allPatientsGoodScore = data["allPatientsGoodScore"+i];
                var patientsEligible = data["patientsEligible"+i];
                var patientsWithSample= data["patientsWithSample"+i];
                var patientsWithResult = data["patientsWithResult"+i];
                var patientsWithResultPast12Months = data["patientsWithResultPast12Months"+i];
                var allPatientsPast12MonthsResultAbove1000WithRepeatVl = data["allPatientsPast12MonthsResultAbove1000WithRepeatVl"+i];
                var allPatientsSwitchTo2nd = data["allPatientsSwitchTo2nd"+i];
                var allPatientsSwitchTo3rd = data["allPatientsSwitchTo3rd"+i];
                var allPatientsWhoCompleted = data["allPatientsWhoCompleted"+i];
                var allPatientsTransferred = data["allPatientsTransferred"+i];
                var allPatientsIIT = data["allPatientsIIT"+i];
                var allPatientsDied = data["allPatientsDied"+i];
                var allPatientsOptedOut = data["allPatientsOptedOut"+i];
                var allPatientsTO = data["allPatientsTO"+i];
                var allPatientsExited = data["allPatientsExited"+i];
                
                console.log("allPatientsKept: ", allPatientsKept.length)
                console.log(allPatientsKept)

                // Call the function and log the summary
                const allPatientsScheduledSum = summarizePatients(allPatientsScheduled);
                const allPatientsKeptSum = summarizePatients(allPatientsKept);
                
                //this doesnt seem right, copied from previous work
                    const allPatientsGoodScoreSum = summarizePatients(allPatientsKept);
                //const allPatientsGoodScoreSum = summarizePatients(allPatientsGoodScore);


                const patientsEligibleSum = summarizePatients(patientsEligible);
                const patientsWithSampleSum = summarizePatients(patientsWithSample);

                const patientsWithResultSum = summarizePatients(patientsWithResult);
                const patientsWithResultLT200Sum = summarizePatients200(patientsWithResult);
                const patientsWithResult200to999Sum = summarizePatients200to999(patientsWithResult);
                const patientsWithResultGT1000Sum = summarizePatients1000(patientsWithResult);

                const patientsWithResultPast12MonthsSum = summarizePatients(patientsWithResultPast12Months);
                const patientsWithResultPast12MonthsLT200Sum = summarizePatients200(patientsWithResultPast12Months);
                const patientsWithResultPast12Months200to999Sum = summarizePatients200to999(patientsWithResultPast12Months);
                const patientsWithResultPast12MonthsGT1000Sum = summarizePatients1000(patientsWithResultPast12Months);
                //stopped here
                
                const patientsWithResultresultPast12Mt1000EACSum = summarizePatients1000(patientsWithResultPast12Months);

                const resultPast12Mt1000RepeatSum = summarizePatients(allPatientsPast12MonthsResultAbove1000WithRepeatVl);
                const resultPast12Mt1000Repeat200Sum = summarizePatients(allPatientsPast12MonthsResultAbove1000WithRepeatVl);
                const resultPast12Mt1000Repeat200To1000Sum = summarizePatients(allPatientsPast12MonthsResultAbove1000WithRepeatVl);
                const resultPast12Mt1000Repeat1000Sum = summarizePatients(allPatientsPast12MonthsResultAbove1000WithRepeatVl);
                const switchTo2ndSum = summarizePatients(allPatientsSwitchTo2nd);
                const switchTo3rdSum = summarizePatients(allPatientsSwitchTo3rd);
                const completed7Sum = summarizePatients(allPatientsWhoCompleted);
                const transferredOutSum = summarizePatients(allPatientsTransferred);
                const ltfuSum = summarizePatients(allPatientsIIT);
                const deadSum = summarizePatients(allPatientsDied);
                const optedOutSum = summarizePatients(allPatientsOptedOut);
                const transitionedSum = summarizePatients(allPatientsTO);
                const exitedSum = summarizePatients(allPatientsExited);
                
                
                
                


                //scheduledPickupFUM10To14_1month6
                
                jq("#scheduledPickupFUM10To14_"+currMonth+mthTitle).html(allPatientsScheduledSum["10-14"]["M"]);
                jq("#scheduledPickupFUM15To19_"+currMonth+mthTitle).html(allPatientsScheduledSum["15-19"]["M"]);
                jq("#scheduledPickupFUM20To24_"+currMonth+mthTitle).html(allPatientsScheduledSum["20-24"]["M"]);
                
                jq("#scheduledPickupFUF10To14_"+currMonth+mthTitle).html(allPatientsScheduledSum["10-14"]["F"]);
                jq("#scheduledPickupFUF15To19_"+currMonth+mthTitle).html(allPatientsScheduledSum["15-19"]["F"]);
                jq("#scheduledPickupFUF20To24_"+currMonth+mthTitle).html(allPatientsScheduledSum["20-24"]["F"]);

                //scheduledKeptPickupFUM10To14_1month6
                
                jq("#scheduledKeptPickupFUM10To14_"+currMonth+mthTitle).html(allPatientsKeptSum["10-14"]["M"]);
                jq("#scheduledKeptPickupFUM15To19_"+currMonth+mthTitle).html(allPatientsKeptSum["15-19"]["M"]);
                jq("#scheduledKeptPickupFUM20To24_"+currMonth+mthTitle).html(allPatientsKeptSum["20-24"]["M"]);
                
                jq("#scheduledKeptPickupFUF10To14_"+currMonth+mthTitle).html(allPatientsKeptSum["10-14"]["F"]);
                jq("#scheduledKeptPickupFUF15To19_"+currMonth+mthTitle).html(allPatientsKeptSum["15-19"]["F"]);
                jq("#scheduledKeptPickupFUF20To24_"+currMonth+mthTitle).html(allPatientsKeptSum["20-24"]["F"]);

                //goodAdhFUM10To14_1month6
                
                jq("#goodAdhFUM10To14_"+currMonth+mthTitle).html(allPatientsGoodScoreSum["10-14"]["M"]);
                jq("#goodAdhFUM15To19_"+currMonth+mthTitle).html(allPatientsGoodScoreSum["15-19"]["M"]);
                jq("#goodAdhFUM20To24_"+currMonth+mthTitle).html(allPatientsGoodScoreSum["20-24"]["M"]);
                
                jq("#goodAdhFUF10To14_"+currMonth+mthTitle).html(allPatientsGoodScoreSum["10-14"]["F"]);
                jq("#goodAdhFUF15To19_"+currMonth+mthTitle).html(allPatientsGoodScoreSum["15-19"]["F"]);
                jq("#goodAdhFUF20To24_"+currMonth+mthTitle).html(allPatientsGoodScoreSum["20-24"]["F"]);
                
                //eligibleFUM10To14_1month6
                
                jq("#eligibleFUM10To14_"+currMonth+mthTitle).html(patientsEligibleSum["10-14"]["M"]);
                jq("#eligibleFUM15To19_"+currMonth+mthTitle).html(patientsEligibleSum["15-19"]["M"]);
                jq("#eligibleFUM20To24_"+currMonth+mthTitle).html(patientsEligibleSum["20-24"]["M"]);
                
                jq("#eligibleFUF10To14_"+currMonth+mthTitle).html(patientsEligibleSum["10-14"]["F"]);
                jq("#eligibleFUF15To19_"+currMonth+mthTitle).html(patientsEligibleSum["15-19"]["F"]);
                jq("#eligibleFUF20To24_"+currMonth+mthTitle).html(patientsEligibleSum["20-24"]["F"]);

                //samplesTakenM10To14_1month6
                
                jq("#samplesTakenM10To14_"+currMonth+mthTitle).html(patientsWithSampleSum["10-14"]["M"]);
                jq("#samplesTakenM15To19_"+currMonth+mthTitle).html(patientsWithSampleSum["15-19"]["M"]);
                jq("#samplesTakenM20To24_"+currMonth+mthTitle).html(patientsWithSampleSum["20-24"]["M"]);
                
                jq("#samplesTakenF10To14_"+currMonth+mthTitle).html(patientsWithSampleSum["10-14"]["F"]);
                jq("#samplesTakenF15To19_"+currMonth+mthTitle).html(patientsWithSampleSum["15-19"]["F"]);
                jq("#samplesTakenF20To24_"+currMonth+mthTitle).html(patientsWithSampleSum["20-24"]["F"]);

                //samplesTakenResultM10To14_1month6
                
                jq("#samplesTakenResultM10To14_"+currMonth+mthTitle).html(patientsWithResultSum["10-14"]["M"]);
                jq("#samplesTakenResultM15To19_"+currMonth+mthTitle).html(patientsWithResultSum["15-19"]["M"]);
                jq("#samplesTakenResultM20To24_"+currMonth+mthTitle).html(patientsWithResultSum["20-24"]["M"]);
                
                jq("#samplesTakenResultF10To14_"+currMonth+mthTitle).html(patientsWithResultSum["10-14"]["F"]);
                jq("#samplesTakenResultF15To19_"+currMonth+mthTitle).html(patientsWithResultSum["15-19"]["F"]);
                jq("#samplesTakenResultF20To24_"+currMonth+mthTitle).html(patientsWithResultSum["20-24"]["F"]);

                //samplesTakenResult200M10To14_1month6
                
                jq("#samplesTakenResult200M10To14_"+currMonth+mthTitle).html(patientsWithResultLT200Sum["10-14"]["M"]);
                jq("#samplesTakenResult200M15To19_"+currMonth+mthTitle).html(patientsWithResultLT200Sum["15-19"]["M"]);
                jq("#samplesTakenResult200M20To24_"+currMonth+mthTitle).html(patientsWithResultLT200Sum["20-24"]["M"]);
                
                jq("#samplesTakenResult200F10To14_"+currMonth+mthTitle).html(patientsWithResultLT200Sum["10-14"]["F"]);
                jq("#samplesTakenResult200F15To19_"+currMonth+mthTitle).html(patientsWithResultLT200Sum["15-19"]["F"]);
                jq("#samplesTakenResult200F20To24_"+currMonth+mthTitle).html(patientsWithResultLT200Sum["20-24"]["F"]);

                //samplesTakenResult200To1000M10To14_1month6
                
                jq("#samplesTakenResult200To1000M10To14_"+currMonth+mthTitle).html(patientsWithResult200to999Sum["10-14"]["M"]);
                jq("#samplesTakenResult200To1000M15To19_"+currMonth+mthTitle).html(patientsWithResult200to999Sum["15-19"]["M"]);
                jq("#samplesTakenResult200To1000M20To24_"+currMonth+mthTitle).html(patientsWithResult200to999Sum["20-24"]["M"]);
                
                jq("#samplesTakenResult200To1000F10To14_"+currMonth+mthTitle).html(patientsWithResult200to999Sum["10-14"]["F"]);
                jq("#samplesTakenResult200To1000F15To19_"+currMonth+mthTitle).html(patientsWithResult200to999Sum["15-19"]["F"]);
                jq("#samplesTakenResult200To1000F20To24_"+currMonth+mthTitle).html(patientsWithResult200to999Sum["20-24"]["F"]);

                //samplesTakenResult1000M10To14_1month6
                
                jq("#samplesTakenResult1000M10To14_"+currMonth+mthTitle).html(patientsWithResultGT1000Sum["10-14"]["M"]);
                jq("#samplesTakenResult1000M15To19_"+currMonth+mthTitle).html(patientsWithResultGT1000Sum["15-19"]["M"]);
                jq("#samplesTakenResult1000M20To24_"+currMonth+mthTitle).html(patientsWithResultGT1000Sum["20-24"]["M"]);
                
                jq("#samplesTakenResult1000F10To14_"+currMonth+mthTitle).html(patientsWithResultGT1000Sum["10-14"]["F"]);
                jq("#samplesTakenResult1000F15To19_"+currMonth+mthTitle).html(patientsWithResultGT1000Sum["15-19"]["F"]);
                jq("#samplesTakenResult1000F20To24_"+currMonth+mthTitle).html(patientsWithResultGT1000Sum["20-24"]["F"]);

                //resultPast12MtM10To14_1month6
                
                jq("#resultPast12MtM10To14_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsSum["10-14"]["M"]);
                jq("#resultPast12MtM15To19_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsSum["15-19"]["M"]);
                jq("#resultPast12MtM20To24_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsSum["20-24"]["M"]);
                
                jq("#resultPast12MtF10To14_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsSum["10-14"]["F"]);
                jq("#resultPast12MtF15To19_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsSum["15-19"]["F"]);
                jq("#resultPast12MtF20To24_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsSum["20-24"]["F"]);

                //resultPast12Mt200M10To14_1month6
                
                jq("#resultPast12Mt200M10To14_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsLT200Sum["10-14"]["M"]);
                jq("#resultPast12Mt200M15To19_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsLT200Sum["15-19"]["M"]);
                jq("#resultPast12Mt200M20To24_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsLT200Sum["20-24"]["M"]);
                
                jq("#resultPast12Mt200F10To14_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsLT200Sum["10-14"]["F"]);
                jq("#resultPast12Mt200F15To19_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsLT200Sum["15-19"]["F"]);
                jq("#resultPast12Mt200F20To24_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsLT200Sum["20-24"]["F"]);

                //resultPast12Mt200To100M10To14_1month6
                
                jq("#resultPast12Mt200To100M10To14_"+currMonth+mthTitle).html(patientsWithResultPast12Months200to999Sum["10-14"]["M"]);
                jq("#resultPast12Mt200To100M15To19_"+currMonth+mthTitle).html(patientsWithResultPast12Months200to999Sum["15-19"]["M"]);
                jq("#resultPast12Mt200To100M20To24_"+currMonth+mthTitle).html(patientsWithResultPast12Months200to999Sum["20-24"]["M"]);
                
                jq("#resultPast12Mt200To100F10To14_"+currMonth+mthTitle).html(patientsWithResultPast12Months200to999Sum["10-14"]["F"]);
                jq("#resultPast12Mt200To100F15To19_"+currMonth+mthTitle).html(patientsWithResultPast12Months200to999Sum["15-19"]["F"]);
                jq("#resultPast12Mt200To100F20To24_"+currMonth+mthTitle).html(patientsWithResultPast12Months200to999Sum["20-24"]["F"]);

                //resultPast12Mt1000M10To14_1month6
                
                jq("#resultPast12Mt1000M10To14_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsGT1000Sum["10-14"]["M"]);
                jq("#resultPast12Mt1000M15To19_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsGT1000Sum["15-19"]["M"]);
                jq("#resultPast12Mt1000M20To24_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsGT1000Sum["20-24"]["M"]);
                
                jq("#resultPast12Mt1000F10To14_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsGT1000Sum["10-14"]["F"]);
                jq("#resultPast12Mt1000F15To19_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsGT1000Sum["15-19"]["F"]);
                jq("#resultPast12Mt1000F20To24_"+currMonth+mthTitle).html(patientsWithResultPast12MonthsGT1000Sum["20-24"]["F"]);

                //resultPast12Mt1000EACM10To14_1month6
                
                jq("#resultPast12Mt1000EACM10To14_"+currMonth+mthTitle).html(patientsWithResultresultPast12Mt1000EACSum["10-14"]["M"]);
                jq("#resultPast12Mt1000EACM15To19_"+currMonth+mthTitle).html(patientsWithResultresultPast12Mt1000EACSum["15-19"]["M"]);
                jq("#resultPast12Mt1000EACM20To24_"+currMonth+mthTitle).html(patientsWithResultresultPast12Mt1000EACSum["20-24"]["M"]);
                
                jq("#resultPast12Mt1000EACF10To14_"+currMonth+mthTitle).html(patientsWithResultresultPast12Mt1000EACSum["10-14"]["F"]);
                jq("#resultPast12Mt1000EACF15To19_"+currMonth+mthTitle).html(patientsWithResultresultPast12Mt1000EACSum["15-19"]["F"]);
                jq("#resultPast12Mt1000EACF20To24_"+currMonth+mthTitle).html(patientsWithResultresultPast12Mt1000EACSum["20-24"]["F"]);

                //resultPast12Mt1000RepeatM10To14_1month6
                
                jq("#resultPast12Mt1000RepeatM10To14_"+currMonth+mthTitle).html(resultPast12Mt1000RepeatSum["10-14"]["M"]);
                jq("#resultPast12Mt1000RepeatM15To19_"+currMonth+mthTitle).html(resultPast12Mt1000RepeatSum["15-19"]["M"]);
                jq("#resultPast12Mt1000RepeatM20To24_"+currMonth+mthTitle).html(resultPast12Mt1000RepeatSum["20-24"]["M"]);
                
                jq("#resultPast12Mt1000RepeatF10To14_"+currMonth+mthTitle).html(resultPast12Mt1000RepeatSum["10-14"]["F"]);
                jq("#resultPast12Mt1000RepeatF15To19_"+currMonth+mthTitle).html(resultPast12Mt1000RepeatSum["15-19"]["F"]);
                jq("#resultPast12Mt1000RepeatF20To24_"+currMonth+mthTitle).html(resultPast12Mt1000RepeatSum["20-24"]["F"]);

                //resultPast12Mt1000Repeat200M10To14_1month6
                
                jq("#resultPast12Mt1000Repeat200M10To14_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat200Sum["10-14"]["M"]);
                jq("#resultPast12Mt1000Repeat200M15To19_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat200Sum["15-19"]["M"]);
                jq("#resultPast12Mt1000Repeat200M20To24_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat200Sum["20-24"]["M"]);
                
                jq("#resultPast12Mt1000Repeat200F10To14_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat200Sum["10-14"]["F"]);
                jq("#resultPast12Mt1000Repeat200F15To19_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat200Sum["15-19"]["F"]);
                jq("#resultPast12Mt1000Repeat200F20To24_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat200Sum["20-24"]["F"]);

                //resultPast12Mt1000Repeat200To1000M10To14_1month6
                
                jq("#resultPast12Mt1000Repeat200To1000M10To14_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat200To1000Sum["10-14"]["M"]);
                jq("#resultPast12Mt1000Repeat200To1000M15To19_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat200To1000Sum["15-19"]["M"]);
                jq("#resultPast12Mt1000Repeat200To1000M20To24_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat200To1000Sum["20-24"]["M"]);
                
                jq("#resultPast12Mt1000Repeat200To1000F10To14_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat200To1000Sum["10-14"]["F"]);
                jq("#resultPast12Mt1000Repeat200To1000F15To19_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat200To1000Sum["15-19"]["F"]);
                jq("#resultPast12Mt1000Repeat200To1000F20To24_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat200To1000Sum["20-24"]["F"]);

                //resultPast12Mt1000Repeat1000M10To14_1month6
                
                jq("#resultPast12Mt1000Repeat1000M10To14_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat1000Sum["10-14"]["M"]);
                jq("#resultPast12Mt1000Repeat1000M15To19_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat1000Sum["15-19"]["M"]);
                jq("#resultPast12Mt1000Repeat1000M20To24_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat1000Sum["20-24"]["M"]);
                
                jq("#resultPast12Mt1000Repeat1000F10To14_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat1000Sum["10-14"]["F"]);
                jq("#resultPast12Mt1000Repeat1000F15To19_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat1000Sum["15-19"]["F"]);
                jq("#resultPast12Mt1000Repeat1000F20To24_"+currMonth+mthTitle).html(resultPast12Mt1000Repeat1000Sum["20-24"]["F"]);

                //switchTo2ndM10To14_1month6
                
                jq("#switchTo2ndM10To14_"+currMonth+mthTitle).html(switchTo2ndSum["10-14"]["M"]);
                jq("#switchTo2ndM15To19_"+currMonth+mthTitle).html(switchTo2ndSum["15-19"]["M"]);
                jq("#switchTo2ndM20To24_"+currMonth+mthTitle).html(switchTo2ndSum["20-24"]["M"]);
                
                jq("#switchTo2ndF10To14_"+currMonth+mthTitle).html(switchTo2ndSum["10-14"]["F"]);
                jq("#switchTo2ndF15To19_"+currMonth+mthTitle).html(switchTo2ndSum["15-19"]["F"]);
                jq("#switchTo2ndF20To24_"+currMonth+mthTitle).html(switchTo2ndSum["20-24"]["F"]);

                //switchTo3rdM10To14_1month6
                
                jq("#switchTo3rdM10To14_"+currMonth+mthTitle).html(switchTo3rdSum["10-14"]["M"]);
                jq("#switchTo3rdM15To19_"+currMonth+mthTitle).html(switchTo3rdSum["15-19"]["M"]);
                jq("#switchTo3rdM20To24_"+currMonth+mthTitle).html(switchTo3rdSum["20-24"]["M"]);
                
                jq("#switchTo3rdF10To14_"+currMonth+mthTitle).html(switchTo3rdSum["10-14"]["F"]);
                jq("#switchTo3rdF15To19_"+currMonth+mthTitle).html(switchTo3rdSum["15-19"]["F"]);
                jq("#switchTo3rdF20To24_"+currMonth+mthTitle).html(switchTo3rdSum["20-24"]["F"]);

                //completed7M10To14_1month6
                
                jq("#completed7M10To14_"+currMonth+mthTitle).html(completed7Sum["10-14"]["M"]);
                jq("#completed7M15To19_"+currMonth+mthTitle).html(completed7Sum["15-19"]["M"]);
                jq("#completed7M20To24_"+currMonth+mthTitle).html(completed7Sum["20-24"]["M"]);
                
                jq("#completed7F10To14_"+currMonth+mthTitle).html(completed7Sum["10-14"]["F"]);
                jq("#completed7F15To19_"+currMonth+mthTitle).html(completed7Sum["15-19"]["F"]);
                jq("#completed7F20To24_"+currMonth+mthTitle).html(completed7Sum["20-24"]["F"]);

                //transferredOutM10To14_1month6
                
                jq("#transferredOutM10To14_"+currMonth+mthTitle).html(transferredOutSum["10-14"]["M"]);
                jq("#transferredOutM15To19_"+currMonth+mthTitle).html(transferredOutSum["15-19"]["M"]);
                jq("#transferredOutM20To24_"+currMonth+mthTitle).html(transferredOutSum["20-24"]["M"]);
                
                jq("#transferredOutF10To14_"+currMonth+mthTitle).html(transferredOutSum["10-14"]["F"]);
                jq("#transferredOutF15To19_"+currMonth+mthTitle).html(transferredOutSum["15-19"]["F"]);
                jq("#transferredOutF20To24_"+currMonth+mthTitle).html(transferredOutSum["20-24"]["F"]);

                //ltfuM10To14_1month6
                
                jq("#ltfuM10To14_"+currMonth+mthTitle).html(ltfuSum["10-14"]["M"]);
                jq("#ltfuM15To19_"+currMonth+mthTitle).html(ltfuSum["15-19"]["M"]);
                jq("#ltfuM20To24_"+currMonth+mthTitle).html(ltfuSum["20-24"]["M"]);
                
                jq("#ltfuF10To14_"+currMonth+mthTitle).html(ltfuSum["10-14"]["F"]);
                jq("#ltfuF15To19_"+currMonth+mthTitle).html(ltfuSum["15-19"]["F"]);
                jq("#ltfuF20To24_"+currMonth+mthTitle).html(ltfuSum["20-24"]["F"]);


                //deadM10To14_1month6
                
                jq("#deadM10To14_"+currMonth+mthTitle).html(deadSum["10-14"]["M"]);
                jq("#deadM15To19_"+currMonth+mthTitle).html(deadSum["15-19"]["M"]);
                jq("#deadM20To24_"+currMonth+mthTitle).html(deadSum["20-24"]["M"]);
                
                jq("#deadF10To14_"+currMonth+mthTitle).html(deadSum["10-14"]["F"]);
                jq("#deadF15To19_"+currMonth+mthTitle).html(deadSum["15-19"]["F"]);
                jq("#deadF20To24_"+currMonth+mthTitle).html(deadSum["20-24"]["F"]);

                //optedOutM10To14_1month6
                
                jq("#optedOutM10To14_"+currMonth+mthTitle).html(optedOutSum["10-14"]["M"]);
                jq("#optedOutM15To19_"+currMonth+mthTitle).html(optedOutSum["15-19"]["M"]);
                jq("#optedOutM20To24_"+currMonth+mthTitle).html(optedOutSum["20-24"]["M"]);
                
                jq("#optedOutF10To14_"+currMonth+mthTitle).html(optedOutSum["10-14"]["F"]);
                jq("#optedOutF15To19_"+currMonth+mthTitle).html(optedOutSum["15-19"]["F"]);
                jq("#optedOutF20To24_"+currMonth+mthTitle).html(optedOutSum["20-24"]["F"]);

                //transitionedM10To14_1month6
                
                jq("#transitionedM10To14_"+currMonth+mthTitle).html(transitionedSum["10-14"]["M"]);
                jq("#transitionedM15To19_"+currMonth+mthTitle).html(transitionedSum["15-19"]["M"]);
                jq("#transitionedM20To24_"+currMonth+mthTitle).html(transitionedSum["20-24"]["M"]);
                
                jq("#transitionedF10To14_"+currMonth+mthTitle).html(transitionedSum["10-14"]["F"]);
                jq("#transitionedF15To19_"+currMonth+mthTitle).html(transitionedSum["15-19"]["F"]);
                jq("#transitionedF20To24_"+currMonth+mthTitle).html(transitionedSum["20-24"]["F"]);

                //exitedM10To14_1month6
                
                jq("#exitedM10To14_"+currMonth+mthTitle).html(exitedSum["10-14"]["M"]);
                jq("#exitedM15To19_"+currMonth+mthTitle).html(exitedSum["15-19"]["M"]);
                jq("#exitedM20To24_"+currMonth+mthTitle).html(exitedSum["20-24"]["M"]);
                
                jq("#exitedF10To14_"+currMonth+mthTitle).html(exitedSum["10-14"]["F"]);
                jq("#exitedF15To19_"+currMonth+mthTitle).html(exitedSum["15-19"]["F"]);
                jq("#exitedF20To24_"+currMonth+mthTitle).html(exitedSum["20-24"]["F"]);

                

         
                
                
                
                

                /*
                if (i>monthDifference) {
                break;
                }
                */

            }
            
            
          
         






















                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWhoKeptScheduledPickupAfter") }');
            })

            .then(function(response){
                
                //var data = JSON.parse(response);
                //var male1014 = data["male10To14"];
                //var male1519 = data["male15To19"];
                //var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                //var female1014 = data["female10To14"];
                //var female1519 = data["female15To19"];
                //var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
//                
                //jq("#scheduledKeptPickupFUM10To14_"+currMonth).html(male1014)
                //jq("#scheduledKeptPickupFUM15To19_"+currMonth).html(male1519)
                //jq("#scheduledKeptPickupFUM20To24_"+currMonth).html(male2024)
                //jq("#scheduledKeptPickupFUMabove24_"+currMonth).html(maleabove24)
//                
                //jq("#scheduledKeptPickupFUF10To14_"+currMonth).html(female1014)
                //jq("#scheduledKeptPickupFUF15To19_"+currMonth).html(female1519);
                //jq("#scheduledKeptPickupFUF20To24_"+currMonth).html(female2024);
                //jq("#scheduledKeptPickupFUFabove24_"+currMonth).html(femaleabove24);
//                
                //var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                //jq("#scheduledKeptPickupFUTotal_"+currMonth).html(total)
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithGoodAdhScoreAfter") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#goodAdhFUM10To14_"+currMonth).html(male1014)
                jq("#goodAdhFUM15To19_"+currMonth).html(male1519)
                jq("#goodAdhFUM20To24_"+currMonth).html(male2024)
                jq("#goodAdhFUMabove24_"+currMonth).html(maleabove24)
                
                jq("#goodAdhFUF10To14_"+currMonth).html(female1014)
                jq("#goodAdhFUF15To19_"+currMonth).html(female1519);
                jq("#goodAdhFUF20To24_"+currMonth).html(female2024)
                jq("#goodAdhFUFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#goodAdhFUTotal_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledEligibleForVL") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#eligibleFUM10To14_"+currMonth).html(male1014)
                jq("#eligibleFUM15To19_"+currMonth).html(male1519)
                jq("#eligibleFUM20To24_"+currMonth).html(male2024)
                jq("#eligibleFUMabove24_"+currMonth).html(maleabove24)
                
                jq("#eligibleFUF10To14_"+currMonth).html(female1014)
                jq("#eligibleFUF15To19_"+currMonth).html(female1519);
                jq("#eligibleFUF20To24_"+currMonth).html(female2024)
                jq("#eligibleFUFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#eligibleFUTotal_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledEligibleForVLWithSampleTaken") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#samplesTakenM10To14_"+currMonth).html(male1014)
                jq("#samplesTakenM15To19_"+currMonth).html(male1519)
                jq("#samplesTakenM20To24_"+currMonth).html(male2024)
                jq("#samplesTakenMabove24_"+currMonth).html(maleabove24)
                
                jq("#samplesTakenF10To14_"+currMonth).html(female1014)
                jq("#samplesTakenF15To19_"+currMonth).html(female1519);
                jq("#samplesTakenF20To24_"+currMonth).html(female2024)
                jq("#samplesTakenFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#samplesTakenTotal_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledEligibleForVLWithSampleTakenAndResult") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#samplesTakenResultM10To14_"+currMonth).html(male1014)
                jq("#samplesTakenResultM15To19_"+currMonth).html(male1519)
                jq("#samplesTakenResultM20To24_"+currMonth).html(male2024)
                jq("#samplesTakenResultMabove24_"+currMonth).html(maleabove24)
                
                jq("#samplesTakenResultF10To14_"+currMonth).html(female1014)
                jq("#samplesTakenResultF15To19_"+currMonth).html(female1519);
                jq("#samplesTakenResultF20To24_"+currMonth).html(female2024)
                jq("#samplesTakenResultFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#samplesTakenResultTotal_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledEligibleForVLWithSampleTakenAndResultBelow200") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#samplesTakenResult200M10To14_"+currMonth).html(male1014)
                jq("#samplesTakenResult200M15To19_"+currMonth).html(male1519)
                jq("#samplesTakenResult200M20To24_"+currMonth).html(male2024)
                jq("#samplesTakenResult200Mabove24_"+currMonth).html(maleabove24)
                
                jq("#samplesTakenResult200F10To14_"+currMonth).html(female1014)
                jq("#samplesTakenResult200F15To19_"+currMonth).html(female1519);
                jq("#samplesTakenResult200F20To24_"+currMonth).html(female2024)
                jq("#samplesTakenResult200Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#samplesTakenResult200Total_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove200Below1000") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#samplesTakenResult200To1000M10To14_"+currMonth).html(male1014)
                jq("#samplesTakenResult200To1000M15To19_"+currMonth).html(male1519)
                jq("#samplesTakenResult200To1000M20To24_"+currMonth).html(male2024)
                jq("#samplesTakenResult200To1000Mabove24_"+currMonth).html(maleabove24)
                
                jq("#samplesTakenResult200To1000F10To14_"+currMonth).html(female1014)
                jq("#samplesTakenResult200To1000F15To19_"+currMonth).html(female1519);
                jq("#samplesTakenResult200To1000F20To24_"+currMonth).html(female2024)
                jq("#samplesTakenResult200To1000Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#samplesTakenResult200To1000Total_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledEligibleForVLWithSampleTakenAndResultAbove1000") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#samplesTakenResult1000M10To14_"+currMonth).html(male1014)
                jq("#samplesTakenResult1000M15To19_"+currMonth).html(male1519)
                jq("#samplesTakenResult1000M20To24_"+currMonth).html(male2024)
                jq("#samplesTakenResult1000Mabove24_"+currMonth).html(maleabove24)
                
                jq("#samplesTakenResult1000F10To14_"+currMonth).html(female1014)
                jq("#samplesTakenResult1000F15To19_"+currMonth).html(female1519);
                jq("#samplesTakenResult1000F20To24_"+currMonth).html(female2024)
                jq("#samplesTakenResult1000Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#samplesTakenResult1000Total_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVLPast12Months") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#resultPast12MtM10To14_"+currMonth).html(male1014)
                jq("#resultPast12MtM15To19_"+currMonth).html(male1519)
                jq("#resultPast12MtM20To24_"+currMonth).html(male2024)
                jq("#resultPast12MtMabove24_"+currMonth).html(maleabove24)
                
                jq("#resultPast12MtF10To14_"+currMonth).html(female1014)
                jq("#resultPast12MtF15To19_"+currMonth).html(female1519);
                jq("#resultPast12MtF20To24_"+currMonth).html(female2024)
                jq("#resultPast12MtFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#resultPast12MtTotal_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultBelow200") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#resultPast12Mt200M10To14_"+currMonth).html(male1014)
                jq("#resultPast12Mt200M15To19_"+currMonth).html(male1519)
                jq("#resultPast12Mt200M20To24_"+currMonth).html(male2024)
                jq("#resultPast12Mt200Mabove24_"+currMonth).html(maleabove24)
                
                jq("#resultPast12Mt200F10To14_"+currMonth).html(female1014)
                jq("#resultPast12Mt200F15To19_"+currMonth).html(female1519);
                jq("#resultPast12Mt200F20To24_"+currMonth).html(female2024)
                jq("#resultPast12Mt200Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#resultPast12Mt200Total_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove200Below1000") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#resultPast12Mt200To100M10To14_"+currMonth).html(male1014)
                jq("#resultPast12Mt200To100M15To19_"+currMonth).html(male1519)
                jq("#resultPast12Mt200To100M20To24_"+currMonth).html(male2024)
                jq("#resultPast12Mt200To100Mabove24_"+currMonth).html(maleabove24)
                
                jq("#resultPast12Mt200To100F10To14_"+currMonth).html(female1014)
                jq("#resultPast12Mt200To100F15To19_"+currMonth).html(female1519);
                jq("#resultPast12Mt200To100F20To24_"+currMonth).html(female2024)
                jq("#resultPast12Mt200To100Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#resultPast12Mt200To100Total_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove1000") }');
            })
                

            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#resultPast12Mt1000M10To14_"+currMonth).html(male1014)
                jq("#resultPast12Mt1000M15To19_"+currMonth).html(male1519)
                jq("#resultPast12Mt1000M20To24_"+currMonth).html(male2024)
                jq("#resultPast12Mt1000Mabove24_"+currMonth).html(maleabove24)
                
                jq("#resultPast12Mt1000F10To14_"+currMonth).html(female1014)
                jq("#resultPast12Mt1000F15To19_"+currMonth).html(female1519);
                jq("#resultPast12Mt1000F20To24_"+currMonth).html(female2024)
                jq("#resultPast12Mt1000Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#resultPast12Mt1000Total_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove1000CompletedEAC") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#resultPast12Mt1000EACM10To14_"+currMonth).html(male1014)
                jq("#resultPast12Mt1000EACM15To19_"+currMonth).html(male1519)
                jq("#resultPast12Mt1000EACM20To24_"+currMonth).html(male2024)
                jq("#resultPast12Mt1000EACMabove24_"+currMonth).html(maleabove24)
                
                jq("#resultPast12Mt1000EACF10To14_"+currMonth).html(female1014)
                jq("#resultPast12Mt1000EACF15To19_"+currMonth).html(female1519);
                jq("#resultPast12Mt1000EACF20To24_"+currMonth).html(female2024)
                jq("#resultPast12Mt1000EACFabove24_"+currMonth).html(femaleabove24)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#resultPast12Mt1000EACTotal_"+currMonth).html(total)
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVl") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#resultPast12Mt1000RepeatM10To14_"+currMonth).html(male1014)
                jq("#resultPast12Mt1000RepeatM15To19_"+currMonth).html(male1519)
                jq("#resultPast12Mt1000RepeatM20To24_"+currMonth).html(male2024)
                jq("#resultPast12Mt1000RepeatMabove24_"+currMonth).html(maleabove24)
                
                jq("#resultPast12Mt1000RepeatF10To14_"+currMonth).html(female1014)
                jq("#resultPast12Mt1000RepeatF15To19_"+currMonth).html(female1519);
                jq("#resultPast12Mt1000RepeatF20To24_"+currMonth).html(female2024)
                jq("#resultPast12Mt1000RepeatFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#resultPast12Mt1000RepeatTotal_"+currMonth).html(total)
                
                
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlBelow200") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#resultPast12Mt1000Repeat200M10To14_"+currMonth).html(male1014)
                jq("#resultPast12Mt1000Repeat200M15To19_"+currMonth).html(male1519)
                jq("#resultPast12Mt1000Repeat200M20To24_"+currMonth).html(male2024)
                jq("#resultPast12Mt1000Repeat200Mabove24_"+currMonth).html(maleabove24)
                
                jq("#resultPast12Mt1000Repeat200F10To14_"+currMonth).html(female1014)
                jq("#resultPast12Mt1000Repeat200F15To19_"+currMonth).html(female1519);
                jq("#resultPast12Mt1000Repeat200F20To24_"+currMonth).html(female2024)
                jq("#resultPast12Mt1000Repeat200Fabove24_"+currMonth).html(femaleabove24)
                
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#resultPast12Mt1000Repeat200Total_"+currMonth).html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove200Below1000") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#resultPast12Mt1000Repeat200To1000M10To14_"+currMonth).html(male1014)
                jq("#resultPast12Mt1000Repeat200To1000M15To19_"+currMonth).html(male1519)
                jq("#resultPast12Mt1000Repeat200To1000M20To24_"+currMonth).html(male2024)
                jq("#resultPast12Mt1000Repeat200To1000Mabove24_"+currMonth).html(maleabove24)
                
                jq("#resultPast12Mt1000Repeat200To1000F10To14_"+currMonth).html(female1014)
                jq("#resultPast12Mt1000Repeat200To1000F15To19_"+currMonth).html(female1519);
                jq("#resultPast12Mt1000Repeat200To1000F20To24_"+currMonth).html(female2024)
                jq("#resultPast12Mt1000Repeat200To1000Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#resultPast12Mt1000Repeat200To1000Total_"+currMonth).html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithVLPast12MonthsResultAbove1000WithRepeatVlAbove1000") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#resultPast12Mt1000Repeat1000M10To14_"+currMonth).html(male1014)
                jq("#resultPast12Mt1000Repeat1000M15To19_"+currMonth).html(male1519)
                jq("#resultPast12Mt1000Repeat1000M20To24_"+currMonth).html(male2024)
                jq("#resultPast12Mt1000Repeat1000Mabove24_"+currMonth).html(maleabove24)
                
                jq("#resultPast12Mt1000Repeat1000F10To14_"+currMonth).html(female1014)
                jq("#resultPast12Mt1000Repeat1000F15To19_"+currMonth).html(female1519);
                jq("#resultPast12Mt1000Repeat1000F20To24_"+currMonth).html(female2024)
                jq("#resultPast12Mt1000Repeat1000Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#resultPast12Mt1000Repeat1000Total_"+currMonth).html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithSwitchTo2ndLine") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#switchTo2ndM10To14_"+currMonth).html(male1014)
                jq("#switchTo2ndM15To19_"+currMonth).html(male1519)
                jq("#switchTo2ndM20To24_"+currMonth).html(male2024)
                jq("#switchTo2ndMabove24_"+currMonth).html(maleabove24)
                
                jq("#switchTo2ndF10To14_"+currMonth).html(female1014)
                jq("#switchTo2ndF15To19_"+currMonth).html(female1519);
                jq("#switchTo2ndF20To24_"+currMonth).html(female2024)
                jq("#switchTo2ndFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#switchTo2ndTotal_"+currMonth).html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledWithSwitchTo3rdLine") }');
            })


             .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#switchTo3rdM10To14_"+currMonth).html(male1014)
                jq("#switchTo3rdM15To19_"+currMonth).html(male1519)
                jq("#switchTo3rdM20To24_"+currMonth).html(male2024)
                jq("#switchTo3rdMabove24_"+currMonth).html(maleabove24)
                
                jq("#switchTo3rdF10To14_"+currMonth).html(female1014)
                jq("#switchTo3rdF15To19_"+currMonth).html(female1519);
                jq("#switchTo3rdF20To24_"+currMonth).html(female2024)
                jq("#switchTo3rdFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#switchTo3rdTotal_"+currMonth).html(total)
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalAYPLHIVEnrolledInOTZWhoComplete7") }');
            }).then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#completed7M10To14_"+currMonth).html(male1014)
                jq("#completed7M15To19_"+currMonth).html(male1519)
                jq("#completed7M20To24_"+currMonth).html(male2024)
                jq("#completed7Mabove24_"+currMonth).html(maleabove24)
                
                jq("#completed7F10To14_"+currMonth).html(female1014)
                jq("#completed7F15To19_"+currMonth).html(female1519);
                jq("#completed7F20To24_"+currMonth).html(female2024)
                jq("#completed7Fabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#completed7Total_"+currMonth).html(total)
                
                totalCompleted7 = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24)  ;
                console.log("total Completed", totalCompleted7);
                renderCompletedCharts(totalEnrolled, totalCompleted7);
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledAndTransferredOutAfter") }');
            })

         .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#transferredOutM10To14_"+currMonth).html(male1014)
                jq("#transferredOutM15To19_"+currMonth).html(male1519)
                jq("#transferredOutM20To24_"+currMonth).html(male2024)
                jq("#transferredOutMabove24_"+currMonth).html(maleabove24)
                
                jq("#transferredOutF10To14_"+currMonth).html(female1014)
                jq("#transferredOutF15To19_"+currMonth).html(female1519);
                jq("#transferredOutF20To24_"+currMonth).html(female2024)
                jq("#transferredOutFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#transferredOutTotal_"+currMonth).html(total)
                
                totalTransferred =  new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24)  ;
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledAndLTFUAfter") }');
            })
            
            .then(function(response){
                console.log("IIT data goes here");
                console.log(response);
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];

                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                
                jq("#ltfuM10To14_"+currMonth).html(male1014)
                jq("#ltfuM15To19_"+currMonth).html(male1519)
                jq("#ltfuM20To24_"+currMonth).html(male2024)
                jq("#ltfuMabove24_"+currMonth).html(maleabove24)
                
                jq("#ltfuF10To14_"+currMonth).html(female1014)
                jq("#ltfuF15To19_"+currMonth).html(female1519);
                jq("#ltfuF20To24_"+currMonth).html(female2024)
                jq("#ltfuFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#ltfuTotal_"+currMonth).html(total)
                
                totalLTFU = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24)  ;
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledAndDiedAfter") }');
            })
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#deadM10To14_"+currMonth).html(male1014)
                jq("#deadM15To19_"+currMonth).html(male1519)
                jq("#deadM20To24_"+currMonth).html(male2024)
                jq("#deadMabove24_"+currMonth).html(maleabove24)
                
                jq("#deadF10To14_"+currMonth).html(female1014)
                jq("#deadF15To19_"+currMonth).html(female1519);
                jq("#deadF20To24_"+currMonth).html(female2024)
                jq("#deadFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#deadTotal_"+currMonth).html(total)
                
                totalDead = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24)  ;
                
                renderLossesChart(totalDead, totalTransferred, totalLTFU);
                
                renderEnrolledVsTransferred(totalEnrolled, totalTransferred);
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledAndOptedOutAfter") }');
            })
            
            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#optedOutM10To14_"+currMonth).html(male1014)
                jq("#optedOutM15To19_"+currMonth).html(male1519)
                jq("#optedOutM20To24_"+currMonth).html(male2024)
                jq("#optedOutMabove24_"+currMonth).html(maleabove24)
                
                jq("#optedOutF10To14_"+currMonth).html(female1014)
                jq("#optedOutF15To19_"+currMonth).html(female1519);
                jq("#optedOutF20To24_"+currMonth).html(female2024)
                jq("#optedOutFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#optedOutTotal_"+currMonth).html(total)
                
                totalOptedOut = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24)  ;
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledAndTransitionedAfter") }');
            })
            
            .then(function(response){
                console.log("1510" +response);
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];

                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
           
                
                jq("#transitionedM10To14_"+currMonth).html(male1014)
                jq("#transitionedM15To19_"+currMonth).html(male1519)
                jq("#transitionedM20To24_"+currMonth).html(male2024)
                jq("#transitionedMabove24_"+currMonth).html(maleabove24)
                
                jq("#transitionedF10To14_"+currMonth).html(female1014)
                jq("#transitionedF15To19_"+currMonth).html(female1519);
                jq("#transitionedF20To24_"+currMonth).html(female2024)
                jq("#transitionedFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#transitionedTotal_"+currMonth).html(total)
                
                totalTransitioned = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;;
                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEnrolledAndExitedAfter") }');
            })

            .then(function(response){
                
                var data = JSON.parse(response);
                var male1014 = data["male10To14"];
                var male1519 = data["male15To19"];
                var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                var female1014 = data["female10To14"];
                var female1519 = data["female15To19"];
                var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];
                
                jq("#exitedM10To14_"+currMonth).html(male1014)
                jq("#exitedM15To19_"+currMonth).html(male1519)
                jq("#exitedM20To24_"+currMonth).html(male2024)
                jq("#exitedMabove24_"+currMonth).html(maleabove24)
                
                jq("#exitedF10To14_"+currMonth).html(female1014)
                jq("#exitedF15To19_"+currMonth).html(female1519);
                jq("#exitedF20To24_"+currMonth).html(female2024)
                jq("#exitedFabove24_"+currMonth).html(femaleabove24)
                
                var total = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24) ;
                jq("#exitedTotal_"+currMonth).html(total)
                
                totalExited = new Number(male1014) + new Number(male1519) + new Number(male2024) + new Number(maleabove24) + new Number(female1014) + new Number(female1519)  + new Number(female2024) + new Number(femaleabove24)  ;
                renderExitChart(totalExited, totalTransitioned, totalOptedOut);
                
                renderEnrolledVsExits(totalEnrolled, totalExited);
                
                //MOD Here
                renderEnrolledVsIIT(totalEnrolled, totalLTFU);
                //MOD Here2
                renderEnrolledVsTTAC(totalEnrolled, totalTransitioned);
                //MOD Here3
                renderAllOUTCOMES(totalDead, totalTransferred, totalExited, totalOptedOut);
                
                console.log("completed", datatableObj);
                //datatableObj.destroy();
                //datatableObj.draw();
                datatableObj.rows().invalidate().draw()
               
                return  myAjax({startDate:startDate, endDate:endDate}, "otz/getTxCurr.action");
            }).then(function(response){

                console.log("newDateFromStart");
            console.log(startDate);
                
                
                var data = JSON.parse(response);
                //var male1014 = data["male10To14"];
                //var male1519 = data["male15To19"];
                //var male2024 = data["male20To24"];var maleabove24 = data["maleabove24"];
                //var female1014 = data["female10To14"];
                //var female1519 = data["female15To19"];
                //var female2024 = data["female20To24"]; var femaleabove24 = data["femaleabove24"];

                
               
                filterm10to14 = txCurrProp10_14.filter((data) => {
                  return (data.gender === 'M') || (data.gender === 'Male');
                  })
                filterm15to19 = txCurrProp15_19.filter((data) => {
                  return (data.gender === 'M') || (data.gender === 'Male');
                  })
                filterm20to24 = txCurrProp20_24.filter((data) => {
                  return (data.gender === 'M') || (data.gender === 'Male');
                  })
                  
                filterf10to14 = txCurrProp10_14.filter((data) => {
                  return (data.gender === 'F') || (data.gender === 'Female');
                  })
                filterf15to19 = txCurrProp15_19.filter((data) => {
                  return (data.gender === 'F') || (data.gender === 'Female');
                  })
                filterf20to24 = txCurrProp20_24.filter((data) => {
                  return (data.gender === 'F') || (data.gender === 'Female');
                  })
                  
                
                var datalengthCurAYPLIVM10To14 = filterm10to14.length;
                var datalengthCurAYPLIVM15To19 = filterm15to19.length;
                var datalengthCurAYPLIVM20To24 = filterm20to24.length;
                var datalengthCurAYPLIVF10To14 = filterf10to14.length;
                var datalengthCurAYPLIVF15To19 = filterf15to19.length;
                var datalengthCurAYPLIVF20To24 = filterf20to24.length;
                
                jq("#AYPLHIVCurrentM10To14_"+currMonth).html(datalengthCurAYPLIVM10To14)
                jq("#AYPLHIVCurrentM15To19_"+currMonth).html(datalengthCurAYPLIVM15To19)
                jq("#AYPLHIVCurrentM20To24_"+currMonth).html(datalengthCurAYPLIVM20To24)
              
                
                jq("#AYPLHIVCurrentF10To14_"+currMonth).html(datalengthCurAYPLIVF10To14)
                jq("#AYPLHIVCurrentF15To19_"+currMonth).html(datalengthCurAYPLIVF15To19);
                jq("#AYPLHIVCurrentF20To24_"+currMonth).html(datalengthCurAYPLIVF20To24)
             

                
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("dnt") }');
            }).then(function(response){
            var data = JSON.parse(response);
            const monthsName = ["January","February","March","April","May","June","July","August","September","October","November","December"];
            var newDateFromStart = new Date(startDate);
            
            var monthYearStringg = monthsName[newDateFromStart.getMonth()] + " " + newDateFromStart.getFullYear();
            
           
            //jq(`[id^="monthYearr"]`).html(monthYearStringg)

            jq(`[id^="facilityDATIMCode"]`).html(data["DATIM_Code"])

            jq("#monthYearrM10To14_"+currMonth).html(monthYearStringg)
            jq("#monthYearrM15To19_"+currMonth).html(monthYearStringg)
            jq("#monthYearrM20To24_"+currMonth).html(monthYearStringg)
            jq("#monthYearrF10To14_"+currMonth).html(monthYearStringg)
            jq("#monthYearrF15To19_"+currMonth).html(monthYearStringg)
            jq("#monthYearrF20To24_"+currMonth).html(monthYearStringg)
            
            jq(`[id^="facilityName"]`).html(data["Facility_Name"])

            jq("#facilityNameM10To14_"+currMonth).html(data["Facility_Name"])
            jq("#facilityNameM15To19_"+currMonth).html(data["Facility_Name"])
            jq("#facilityNameM20To24_"+currMonth).html(data["Facility_Name"])
            jq("#facilityNameF10To14_"+currMonth).html(data["Facility_Name"])
            jq("#facilityNameF15To19_"+currMonth).html(data["Facility_Name"])
            jq("#facilityNameF20To24_"+currMonth).html(data["Facility_Name"])
            
            jq(`[id^="facilityLGA"]`).html(data["Facility_LGA"])

            jq("#facilityLGAM10To14_"+currMonth).html(data["Facility_LGA"])
            jq("#facilityLGAM15To19_"+currMonth).html(data["Facility_LGA"])
            jq("#facilityLGAM20To24_"+currMonth).html(data["Facility_LGA"])
            jq("#facilityLGAF10To14_"+currMonth).html(data["Facility_LGA"])
            jq("#facilityLGAF15To19_"+currMonth).html(data["Facility_LGA"])
            jq("#facilityLGAF20To24_"+currMonth).html(data["Facility_LGA"])
            
            jq(`[id^="facilityState"]`).html(data["Facility_State"])

            jq("#facilityStateM10To14_"+currMonth).html(data["Facility_State"])
            jq("#facilityStateM15To19_"+currMonth).html(data["Facility_State"])
            jq("#facilityStateM20To24_"+currMonth).html(data["Facility_State"])
            jq("#facilityStateF10To14_"+currMonth).html(data["Facility_State"])
            jq("#facilityStateF15To19_"+currMonth).html(data["Facility_State"])
            jq("#facilityStateF20To24_"+currMonth).html(data["Facility_State"])

            jq(`[id^="parner_Name"]`).html(data["Parner_Name"])

            jq("#parner_NameM10To14_"+currMonth).html(data["Parner_Name"])
            jq("#parner_NameM15To19_"+currMonth).html(data["Parner_Name"])
            jq("#parner_NameM20To24_"+currMonth).html(data["Parner_Name"])
            jq("#parner_NameF10To14_"+currMonth).html(data["Parner_Name"])
            jq("#parner_NameF15To19_"+currMonth).html(data["Parner_Name"])
            jq("#parner_NameF20To24_"+currMonth).html(data["Parner_Name"])
            
            console.log(data);
           
           
            
                //Export();
                console.log("which loop is last?")
                if(currMonth==formattedMonthLength){
                setTimeout(tableToCSV, 5000);    
                }
                //tableToCSV();
                return  myAjax({startDate:startDate, endDate:endDate, ageType:ageTyp}, '${ ui.actionLink("getTotalEligibleForMonthZeroVL") }');
            })

          
   }
   
   jq("#Export").click(function(e){
        //startDate = jq("#startDate").val();
        //endDate = jq("#endDate").val();
        
        //console.log("Start: "+startDate);
        //console.log("End: "+endDate);
        
        Export();
            
    });

    /*
    function Export() {
    jq(".loadingViewButton").html('<i class="icon-play"></i>&nbsp; Run');
    jq(".loadingViewButton").attr("disabled", false);
            jq(".dataTable").table2excel({
                filename: "OTZReport.xls"
            });
    }
    */
    /*
    var optionses = {
    "separator":",",
    filename: "OTZReport.csv"
    }
    function Export() {
    jq(".loadingViewButton").html('<i class="icon-play"></i>&nbsp; Run');
    jq(".loadingViewButton").attr("disabled", false);
            jq(".dataTable").table2csv(optionses);
    }
    */
    
    
    
    
    
    
    
    
 function tableToCSV() {
    var endloop = 0;
 
            // Variable to store the final csv data
            var csv_data = [];
 
            // Get each row data
            var rows = document.getElementsByTagName('tr');
            for (var i = 0; i < rows.length; i++) {
                
 
                // Get each column data
                var cols = rows[i].querySelectorAll('td,th');
 
                // Stores each csv row data
                var csvrow = [];
                for (var j = 0; j < cols.length; j++) {
                    if(cols[j].innerHTML=="ended"){
                        endloop=1;
                        break;
                    }
 
                    // Get the text data of each cell
                    // of a row and push it to csvrow
                    csvrow.push(cols[j].innerHTML);
                }
                if(endloop){break;}
 
                // Combine each column value with comma
                csv_data.push(csvrow.join(","));
            }
 
            // Combine each row data with new line character
            csv_data = csv_data.join('\\n');
 
            // Call this function to download csv file 
            downloadCSVFile(csv_data);
            jq(".loadingViewButton").html('<i class="icon-play"></i>&nbsp; Run');
            jq(".loadingViewButton").attr("disabled", false);
 
        }
 
        function downloadCSVFile(csv_data) {
 
            // Create CSV file object and feed
            // our csv_data into it
            CSVFile = new Blob([csv_data], {
                type: "text/csv"
            });
 
            // Create to temporary link to initiate
            // download process
            var temp_link = document.createElement('a');
 
            // Download csv file
            temp_link.download = "OTZReport.csv";
            var url = window.URL.createObjectURL(CSVFile);
            temp_link.href = url;
 
            // This link should not be displayed
            temp_link.style.display = "none";
            document.body.appendChild(temp_link);
 
            // Automatically click the link to
            // trigger download
            temp_link.click();
            document.body.removeChild(temp_link);
        }

    
    
    
    
</script>








