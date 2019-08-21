/*
* 提交回复
* */
function postData() {
   var questionId = $("#questionId").val();
   var comment_content = $("#comment_content").val();
   comment2Target(questionId,1,comment_content);
}
function comment2Target(targetId,Type,content) {
    if(!content){
        alert("不能回复空内容");
        return;
    }
    $.ajax({
        type: "post",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": Type
        }),
        success: function (response) {
            if(response.code==200){
                $("#comment_section").text("");
                $("#comment_section").hide();
                location.reload();
            }else{
                if(response.code==2004){
                    var IsAccept = confirm(response.message);
                    if(IsAccept){
                        window.open("/login");
                        window.localStorage.setItem("login",true);
                    }
                }else
                    alert(response.message);
            }
            //console.log(response);
        },
        dataType: "json"
    });
}
function comment(e) {
    var id = e.getAttribute("data-id");
    comment2Target(id,2,$("#input-"+id).val());
    $("#input-"+id).text("");
}
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    if($("#comment-"+id).hasClass("in")){
        $("#comment-"+id).removeClass("in");
        e.classList.remove("active");
    }else{
        var subCommentContainer = $("#comment-"+id);
        if(subCommentContainer.children().length == 1){
            $.getJSON( "/comment/"+id, function( data ) {
                $.each( data.data.reverse(), function( index, comment ) {
                    var media_body = $("<div/>",{
                       "class":"media-body",
                    }).append(
                        $("<h6/>",{
                        "class":"media-heading",
                        html:comment.user.name
                    })).append(
                        $("<div/>",{
                        html:comment.content
                    })).append($("<div/>",{
                            "class":"menu"
                        }).append($("<span/>",{
                            "class":"pull-right",
                            "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));
                    var media_left = $("<div/>",{
                        "class":"media-left"
                    }).append(
                        $("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));
                    var  mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(media_left).append(media_body);
                    var commentElement =  $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    });
                    commentElement.append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
            });
        }
        $("#comment-"+id).addClass("in");
        e.classList.add("active");
    }
}