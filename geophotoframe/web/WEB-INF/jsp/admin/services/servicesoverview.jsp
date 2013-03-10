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
<%@page errorPage="/WEB-INF/jsp/commons/errorpage.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/admin.jsp">
    
    <stripes:layout-component name="head">
        <script lang="JavaScript">
            var uxpath = '${contextPath}/scripts/ux';
            var gridurl = '<stripes:url beanclass="nl.meine.gpf.stripes.ServiceActionBean" event="getGridData"/>';
            var editserviceUrl = '<stripes:url beanclass="nl.meine.gpf.stripes.ServiceActionBean" event="edit"/>';
            var removeserviceUrl = '<stripes:url beanclass="nl.meine.gpf.stripes.ServiceActionBean" event="remove"/>';
            Ext.Loader.setPath('Ext.ux', uxpath);
            
        </script>
        <script src="${contextPath}/scripts/services.js"></script>
    </stripes:layout-component>
    <stripes:layout-component name="content">
        <h1> Services overview </h1>
        <div id="grid-container"></div>
        <stripes:form beanclass="nl.meine.gpf.stripes.ServiceActionBean">
            <stripes:submit name="add" value="New service"/>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>