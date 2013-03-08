<%--
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
<%@page errorPage="/WEB-INF/jsp/templates/errorpage.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/admin.jsp">
    
    <stripes:layout-component name="head">
    </stripes:layout-component>
    <stripes:layout-component name="content">
        <h1>Edit service</h1>
        <stripes:form beanclass="nl.meine.gpf.stripes.ServiceActionBean">
            <table>
                <tr>
                    <td>Name*</td><td><stripes:text name="service.name"/></td>
                </tr>
                <tr>
                    <td>Description*</td><td><stripes:text name="service.description"/></td>
                </tr>
                <tr>
                    <td>UR*</td><td><stripes:text name="service.url"/></td>
                </tr>
                <tr>
                    <td>Type*</td><td><stripes:select name="service.type"><stripes:option>-</stripes:option> <stripes:options-enumeration enum="nl.meine.gpf.entities.GeoserviceType"/></stripes:select></td>
                </tr>
            </table>
                    <stripes:submit name="save" value="Save"/> <stripes:reset value="Reset" name="reset"/>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>