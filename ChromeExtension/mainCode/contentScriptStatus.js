chrome.storage.sync.get(function (data) {
    // from-mine = 1인지 확인해야함
    var userSolutions = document.querySelectorAll('.result-ac')
    for (const solution of userSolutions) {
        if (solution.tagName === 'SPAN') {
            // solution.innerText = solution.parentElement.parentElement.parentElement.firstChild.innerText + '번 문제제출'
            solution.addEventListener('click', function() {
                let domparser = new DOMParser();

                function reqListener() {
                    var test = domparser.parseFromString(this.responseText, "text/html")
                    console.log(test.getElementsByClassName("codemirror-textarea")[0].innerHTML);
                }

                var oReq = new XMLHttpRequest();
                oReq.addEventListener("load", reqListener);
                oReq.open("GET", "https://www.acmicpc.net/source/" + solution.parentElement.parentElement.parentElement.firstChild.innerText);
                // 내소스 아니거나 오류가 있을 시 소스를 불러올 수 없다고 말해야 함
                oReq.send();

            })
        }
    }



    if (data.isSubmit === true) {
        // alert(data.problem.name + ',' + data.problem.id + '번 문제를 풀러 왔구나')


        var target = document.querySelector('.result-text')

        var observer = new MutationObserver(function (mutations) {
            if (target.firstChild.innerText === "맞았습니다!!") {
                var userCode = {
                    language: document.querySelector('.time').nextSibling.firstChild.innerText,
                    memory: document.querySelector('.memory').innerText,
                    time: document.querySelector('.time').innerText,
                    len: document.querySelector('.b-text').previousSibling.innerText // 문제확인
                }
                target.firstChild.innerHTML = '<button id="ssalogTrigger">SSALOG</button>'
                const trigger = document.querySelector('#ssalogTrigger')
                trigger.addEventListener('click', function (event) {

                    // 여기서 코드 가져오기 로직 일단 위로 옮겨놓음
                    


                    // alert('SSALOG로 이동합니다. \n\n문제번호: ' + data.problem.id +
                    //     '\n사용언어 : ' + userCode.language +
                    //     '\n시간 : ' + userCode.time +
                    //     '\n메모리 : ' + userCode.memory +
                    //     '\n소스 길이 : ' + userCode.len +
                    //     '\n소스코드 : \n' + data.submitCode)
                })
            }
        });
        var config = {
            attributes: true,
            childList: true,
            characterData: true
        };
        observer.observe(target, config);

    } else {
        // alert('문제를 구경하러 왔구나')
    }
});


//무작정 이렇게 떼버리면 새로고침하면 작동안함
chrome.storage.sync.remove('isSubmit');


// if (document.getElementById("status-table").lastChild.firstChild.getElementsByClassName('result-wait').length !== 0) {
//     // 진행중인 경우 result-judging 도 넣어야 할 거 같은데
//     // c로 바로 끝나는거 확인
//     alert('문제를 풀러 왔구나')
//     // document.getElementsByClassName('result-text')[0].firstChild.addEventListener('load', function(event) {
//     //     alert('how do i know')
//     // })

// } else {
//     alert('문제를 풀지 않는구나')
// }