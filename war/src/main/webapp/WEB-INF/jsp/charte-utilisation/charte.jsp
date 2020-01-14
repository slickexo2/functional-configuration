<%@ page import="java.net.URLEncoder" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="org.exoplatform.container.PortalContainer" %>
<%@ page import="org.exoplatform.services.resources.ResourceBundleService" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="org.gatein.common.text.EntityEncoder" %>
<%@ page language="java" %>
<%
    String contextPath = request.getContextPath();
    String lang = request.getLocale().getLanguage();
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<%=lang%>" lang="<%=lang%>">
<head>
    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <title>Charte utilisateur</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <!--link href="<%=request.getContextPath()%>/skin/charte/css/charte.css" rel="stylesheet" type="text/css"/-->

    <!--script type="text/javascript">
      function resizeIframe(obj){
         obj.style.height = 0.85 * screen.height + 'px';
     }
    </script-->

</head>
<body>


	<!--iframe src="/gendarmerie-extension-war/charte-utilisation.html" onload='resizeIframe(this)' style="width:100%;" scrolling="yes" ></iframe-->

    <div class="uiWelcomeBox" id="AccountSetup">
        <div class="bottom clearfix">

            <form name="charteForm" action="<%= contextPath + "/charte-utilisation-action"%>" method="post">
                <div class="pull-right">
                    <button class="btn inactive" id="continueButton" type="submit">Continuer</button>
                </div>

                <div class="pull-left">
                    <label class="uiCheckbox"><input type="checkbox" id="agreement" name="checkcharte" value="false" onclick="toggleState();" class="checkbox"/>
                        <span>Je reconnais avoir pris connaissance de la présente charte d’utilisation
                            et m’engage à respecter les clauses décrites dans ce document.</span>
                    </label>
                </div>

            </form>
        </div>
    </div>

</body>
</html>
