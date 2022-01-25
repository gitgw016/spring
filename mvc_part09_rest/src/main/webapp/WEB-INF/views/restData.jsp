<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- GET POST PUT PATCH DELETE -->
	<h1>getSample Get</h1>
	<form action="getSample" method="get" enctype="application/x-www-form-urlencoded">
		<input type="text" name="name"/><br/>
		<input type="number" name="age"/><br/>
		<input type="submit" value="getSample GET"/><br/>
	</form>
	<hr/>
	<h1>getSample POST</h1>
	<form action="getSample" method="post">
		<input type="text" name="name"/><br/>
		<input type="number" name="age"/><br/>
		<input type="submit" value="getSample post"/><br/>
	</form>
	<hr/>
	<h1>getSample PUT</h1>
	<form action="getSample" method="post" >
		<input type="hidden" name="_method" value="put"/>
		<input type="text" name="name"/><br/>
		<input type="number" name="age"/><br/>
		<input type="submit" value="getSample PUT"/><br/>
	</form>
	<hr/>
	<h1>getSample PATCH</h1>
	<form action="getSample" method="post" >
		<input type="hidden" name="_method" value="patch"/>
		<input type="text" name="name"/><br/>
		<input type="number" name="age"/><br/>
		<input type="submit" value="getSample PATCH"/><br/>
	</form>
	<hr/>
	<h1>getSample DELETE</h1>
	<form action="getSample" method="post" >
		<input type="hidden" name="_method" value="delete"/>
		<input type="text" name="name"/><br/>
		<input type="number" name="age"/><br/>
		<input type="submit" value="getSample DELETE"/><br/>
	</form>
	
</body>
</html>