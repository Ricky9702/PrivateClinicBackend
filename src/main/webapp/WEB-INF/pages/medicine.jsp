<%-- 
    Document   : medicine.jsp
    Created on : Sep 10, 2023, 11:31:27 AM
    Author     : hp
--%>

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
        <div class="col-6">
            <input class="form-control me-2 mb-2" style="width: 50%" type="text" name="kw" id="kw" placeholder="Nhập tên thuốc...">
            
            <input class="form-control" style="width: 50%" list="datalistOptions" id ="cateName" name="userName" id="exampleDataList" placeholder="Nhập loại thuốc....">
            <datalist id="datalistOptions">
                <c:forEach items="${cateList}" var="cate">
                     <option value="${cate.name}">
                </c:forEach>
            </datalist> 
        </div>
        
        <div class="col text-end">
            <c:url value="" var="url" />
            <button type="button" style="float: right;" class="btn btn-success d-flex align-items-center mx-1" onclick="navigateTo('${url}' + 'create/')">
                Thêm <i class="material-icons">add_circle</i>
            </button>
        </div>
              
    </div>
      
    <div id="warning" class="alert alert-danger my-4" role="alert" style="display:none">
      Loading....
    </div> 
                
           <table class="table mt-3" style="width: 100%">
            <thead id="table-header">
                <tr class="table-primary">
                    <th>Nhóm thuốc</th>
                    <th>Tên thuốc</th>
                    <th>Đơn vị</th>
                    <th>Đơn giá</th>
                    <th>Số lượng</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody id="table-body">
            </tbody>
        </table>
        <div id="message"></div>
</div>
<script>
    
    const confirmDelete = (id) => {
        if (confirm("Bạn có chắc chắn muốn xóa thông tin thuốc này không? (tất cả thông tin thuốc sẽ tạm bị vô hiệu hóa)")) {
            renderLoading(true);
            fetch('http://localhost:8080/Clinic/management/medicines/' + id, {
                method: 'DELETE'
            })
                    .then(response => {
                        renderLoading(false);
                        window.location.reload();
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
        }
    };
    
    document.addEventListener("DOMContentLoaded", () => {
        fetchMedicineUnit(); 
    });
    
     const navigateTo = (userUrl) => {
        window.location.href = userUrl;
    }
    
        document.getElementById('kw').addEventListener('input', function () {
         var kw = this.value.toLowerCase();
         var cateName = document.getElementById('cateName').value.toLowerCase(); 
         fetchMedicineUnit(kw, cateName);
     });

     document.getElementById('cateName').addEventListener('input', function () {
         var cateName = this.value.toLowerCase();
         var kw = document.getElementById('kw').value.toLowerCase();
         fetchMedicineUnit(kw, cateName);
     });
    
    
    const renderLoading = (flag) => {
        const warning = document.getElementById("warning");
        if (flag)
            document.getElementById('warning').style.display = 'block';
        else
            document.getElementById('warning').style.display = 'none';
    };
    
   const renderTable = (finalData) => {
        const tableBody = document.getElementById("table-body");
        tableBody.innerHTML = "";
        
        for (const category in finalData) {
            // Create a row for the category
            const categoryRow = document.createElement("tr");

            // Category cell with rowspan for both medicine name and units
            const categoryCell = document.createElement("td");
            categoryCell.textContent = category;

            // Calculate the total number of rows needed for this category
            let totalRows = 0;

            for (const medicineId in finalData[category]) {
                const medicineInfo = finalData[category][medicineId];
                totalRows += medicineInfo.units.length;
            }

            console.info(totalRows);
            categoryCell.setAttribute("rowspan", totalRows + 1);
            categoryRow.appendChild(categoryCell);

            // Add the category row to the table body
            tableBody.appendChild(categoryRow);

            for (const medicineId in finalData[category]) {
                const medicineInfo = finalData[category][medicineId];

                // Create rows for each unit of the medicine
                medicineInfo.units.forEach((unit, index) => {
                    const unitRow = document.createElement("tr");

                    if (index === 0) {
                        // Add medicine name in the first unit row with rowspan
                        const medicineNameCell = document.createElement("td");
                        medicineNameCell.textContent = medicineInfo.medicineName;
                        medicineNameCell.setAttribute("rowspan", medicineInfo.units.length);
                        unitRow.appendChild(medicineNameCell);
                    }

                    const unitTranslation = {
                        "bottle": "Chai",
                        "tablet": "Vỉ",
                        "pill": "Viên",
                        "jar": "Lọ",
                        "packet": "Gói"
                        // Add more translations as needed
                    };
                    
                    const activeTranslation = {
                        1: "Hoạt động",
                        0: "Vô hiệu hóa"
                    };

                    // Units cell
                    const unitsCell = document.createElement("td");
                    unitsCell.textContent = unitTranslation[unit[1]] ? unitTranslation[unit[1]] : unit[1];
                    unitRow.appendChild(unitsCell);

                    // Unit Price cell
                    const unitPriceCell = document.createElement("td");
                    unitPriceCell.textContent = unit[2];
                    unitRow.appendChild(unitPriceCell);

                    // Quantity cell
                    const quantityCell = document.createElement("td");
                    quantityCell.textContent = unit[3];
                    unitRow.appendChild(quantityCell);
                    
                     // Quantity cell
                    const statusCell = document.createElement("td");
                    statusCell.textContent = activeTranslation[unit[4]];
                    unitRow.appendChild(statusCell);
                    
                    // Create a button for each unit
                    const buttonCell = document.createElement("td");
                    buttonCell.innerHTML = `
                                <div class="d-flex">
                                    <button type="button" class="btn btn-warning d-flex align-items-center mx-1" 
                                             onclick="navigateTo('/Clinic/management/medicines/\${unit[0]}')">
                                        Sửa <i class="material-icons">edit</i>
                                    </button>
                                    <button type="button" class="btn btn-danger d-flex align-items-center mx-1" 
                                             onclick="confirmDelete('\${unit[0]}')">
                                        Xóa <i class="material-icons">delete</i>
                                    </button>
                                </div>
                                        `;
                    unitRow.appendChild(buttonCell);

                    tableBody.appendChild(unitRow);
                });
            }
        }
    };




    
    const groupedData = (data) => {
        const finalData = {};
        
        data.forEach((item) => {
            const category = item[0];
            const medicineName = item[1];
            const unit = item.slice(2);
            
            if (!finalData[category])
                finalData[category] = {};
            
            if (!finalData[category][medicineName]) {
                finalData[category][medicineName] = {
                  medicineName,
                  'units': []
                };
            }
            
            finalData[category][medicineName].units.push(unit);
            
        });
        return finalData;
    };
    
    
    
   const fetchMedicineUnit = (kw, cateName) => {
       let e = `http://localhost:8080/Clinic/api/medicine-unit-list/`;
       
       if (kw && kw.length > 0) {
           e += "?kw=" + kw;
       }
       
       else if (cateName && cateName.length > 0) {
           e += "?cateName=" + cateName;
       }
       
       else if (kw && kw.length > 0 && cateName && cateName.length > 0) {
           e += "?cateName=" + cateName + "&kw=" + kw;
       }
       
       renderLoading(true);
       
       fetch(e).then(res => res.json()).then(data => {
           console.info(data);
         
           if (data) {
                renderLoading(false);
                console.info(groupedData(data))
                return renderTable(groupedData(data));
           }
       });
   };
                    
</script>
