<%-- 
    Document   : detailUser
    Created on : Sep 8, 2023, 12:23:18 PM
    Author     : hp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
    <form:form modelAttribute="user" action="${action}" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <form:errors path="*" element="div" cssClass="alert alert-danger" />
        </div>

        <!-- Hidden Fields for ID and Image -->
        <form:hidden path="id" />
        <form:hidden path="image" />

        <div class="form-floating mb-3 mt-3">
            <form:input type="file" class="form-control form-control-sm" path="file" id="file"  />
            <label class="form-label" for="file">Ảnh đại diện</label>
            <c:if test="${user.image != null}">
                <img src="${user.image}" width="120" />
            </c:if>
        </div>

        <!-- User Name -->
        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="name" id="name" placeholder="Tên người dùng" />
            <label for="name">Tên người dùng</label>
            <form:errors path="name" element="div" cssClass="text-danger" />
        </div>

        <!-- User username -->
        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="username" id="username" placeholder="Tên đăng nhập" />
            <label for="name">Tên đăng nhập</label>
            <form:errors path="username" element="div" cssClass="text-danger" />
        </div>

        <!-- User email -->
        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="email" id="email" placeholder="Email" />
            <label for="name">Email</label>
            <form:errors path="email" element="div" cssClass="text-danger" />
        </div>

        <!-- User Birthday -->
        <div class="form-floating">
            <form:input type="date" class="form-control" path="birthday" id="birthday" placeholder="Ngày sinh" />
            <label for="birthday">Ngày sinh</label>
            <form:errors path="birthday" element="div" cssClass="text-danger" />
        </div>

        <!-- User Address -->
        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="address" id="address" placeholder="Địa chỉ" />
            <label for="address">Địa chỉ</label>
            <form:errors path="address" element="div" cssClass="text-danger" />
        </div>

        <!-- User Gender (Radio Buttons) -->
        <div class="form-floating mb-3 mt-3">
            <form:select path="gender" id="gender" cssClass="form-select">
                <form:option value="Male">Nam</form:option>
                <form:option value="Female">Nữ</form:option>
            </form:select>
            <label for="gender">Giới tính</label>
            <form:errors path="gender" element="div" cssClass="text-danger" />
        </div>

        <!-- User Role -->
        <div class="form-floating mb-3 mt-3">
            <form:select path="active" id="active" cssClass="form-select">
               <form:option value="1">Hoạt động</form:option>
                <form:option value="0">Vô hiệu hóa</form:option>
            </form:select>
            <label for="gender">Trạng thái</label>
            <form:errors path="active" element="div" cssClass="text-danger" />
        </div>

        <!-- User Role -->
        <div class="form-floating mb-3 mt-3">
            <form:select path="userRole" id="userRole" cssClass="form-select" onchange="toggleDepartmentDiv()">
                <form:option value="ROLE_NURSE">Y tá</form:option>
                <form:option value="ROLE_DOCTOR">Bác sỹ</form:option>
            </form:select>
            <label for="gender">Vai trò</label>
            <form:errors path="userRole" element="div" cssClass="text-danger" />
        </div>
        
         <!-- Department -->
        <div class="form-floating mb-3 mt-3" id="departmentDiv" style="display: none;">
           <form:select path="roleDetail" id="departmentSelect" cssClass="form-select" onchange="toggleDepartmentDiv()">
            </form:select>
            <label for="gender">Khoa</label>
        </div>

        <!-- User Password -->
        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="password" id="password" placeholder="Mật khẩu" />
            <label for="address">Mật khẩu</label>
            <form:errors path="password" element="div" cssClass="text-danger" />
        </div>


        <!-- Submit Button -->
        <div class="form-floating mb-3 mt-3">
            <button type="submit" class="btn btn-primary">
                <c:choose>
                    <c:when test="${user.id != null}">Cập nhật</c:when>
                    <c:otherwise>Thêm mới</c:otherwise>
                </c:choose>
            </button>
        </div>
    </form:form>
<script>
    
    function toggleDepartmentDiv() {
        var userRoleSelect = document.getElementById("userRole");
        var departmentDiv = document.getElementById("departmentDiv");

        if (userRoleSelect.value === "ROLE_DOCTOR") {
            departmentDiv.style.display = "block"; // Show the department div
        } else {
            departmentDiv.style.display = "none"; // Hide the department div
        }
    }
    
     document.addEventListener("DOMContentLoaded", function () {
        var departmentSelect = document.getElementById("departmentSelect");
        var currentDeparment = null;
           
           if (${user.id != null}) {
                let e = "http://localhost:8080/Clinic/api/doctors/" + "${user.id}";
                fetch(e)
              .then(response => {
                if (!response.ok) {
                  throw new Error('Network response was not ok');
                }
                return response.json(); // Get the response as text
              })
              .then(data => {
                // Parse the XML data here or process it as needed
                currentDeparment = (data.departmentId);
                console.info(currentDeparment);
              })
              .catch(error => {
                console.error('Fetch error:', error);
              });
           }
          

         
        fetch("http://localhost:8080/Clinic/api/departments/")
            .then(function (response) {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error("Failed to fetch department data");
                }
            })
            .then(function (data) {
                data.forEach(function (department) {
                    var option = document.createElement("option");
                    option.value = department.id;
                    option.textContent = department.name;
                    departmentSelect.appendChild(option);
                    if (currentDeparment && currentDeparment.id == department.id)
                        option.selected = true;
                });
                departmentSelect.style.display = "block";
            })
            .catch(function (error) {
                console.error("Error:", error);
            });
            
        var roleSelect = document.getElementById("userRole");
        var departmentDiv = document.getElementById("departmentDiv");

        // Trigger the onchange event
        roleSelect.dispatchEvent(new Event("change"));

        // Check the initial value
        if (roleSelect.value === "ROLE_DOCTOR") {
            departmentDiv.style.display = "block";
        }
    });

    
</script>
