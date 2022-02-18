<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<script>
	/*
		정규식은 문자열에 포함된 문자 조합을 찾기위해 사용되는 패턴
		RegExp exec, test 함수
		string match, replace, search, split
	*/
	
	const regex = /\d{3}-\d{3,4}-\d{4}/;
	// \d 숫자를 의미, {}수자는 갯수를 의미
	let bool = regex.test('019-3903-4660');
	console.log("bool : "+bool);
	bool = regex.test('0-390-460');
	console.log("bool : "+bool);
	
	const text = "배민구의 번호는 010-3903-4660이다. 장난전화 ㄱㄱ";
	let result = text.match(regex);
	console.log(result);
	
	/* 
		Character		내용
		|				또는
		()				group
		[]				문자셋, 괄호안의 어떤 문자든
		[^]				부정 문자셋, 괄호안의 어떤 문자가 아닐때
		(?:)			해당되는 문자열을 찾지만 그룹에 포함시키지는 않음
		?				없거나 있거나(0 or 1개)
		*				없거나 있거나 많거나(0 or 여러개)
		+				하나 또는 많이(1 or 여러개) must
		{n}				n만큼 반복
		{min,}			최소값만 지정
		{min,max}		범위지정 min에서 max 갯수만큼
		
		\				특수 문자가 아닌 문자
		\d				digit 숫자
		\D				숫자가 아님
		\w				word 문자
		\W				문자가 아님
		\s				space 공백
		\S				공백이 아님
		
		/\d{2,3}/gmisy
		flags
		/jpg|jpeg|png|gif/i
		g				패턴과 일치하는 모든 것들은 찾는다. g가 없으면 첫번째 결과만 반환
		i				대소문자를 구분없이 검색, A와a가 동등
		m				여러행의 정보를 검색
	*/
	
</script>
</body>
</html>