<%@page language="java" contentType="text/html; character=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>首页</title>
    <link type="text/css" rel="stylesheet" href="${contextPath}/resource/user/css/style.css">
    <script src="${contextPath}/resource/user/js/jquery-1.8.3.min.js"></script>
    <script src="${contextPath}/resource/user/js/jquery.luara.0.0.1.min.js"></script>
</head>
<body style="margin-left: -45px;overflow-x: hidden;">
    <%@include file="/common/utop.jsp"%>
<!--导航条-->
    <div style="width:100%;height: 45px;background: #7ca2dd;margin-top: 40px;position: relative;z-index: 100;">
        <!--中间的部分-->
        <div class="width1200 center_yh relative_yh" style="height: 45px;">
            <!--普通导航-->
            <div class="left_yh font16" id="pageNav">
                <a href="${contextPath}/login/uIndex">首页</a>
                <a href="${contextPath}/news/list">公告</a>
                <a href="${contextPath}/message/add">留言</a>
            </div>
        </div>
    </div>
<div style="background: #f0f0f0;padding-top:34px;padding-bottom: 34px;">
    <div class="width1200 hidden_yh center_yh">
        <div id="vipRight" style="width: 1200px">
            <div style="width:1000px;height: 60px;line-height: 60px;text-indent: 50px;background: #f5f8fa;border-bottom: 1px solid #ddd;">
                <span>公告列表</span><span style="margin-left: 670px">公告日期</span>
            </div>
            <div style="padding:20px;width:960px;background-color: #fff">
                <c:forEach items="${pagers.datas}" var="data" varStatus="l">
                    <a href="${contextPath}/news/view?id=${data.id}">
                        <div class="hidden_yh" style="border-bottom: 1px dashed #ddd; padding-top:10px;padding-bottom: 10px;">
                            <div class="left_yh" style="margin-left:40px;width:580px;">
                                <font color="red"> ${data.name}</font>
                            </div>
                            <div class="right_yh" style="margin-right: 120px;">
                                <font color="black"><fmt:formatDate value="${data.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
        <div class="pagelist" style="margin-top: 400px;">
            <!--分页开始-->
            <pg:pager url="${contextPath}/news/list" maxIndexPages="5" items="${pagers.total}" maxPageItems="5" export="curPage=pageNumber">
                <pg:last>
                    共${pagers.total}记录，共${pageNumber}页，
                </pg:last>
                当前第${curPage}页
                <pg:first>
                    <c:choose>
                        <c:when test="${curPage eq pageNumber}">
                            <font color="red">首页</font>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageUrl}" style="text-decoration-color: #0f0f0f">首页</a>
                        </c:otherwise>
                    </c:choose>
                </pg:first>
                <pg:prev>
                    <a href="${pageUrl}">上一页</a>
                </pg:prev>
                <pg:pages>
                    <c:choose>
                        <c:when test="${curPage eq pageNumber}">
                            <font color="red">[${pageNumber}]</font>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageUrl}">${pageNumber}</a>
                        </c:otherwise>
                    </c:choose>
                </pg:pages>
                <pg:next>
                    <a href="${pageUrl}">下一页</a>
                </pg:next>
                <pg:last>
                    <c:choose>
                        <c:when test="${curPage eq pageNumber}">
                            <font color="red">尾页</font>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageUrl}">尾页</a>
                        </c:otherwise>
                    </c:choose>
                </pg:last>
            </pg:pager>
        </div>
    </div>
</div>

<%@include file="/common/ufooter.jsp"%>
</body>
</html>



















