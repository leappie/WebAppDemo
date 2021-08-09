<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Holiday checker</title>
</head>
<h1>Holiday checker</h1>

<%-- send to default route as a post requets --%>
<form action="/" method="post">
    <label for="location">Location: </label>
    <input type="text" id="location" name="location" value="${location}" placeholder="Example: UK, US, NL etc.">
    <br><br>

    <label for="date">Date: </label>
    <input type="text" id="date" name="date" value="${date}" placeholder="dd-mm-yyyy">
    <br><br>

    <small>Date input types:</small><br>
    <small>&emsp;- yyyy</small><br>
    <small>&emsp;- mm-yyyy</small><br>
    <small>&emsp;- dd-mm-yyyy</small><br><br>

    <button type="submit">Submit</button>
</form>

<p>Result: </p>
<textarea rows="10" cols="120" style="text-align: left">
    ${result}
</textarea>


</body>
</html>