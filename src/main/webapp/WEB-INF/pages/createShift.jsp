<%-- 
    Document   : createShift
    Created on : Sep 10, 2023, 7:53:14 AM
    Author     : hp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <c:url value="" var="action" />
    <form:form action="${action}" method="post" enctype="multipart/form-data">
        
            <c:if test="${not empty msg && msg ne null}">
         <div class="alert alert-danger mt-3" role="alert">
            <c:out value="${msg}" />
             </div>
        </c:if>
        
        
        <div class="form-floating mb-3 mt-3">
            <input class="form-control" list="datalistOptions" name="userName" id="exampleDataList" placeholder="Nhập họ tên....">
            <datalist id="datalistOptions">
                <c:forEach items="${userList}" var="user">
                     <option value="${user.name}">
                </c:forEach>
            </datalist>
            <label for="exampleDataList" class="form-label mb-3">Nhân viên</label>
        </div>
        
    

     <div class="form-floating mb-3 mt-3">
         <select class="form-select" aria-label="Default select example" name="shiftId">
             <c:forEach items="${shiftList}" var="shift" >
                 <option value="${shift.id}" ${shift.id eq 1 ? 'disabled' : ''}>${shift.startTime} - ${shift.endTime}</option>
                 </c:forEach>
         </select>
        <label for="exampleDataList" class="form-label mb-3">Giờ trực</label>
     </div>
        
    <div class="form-floating mb-3 mt-3">
        <input class="form-control" type="date" id ="dateInput" name="date">
    <label for="exampleDataList" class="form-label mb-3">Ngày làm</label>
  </div>
        <button type="submit" class="btn btn-primary">Thêm mới</button>
    </form:form>
<script>
    // Use Moment.js to get tomorrow's date
    const tomorrow = moment().add(1, 'day');

    // Format tomorrow's date in YYYY-MM-DD
    const tomorrowFormatted = tomorrow.format('YYYY-MM-DD');

    // Set the minimum date for the input element to tomorrow
    document.getElementById('dateInput').min = tomorrowFormatted;
    document.getElementById('dateInput').value = tomorrowFormatted;
</script>