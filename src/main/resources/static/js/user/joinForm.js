let valid = {
    username: {
        state: false,
        msg: ""
    },
    password: {
        state: false,
        msg: ""
    }
}

$("#username").focusout(()=>{
    usernameSameCheck();
});

$("#password").focusout(()=>{
    passwordSameCheck();
});

$("#same-password").keyup((event) => {
    passwordSameCheck();
});

function validation() {
    if (valid.username.state && valid.password.state) {
       return true;
    } else {
        // 에러 박스에 에러 메시지 추가하기
        let errorMsgs = ``;

        errorMsgs += `${valid.username.msg}<br/>`;
        errorMsgs += `${valid.password.msg}<br/>`;

        $(".my_error_box").html(errorMsgs);
        $(".my_error_box").removeClass("my_hidden");
        return false;
    }
   
}

async function usernameSameCheck(){
    let username = $("#username").val();

    let response = await fetch(`/api/user/username-same-check?username=${username}`);
    let responseParse = await response.json();

    if(response.status === 200) {
        if(!responseParse) {
            valid.username.state = false;
            valid.username.msg = "중복된 ID 입니다";
        } else {
            valid.username.state = true;
            valid.username.msg = "";
        }
    } else {
        alert(responseParse);
    }
}

function passwordSameCheck() {
    // 1. password 값 찾기
    let password = $("#password").val();

    // 2. same-password 값 찾기
    let samePassword = $("#same-password").val();

    // 3. 두 개의 값을 비교하기
    if (password == samePassword) {
        valid.password.state = true;
        valid.password.msg = "";
    } else {
        valid.password.state = false;
        valid.password.msg = "비밀번호가 동일하지 않습니다";
        
    }
}
