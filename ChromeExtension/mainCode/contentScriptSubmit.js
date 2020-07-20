document.querySelector('#submit_button').addEventListener('click', function () {
    var codeSum = ""
    var userCodes = document.getElementsByClassName('CodeMirror-line')
    var problem = {
        name: document.getElementById('submit_form').firstChild.innerText,
        id: document.getElementById('submit_form').childNodes[1].value
    }
    for (const userCode of userCodes) {
        codeSum = codeSum + userCode.innerText + '\n'
    }
    chrome.storage.sync.set({
        submitCode: codeSum,
        isSubmit: true,
        problem: problem
    });
})


