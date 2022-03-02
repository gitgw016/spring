<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<style>
	.maxWidth{
		max-width:500px;
	}
	
	span.text-danger{
		display:block;
		color:red;
		font-size: 10px;
		padding:5px;
	}
	
.uploadImage {
	width: 100px;
	height: 100px;
	border-radius:50px;
	border:1px solid #ccc;
}

textarea{
	resize:none;
}
	
</style>
<div class="container">
	
	<form id="joinForm" action="${path}/user/joinPost" method="post" enctype="multipart/form-data">
		<table class="container table table-bordered maxWidth">
			<tr>
				<th colspan="2" class="text-center"><h1>JOIN PAGE </h1></th>
			</tr>
			<tr>
				<td>프로필 이미지</td>
				<td class="text-center">
				<img src="${path}/resources/img/profile.jpg" id="viewImage" class="uploadImage" /> 
					<br /> 
				<input type="file" id="profileImage" name="profileImage" accept="image/*" /></td>
			</tr>
			<tr>
				<td>아이디(email)</td>
				<td>
					<input type="text" class="form-control" name="u_id" id="u_id" autocomplete="off"/>
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" class="form-control" name="u_pw" id="u_pw" autocomplete="off"/>
				</td>
			</tr>
			<tr>
				<td>비밀번호 확인</td>
				<td>
					<input type="password" class="form-control" name="u_repw" id="u_repw"/>
				</td>
			</tr>
			<tr>
				<td>이름(2~6자이내)</td>
				<td>
					<input type="text" name="u_name" class="form-control" id="u_name"/>
				</td>
			</tr>
			<tr>
				<td>전화번호(-제외 숫자만)</td>
				<td>
					<input type="text" name="u_phone" class="form-control" id="u_phone"/>
				</td>
			</tr>
			<tr>
				<td>생년월일(ex-19820607)</td>
				<td>
					<input type="text" name="u_birth" class="form-control" id="u_birth" autocomplete="off"/>
				</td>
			</tr>
			<tr>
				<td>주소</td>
				<td>
					<div class="row">
						<div class="col-md-8">
							<input type="text" class="form-control" name="u_post" id="u_post"/>
						</div>
						<div class="col-md-4">
							<input type="button" class="form-control btn btn-default" onclick="sample6_execDaumPostcode();" value="주소찾기"/>
						</div>
					</div>
					<br/>
					<input type="text" class="form-control" name="u_addr" id="u_addr"/>
					<br/>
					<input type="text" class="form-control" name="u_addr_detail" id="u_addr_detail"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<h4>이용약관</h4>
					<textarea class="form-control" readonly>당신의 개인정보는 언제든지 회사에서 사용할수 있으며... 다 뿌리고 다니거나 민구가 야동받는데 이용할거다... 그래도 여기에 가입할거니??</textarea>
					<label>
						개인정보 이용동의
						<input type="checkbox" name="u_info" id="u_info" value="y"/>
					</label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" id="joinBtn" class="form-control btn btn-primary" value="회원가입" />
				</td>
			</tr>
		</table>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
</div>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>

	var imgTemp = $("#viewImage").attr("src");
	
	$("#profileImage").on("change", function(){
		var files = $(this)[0].files[0];
		console.log(files.type);
		if (!files.type.startsWith('image/')) {
			alert('이미지를 선택해주세요.');
			removeImage();
		}else{
			var path = window.URL.createObjectURL(files);
			$("#viewImage").attr("src",path);	
		}
	});
	
	$("#removeProfile").click(function(){
		removeImage();
	});
	
	function removeImage(){
		$("#profileImage").val("");
		$("#viewImage").attr("src",imgTemp);
	}


	function sample6_execDaumPostcode(){
		new daum.Postcode({
			oncomplete : function(data){
				// 주소 검색 결과
				console.log(data);
				
				var fullAddr='';	// 최종 주소
				var extraAddr = ''; // 조합형 주소
				
				// 선택한 주소 타입에 따라 주소값을 가져온다
				if(data.userSelectedType === 'R'){ // 도로명 주소
					fullAddr = data.roadAddress;
				}else{
					// 지번 주소
					fullAddr = data.jibunAddress;
				}
				
				// 도로명 주소 타입 조합
				if(data.userSelectedType === 'R'){
					// 법정동명이 있을때 법정동명 추가
					if(data.bname !== ''){
						extraAddr += data.bname;
					}
					
					// 건물명이 존재 한다면..건물명 추가
					if(data.buildingName !== ''){
						extraAddr += (extraAddr !== '' ?','+data.buildingName : data.buildingName);
					}
					
					fullAddr += (extraAddr !== '' ? ' ('+extraAddr+')' : '');
				}
				// 우편번호 삽입
				$("#u_post").val(data.zonecode);
				// 전체 주소 삽입
				$("#u_addr").val(fullAddr);
				// 상세주소 작성
				$("#u_addr_detail").focus();
			}
		}).open();
	}
	
		$("#u_birth").datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : "yymmdd",
			dayNames : ['월요일','화요일','수요일','목요일','금요일','토요일','일요일'],
			dayNamesMin : ['월','화','수','목','금','토','일'],
			monthNamesShort : ['1','2','3','4','5','6','7','8','9','10','11','12'],
			monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] 	
		});
		
		// 규칙 추가
		$.validator.addMethod("regex",function(value,element,regexpr){
			// element : 검증 대상 요소
			// value : 검증 대상 요소가 가진 value 값
			// regexpr : 검증에 필요한 값 - 정규표현식
			return regexpr.test(value);
		});
		
		var regexPass = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;	
		var regexMobile = /^[0-9]{2,3}?[0-9]{3,4}?[0-9]{4}$/;	
		
		// 유효성 검사
		$("#joinForm").validate({
			rules : {
				u_id : {
					required : true,
					email : true,
					remote : {
						type : "post",
						url : "${path}/user/uidCheck"
					}
				},
				u_pw : {
					required: true,
					minlength : 8,
					maxlength : 16,
					regex : regexPass
				},
				u_repw : {
					required: true,
					minlength : 8,
					maxlength : 16,
					equalTo : "#u_pw"
				},
				u_name : {
					required : true,
					rangelength : [2,6]
				},
				u_phone : {
					required : true,
					regex : regexMobile
				},
				u_birth : {
					required : true
				},
				u_post : {
					required : true
				},
				u_addr : {
					required : true
				},
				u_addr_detail : {
					required : true
				},
				u_info : {
					required : true
				}
			},
			messages : {
				u_id : {
					required : "아이디 적어라",
					email : "이메일이 아니잖아",
					remote : "딴놈이 쓴다"
				},
				u_pw : {
					required : "비밀번호 쓰라",
					minlength : "8자 이상 쓰라",
					maxlength : "16글자 이하",
					regex : "섞어쓰라"
				},
				u_repw : {
					required : "비밀번호 쓰라",
					minlength : "8자 이상 쓰라",
					maxlength : "16글자 이하",
					equalTo : "비밀번호 안맞다"
				},
				u_name : {
					required : "이름 없냐?",
					rangelength : $.validator.format("{0}에서 {1}사이로 쓰라")
				},
				u_phone : {
					required : "님폰없?",
					regex : "전화번호 똑바로 쓰라"
				},
				u_birth : {
					required : "안태어남?"
				},
				u_post : {
					required : "우편번호 모르니?"
				},
				u_addr : {
					required : "집없니?"
				},
				u_addr_detail : {
					required : "집없냐구"
				},
				u_info : {
					required : "동의해라 ㅎㅎ"
				}
			},
			// debug : true,		// 확인 후 submit 실행하지 않음
			errorElement : "span",
			errorClass : "text-danger"
			
		});
		
		$.validator.setDefaults({
			submitHandler : function(){
				$("#joinForm").submit();
			}
		});
		
		$(document).ajaxSend(function(e,xhr,options){
			xhr.setRequestHeader(/* "${_csrf.parameterName}" */ "${_csrf.headerName}","${_csrf.token}");
		});
		 
</script>
</body>
</html>