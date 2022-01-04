/*
* 提交回复
* */
function post() {
    var questionId = $("#question_id").val()
    var context = $("#comment_content").val();

    comment2target(questionId, 1, context);
}

function comment2target(targetId, type, context) {
    if (!context) {
        alert("不能回复空内容~~~");
    }
    $.ajax({
        type: 'POST',
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "context": context,
            "type": type
        }),
        success: function (data) {
            if (data.code == 200) {
                window.location.reload();
            } else if (data.code == 2003) {
                var isAccepted = confirm(data.message);
                if (isAccepted) {
                    window.open("https://github.com/login/oauth/authorize?client_id=f88ba218935aa8831b3f&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                    window.localStorage.setItem("closable", true);
                } else {
                    alert(data.message);
                }
            }
        },
        dataType: "json"
    });
}

function community(e) {
    var commentId = e.getAttribute("data-id");
    var context = $("#input-" + commentId).val();
    comment2target(commentId, 2, context);
}

/*
* 展开二级评论
* */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#community-" + id);
    var collapse = e.getAttribute("data-collapse");

    // 获取二级评论展开状态
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#community-" + id);
        if (subCommentContainer.children().length != 1) {
            // 展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index,comment) {
                    var mediaBodyElement = $("<div/>",{
                        "class": "media-body",
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.context
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaLeftElement = $("<div/>",{
                        "class": "media-left",
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaElement = $("<div/>",{
                       "class": "media",
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>",{
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                // 展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e){
    var value= e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }

}
