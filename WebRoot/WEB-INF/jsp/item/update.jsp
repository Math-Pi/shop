<%@page language="java" contentType="text/html; character=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>管理员后台</title>
    <link rel="stylesheet" href="${contextPath}/resource/css/pintuer.css">
    <link rel="stylesheet" href="${contextPath}/resource/css/admin.css">
    <script src="${contextPath}/resource/js/jquery.js"></script>
    <script src="${contextPath}/resource/js/pintuer.js"></script>
    <script type="text/javascript" src="${contextPath}/resource/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${contextPath}/resource/ueditor/ueditor.all.min.js"></script>
</head>
<body>
<div class="panel admin-panel">
    <div class="panel-head" id="add">
        <strong><span class="icon-pencil-square-o">商品维护</span> </strong>
    </div>
    <div class="body-content">
        <form action="${contextPath}/item/toUpdate" method="post" class="form-x" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${obj.id}" />
            <div class="form-group">
                <div class="label"><label>商品名称：</label></div>
                <div class="field">
                    <input type="text" class="input w50" name="name" data-validate="required:请输入商品名称" value="${obj.name}"/>
                    <div class="tips"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label"><label>商品价格：</label></div>
                <div class="field">
                    <input type="text" class="input w50" name="price" data-validate="required:请输入商品价格"  value="${obj.price}"/>
                    <div class="tips"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label"><label>商品折扣：</label></div>
                <div class="field">
                    <input type="text" class="input w50" name="zk" data-validate="required:请输入商品折扣"  value="${obj.zk}"/>
                    <div class="tips"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label"><label>商品类别：</label></div>
                <div class="field">
                    <select name="categoryIdTwo" class="input w50">
                        <c:forEach items="${types}" var="data" varStatus="l">
                            <option value="${data.id}" ${obj.categoryIdTwo == data.id?"selected":""}>${data.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="label"><label>主图：</label></div>
                <div class="field">
                    <input type="file" class="input w50" name="file" />
                    <div class="tips"><img style="width: 300px;height: 400px" src="${obj.url1}" alt="${obj.url1}"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label"><label>副图1：</label></div>
                <div class="field">
                    <input type="file" class="input w50" name="file" />
                    <div class="tips"><img style="width: 300px;height: 400px" src="${obj.url2}" alt="${obj.url2}"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label"><label>副图2：</label></div>
                <div class="field">
                    <input type="file" class="input w50" name="file" />
                    <div class="tips"><img style="width: 300px;height: 400px" src="${obj.url3}" alt="${obj.url3}"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label"><label>副图3：</label></div>
                <div class="field">
                    <input type="file" class="input w50" name="file" />
                    <div class="tips"><img style="width: 300px;height: 400px" src="${obj.url4}" alt="${obj.url4}"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label"><label>副图4：</label></div>
                <div class="field">
                    <input type="file" class="input w50" name="file" />
                    <div class="tips"><img style="width: 300px;height: 400px" src="${obj.url5}" alt="${obj.url5}"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label"><label>描述：</label></div>
                <div class="field">
                    <input type="text" class="input w50 " name="ms" value="${obj.ms}" />
                    <div class="tips"></div>
                </div>
            </div>

            <div class="form-group">
                <div class="label"></div>
                <div class="field">
                    <button class="button bg-main icon-check-square-o" type="submit">提交</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>