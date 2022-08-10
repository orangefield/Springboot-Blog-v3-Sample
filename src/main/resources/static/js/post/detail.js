// 권한체크 후 게시글 삭제 /s/post/1
$("#btn-delete").click(() => {
    postDelete();
});

let postDelete = async () => {
    let postId = $("#postId").val();
    let pageOwnerId = $("#pageOwnerId").val();

    let response = await fetch(`/s/api/post/${id}`, {
        method: "DELETE"
    });

    if (response.status == 200) {
        alert("삭제성공");
        location.href = "/";
    } else {
        alert("삭제실패");
    }
};