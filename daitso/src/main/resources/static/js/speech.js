
let recognition = null;
function checkCompatibility(){
	recognition = new(window.SpeechRecognition || window.webkitSpeechRecognition) ();
	recognition.lang = "ko-KR";
	recognition.maxAlternatives = 5;
}

function startSpeechRecognition(){
	
	if(!recognition) {
		alert("You cannot use speech api.");
	}else{
		const box = document.querySelector('.hearder-search-div');
		const el = `<div style="width:25px; height:25px; text-align:center; cursor:pointer" onclick="endSpeechRecognition();"><img src="/assets/header/micAfter.png" style="16px; height:21px"></div>`
		box.innerHTML = el;
		console.log("start");
	
		
		recognition.addEventListener("speechstart", () => {
			console.log("Speech start");
		});
		
		recognition.addEventListener("speechend", () => {
			console.log("Speech End");
		});
		
		recognition.addEventListener("result", (event) => {
			console.log("Speech Result", event.results);
			//음성과 가장 유사한 텍스트가 첫 번째 배열에 들어옴
			const text = event.results[0][0].transcript;
			document.getElementById("search-product").value = text;
		});		
	}
	
	recognition.start();
	
}

function endSpeechRecognition() {
	const box = document.querySelector('.hearder-search-div');
	const el = `<div class="header-search-btn" onclick="startSpeechRecognition();" style="cursor:pointer; width:25px; height:25px"><img src="/assets/header/micBefore.png"></div>`
	box.innerHTML = el;
	recognition.stop();
}

window.addEventListener('load', checkCompatibility);