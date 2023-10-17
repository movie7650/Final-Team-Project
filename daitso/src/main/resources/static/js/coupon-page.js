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

function getCoupon(){
	console.log("toy");
}