<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<link rel="stylesheet" type="text/css"
      href="<html:rewrite page="/css/style.css"/>">
<html>
<head>
    <title>Demo Struts Tiles</title>
</head>
<body>

<div class="header">
    <tiles:insert attribute="header"/>
</div>

<div class="body">
    <tiles:insert attribute="body"/>
</div>

<div class="footer">
    <tiles:insert attribute="footer"/>
</div>

</body>
</html>
