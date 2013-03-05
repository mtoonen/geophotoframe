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
<%--@page errorPage="/WEB-INF/jsp/commons/errorpage.jsp"--%>

<stripes:layout-render name="/WEB-INF/jsp/templates/admin.jsp">

    <stripes:layout-component name="head">
    </stripes:layout-component>
    <stripes:layout-component name="content">
        <h1>User</h1>
            <p>
                <stripes:errors/>
                <stripes:messages/>
            </p>
        <stripes:form beanclass="nl.meine.gpf.stripes.UserActionBean">
            <table>
                <tr>
                    <td>Username</td> <td><stripes:text name="user.username"/></td>
                </tr>
                <tr>
                    <td>Full name</td> <td><stripes:text name="user.fullname"/></td>
                </tr>
                <tr>
                    <td>Password</td> <td><stripes:password name="password"/></td>
                </tr>
                <tr>
                    <td>Password again</td> <td><stripes:password name="password2"/></td>
                </tr>
                <tr>

                    <td><stripes:submit name="save" value="Save"/><stripes:submit name="cancel" value="Cancel"/><stripes:reset name="reset" value="Reset"/></td>
                </tr>
            </table>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>