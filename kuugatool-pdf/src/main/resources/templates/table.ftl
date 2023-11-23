<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <title>表格样式</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width"/>
    <link rel="stylesheet" href="https://static.loyalvalleycapital.com/web/css/frame.css"/>
    <style>
        table.table-separate th {
            font-weight: bold;
            font-size: 14px;
            border-top: 1px solid #F3EDE9 !important;
        }

        table.table-separate td {
            padding: 13px 0;
            font-weight: 100;
        }

        .table-separate td.tit {
            background-color: #f4f9fe;
            font-weight: normal;
            padding: 22px 0;
            width: 15%;
        }

        .table-separate td.cont {
            text-align: left;
            padding: 16px 22px;
            width: 85%;
            line-height: 175%;
        }

        .table-separate.no-border th {
            border: none;
            text-align: left;
        }

        .table-separate.no-border td {
            text-align: left;
            border: none;
        }

        table {
            border-collapse: collapse;
            table-layout: fixed;
            word-break: break-all;
            font-size: 10px;
            width: 100%;
            text-align: center;
        }

        td {
            word-break: break-all;
            word-wrap: break-word;
        }

        @page {
            size: 210mm 297mm;//纸张大小A4
        margin: 0.25in;
            -fs-flow-bottom: "footer";
            -fs-flow-left: "left";
            -fs-flow-right: "right";
            padding: 1em;
        }

        #footer {
            font-size: 90%;
            font-style: italic;
            position: absolute;
            top: 0;
            left: 0;
            -fs-move-to-flow: "footer";
        }

        #pagenumber:before {
            content: counter(page);
        }

        #pagecount:before {
            content: counter(pages);
        }
    </style>
</head>
<body class="bg-white pb-3" style="font-family: SimSun;">
<div id="footer" style=""> Page <span id="pagenumber"/> of <span id="pagecount"/></div>
<div style="max-width:600px;margin:0 auto;padding:10px;">
    <div class="f18 text-center mv-2 bold">${companyName!""}合伙人服务日报</div>
    <div class="f14 text-center mb-3">${date!""}</div>

    <div class="">
        <div class="f14 mb bold">一、旗舰基金股东动态</div>
        <div class="f14 mb">1、旗舰基金公司新闻</div>
        <table class="table-separate">
            <tbody>
            <tr>
                <td class="tit" valign="middle">${name!""}</td>

                <td class="cont">
                    <p class="bold">&nbps;第 3 期员工持股计划（草案）摘要</p>
                    <p>1、甘肃刚泰控股（集团）股份有限公司（以下简称“公司”）第3期员工持
                        股计划（以下简称“第3期员工持股计划”）根据《中华人民共和国公司法》、《中华人民共和国证券法》、
                        《关于上市公司实施员工持股计划试点的指导意见》
                        及其他有关法律、法规、规范性文件以及《甘肃刚泰控股（集团）股份有限公司章程》的规定制定，遵循公平、公正、公开的原则，旨在完善公司的激励机制，确保公司未来发展战略和经营目标的实现。</p>
                    <p>4、第3期员工持股计划以“份”作为认购单位，每份份额为1元，起始认购
                        份数为100万份，超过100万份的，以10万份的整数倍累积计算。公司全部员工
                        持股计划涉及的股票数量累计不超过公司现有股本总额的10%，任一持有人持有的
                        员工持股计划份额所对应的标的股票数量不超过公司股本总额的 1%（不包括员工
                        在公司首次公开发行股票上市前获得的股份、通过二级市场自行购买的股份及通过股权激励获得的股份）。</p>
                </td>
            </tr>
            </tbody>
        </table>


        <div class="f14 mt-3 mb bold">一、客户沟通和反馈</div>
        <div class="f14 mb">今天联系的客户：</div>
        <div class="">
            <table class="table-separate">
                <thead>
                <th>联系人</th>
                <th>沟通内容</th>
                <th>客户反馈</th>
                </thead>
                <tbody>
                <tr>
                    <td>XXX</td>
                    <td>XXX</td>
                    <td>XXX</td>
                </tr>
                <tr>
                    <td>XXX</td>
                    <td>XXX</td>
                    <td>XXX</td>
                </tr>
                </tbody>
            </table>
        </div>


        <div class="f14 mt-3 mb bold">三、官网、微信和客户服务系统变化</div>
        <div class="">
            <table class="table-separate">
                <tbody>
                <tr>
                    <td class="tit" valign="middle">运营</td>
                    <td class="">
                        <p class="bold">XXX</p>
                    </td>
                    <td class="text-left pl-2">
                        <p>· 上海正心公益基金会正式获批 1727 </p>
                        <p>· XXXX</p>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="f14 mt-3 mb bold">四、其他工作</div>
        <div class="f14 mb">近期过生日的客户有：</div>
        <div class="">
            <p>8.27 王军</p>
            <p class="bold">8.27 陈鱼海</p>
        </div>
    </div>
</div>

</body>
</html>
