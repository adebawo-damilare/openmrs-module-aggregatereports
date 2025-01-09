<%


def patients = config.patients
def startDate = config.startDate;
def endDate = config.endDate;
def title = config.title;
def Misc = config.Misc;
def getFactorial = { num -> (num <= 1) ? 1 : num * call(num - 1) }
%>
<fieldset>
        <legend>Filters</legend>
        <div class="row">
           <label class="col-sm-6 col-md-2 " ><strong>Start Date</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <input type="text" class="form-control date" id="startDate" name="startDate" value="<%= startDate%>"/>
            </div>
            
            <label class="col-sm-6 col-md-2 "><strong>Start Date</strong></label>
            <div class="col-sm-6 col-md-3" style="position:relative">
               <input type="text" class="form-control date" id="endDate" name="endDate" value="<%= endDate%>"/>
            </div>
            
            
            <div class="col-sm-6 col-md-2">
                <button class="button" id="filterCQI">Filter</button>
            </div>
        </div>
    </fieldset>
    <br/>
<div class="table-responsive">
        <table class="table-bordered dataTable" width="100%">
            <thead>
                <th>S/N</th>
                <th>Pepfar ID</th>
                <th>Gender</th>
                <th>Age at OTZ Enrollment</th>
                <th>Current Age</th>
                <th>Date Enrolled in OTZ</th>
                <th>Date Enrolled in OTZ Plus</th>
                <th>ART Start Date</th>
                <!--<th>Age Range</th>-->
                
        </thead>
            <tbody>
                <% 
                 int sn = 1;
                for(int i=0; i<patients.size(); i++)
                {
                %>
                    
                    <tr>
                        <td><%= sn++ %></td>
                        <td><%= patients.get(i).getPepfarId()%></td>
                        <td><%= patients.get(i).getGender()%></td>
                        <% 
                        if(patients.get(i).getAge() >= 10 && patients.get(i).getAge() <= 24)
                        {
                        %>
                            <td><%= patients.get(i).getAge()%></td>
                            <td><%= patients.get(i).getCage()%></td>
        

                        <%
                            }else{
                        %>
                        <td bgcolor="yellow"><%= patients.get(i).getAge()%></td>
                        <td bgcolor="yellow"><%= patients.get(i).getCage()%></td>
                        <%
                            }
                        %>
                        <td><%= patients.get(i).getEnrollmentDate()%></td>
                        <%
                        if(patients.get(i).getOtzplusedate() != null)
                        {
                        %>
                        <td><%= patients.get(i).getOtzplusedate()%></td>
                        <%
                            }else{
                        %>
                        <td></td>
                        <%
                            }
                        %>
                        <td><%= patients.get(i).getArtStartDate()%></td>
                        
                    </tr>
        
                <%
                }   
                %>
                
            
            </tbody>
        </table>
        
    
    </div>
