<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>에브리웨어 | everyWEAR</title>
<link rel="icon" type="image/png" href="images/fav-icon.png">
<link rel="stylesheet" type="text/css" href="css/postMn.css?v=6541">
</head>
<body>

	<%@ include file="includes/header.jsp"%>

	<section2 class="content2">
	<h3>게시물 관리</h3>
	</section2>

	<div class="container">
		<div class="user-box">
			<p class="username">정시영 님</p>
			<div class="user-info">
				<div class="label">적립금</div>
				<div class="value">25,000 ￦</div>
				<div class="label">쿠폰</div>
				<div class="value">2 개</div>
			</div>
		</div>

		<aside class="sidebar2">
			<ul>
				<li><a href="myPage.jsp">회원 정보 수정</a></li>
				<li><a href="orderHistory2.jsp">주문 내역</a></li>
				<li><a href="cart2.jsp">장바구니</a></li>
				<li><a href="wishList2.jsp">찜 상품</a></li>
				<li><a href="postMn.jsp">게시물 관리</a></li>
				<li><a href="deliveryMn.jsp">배송지 관리</a></li>
			</ul>
		</aside>

		<section class="content">
			<div class="tab-container">
				<div class="tabs">
					<button class="tab-btn active" onclick="showTab('review')">리뷰</button>
					<button class="tab-btn" onclick="showTab('qna')">Q&A</button>
				</div>

				<div class="tab-content" id="review-tab">
					<%-- 사용자가 작성한 리뷰 목록 출력 --%>
					<%-- <jsp:include page="reviewList.jsp" /> --%>
					<table class="notice-table" id="notice-table">
						<tbody>
							<tr>
								<td class="pdInfo">
									<div class="product-box">
										<img src="images/review1.jpg" alt="ARCH LOGO VARSITY JACKET"
											class="product-img">
										<div class="product-info">
											<strong>ARCH LOGO VARSITY JACKET</strong><br> NAVY<br>
											마음에 들어요
										</div>
									</div>
								</td>
								<td class="date">2025-03-30<br>★★★★☆
									<div class="review-actions">
										<a href="#" class="edit">수정</a> <a href="#"
											class="delete disabled">삭제</a>
									</div>
								</td>
							</tr>
							<tr>
								<td class="pdInfo">
									<div class="product-box">
										<img src="images/review1.jpg" alt="ARCH LOGO VARSITY JACKET"
											class="product-img">
										<div class="product-info">
											<strong>ARCH LOGO VARSITY JACKET</strong><br> NAVY<br>
											마음에 들어요
										</div>
									</div>
								</td>
								<td class="date">2025-03-30<br>★★★★☆
									<div class="review-actions">
										<a href="#" class="edit">수정</a> <a href="#"
											class="delete disabled">삭제</a>
									</div>
								</td>
							</tr>
							<tr>
								<td class="pdInfo">
									<div class="product-box">
										<img src="images/review1.jpg" alt="ARCH LOGO VARSITY JACKET"
											class="product-img">
										<div class="product-info">
											<strong>ARCH LOGO VARSITY JACKET</strong><br> NAVY<br>
											마음에 들어요
										</div>
									</div>
								</td>
								<td class="date">2025-03-30<br>★★★★☆
									<div class="review-actions">
										<a href="#" class="edit">수정</a> <a href="#"
											class="delete disabled">삭제</a>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="tab-content hidden" id="qna-tab">
					<%-- 사용자가 작성한 Q&A 목록 출력 --%>
					<%-- <jsp:include page="qnaList.jsp" /> --%>
					<table class="notice-table" id="notice-table">
						<tbody>
							<tr>
								<td class="pdInfo">
									<div class="product-box">
										<img src="images/review1.jpg" alt="ARCH LOGO VARSITY JACKET"
											class="product-img">
										<div class="product-info">
											<strong>ARCH LOGO VARSITY JACKET</strong><br> NAVY<br>
											배송 문의
										</div>
									</div>
								</td>
								<td class="date">2025-03-30<br>답변 예정
									<div class="review-actions">
										<a href="#" class="edit">수정</a> <a href="#"
											class="delete disabled">삭제</a>
									</div>
								</td>
							</tr>
							<tr>
								<td class="pdInfo">
									<div class="product-box">
										<img src="images/review1.jpg" alt="ARCH LOGO VARSITY JACKET"
											class="product-img">
										<div class="product-info">
											<strong>ARCH LOGO VARSITY JACKET</strong><br> NAVY<br>
											상품 상세 문의
										</div>
									</div>
								</td>
								<td class="date">2025-03-30<br>답변 완료
									<div class="review-actions">
										<a href="#" class="edit"></a> <a href="#"
											class="delete disabled">삭제</a>
									</div>
								</td>
							</tr>
							<tr>
								<td class="pdInfo">
									<div class="product-box">
										<img src="images/review1.jpg" alt="ARCH LOGO VARSITY JACKET"
											class="product-img">
										<div class="product-info">
											<strong>ARCH LOGO VARSITY JACKET</strong><br> NAVY<br>
											재입고 문의
										</div>
									</div>
								</td>
								<td class="date">2025-03-30<br>답변 완료
									<div class="review-actions">
										<a href="#" class="edit"></a> <a href="#"
											class="delete disabled">삭제</a>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

		</section>
	</div>

	<script>
	function showTab(tab) {
  	const reviewTab = document.getElementById('review-tab');
  	const qnaTab = document.getElementById('qna-tab');
  	const buttons = document.querySelectorAll('.tab-btn');

  	buttons.forEach(btn => btn.classList.remove('active'));

  	if (tab === 'review') {
    	reviewTab.classList.remove('hidden');
    	qnaTab.classList.add('hidden');
    	buttons[0].classList.add('active');
 	 	} else {
    	reviewTab.classList.add('hidden');
    	qnaTab.classList.remove('hidden');
    	buttons[1].classList.add('active');
  	}
	}
	</script>

</body>