<%-- 
    Document   : nav.jsp
    Created on : Sep 10, 2023, 7:23:17 AM
    Author     : hp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<ul class="nav nav-tabs mt-3">
    <li class="nav-item">
        <c:url value="/management/users/" var="url"/>
        <a class="nav-link" href="${url}">Quản lý nhân viên</a>
    </li>
    <li class="nav-item">
        <c:url value="/management/shifts/" var="url"/>
        <a class="nav-link" href="${url}">Quản lý lịch trực</a>
    </li>
    <li class="nav-item">
         <c:url value="/management/medicines/" var="url"/>
        <a class="nav-link" href="${url}">Quản lý thuốc</a>
    </li>
</ul>

<script>
    // Get the current page URL
    const currentPageUrl = window.location.pathname;

    // Get all navigation links
    const navLinks = document.querySelectorAll('.nav-link');

    // Loop through the links and set the 'active' class on the matching link
    navLinks.forEach(link => {
        if (link.getAttribute('href') === currentPageUrl) {
            link.classList.add('active');
        }
    });
</script>
