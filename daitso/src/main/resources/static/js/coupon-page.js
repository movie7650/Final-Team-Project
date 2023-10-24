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
			.then(response=> {
				if(!response.ok){
					return response.text().then(error => {
						throw new Error(error)
					})
				}else{
					window.alert("다운로드 성공.");
					window.location.replace(window.location.origin + "/mypage/mycoupon");					
				}
			})
			.catch(error => {
				console.log(error);
				window.alert(error);	
			})
	}
}