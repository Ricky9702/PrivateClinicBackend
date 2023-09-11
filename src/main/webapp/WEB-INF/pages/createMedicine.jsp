<%-- 
    Document   : createMedicine
    Created on : Sep 10, 2023, 7:44:21 PM
    Author     : hp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/management/medicines/create/" var="action" />

<form:form modelAttribute="medicineUnit" enctype="multipart/form-data" action="${action}" method="post">
    <div class="form-floating mb-3 mt-3">
        
        <form:hidden path="id" />
        
        <form:input type="text" class="form-control" name="cate" id="cateInput" path="medicineId.categoryId.name" list="cateData" placeholder="Nhập tên nhóm thuốc"/>
        <datalist id="cateData">
            <c:forEach items="${cateList}" var="c">
                <option value="${c.name}"/>
            </c:forEach>
        </datalist>
        <label for="cate" class="form-label mb-3">Nhóm thuốc</label>
        <form:errors path="medicineId.categoryId.name" element="div" cssClass="text-danger" />
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control" name="med" id="medInput" path="medicineId.name" list="medData" placeholder="Nhập tên nhóm thuốc"/>
        <datalist id="medData">
        </datalist>
        <label for="med" class="form-label mb-3">Thuốc</label>
        <form:errors path="medicineId.name" element="div" cssClass="text-danger" />
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control" name="unitView" id="medInput" path="unitId.name" list="unitData" placeholder="Nhập tên nhóm thuốc"/>
        <datalist id="unitData">
            <c:forEach items="${unitList}" var="u">
                <option value="${u.name}"/>
            </c:forEach>
        </datalist>
        <label for="unitView" class="form-label mb-3">Đơn vị</label>
        <form:errors path="unitId.name" element="div" cssClass="text-danger" />
    </div>

    <div class="form-floating mb-3 mt-3">
    <c:choose>
        <c:when test="${medicineUnit.id == null}">
            <form:input type="number" class="form-control" path="unitPrice"
                        id="price" placeholder="Giá thuốc" name="price" min="0" value="0"/>
        </c:when>
        <c:otherwise>
            <form:input type="number" class="form-control" path="unitPrice"
                        id="price" placeholder="Giá thuốc" name="price" min="0" />
        </c:otherwise>
        </c:choose>
         <label for="quantity">Số lượng</label>
    <form:errors path="quantity" element="div" cssClass="text-danger" />
    
    </div>
    
    <div class="form-floating mb-3 mt-3">
        <c:choose>
            <c:when test="${medicineUnit.id == null}">
                <form:input type="number" class="form-control" path="quantity"
                            id="quantity" placeholder="Số lượng" name="quantity" min="0" 
                            value="0" />
            </c:when>
            <c:otherwise>
                <form:input type="number" class="form-control" path="quantity"
                            id="quantity" placeholder="Số lượng" name="quantity" min="0" />
            </c:otherwise>
        </c:choose>
        <label for="quantity">Số lượng</label>
        <form:errors path="quantity" element="div" cssClass="text-danger" />
    </div>
    
     <div class="form-floating mb-3 mt-3">
            <form:select path="active" id="active" cssClass="form-select">
               <form:option value="1">Hoạt động</form:option>
                <form:option value="0">Vô hiệu hóa</form:option>
            </form:select>
            <label for="gender">Trạng thái</label>
            <form:errors path="active" element="div" cssClass="text-danger" />
        </div>
    
    <div class="form-floating mb-3 mt-3">
        <button type="submit" class="btn btn-info" >
            <c:choose>
                <c:when test="${medicineUnit.id != null}">Cập nhật thuốc</c:when>
                <c:otherwise>Thêm mới</c:otherwise>
            </c:choose>
        </button>
    </div>
</form:form>       













<script>
    const fetchMedicines = (cateName) => {

        console.info(cateName);

        var medDataList = document.getElementById("medData");

        medDataList.innerHTML = "";

        if (cateName) {
            let e = "http://localhost:8080/Clinic/api/medicines/";
            e += "?cateName=" + cateName;

            fetch(e).then(res => res.json()).then(data => {
                if (data) {
                    console.info(data);
                    data.forEach((medicine) => {
                        var option = document.createElement("option");
                        option.value = medicine.name;
                        medDataList.appendChild(option);
                    });
                }
            });
        }
    };

    document.getElementById('cateInput').addEventListener('input', function () {
        var kw = this.value.toLowerCase();
        fetchMedicines(kw);
    });
</script>






</script>