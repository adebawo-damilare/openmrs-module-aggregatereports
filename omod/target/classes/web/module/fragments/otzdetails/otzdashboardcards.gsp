
<br/>
    <div class="row" >
        
       <div class="col-sm-4 col-md-8">
            <a class="bootcards-summary-item label-white showDetails" href="javascript:void(0);" data-isOptimum="enrolled" data-category="enrolled" data-regimen=""  data-title="Total AYPLHIV enrolled in OTZ"  >
               <div class="row">
                   <div class="col-sm-5"><i class="icon-success icon-user"></i></div>
                   <div class="col-sm-7"><h4><strong><span class="large icon-success" id="totalEnrolledInOTZ">-</span><br/></strong> </h4></div>
               </div>
               <span class="label label-success" id="optimal_less_20_percent">AYPLHIV enrolled in OTZ</span>
            </a>
            
        </div>
        <div class="col-sm-4 col-md-4">
            <a class="bootcards-summary-item label-green1 showDetails" href="javascript:void(0);" data-isOptimum="tx_curr" data-catetory="tx_curr" data-regimen=""  data-title="TxCurr of AYPLHIV"  >
               <div class="row">
                   <div class="col-sm-5"><i class="icon-info  fa fa-users"></i></div>
                   <div class="col-sm-7"><h4><strong><span class="large icon-info" id="txCurrAyP">-</span><br/></strong> </h4></div>
               </div>
               <span class="label label-info" id="optimal_less_20_percent">AYPLHIVs Currently on Treatment</span>
            </a>
            
        </div>
       
    </div>
    <br/>
    
    <div class="row" >
       
        <div class="col-sm-4 col-md-4">
            <a class="bootcards-summary-item label-green2 showDetails" href="javascript:void(0);" data-isOptimum="maleEnrolled" data-category="maleEnrolled" data-regimen="" data-title="Total Male AYPLHIV enrolled in OTZ" >
               <div class="row">
                   <div class="col-sm-5"><i class=" icon-info fa fa-male"></i></div>
                   <div class="col-sm-7"><h4><strong><span class="large icon-info" id="totalEnrolledInOTZMale">-</span><br/></strong> </h4></div>
               </div>
               <span class="label label-warning" id="optimal_20_30_percent">AYPLHIV enrolled in OTZ (Male)</span>

            </a>
        </div>
        <div class="col-sm-4 col-md-4">
            <a class="bootcards-summary-item label-success showDetails" href="javascript:void(0);" data-isOptimum="femaleEnrolled" data-category="femaleEnrolled" data-regimen="" data-title="Total Female AYPLHIV enrolled in OTZ">
               <div class="row">
                   <div class="col-sm-5"><i class=" icon-info fa fa-female"></i></div>
                   <div class="col-sm-7"><h4><strong><span class="large icon-info" id="totalEnrolledInOTZFemale">-</span><br/></strong> </h4></div>
               </div>
               <span class="label label-info" id="optimal_above_30_percent">AYPLHIV enrolled in OTZ (Female)</span>

            </a>
            <!--<h3 class="centertext"><strong > >30kg </strong></h3>-->
        </div>
        <div class="col-sm-4 col-md-4">
            <a class="bootcards-summary-item label-info" href="javascript:void(0);" data-isOptimum="true" data-weight="bet_20_30" data-regimen="" data-title="Total Male AYPLHIV enrolled in OTZ" >
               <div class="row">
                   <div class="col-sm-5"><i class=" icon-info fa icon-bar-chart"></i></div>
                   <div class="col-sm-7"><h4><strong><span class="large icon-info" id="aypEnrolled">-</span><br/></strong> </h4></div>
               </div>
               <span class="label label-success" id="optimal_20_30_percent">% of AYPLHIVs Currently on Treatment</span>

            </a>
        </div>
        
        
    </div>
    
    <br/>
    <div class="row" style="width:110% !important">
        <div class="col-sm-12 col-md-6">
            <h3>Number of AYPLHIV enrolled in OTZ</h3>
            <div id="chartTotalEnrolled" style="height:500px;"></div>
        </div>
        <div class="col-sm-12 col-md-6">
            <h3>AYPLHIV enrolled in OTZ by Sex Distribution</h3>
            <div id="chartTotalEnrolledGender" style="height:500px;"></div>
        </div>

    </div>
    
    <div class="row" style="width:110% !important">
        
        <div class="col-sm-12 col-md-12">
            <h3>Proportion of AYPLHIV in OTZ currently on ART</h3>
            <div id="proportionOfTxCurrInOtz" style="height:500px;"></div>
        </div>


        <!--
        <div class="col-sm-12 col-md-6">
            <h3>Proportion of  Tx_Curr AYPLHIV in OTZ (by current age)</h3>
            <div id="proportionOfTxCurrInOtzByA2" style="height:500px;"></div>
        </div>
        -->
        <!--
        <div class="col-sm-12 col-md-6">
            <h3>AYPLHIV enrolled in OTZ by Age Bands</h3>
            <div id="chartTotalEnrolledAge" style="height:500px;"></div>
        </div>
        -->
    </div>
    
    <div class="row" style="width:110% !important">
        <div class="col-sm-12 col-md-12">
            <h3>Proportion of  TX_CURR AYPLHIV in OTZ (by Age)</h3>
            <div id="proportionOfTxCurrInOtzByA2" style="height:600px;"></div>
        </div>
    </div>
    
 <script type="text/javascript">
 
  
var jq = jQuery; 
var totalEnrolled = 0;
var totalTxCurr = 0;
var AYPLHIVsTxCurr = 0;
var totalMale = 0;
var totalFemale = 0;
var total1014 = 0;
var total1519 = 0;
var total2024 = 0;
var txCurr = [];
var enrolledPatients = [];
var maleEnrolled = [];
var femaleEnrolled = [];
var proportionOfTxCurrInOtzByAD = new Array();
var txCurrProp0_9 = [];
var txCurrProp10_14 = [];
var txCurrProp15_19 = [];
var txCurrProp20_24 = [];
var txCurrProp_total2024 = [];
var txCurrPropabove_24 = [];
var patients0_9ce  = [];
var patients10_14ce  = [];
var patients15_19ce  = [];
var patients20_24ce  = [];
var patientsabove_24ce  = [];
var patients0_9cn = [];
var patients10_14cn = [];
var patients15_19cn = [];
var patients20_24cn = [];
var patientsabove_24cn = [];
var propLoo = [];
var txCurrProp = [];
     var enrolListDub = [];
var enrolListFull = [];
var txCurrPropObj = {};
var TxOTZEnrolPropObj = {};
var TxOTZNEnrolPropObj = {};
 
jq(document).ready(function(){


    
    jq(".showDetails").click(function(){
        var isOptimum = jq(this).attr("data-isOptimum");
        var category = jq(this).attr("data-category");
        var title = jq(this).attr("data-title");
       
        dataquality_renderOTZData(isOptimum, category,  title);
        
    });
})

 function setCardValues(data)
 {
    var male0To10 = data["male0To10"];
    var male1014 = data["male10To14"];
    var male1519 = data["male15To19"];
    var male2024 = data["male20To24"];
    var maleabove24 = data["maleabove24"];
  
  	var female0To10 = data["female0To10"];
    var female1014 = data["female10To14"];
    var female1519 = data["female15To19"];
    var female2024 = data["female20To24"];
    var femaleabove24 = data["femaleabove24"];
   
    
    var totalMale = parseFloat(male0To10)+parseFloat(male1014) + parseFloat(male1519) + parseFloat(male2024) + parseFloat(maleabove24);
    var totalFemale = parseFloat(female0To10) + parseFloat(female1014) + parseFloat(female1519) + parseFloat(female2024) + parseFloat(femaleabove24);
    totalEnrolled = totalMale + totalFemale;
    jq("#totalEnrolledInOTZ").html(totalEnrolled);
    jq("#totalEnrolledInOTZFemale").html(totalFemale);
    jq("#totalEnrolledInOTZMale").html(totalMale);
    
    
     var totalByGender = new Array();
        totalByGender.push({title: "Male", value: totalMale, weightBand:"above_30", isOptimum:"maleEnrolled", regimen:"male", chartType:"vlotz" })
        totalByGender.push({title: "Female", value: totalFemale, weightBand:"above_30", isOptimum:"femaleEnrolled", regimen:"female",  chartType:"vlotz" });
        var colors = ["#A0CEF0", "#a903fc"]

         buildPieCharts(totalByGender, "value", "title", "chartTotalEnrolledGender", false, true, colors);
    
    getDashboardValues();
    getVlData();
    
 }
 

var patients0_9 = [];
var patients10_14 = [];
var patients15_19 = [];
var patients20_24 = [];
var patientsabove_24 = [];
var patients10_14c = [];
var patients15_19c = [];
var patients20_24c = [];
var patientsabove_24c = [];
var patientsFullDisclosure = [];
var patientsExited = [];
var allPatientsTransferred = [];
var allPatientsDied = [];
var allPatientsOptedOut = [];
var idLists = [];



 function getDashboardValues(){
    
    myAjax({startDate:startDate, endDate:endDate}, "otz/getAYPLHIVEnrolled.action").then(function(response){
    var data = JSON.parse(response);

    enrolListDub = data;  
        
    console.log("sssssssssssssssssssssssssssdddddddddddddddddddddddddddddd");
        console.log(enrolListDub);
        
                return  myAjax({startDate:startDate, endDate:endDate}, "otz/getTxCurr.action");
        
    }).then(function(response){
        var data = JSON.parse(response);
        enrolListFull = data;
        txCurrProp = data;
        console.log("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        console.log(txCurrProp);
        
        //////////////////////////////////////////////////////
        console.log("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        idLists.length=0;
        for(var i=0; i<txCurrProp.length; i++){
          
            //console.log(i);
                idLists.push(txCurrProp[i]["patientIdentifier"]);
            //console.log(idLists);
           // console.log(idLists.length);
        }
        
        
             console.log("enrollistdubbbbbbbbbbbbbbbbbbb");
        console.log(Object.keys(enrolListDub).length);
        console.log(enrolListDub);
        console.log("txCurrPropppppppppppppppppppppppp");
        console.log(txCurrProp.length);
        
        
        
        
        ////////////////keeeep////////////////////////
        /*
        for(var i=0; i<Object.keys(enrolListDub).length; i++){
            
                if(idLists.includes(enrolListDub[i]["patientIdentifier"])){
                   console.log("Found "+i);
           }else{
                console.log("Not found "+i);
                enrolListFull.push(enrolListDub[i]);
           }
        }
        */
        ////////////////keeeep////////////////////////
        console.log("enrollistfullllllllllll");
        console.log(enrolListFull.length);
        //////////////////////////////////////////////////////
        
        data2 = data;
        console.log("data222222222222222222222222222222222222222222222222222222222222222222");
        console.log(data2);
        console.log("ssssssssssssssssssssssssssssssssxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        console.log(enrolListDub.patients);

        var filteredData2 = data2.filter(function(obj) {
            return enrolListDub.patients.some(function(patient) {
                return patient.pepfarId === obj.pepfarId;
            });
        });

        console.log("ffffffffffffffffffffffffdddddddddddddddddddd222222222222222222222222222");
        console.log(filteredData2);


        //totalTxCurr = data2.length;
        totalTxCurr = filteredData2.length;
        txCurr = data;
        jq("#txCurrAyP").html(totalTxCurr);
        
        var proportionEnrolled = (totalTxCurr/totalEnrolled) * 100;
        jq("#aypEnrolled").html(proportionEnrolled.toFixed(1)+"%");
        
        
        for (var member in txCurrPropObj) delete txCurrPropObj[member];
        for (var member in TxOTZNEnrolPropObj) delete TxOTZNEnrolPropObj[member];
        txCurrProp0_9.length=0;
        txCurrProp10_14.length=0;
        txCurrProp15_19.length=0;
        txCurrProp20_24.length=0;
        txCurrPropabove_24.length=0;
		patients0_9ce.length=0;
		patients10_14ce.length=0;
        patients15_19ce.length=0;
        patients20_24ce.length=0;
        patientsabove_24ce.length=0;
        patients0_9cn.length=0;
         patients10_14cn.length=0;
        patients15_19cn.length=0;
        patients20_24cn.length=0;
        patientsabove_24cn.length=0;
        for(var i=0; i<txCurrProp.length; i++){
            
                if(txCurrProp[i]["cage"] < 10 ){
                   // txCurrProp_total1014++;
                    txCurrProp0_9.push(txCurrProp[i]);
                    
                }
                if(txCurrProp[i]["cage"] >= 10 && txCurrProp[i]["cage"] <=14){
                   // txCurrProp_total1014++;
                    txCurrProp10_14.push(txCurrProp[i]);
                    
                }
                if(txCurrProp[i]["cage"] >= 15 && txCurrProp[i]["cage"] <=19){
                   // txCurrProp_total1519++;
                    txCurrProp15_19.push(txCurrProp[i]);
                    
                }
                if(txCurrProp[i]["cage"] >= 20 && txCurrProp[i]["cage"] <=24){
                   // txCurrProp_total2024++;
                    txCurrProp20_24.push(txCurrProp[i]);
                    
                }
                if(txCurrProp[i]["cage"] > 24){
                   // txCurrProp_totalabove24++;
                    txCurrPropabove_24.push(txCurrProp[i]);
                    
                }
                
                var selendDate = Date.parse(endDate);
                var enrAge = new Date(txCurrProp[i]["enrollmentDate"]);
               
               
               	if(!(enrAge<selendDate) && (txCurrProp[i]["cage"] < 10 &&  txCurrProp[i]["age"] >= 0)){

                    patients0_9cn.push(txCurrProp[i]);
                    
                }
                if(!(enrAge<selendDate) && (txCurrProp[i]["cage"] >= 10 && txCurrProp[i]["cage"] <=14 && txCurrProp[i]["age"] >= 0)){

                    patients10_14cn.push(txCurrProp[i]);
                    
                }
                if(!(enrAge<selendDate) && (txCurrProp[i]["cage"] >= 15 && txCurrProp[i]["cage"] <=19 && txCurrProp[i]["age"] >= 0)){
                //if(txCurrProp[i]["cage"] >= 15 && txCurrProp[i]["cage"] <=19 && txCurrProp[i]["age"] == 0){
                    patients15_19cn.push(txCurrProp[i]);
                }
                if(!(enrAge<selendDate) && (txCurrProp[i]["cage"] >= 20 && txCurrProp[i]["cage"] <=24 && txCurrProp[i]["age"] >= 0)){
                //if(txCurrProp[i]["cage"] >= 20 && txCurrProp[i]["cage"] <=24 && txCurrProp[i]["age"] == 0){
                    
                    patients20_24cn.push(txCurrProp[i]);
                    
                }
                if(!(enrAge<selendDate) && (txCurrProp[i]["cage"] > 24 && txCurrProp[i]["age"] >= 0)){
                //if(txCurrProp[i]["cage"] > 24 && txCurrProp[i]["age"] == 0){
                   
                    patientsabove_24cn.push(txCurrProp[i]);
                    
                }
				
		
                if(enrAge>selendDate){continue;}		
				if(txCurrProp[i]["cage"] < 10  && txCurrProp[i]["age"] > 0){

                    patients0_9ce.push(txCurrProp[i]);
                    
                }
                if(txCurrProp[i]["cage"] >= 10 && txCurrProp[i]["cage"] <=14 && txCurrProp[i]["age"] > 0){

                    patients10_14ce.push(txCurrProp[i]);
                    
                }
                if(txCurrProp[i]["cage"] >= 15 && txCurrProp[i]["cage"] <=19 && txCurrProp[i]["age"] > 0){
                    
                    patients15_19ce.push(txCurrProp[i]);
                    
                }
                if(txCurrProp[i]["cage"] >= 20 && txCurrProp[i]["cage"] <=24 && txCurrProp[i]["age"] > 0){
                    
                    patients20_24ce.push(txCurrProp[i]);
                    
                }
                if(txCurrProp[i]["cage"] > 24 && txCurrProp[i]["age"] > 0){
                   
                    patientsabove_24ce.push(txCurrProp[i]);
                    
                }
                
         }
        
        // propLoo = [txCurrProp10_14:txCurrProp10_14.length, txCurrProp15_19:txCurrProp15_19.length, txCurrProp20_24:txCurrProp20_24.length, txCurrPropabove_24:txCurrPropabove_24.length];

       //   propLoo = [txCurrProp10_14:12, txCurrProp15_19:10, txCurrProp20_24:8, txCurrPropabove_24:6];

        console.log("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        console.log(txCurrProp10_14);
        console.log(txCurrProp15_19);
        console.log(txCurrProp20_24);
        console.log(txCurrPropabove_24);
        console.log("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        console.log(patients10_14c);
        console.log(patients15_19c);
        console.log(patients20_24c);
        console.log(patientsabove_24c);             
        
                    
       
        
        
        
        return  myAjax({startDate:startDate, endDate:endDate}, "otz/getAYPLHIVEnrolled.action");

    }).then(function(response){
        
        totalMale = 0;
        totalFemale = 0;
        total1014 = 0;
        total1519 = 0;
        total2024 = 0;
        totalabove24 = 0;
         var data = JSON.parse(response);
         
         //lets do some work on the browser
         var quarters = data["quarters"]["myHashMap"];
         
         var patients = data["patients"];
         enrolledPatients = patients;
         
         var patientsFull = data["allPatientsFullDisclosure"];
         patientsFullDisclosure = patientsFull;
         patientsExited = data["allPatientsExited"];
         allPatientsTransferred = data["allPatientsTransferred"];
         alPatientsDied = data["allPatientsDied"];
         console.log("all died++++++++++++++++++++++++++++");
         console.log(alPatientsDied);
         
         alPatientsOptedOut = data["allPatientsOptedOut"];
         console.log("all Out++++++++++++++++++++++++++++");
         console.log(alPatientsOptedOut);
         
         console.log("transferred", allPatientsTransferred);
         
        for (var member in TxOTZEnrolPropObj) delete TxOTZEnrolPropObj[member];
        patients10_14c.length=0;
        patients15_19c.length=0;
        patients20_24c.length=0;
        patientsabove_24c.length=0;
         for(var i=0; i<patients.length; i++)
         {
             var quarter = dqr_getFiscalQuarter(patients[i]["enrollmentDate"]);
             quarters[quarter]++;
             //if(patients[i]["age"] >= 10){
                
                if(patients[i]["gender"] == "Male" || patients[i]["gender"] == "M" )
                {
                   totalMale++;
                   maleEnrolled.push(patients[i]);
                   
                }
                else if(patients[i]["gender"] == "Female" || patients[i]["gender"] == "F" ){
                   totalFemale++;
                   femaleEnrolled.push(patients[i])
                }
                
                if(patients[i]["age"] >= 10 && patients[i]["age"] <=14){
                    total1014++;
                    patients10_14.push(patients[i]);
                    
                }
                if(patients[i]["age"] >= 15 && patients[i]["age"] <=19){
                    total1519++;
                    patients15_19.push(patients[i]);
                    
                }
                if(patients[i]["age"] >= 20 && patients[i]["age"] <=24){
                    total2024++;
                    patients20_24.push(patients[i]);
                    
                }
                if(patients[i]["age"] > 24){
                    totalabove24++;
                    patientsabove_24.push(patients[i]);
                    
                }
                
                

                
                
                
                if(patients[i]["cage"] >= 10 && patients[i]["cage"] <=14){
                    total1014++;
                    patients10_14c.push(patients[i]);
                    
                }
                if(patients[i]["cage"] >= 15 && patients[i]["cage"] <=19){
                    total1519++;
                    patients15_19c.push(patients[i]);
                    
                }
                if(patients[i]["cage"] >= 20 && patients[i]["cage"] <=24){
                    total2024++;
                    patients20_24c.push(patients[i]);
                    
                }
                if(patients[i]["cage"] > 24){
                    totalabove24++;
                    patientsabove_24c.push(patients[i]);
                    
                }
                
             //}
         }
        
        
        txCurrPropObj["0-9"] = txCurrProp0_9;
        txCurrPropObj["10-14"] = txCurrProp10_14;
        txCurrPropObj["15-19"] = txCurrProp15_19;
        txCurrPropObj["20-24"] = txCurrProp20_24;
        txCurrPropObj["Above 24"] = txCurrPropabove_24;
		
        //TxOTZEnrolPropObj["10-14"] = patients10_14c;
        //TxOTZEnrolPropObj["15-19"] = patients15_19c;
        //TxOTZEnrolPropObj["20-24"] = patients20_24c;
        //TxOTZEnrolPropObj["Above 24"] = patientsabove_24c;
		
		TxOTZEnrolPropObj["0-9"] = patients0_9ce;
		TxOTZEnrolPropObj["10-14"] = patients10_14ce;
        TxOTZEnrolPropObj["15-19"] = patients15_19ce;
        TxOTZEnrolPropObj["20-24"] = patients20_24ce;
        TxOTZEnrolPropObj["Above 24"] = patientsabove_24ce;
		
        TxOTZNEnrolPropObj["0-9"] = patients0_9cn;
        TxOTZNEnrolPropObj["10-14"] = patients10_14cn;
        TxOTZNEnrolPropObj["15-19"] = patients15_19cn;
        TxOTZNEnrolPropObj["20-24"] = patients20_24cn;
        TxOTZNEnrolPropObj["Above 24"] = patientsabove_24cn;
		
        proportionOfTxCurrInOtzByAD.length=0;
        
        
         proportionOfTxCurrInOtzByAD.push(
                {
                    title:"0-9",
                    cTxCurrProp:txCurrProp0_9.length,
                    cEnrolledProp:patients0_9ce.length,
                    cNotEnrolledProp: patients0_9cn.length,
                    percentage: ((patients0_9ce.length / txCurrProp0_9.length)  * 100).toFixed(1)

                 } );
                 
         proportionOfTxCurrInOtzByAD.push(
                {
                    title:"10-14",
                    cTxCurrProp:txCurrProp10_14.length,
                    cEnrolledProp:patients10_14ce.length,
                    cNotEnrolledProp: patients10_14cn.length,
                    percentage: ((patients10_14ce.length / txCurrProp10_14.length)  * 100).toFixed(1)

                 } );
        proportionOfTxCurrInOtzByAD.push(
                {
                    title:"15-19",
                    cTxCurrProp:txCurrProp15_19.length,
                    cEnrolledProp:patients15_19ce.length,
                    cNotEnrolledProp: patients15_19cn.length,
                    percentage: ((patients15_19ce.length / txCurrProp15_19.length)  * 100).toFixed(1)

                 } );
        proportionOfTxCurrInOtzByAD.push(
                {
                    title:"20-24",
                    cTxCurrProp:txCurrProp20_24.length,
                    cEnrolledProp:patients20_24ce.length,
                    cNotEnrolledProp: patients20_24cn.length,
                    percentage: ((patients20_24ce.length / txCurrProp20_24.length)  * 100).toFixed(1)

                 } );
        proportionOfTxCurrInOtzByAD.push(
                {
                    title:"Above 24",
                    cTxCurrProp:txCurrPropabove_24.length,
                    cEnrolledProp:patientsabove_24ce.length,
                    cNotEnrolledProp: patientsabove_24cn.length,
                    percentage: ((patientsabove_24ce.length / txCurrPropabove_24.length)  * 100).toFixed(1)

                 } );
				 
				 
				 
				 
/*				 
        proportionOfTxCurrInOtzByAD.push(
                {
                    title:"10-14",
                    cTxCurrProp:txCurrProp10_14.length,
                    cEnrolledProp:patients10_14c.length,
                    cNotEnrolledProp: txCurrProp10_14.length - patients10_14c.length,
                    percentage: ((patients10_14c.length / txCurrProp10_14.length)  * 100).toFixed(1)

                 } );
        proportionOfTxCurrInOtzByAD.push(
                {
                    title:"15-19",
                    cTxCurrProp:txCurrProp15_19.length,
                    cEnrolledProp:patients15_19c.length,
                    cNotEnrolledProp: txCurrProp15_19.length - patients15_19c.length,
                    percentage: ((patients15_19c.length / txCurrProp15_19.length)  * 100).toFixed(1)

                 } );
        proportionOfTxCurrInOtzByAD.push(
                {
                    title:"20-24",
                    cTxCurrProp:txCurrProp20_24.length,
                    cEnrolledProp:patients20_24c.length,
                    cNotEnrolledProp: txCurrProp20_24.length - patients20_24c.length,
                    percentage: ((patients20_24c.length / txCurrProp20_24.length)  * 100).toFixed(1)

                 } );
        proportionOfTxCurrInOtzByAD.push(
                {
                    title:"Above 24",
                    cTxCurrProp:txCurrPropabove_24.length,
                    cEnrolledProp:patientsabove_24c.length,
                    cNotEnrolledProp: txCurrPropabove_24.length - patientsabove_24c.length,
                    percentage: ((patientsabove_24c.length / txCurrPropabove_24.length)  * 100).toFixed(1)

                 } );
*/				 
				 
        
        
        
         
        var lastQuarter = "";
        var otzData = new Array();
        console.log(quarters);
        for(var key in quarters)
        {
            if(quarters.hasOwnProperty(key))
            {
                otzData.push(
                    {
                        quarter:key.toUpperCase(),
                        totalCount:quarters[key],
                        color:"#589BD4"
                     })
                //lastQuarter = key.toUpperCase();
            }
            
        }
        lastQuarter =  dqr_getFiscalQuarter(endDate).toUpperCase();
        
        /*var legendData = [{color:"#e35b17", title:"Patient with VL >=1000 cp/ml"}, {color:"#edc009", title:"Patient with VL between 200 - 999 cp/ml"},
                        {color:"#23ba58", title:"Patient with VL <200 cp/ml"},      
                 ]*/
        var legendData = [];


                     var graphArray = [
                        {field: "totalCount", description:"quarter", title:"Total AYPLHIV in OTZ", isOptimum:"enrolled", chartType:"vlotz"},
                       ]
                   //create chart
        buildBarCharts("# of members", otzData, graphArray, "quarter", "chartTotalEnrolled", "", "none", legendData, true, false);
        

        
        
       


        // a quick dirty swap here, originally (txCurr:totalTxCurr,txCurrEnrolled:totalEnrolled, (totalEnrolled / totalTxCurr))
        // swapped to (txCurr:totalEnrolled,txCurrEnrolled:totalTxCurr, (totalTxCurr / totalEnrolled))
        // based on new requirements
        // Not given enough time to rename the chart variables appropriately
        // this does not affect UI/UX
         var otzTxData = new Array();
         otzTxData.push(
             {
                 quarter:lastQuarter,
                 txCurr:totalEnrolled,
                 txCurrEnrolled:totalTxCurr,
                 percentage:((totalTxCurr / totalEnrolled)  * 100).toFixed(1)
              }

          );
          
          legendData = [{color:"#63A6E2", title:"# of  AYPLHIV enrolled in OTZ"}, {color:"#148240", title:"# of AYPLHIV currently on ART"},
{color:"#bf6c19", title:"% of AYPLHIVs Currently on ART"} 
                 ]


              var graphArray2 = [
                 {field: "txCurr", description:"quarter", type:"column", valueAxis:1, title:"# of AYPLHIV currently on ART", isOptimum:"tx_curr", chartType:"vlotz", color:"#63A6E2"},
                 {field: "txCurrEnrolled", description:"quarter", type:"column", valueAxis:1, title:"# of  AYPLHIV enrolled in OTZ", isOptimum:"enrolled", chartType:"vlotz", color:"#148240"},
                 {field: "percentage", type:"line", valueAxis:2,  color:"#bf6c19", description:"title", title:"% of  AYPLHIV enrolled in OTZ", isOptimum:"percentage", chartType:"vlotz"},
                ]
            //create chart
            var max = totalTxCurr;
            var min = totalEnrolled
            max = max+2;
            min = dataQuality_calculateMin(max, min);
            console.log("min max", max, min);
             dataQuality_buildBarCharts("# of members", otzTxData, graphArray2, "quarter", "proportionOfTxCurrInOtz", "", "none", legendData, true, false, max, min);
        
        
        /////////////////////////////////////////////##########################/////////////////////////////////////////////
            
        
                    legendData = [{color:"#63A6E2", title:"# of AYPLHIV currently on ART"},
                    {color:"#148240", title:"# of  AYPLHIV enrolled in OTZ"},
                    {color:"#dbc518", title:"# of  AYPLHIV not enrolled in OTZ"},
                    {color:"#ffffff01", title:"% of  AYPLHIV enrolled in OTZ"}    
             ]
            graphArray2 = [
               {field: "cTxCurrProp", type:"column", valueAxis:1, color:"#589BD4", description:"title", title:"", isOptimum:"cTxCurrProp", chartType:"vlotz"},
               {field: "cEnrolledProp", type:"column", valueAxis:1, newStack:true,  color:"#148240", description:"title", title:"", isOptimum:"cEnrolledProp", chartType:"vlotz"},
               {field: "cNotEnrolledProp", type:"column", valueAxis:1,  color:"#dbc518", description:"title", title:"", isOptimum:"cNotEnrolledProp", chartType:"vlotz"},
               {field: "percentage", type:"line", valueAxis:2,  color:"#ffffff01", description:"title", title:"", isOptimum:"totalInOtz", chartType:"otz"},
               
              ]
               //create chart
          dataQuality_buildBarCharts("Total Patients", proportionOfTxCurrInOtzByAD, graphArray2, "title", "proportionOfTxCurrInOtzByA2", "", "regular", legendData, true, false);
          
        /////////////////////////////////////////////##########################/////////////////////////////////////////////

            //buildBarCharts("# of members", otzTxData, graphArray2, "quarter", "proportionOfTxCurrInOtz", "", "none", legendData, true, false, max, min);
        
       
       //Total enrolled in OTZ by Sex distribution
               


               legendData = [{color:"#A0CEF0", title:"10-14"}, {color:"#d6d6c9", title:"15-19"},
                        {color:"#e0dd96", title:"20-24"},      
                 ]
              var totalByAge = new Array();
              totalByAge.push({title: "10-14", value: total1014, chartType:"vlotz", regimen:"total10_14", isOptimum:"total10_14" })
              totalByAge.push({title: "15-19", value: total1519, chartType:"vlotz", regimen:"total15_19", isOptimum:"total15_19" });
              totalByAge.push({title: "20-24", value: total2024,chartType:"vlotz", regimen:"total20_24", isOptimum:"total20_24" });
              var colors = ["#A0CEF0", "#d6d6c9", "#e0dd96"]

              buildPieCharts(totalByAge, "value", "title", "chartTotalEnrolledAge", false, false, colors, legendData);
               
                    
        
    });

              

 } 


 </script>
    
    