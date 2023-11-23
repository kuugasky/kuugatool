<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <style type="text/css">
        body {
            font-family: pingfang sc light, serif;
        }

        .center {
            text-align: center;
            width: 100%;
        }
    </style>
</head>
<body>
<!--第一页开始-->
<div class="page">
    <div class="center"><p>${title}</p></div>
    <div><p>姓名:${name}</p></div>
    <div><p>性别:${sex}</p></div>
    <div><p>年龄:${age}</p></div>
    <!--外部链接-->
    <p>GitHub地址</p>
    <div>
        <img src="${githubAddress}" alt="github address" width="110" height="20.8"/>
    </div>
</div>
<!--第一页结束-->
<!---分页标记-->
<span style="page-break-after:always;"></span>
<!--第二页开始-->
<div class="page">
    <div>${nextTitle}</div>
    <div>爱好:</div>
    <div>
        <#list hobbies as item>
            <div><p>${item}</p></div>
        </#list>
    </div>

</div>


<!--第二页结束-->
</body>
</html>