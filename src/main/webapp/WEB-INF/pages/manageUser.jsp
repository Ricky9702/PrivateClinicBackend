<%-- 
    Document   : manageUser
    Created on : Sep 8, 2023, 9:46:19 AM
    Author     : hp
--%>
<style>
    table {
        border-collapse: collapse;
        width: 100%;
    }

    th, td {
        width: 150px; 
        border: 1px solid darkgrey; /* Add a 1px solid border to each cell */
        padding: 8px; /* Add some padding to the cells for spacing */
        text-align: center; /* Align text to the left */
    }

    th {
        background-color: darkgrey; /* Add a background color to header cells */
    }

</style>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<div class="container mt-3">
    
    <%@ include file="nav.jsp" %>
    
    <div class="row mt-3">
        <div class="col-5">
            <form class="d-flex" action="${action}">
                <input class="form-control me-2" style="width: 60%" type="text" name="kw" id="kw" placeholder="Nhập họ tên...">
                <button class="btn btn-primary" type="submit">Tìm</button>
            </form>
        </div>
        <div class="col text-end">
            <c:url value="" var="userUrl" />
            <button type="button" style="float: right" class="btn btn-success d-flex align-items-center mx-1" onclick="navigateTo('${userUrl}' + 'create/')">
                Thêm <i class="mx-1 material-icons">group_add</i>
            </button>
        </div>
    </div>
                
            <table class="table table-hover mt-3" style="width: 100%">
            <tr class="table-primary">
                <th>Mã</th>
                <th>Tên</th>
                <th>Ngày sinh</th>
                <th>Địa chỉ</th>
                <th>Giới tính</th>
                <th>Vai trò</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            <tbody id="userList">
                <c:forEach items="${userList}" var="user">
                    <tr id="${user.id}">
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.birthday}</td>
                        <td style="width: 25%">${user.address}</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.gender == 'Female'}">Nữ</c:when>
                                <c:otherwise>Nam</c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${user.userRole == 'ROLE_DOCTOR'}">Bác sỹ</c:when>
                                <c:otherwise>Y tá</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${user.active == 1}">Hoạt động</c:when>
                                <c:otherwise>Vô hiệu hóa</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:url value="" var="userUrl" />

                            <div class="d-flex">
                                <button type="button" class="btn btn-warning d-flex align-items-center mx-1" onclick="navigateTo('${userUrl}${user.id}')">
                                    Sửa <i class="material-icons">edit</i>
                                </button>
                                <button type="button" class="btn btn-danger d-flex align-items-center mx-1" onclick="confirmDelete('${user.id}')">
                                    Xóa <i class="material-icons">delete</i>
                                </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>            
</div>

    </body>
<script>

    const confirmDelete = (userId) => {
        if (confirm("Bạn có chắc chắn muốn xóa thông tin người dùng này? (tất cả thông tin của người dùng sẽ tạm bị vô hiệu hóa)")) {
            fetch('http://localhost:8080/Clinic/management/users/' + userId, {
                method: 'DELETE'
            })
                    .then(response => {
                        window.location.reload();
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
        }
    };


    document.getElementById('kw').addEventListener('input', function () {
        var filterValue = this.value.toLowerCase();
        var userList = document.getElementById('userList');
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

    const navigateTo = (userUrl) => {
        window.location.href = userUrl;
    }
</script>







