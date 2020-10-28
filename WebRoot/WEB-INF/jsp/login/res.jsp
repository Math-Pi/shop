<%@page language="java" contentType="text/html; character=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>首页</title>
    <link type="text/css" rel="stylesheet" href="${contextPath}/resource/user/css/style.css">
    <script src="${contextPath}/resource/user/js/jemailry-1.8.3.min.js"></script>
    <script src="${contextPath}/resource/user/js/jemailry.luara.0.0.1.min.js"></script>
</head>
<body style="background-color:#eee;overflow-x: hidden;">
<div class=" hidden_yh" style="width:100%;border-top: 1px solid #ddd;">
    <div style="margin-left:400px;margin-top:40px;width:500px;height: 500px;background-color:#fff ;">
    <div class="width1200 hidden_yh center_yh" style="margin-left:-400px;padding-top:50px;">
        <font color="red" style="margin-left:370px;margin-top:-20px;">${msg}</font>
        <form action="${contextPath}/login/toRes" method="post">
            <h3 class="tcenter font30 c_33" style="font-weight: 100;margin-top:-20px;margin-bottom: 36px;"><b>用户注册</b></h3>
            <div class="center_yh hidden_yh" style="width: 475px;margin-bottom: 30px;">
                <span style="margin-right: 40px;height: 42px;line-height: 42px;width: 100px;" class="left_yh block_yh tright">用户名：</span>
                <input type="text" name="userName" placehpassWorder="请输入用户名" style="border:1px solid #c9c9c9;width: 292px;height: 42px;font-size: 16px;text-indent: 22px;" class="left_yh">
            </div>
            <div class="center_yh hidden_yh" style="width: 475px;margin-bottom: 30px;">
                <span style="margin-right: 40px;height: 42px;line-height: 42px;width: 100px;" class="left_yh block_yh tright">设置密码：</span>
                <input type="text" name="passWord" placehpassWorder="请输入密码" style="border:1px solid #c9c9c9;width: 292px;height: 42px;font-size: 16px;text-indent: 22px;" class="left_yh">
            </div>
            <div class="center_yh hidden_yh" style="width: 475px;margin-bottom: 30px;">
                <span style="margin-right: 40px;height: 42px;line-height: 42px;width: 100px;" class="left_yh block_yh tright">手机号：</span>
                <input type="text" name="phone" placehpassWorder="请输入手机号" style="border:1px solid #c9c9c9;width: 292px;height: 42px;font-size: 16px;text-indent: 22px;" class="left_yh">
            </div>
            <div class="center_yh hidden_yh" style="width: 475px;margin-bottom: 30px;">
                <span style="margin-right: 40px;height: 42px;line-height: 42px;width: 100px;" class="left_yh block_yh tright">电子邮箱：</span>
                <input type="text" name="email" placehpassWorder="请输入邮箱" style="border:1px solid #c9c9c9;width: 292px;height: 42px;font-size: 16px;text-indent: 22px;" class="left_yh">
            </div>
            <p class="font14 c_66" style="text-indent: 500px;margin-top: 30px;margin-left: -40px;">
                <input type="checkbox" checked>我已阅读并同意<a href="#" class="red">《会员注册协议》</a>和<a href="#" class="red">《隐私保护政策》</a>
            </p>
            <input type="submit" value="立即注册" class="ipt_tj" style="width: 295px;height: 44px;margin-top: 20px;margin-left: 460px;">
        </form>
    </div></div>
</div>
<%@include file="/common/ufooter.jsp"%>
</body>
<script>
    $.post(function(){
        alert("用户名不能为空");
        var userName = $("#userName").val();
        var passWord = $("#passWord").val();
        var phone = $("#phone").val();
        var email = $("#email").val();
        if(userName==null||userName==''){
            alert("用户名不能为空");
            return false;
        }
        if(passWord==null||passWord==''){
            alert("密码不能为空");
            return false;
        }
        if(phone==null||phone==''){
            alert("手机号码不能为空");
            return false;
        }
        if(email==null||email==''){
            alert("电子邮箱不能为空");
            return false;
        }
        $.ajax({
            type:"POST",
            url:"${contextPath}/login/uLogin",
            success:function () {
                alert("注册成功,请登录");
                top.location.href = "${contextPath}/login/uLogin";
            }
        });
    });
</script>
</html>



















