const ssalogDiv = document.createElement('div')
// ssalogDiv.setAttribute('class', 'job')
ssalogDiv.style.background = '#FF8000' 
ssalogDiv.innerHTML = "<a href='#'><span>VISIT<br/>SSALOG</span></a>"

// ssalogButton.innerText = "SSALOG"
const _header = document.querySelector('.header-inner')
if (_header !== null) {
    _header.appendChild(ssalogDiv)
    ssalogDiv.addEventListener('click', function() {
        alert('SSALOG 런칭 준비중입니다!\n일단 기대해주세요')
    })
}