const userNode = document.querySelector(".username");
if (userNode) {
  // 로그인 했을 시
  userName = userNode.innerText;
  sch = location.search;
  var params = new URLSearchParams(sch);
  var currentUser = params.get("user_id");
  if (currentUser === userName) {
    // 내소스 보기 화면
    // 이미 있는 소스들 중 정답인 개체에 대해 eventlistener 달기
    const addListener = function (elem) {
      let domparser = new DOMParser();
      var codeData = {
        scoring: elem[0].innerHTML,
        problemname: elem[2].firstChild.getAttribute("data-original-title"),
        problemid: elem[2].firstChild.innerText,
        memory: elem[4].innerText,
        time: elem[5].innerText,
        language: elem[6].firstChild.innerText,
        len: elem[7].innerText,
      };
      function reqListener() {
        var test = domparser.parseFromString(this.responseText, "text/html");
        codeData.code = test.getElementsByClassName(
          "codemirror-textarea"
        )[0].innerText;
        console.log(codeData);

        var httpRequest = new XMLHttpRequest();
        // httpRequest.onreadystatechange = function () {
        //   if (
        //     httpRequest.readyState == XMLHttpRequest.DONE &&
        //     httpRequest.status == 200
        //   ) {
        //     alert(httpRequest.responseText);
        //   }
        // };
        httpRequest.open(
          "POST",
          "https://ssalog.gq/api/newuser/post/write/",
          true
        );
        httpRequest.setRequestHeader(
          "Content-Type",
          "application/json; charset=UTF-8"
        );
        httpRequest.send(JSON.stringify(codeData));

        var win = window.open(
          "https://ssalog.gq/WriteLog/" + codeData.scoring,
          "_blank"
        );
        win.focus();
      }
      var oReq = new XMLHttpRequest();
      oReq.addEventListener("load", reqListener);
      oReq.open("GET", "https://www.acmicpc.net/source/" + elem[0].innerText);
      oReq.send();
    };

    var userSolutions = document.querySelectorAll("span.result-ac");
    for (const solution of userSolutions) {
      let solutionConfigs = solution.closest("tr").childNodes;
      // 이렇게 하면 외부 콜백함수에 변수를 담아 사용할 수 있다.
      var portal = document.createElement("button");
      portal.innerText = "Go to SSaLog";
      solution.innerText = "맞았습니다!!  ";
      solution.append(portal);
      portal.addEventListener("click", (event) => addListener(solutionConfigs));
    }
    var target = document.querySelector(".result-text");
    var observer = new MutationObserver(function (mutations) {
      if (target.firstChild.innerText === "맞았습니다!!") {
        var portal = document.createElement("button");
        portal.innerText = "Go to SSaLog";
        target.firstChild.innerText = "맞았습니다!!  ";
        target.firstChild.append(portal);
        // target.firstChild.innerHTML = '<button id="ssalogTrigger">SSALOG</button>'
        // const trigger = document.querySelector('#ssalogTrigger')
        let solutionConfigs = target.closest("tr").childNodes;
        portal.addEventListener("click", (event) =>
          addListener(solutionConfigs)
        );
      }
    });
    var config = {
      attributes: true,
      childList: true,
      characterData: true,
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
