// package cn.kuugatool.core.string;
//
// import Kuuga.util.encryption.digitaldigest.Md5Util;
//
// import java.util.Random;
//
// /**
//  * 字符串映射工具类【短网址生成】
//  * ① 将长字符串用md5算法生成32位签名串，分为4段,，每段8个字符；
//  * ② 对这4段循环处理，取每段的8个字符, 将他看成16进制字符串与0x3fffffff(30位1)的位与操作，超过30位的忽略处理；
//  * ③ 将每段得到的这30位又分成6段，每5位的数字作为字母表的索引取得特定字符，依次进行获得6位字符串；
//  * ④ 这样一个md5字符串可以获得4个6位串，取24位字符的前12位。
//  *
//  * @author kuuga
//  * @since 2020-05-12 下午2:44
//  */
// public class StringMappingUtil {
//
//     /**
//      * 自定义生成 MD5 加密字符传前的混合 KEY
//      */
//     private static final String KEY = "SBWL_2020";
//
//     public static void main(String[] args) {
//         // 原始链接
//         String longString = "http://www.51bi.com/bbs/_t_278433840/";
//         System.out.println("长链接:" + longString);
//         //将产生4组6位字符串
//         String[] aResult = shortString(longString);
//         // 打印出结果
//         for (int i = 0; i < aResult.length; i++) {
//             System.out.println("[" + i + "]:" + aResult[i]);
//         }
//         Random random = new Random();
//         //产成4以内随机数
//         int j = random.nextInt(4);
//         //随机取一个作为短链
//         System.out.println("短链接:" + aResult[j]);
//     }
//
//     public static String[] shortString(String longString) {
//         // 要使用生成 URL 的字符
//         String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
//                 "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
//                 "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
//                 "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
//                 "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
//                 "U", "V", "W", "X", "Y", "Z"
//
//         };
//         // 对传入网址进行 MD5 加密
//         String hex = Md5Util.getMD5(KEY + longString).toUpperCase();
//
//         String[] resUrl = new String[4];
//         for (int i = 0; i < 4; i++) {
//
//             // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
//             String sTempSubString = hex.substring(i * 8, i * 8 + 8);
//
//             // 这里需要使用 long 型来转换，因为 Integer.parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用long ，则会越界
//             long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
//             StringBuilder outChars = new StringBuilder();
//             for (int j = 0; j < 6; j++) {
//                 // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
//                 long index = 0x0000003D & lHexLong;
//                 // 把取得的字符相加
//                 outChars.append(chars[(int) index]);
//                 // 每次循环按位右移 5 位
//                 lHexLong = lHexLong >> 5;
//             }
//             // 把字符串存入对应索引的输出数组
//             resUrl[i] = outChars.toString();
//         }
//         return resUrl;
//     }
//
// }
