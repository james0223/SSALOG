const userNode = document.querySelector('.username')
if (userNode) {
    // 로그인 했을 시
    userName = userNode.innerText
    sch = location.search
    var params = new URLSearchParams(sch)
    var currentUser = params.get('user_id')
    if (currentUser === userName) {
        // 내소스 보기 화면
        // 이미 있는 소스들 중 정답인 개체에 대해 eventlistener 달기

        var userSolutions = document.querySelectorAll('.result-ac')
        for (const solution of userSolutions) {
            if (solution.tagName === 'SPAN') {

                solution.addEventListener('click', function () {
                    var solutionConfigs = solution.closest('tr').childNodes
                    let domparser = new DOMParser();
                    var codeData = {
                        problem_name: solutionConfigs[2].firstChild.getAttribute('data-original-title'),
                        problem_id: solutionConfigs[2].firstChild.innerText,
                        memory: solutionConfigs[4].innerText,
                        time: solutionConfigs[5].innerText,
                        language: solutionConfigs[6].firstChild.innerText,
                        len: solutionConfigs[7].innerText
                    }

                    function reqListener() {
                        var test = domparser.parseFromString(this.responseText, "text/html")
                        codeData.code = test.getElementsByClassName("codemirror-textarea")[0].innerHTML;
                        console.log(codeData)
                    }
                    var oReq = new XMLHttpRequest();
                    oReq.addEventListener("load", reqListener);
                    oReq.open("GET", "https://www.acmicpc.net/source/" + solutionConfigs[0].innerText);
                    oReq.send();
                })
            }
        }
        var target = document.querySelector('.result-text')
        var observer = new MutationObserver(function (mutations) {
            if (target.firstChild.innerText === "맞았습니다!!") {

                // target.firstChild.innerHTML = '<button id="ssalogTrigger">SSALOG</button>'
                // const trigger = document.querySelector('#ssalogTrigger')
                target.firstChild.addEventListener('click', function (event) {
                    var solutionConfigs = target.closest('tr').childNodes
                    let domparser = new DOMParser();

                    var codeData = {
                        problem_name: solutionConfigs[2].firstChild.getAttribute('data-original-title'),
                        problem_id: solutionConfigs[2].firstChild.innerText,
                        memory: solutionConfigs[4].innerText,
                        time: solutionConfigs[5].innerText,
                        language: solutionConfigs[6].firstChild.innerText,
                        len: solutionConfigs[7].innerText
                    }

                    function reqListener() {
                        var test = domparser.parseFromString(this.responseText, "text/html")
                        codeData.code = test.getElementsByClassName("codemirror-textarea")[0].innerHTML;
                        console.log(codeData)
                    }
                    var oReq = new XMLHttpRequest();
                    oReq.addEventListener("load", reqListener);
                    oReq.open("GET", "https://www.acmicpc.net/source/" + solutionConfigs[0].innerText);
                    // 내소스 아니거나 오류가 있을 시 소스를 불러올 수 없다고 말해야 함
                    oReq.send();


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
    }
}



// chrome.storage.sync.get(function (data) {
//     // 일단 필요없음
// });


//무작정 이렇게 떼버리면 새로고침하면 작동안함
// chrome.storage.sync.remove('isSubmit');


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