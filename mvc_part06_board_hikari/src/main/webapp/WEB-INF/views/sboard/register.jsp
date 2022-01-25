<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp"/>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">SEARCH BOARD REGISTER</h3>
				</div>
				<form action="register" method="post">
					<div class="box-body">
						<div class="form-group">
							<label>TITLE</label>
							<input type="text" name="title" class="form-control" placeholder="TITLE" required />
						</div>
						<div class="form-group">
							<label>WRITER</label>
							<input type="text" name="writer" class="form-control" placeholder="Enter Writer" required />
						</div>
						<div class="form-group">
							<label>CONTENT</label>
							<textarea name="content" class="form-control" placeholder="Enter Content" rows="7" required></textarea>
						</div>
					</div>
					<div class="box-footer">
						<input type="submit" value="SUBMIT" class="btn btn-warning form-control"/>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>
<jsp:include page="../include/footer.jsp"/>