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
        problemname: elem[2].lastChild.getAttribute("data-original-title"),
        problemid: elem[2].lastChild.innerText,
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
      portal.setAttribute(
        "style",
        "background-color: rgba(0,0,0,0); margin-left : 10px; border: 1px solid green;  border-top-left-radius: 5px; "
      );
      portal.innerText = "SSALOG";

      portal.addEventListener("mouseover", (event) => {
        // console.dir(event.target)
        event.target.setAttribute(
          "style",
          "background-color: green; margin-left : 10px; border: 1px solid green; color : white;  "
        );
        event.target.innerText = "Write>>";
      });
      portal.addEventListener("mouseout", (event) => {
        // console.dir(event.target)
        event.target.setAttribute(
          "style",
          "background-color: rgba(0,0,0,0); margin-left : 10px; border: 1px solid green;  "
        );
        event.target.innerText = "SSALOG";
      });
      portal.addEventListener("click", (event) => addListener(solutionConfigs));

      solution.innerText = "맞았습니다!!  ";
      solution.append(portal);
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
