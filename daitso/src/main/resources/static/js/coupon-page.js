couponPaging(1);

function couponPaging(page){
	fetch('/coupon/download/' + page)
	  .then(response => {
		    return response.text(); // XML 데이터 가져오기
		  })
		  .then(data => {
			  const mainFrame = document.querySelector('.main-frame');
			  mainFrame.innerHTML = data;
		  })
		  .catch(error => {
		    console.error("Fetch error:", error);
		  });
}

function getCoupon(couponId, customerId, _csrfToken){
	if(customerId == -1){
		window.location.replace(window.location.origin + "/customer/login");
	}
	else{
			const data = {
				customerId : customerId,
				couponId : couponId,
			};
			fetch("/coupon/download",{
				method: "POST",
				headers: {
					'Content-Type': 'application/json',
					"X-CSRF-TOKEN": _csrfToken,
				},
				body: JSON.stringify(data)
			})
			.then(response => response.json())
			.then(data=> {
				if(data > 0){
					window.alert("다운로드 성공.");
					window.location.replace(window.location.origin + "/mypage/mycoupon");
				}else if(data == -1){
					window.alert("이미 담긴 쿠폰입니다.");
					window.location.replace(window.location.origin + "/mypage/mycoupon");
				}else{
					window.alert("다운로드 실패.");
					window.location.reload();
				}
			})
	}
}