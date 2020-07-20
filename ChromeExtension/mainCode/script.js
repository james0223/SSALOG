// function matching(user) {
//     // 컨텐츠 페이지를 대상으로 실행하는 코드
//     chrome.tabs.executeScript({
//         code: 'document.querySelector("body").innerText'
//     }, function (result) {
//         // 위 코드가 실행된 후 이 함수를 호출. 이때 코드 뒤의 내용을 result에 담아서 오게 된다.
//         var bodyText = result[0];
//         var bodyNum = bodyText.split(' ').length;
//         var myNum = bodyText.match(new RegExp('\\b(' + user + ')\\b', 'gi')).length;
//         var per = myNum / bodyNum * 100;
//         // 소수 둘째자리까지 표현
//         per = per.toFixed(2);
//         document.querySelector('#result').innerText = myNum + '/' + bodyNum + '(' + (per) + '%)';
//     });

// }


//스토리지에 저장된 값 가져오기
// chrome.storage.sync.get(function (data) {
//     document.querySelector('#user').value = data.userWords;
//     matching(data.userWords);
// });


// //addEventListener
// document.querySelector('#user').addEventListener('change', function () {
//     var user = document.querySelector('#user').value;

//     // 크롬 스토리지에 저장
//     chrome.storage.sync.set({
//         userWords: user
//     });

//     matching(user);

// })