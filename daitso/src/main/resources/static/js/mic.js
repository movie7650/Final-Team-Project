window.addEventListener('beforeunload', function(event) {
    stopSTT(); 
});
function stopSTT(){
	fetch('/voice?selector=1')
}

function pressMic(){
	const div = document.querySelector('.hearder-search-div');
	div.innerHTML = `<div class="spinner-border text-danger" role="status">
  						<span class="sr-only">Loading...</span>
					 </div>`
	fetch('/voice')
	.then(response => response.json())
	.then(data => {
		console.log(data)
		const el = document.querySelector('#search-product');
		el.value = data.text;
		div.innerHTML = `<button class="header-search-btn" onclick="pressMic();"><img src="/assets/header/microphone-svgrepo-com.svg"></button>`
	})
}