//번역
window.onload = function() {
	const locale = navigator.language || navigator.userLanguage;
	if(locale != "ko") {
		// 번역(text)
		document.querySelectorAll(".korea").forEach((item) => {
			fetch( window.location.origin + "/translate" , {
			    method: "POST",
			    headers: { 
			        "Content-Type": "application/json",
			        "X-CSRF-TOKEN": csrf_token
			    },
			    body: JSON.stringify({
			        text: item.innerText
			    }),
			})
			.then((res) => res.json())
			.then((data) => {
				item.innerText = data.message.result.translatedText;
			});
		})
	
		// 번역(placeholder)
		document.querySelectorAll(".korea-input").forEach((item) => {
			fetch( window.location.origin + "/translate" , {
			    method: "POST",
			    headers: { 
			        "Content-Type": "application/json",
			        "X-CSRF-TOKEN": csrf_token
			    },
			    body: JSON.stringify({
			        text: item.placeholder
			    }),
			})
			.then((res) => res.json())
			.then((data) => {
				item.placeholder = data.message.result.translatedText;
			}); 
		}) 		
	}	
}