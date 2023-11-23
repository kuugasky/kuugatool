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

        ul, ul li {
            list-style: none;
            margin: 0;
            padding: 0;
        }

        label {
            color: #008000;
        }

        .rmb {
            font-family: Arial, Helvetica, sans-serif;
        }

        .c-red {
            color: #ff3133;
        }

        .c-black {
            color: #000;
        }

        .f-big {
            font-size: 20px;
        }

        .f-small {
            font-size: 12px;
        }

        .f-small2 {
            font-size: 10px;
        }

        .invoicMain {
            width: 620px;
            margin: 0 auto;
            font-size: 14px;
            color: #000;
            padding: 20px;
            border: 1px dotted #000;
        }

        .toptip {
            text-align: center;
            padding-top: 4px;
            height: 20px;
        }

        .invoiceHeader {
            height: 126px;
            display: flex;
            /* justify-content: space-between; */
            align-items: center;
        }

        .headerLeft {
            width: 300px;
            display: flex;
        }

        .headerLeft div:nth-child(1) {
            width: 85px;
            line-height: 26px;
        }

        .headerLeft div p {
            line-height: 26px;
        }

        .headerLeft div:nth-child(2) p.c-red {
            width: 170px;
            height: 46px;
            text-align: center;
            line-height: 42px;
            font-size: 24px;
            letter-spacing: 2px;
        }

        .headerLeft div p.c span {
            font-size: 18px;
            letter-spacing: 1px;
        }

        .headerRight {
            padding-left: 28px;
            width: 316px;
        }

        .headerRight p:nth-child(1) {
            font-size: 24px;
        }

        .headerRight p:nth-child(1) strong {
            font-family: 'LiSu';
            font-size: 30px;
            padding-right: 20px;
        }

        .headerRight p:nth-child(2) {
            text-align: right;
            margin-top: -5px;
        }

        .headerRight p:nth-child(3) {
            text-align: right;
            font-size: 18px;
            letter-spacing: 1px;
        }

        .headerRight p:nth-child(4) {
            text-align: center;
            letter-spacing: 1px;
        }

        .headerMiddle {
            text-align: center;
            width: 300px;
        }

        .headerMiddle h1 {
            font-size: 32px;
            color: #008000;
            padding-bottom: 22px;
        }

        .total .rmb {
            font-size: 16px;
        }

        .line {
            height: 2px;
            border-top: #008000 1px solid;
            border-bottom: #008000 1px solid;
            margin-bottom: 30px;
        }

        .headerRight li {
            line-height: 24px;
        }

        .invoiceBody {
            border: 1px solid #008000;
        }

        .userInfo {
            width: 100%;
            display: flex;
            align-items: center;
            height: 96px;
            border-bottom: 1px solid #008000;
        }

        .userInfo ul {
            width: 50%;
            margin: 0 5px;
            padding: 0;
        }

        .userInfo ul li {
            line-height: 24px;
        }

        .userInfo ul li:nth-child(2) .f-big {
            padding-left: 24px;
            letter-spacing: 2px;
        }

        .userInfo ul li:nth-child(3) {
            position: relative;
        }

        .userInfo ul li .f-small2 {
            position: absolute;
            width: 120%;
            transform: scale(0.8);
            left: 44px;
        }

        .buy {
            width: 20px;
            border-right: 1px solid #008000;
            padding: 0 10px;
            text-align: center;
            height: 100%;
            display: flex;
            align-items: center;
            color: #008000;
            line-height: 20px;
        }

        .password {
            width: 20px;
            padding: 0 10px;
            border-right: 1px solid #008000;
            border-left: 1px solid #008000;
            text-align: center;
            height: 100%;
            display: flex;
            align-items: center;
            color: #008000;
            line-height: 20px;
        }

        .pwdInfo {
            flex: 1;
            padding: 0 15px;
            height: 88px;
            overflow: hidden;
            word-break: break-all;
            letter-spacing: 2px;
        }

        .goodsInfo {
            height: 210px;
            margin: 0;
            padding: 0;
        }

        .goodsInfo li {
            display: flex;
            color: #008000;
            text-align: center;
        }

        .name {
            width: 260px;
            border-right: 1px solid #008000;
        }

        .spec {
            width: 140px;
            border-right: 1px solid #008000;
        }

        .qty {
            width: 108px;
            border-right: 1px solid #008000;
        }

        .unit, .taxRate {
            width: 65px;
            border-right: 1px solid #008000;
        }

        .qty, .price {
            width: 160px;
            border-right: 1px solid #008000;
        }

        .money {
            flex: 1;
            border-right: 1px solid #008000;
        }

        .taxAmount {
            flex: 1;
        }

        .GoodsTable {
            height: 210px;
            width: 100%;
            border-collapse: collapse;
            table-layout: fixed
        }

        .GoodsTable td {
            border-right: 1px solid #008000;
            color: #008000;
            padding: 0 4px;
        }

        .invoice-list td {
            color: #000;
            vertical-align: middle;
        }

        .invoice-list td:nth-child(5), .invoice-list td:nth-child(6), .invoice-list td:nth-child(7), .invoice-list td:nth-child(8), .total td:nth-child(5), .total td:nth-child(6), .total td:nth-child(7), .total td:nth-child(8) {
            text-align: right;
        }

        .invoice-list {
            height: 126px;
            overflow: hidden;
        }

        .invoice-list tr td {
            height: 21px;
        }

        .GoodsTable thead tr {
            height: 24px;
            text-align: center;
        }

        .GoodsTotal {
            height: 34px;
        }

        .GoodsTotal {
            border-top: 1px solid #008000;
            border-bottom: 1px solid #008000;
        }

        .total td {
            color: #000;
        }

        .total td:nth-child(1) {
            text-align: center;
            color: #008000;
        }

        .total td:nth-child(6), .total td:nth-child(8) {
            font-size: 18px;
        }

        .invoicetFooter {
            padding-top: 2px;
            display: flex;
            justify-content: space-between;
        }

        .invoicetFooter li {
            width: 25%;
        }

        .invoiceHeader-left {
            width: 67%;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
    </style>
</head>
<body>
<div class="toptip c-red">模板样式仅供参考，不可用于实际开票依据！</div>
<div class="invoicMain">
    <!-- head start -->
    <div class="invoiceHeader">
        <div class="headerLeft">
            <div>
                <!--
                <img src="" width="83" height="83" alt="">
                -->
            </div>
            <div>
                <p class="c-red">3700194130</p>
                <p class="c"><label>机器编号：</label><span>499098532085</span></p>
            </div>
        </div>
        <div class="headerMiddle">
            <h1>增值税专用发票</h1>
            <div class="line"></div>
        </div>
        <div class="headerRight">
            <p><strong>№</strong><span class="c-red">35891172</span></p>
            <p>3700194130</p>
            <p>35891172</p>
            <p><label>开票日期：</label><span>2021年06月07日</span></p>
        </div>
    </div>
    <!-- head end -->
</div>
</body>
</html>
