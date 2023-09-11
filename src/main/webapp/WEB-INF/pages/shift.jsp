<%-- 
    Document   : shift.jsp
    Created on : Sep 9, 2023, 12:20:20 PM
    Author     : hp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style>
    table {
        border-collapse: collapse;
        width: 100%;
    }

    th, td {
        width: 150px; 
        border: 1px solid darkgrey; /* Add a 1px solid border to each cell */
        padding: 8px; /* Add some padding to the cells for spacing */
        text-align: center;
        vertical-align: middle;/* Align text to the left */
    }

    th {
        background-color: darkgrey;
        /* Add a background color to header cells */
    }
</style>

<div class="container mt-3">
    
    <%@ include file="nav.jsp" %>
    
    <div class="row mt-3">
        <div class="col-5">
            <input class="form-control me-2" style="width: 60%" type="text" name="kw" id="kw" placeholder="Nhập họ tên...">
        </div>
                
        <div class="col">
            <input class="form-control me-2" style="width: 50%" type="week" id="weekInput">
        </div>
                
        <div class="col text-end">
            <c:url value="" var="url" />
            <button type="button" style="float: right" class="btn btn-success d-flex align-items-center mx-1" onclick="navigateTo('${url}' + 'create/')">
                Thêm <i class="mx-1 material-icons">access_time</i>
            </button>
        </div>
    </div>
                
            <table class="table table-hover mt-3" style="width: 100%">
            <thead id="table-header">
            </thead>
            <tbody id="table-body">
            </tbody>
        </table>
        <div id="message"></div>
</div>



<script>
    
     const navigateTo = (userUrl) => {
        window.location.href = userUrl;
    };
    
    document.getElementById('kw').addEventListener('input', function () {
        var filterValue = this.value.toLowerCase();
        var userList = document.getElementById('table-body');
        var rows = userList.getElementsByTagName('tr');

        for (var i = 0; i < rows.length; i++) {
            var userName = rows[i].getElementsByTagName('td')[1].textContent.toLowerCase(); // Assuming name is in the second column (index 1)

            if (userName.includes(filterValue)) {
                rows[i].style.display = ''; // Show the row
            } else {
                rows[i].style.display = 'none'; // Hide the row
            }
        }
    });
    
    const setCurrentWeek = () => {
        const weekInput = document.getElementById("weekInput");
        weekInput.addEventListener("input", getSelectedWeek);
        
        const currentDate = moment();
        weekInput.value = currentDate.format("YYYY") + "-" + "W" + currentDate.format("WW");
        
        weekInput.dispatchEvent(new Event("input"));
    };

    document.addEventListener("DOMContentLoaded", function () {
        setCurrentWeek(); 
    });
    
    const fetchNurseShifts = (startDate, endDate) => {
        const apiUrl = "http://localhost:8080/Clinic/api/nurse-shift-stats/?fromDate=" + startDate + "&toDate=" + endDate;
        console.info(apiUrl);
        return fetch(apiUrl)
            .then(res => res.json())
            .then(data => {
                return data;
            });
    };
    
     const fetchDoctorShifts = (startDate, endDate) => {
        const apiUrl = "http://localhost:8080/Clinic/api/doctors-shift-stats/?fromDate=" + startDate + "&toDate=" + endDate;
        console.info(apiUrl);
        return fetch(apiUrl)
            .then(res => res.json())
            .then(data => {
                return data;
            });
    };
    
    const renderTable = (data, daysOfWeek) => {
        const message = document.getElementById("message");
        const tableBody = document.getElementById("table-body");
        tableBody.innerHTML = "";
         const groupedShifts = {};
         if (data) {
            data.forEach(userShift => {
                
                const userData = {
                    "id": userShift[0],
                    "name": userShift[1],
                    "role": userShift[2]
                };
                
                const shifts = userShift.slice(3);

                if (!groupedShifts[userData.id]) {
                    groupedShifts[userData.id] = { userData, shifts: [] };
                }

                groupedShifts[userData.id].shifts.push(shifts);
          });
          
          if (Object.keys(groupedShifts).length === 0) {
                    message.innerHTML = `
                    <div class="alert alert-danger mt-3" style="width: 100%;">
                    <strong>Không tồn tại lịch trực....</strong>
                  </div>
                    `;
                }
      for (const userId in groupedShifts) {
          
                    const user = groupedShifts[userId];
                    const row = document.createElement("tr");

                    const id = document.createElement("td");
                    id.textContent = userId;
                    row.appendChild(id);        

                    const name = document.createElement("td");
                    name.textContent = user.userData.name;
                    row.appendChild(name);
                    
                    const role = document.createElement("td");
                    role.textContent = user.userData.role === "ROLE_DOCTOR" ? "Bác sỹ" : "Y tá";
                    row.appendChild(role);

                    daysOfWeek.forEach((d, index) => {
                    
                    const shiftDataCell = document.createElement("td");
                    
                    user.shifts.sort((a, b) => a[0].id - b[0].id).forEach(shift => {
                            const timestamp = shift[1];
                            const date = moment(timestamp);
                            const formattedDate = date.format('YYYY-MM-DD');

                            if (formattedDate === d) {
                                const shiftSpan = document.createElement("span");

                                if (shift[0].id === 1) {
                                    shiftSpan.className = "badge bg-primary mx-1 my-1";
                                } else if (shift[0].id === 2) {
                                     shiftSpan.className = "badge bg-warning mx-1 my-1";
                                } else {
                                    shiftSpan.className = "badge bg-danger mx-1 my-1";
                                }
                                shiftSpan.innerHTML = shift[0].startTime + " - " + shift[0].endTime;
                                shiftDataCell.appendChild(shiftSpan);
                            }
                    });
                    row.appendChild(shiftDataCell);
                        });

                    tableBody.appendChild(row);
                    }
                }
    };
    
   
    const getSelectedWeek = async () => {
        var tableHeader = document.getElementById("table-header");
        var tableBody = document.getElementById("table-body");
        var input = document.getElementById("weekInput").value;
        const selectedWeek = moment(input, "YYYY-WWW");
        const startDate = selectedWeek.startOf("week").day(1);
        const daysOfWeek = [];
        // Clear the table body
        tableHeader.innerHTML = "";
        tableBody.innerHTML = "";
        message.innerHTML = "";
        // Create the table header row
        let headerRow = document.createElement("tr");
        headerRow.className = "table-primary";

        let headerCell = document.createElement("th");
        headerCell.innerHTML = "Mã " + "<br />" +"";

        let headerCell2 = document.createElement("th");
        headerCell2.innerHTML = "Tên" + "<br />" + "";

         let headerCell3 = document.createElement("th");
        headerCell3.innerHTML = "Vai trò" + "<br />" + "";

        headerRow.appendChild(headerCell);
        headerRow.appendChild(headerCell2);
        headerRow.appendChild(headerCell3);

            const days = ["Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy", "Chủ nhật"];
            for (let i = 0; i < 7; i++) {
                const day = startDate.clone().add(i, "days");
                let dayHeaderCell = document.createElement("th");
                const formattedDate = day.format("YYYY-MM-DD");
                dayHeaderCell.innerHTML = days[i] + "<br />" + day.format("DD/MM/YYYY");
                daysOfWeek.push(formattedDate); // Use the formatted date for further processing
                headerRow.appendChild(dayHeaderCell);
            }

        tableHeader.appendChild(headerRow);
            console.info(daysOfWeek[0]);
            console.info(daysOfWeek[6]);

            let doctorShifts = await fetchDoctorShifts(daysOfWeek[0], daysOfWeek[6]);
            let nurseShifts = await fetchNurseShifts(daysOfWeek[0], daysOfWeek[6]);

            let allShifts = doctorShifts.concat(nurseShifts);

            if (allShifts.length === 0) {
            message.innerHTML = `
                <div class="alert alert-danger mt-3" style="width: 100%;">
                    <strong>Không tồn tại lịch trực....</strong>
                </div>
                `;
            }
            renderTable(allShifts, daysOfWeek);
    };
</script>


