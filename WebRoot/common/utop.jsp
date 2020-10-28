<%@page language="java" contentType="text/html; character=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<div style="width:100%;height: 25px;background: #f0f0f0;">
    <div class="width1200 center_yh font14 c_66" style="height: 25px;line-height: 25px;">
        <font class="left_yh" style="margin-left: 10px">欢迎来到IT书籍商城！</font>
        <div class="right_yh" id="fast_jump">
            <c:if test="${userId!=null}">
                <a href="#">欢迎您：${username}</a>
                <b></b>
                <a href="${contextPath}/login/uTui">退出</a>
                <b></b>
                <a href="${contextPath}/car/findBySql">我的购物车</a>
                <b></b>
            </c:if>
            <c:if test="${userId==null}">
                <a href="${contextPath}/login/uLogin">登录</a>
                <b></b>
                <a href="${contextPath}/login/res">注册</a>
                <b></b>
            </c:if>
            <a href="${contextPath}/user/view">个人中心</a>
        </div>
    </div>
</div>
<div class="width100 hidden_yh" align="center">

    <div class="width1200 center_yh hidden_yh">
        <a href="${contextPath}"><img src="${contextPath}/resource/images/logo.png" alt="logo" style="width: 200px;height: 75px;float: left;margin-left: 30px"></a>
        <div class="" style="height:28px;width:550px;border:2px solid #7ca2dd;margin-top:20px;">
            <form action="${contextPath}/item/shoplist" method="post">
                <input type="text" placeholder="搜索商品" class="search" name="condition" value="${condition}">
                <input type="submit" class="btnSearh" value="搜索">
            </form>
        </div>
    </div>
</div>