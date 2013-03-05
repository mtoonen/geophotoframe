<%--
 Geo-OV - applicatie voor het registreren van KAR meldpunten               
                                                                           
 Copyright (C) 2009-2013 B3Partners B.V.                                   
                                                                           
 This program is free software: you can redistribute it and/or modify      
 it under the terms of the GNU Affero General Public License as            
 published by the Free Software Foundation, either version 3 of the        
 License, or (at your option) any later version.                           
                                                                           
 This program is distributed in the hope that it will be useful,           
 but WITHOUT ANY WARRANTY; without even the implied warranty of            
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the              
 GNU Affero General Public License for more details.                       
                                                                           
 You should have received a copy of the GNU Affero General Public License  
 along with this program. If not, see <http://www.gnu.org/licenses/>.      
--%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="/WEB-INF/jsp/commons/errorpage.jsp" %>

<!DOCTYPE html>

<stripes:layout-definition>
    <html>
    <head>
        <title><fmt:message key="index.title"/></title>
        <script type="text/javascript"></script>
        <link rel="stylesheet" href="${contextPath}/styles/geo-photo-frame.css" type="text/css" media="screen" />
        <stripes:layout-component name="head"/>        
    </head>
    <body class="admin" id="adminBody">
        <div id="viewportcontainer">
            <stripes:layout-component name="headerlinks">
                
        <%@include file="/WEB-INF/jsp/templates/headerlinks.jsp" %>

            </stripes:layout-component>
            <div id="contentcontainer">
                <stripes:layout-component name="content"/>
            </div>
        </div>
    </body>
</html>
</stripes:layout-definition>
