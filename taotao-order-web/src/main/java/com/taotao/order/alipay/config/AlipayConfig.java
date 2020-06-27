package com.taotao.order.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class AlipayConfig {
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016102100732593";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCp5HeLzCMqU0DAcXt84OqnPnWEHiu36TZTs01kYfSwoSAvaHEDmvlHNHv5wCVZ/7cbVC7xHUDrcl7v7zO12Tu9sxXwnSravL6/kuy3KB0ut9J7pOJF1/31PMUZzQYNoEt+mXetu16ZFV/MvnUy4m1gd6OSF/mTxdWuRXzBd7oLYYcbp7j7k0pdr9vtFu+Yud+PvBcatOdwn5ON4RDasu5iZQPNZ7g69n6PC5XC9PZytowi7CXKY0a4rCceelZTRzzZe1AKPvN9vAtwkVJgarJ/GZ2fddN7S3ZhetR6GsjVEF52l2FC5u9fMbvri+DKc1sO0wh+4cXrHaS5V6VYxgbzAgMBAAECggEADgRCdK8364MI4Ze7R3dLBNdbXLYch0TfbZJqfTCzvmrcu+yS/mgnzt8ep2FZ843sbn3Ija4LMCG0gXkrvAIbMrB2y+47neZamnWlwAq4NaWG4RqebILAeh40aMeIi1FBSUWiIRxHVrvztnG0O6mAuqfEQAZoWe/FjGZBP7kACXY0abnahMvPXu0hwSYqJT9iv/MwSVPa5fqY7RHIb/N8elxrcSHCYCp8OulCsKTfb9+BUOmJ+0Ouu44zc3uFrDXgWSVCz5o6AeZKCF7eoW3BxSrO7sF8jP+VJn0qDerHotDH97SccBM/eyKBdms3ox64V6CGIh3NwP2x3vkQr7h32QKBgQD7iLeTEeU/S+E06RgrcTOHSItw0zpMRIjnJWCrJZZ5gk7eqjLIPf2H5u6/yYdihaXfschpP2DLr7xktoiuy4QbSKRtBBPAbzhRg7P58VaAvM5ZglgW4dzTyNGvDtN3pL+7+lixV3NzH1ZFbFQOIB/mAgS71rY2OsggZj529FTvLQKBgQCs6KtLnekfiRhuwVX2s0/94Ozu+nCzO1udIZdJ7Y0eqlqFpmSjYsXn3a9kBFacddm+RYpvYNccnZsy9mjRKP8v0N6nU8Rh4D5R+C9ZRaH8f45ZxYGTMioGESQ4Woaj06OYYKl+OKPYvtcICs8vfrQRj0Ahcm92LdO/ebxaDjeinwKBgQCd5bD3TaCPH178C+b0PleOcOe3p6FNByi5kSkhxrKzDC3ecRD3ypJAoaL2Gdg27Ja32a5pAzQMKQEmZ79dmrwPNIajOI/9mNPNt9Ne7zTSE7OlVMFfe+FXwRR/w9rTnEQQCJNsn+0FM4LAicQqKdhcVqAKGjq/yB6g2A4WNkKq8QKBgBowKzGKuzJBi2fK9IAm7GihVoFjgQpEZT3JRqa1yffpCUjQyNFONndFxji5x3xvBQmicrMn0azN2iG/3GRBd1UAp5hkQYwpcIbttg4pU71wfFjwZw2qPV8p3XiDjXzmoxKDIWiyn25GiT74lCS3ZgJ61dW7BVK64iVQBM0G9jo1AoGBAJwxMSginEtplZYxyEKHB38F+4zPWFjiixb8u4tvkgEn/Hi7O+5zXSJZFE2BbyVVUcIoyB9BdD/f4cZMeIwOs6Qs/0hwvjW65RHM6BafS+PANzL8mhQhKPmYADFz5cr5ywXIb2Ym66hNbG0ODbIE2unh+3r5nlVEp3PZ3Bj6Y5Ie";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjHx4/DpiBT1j7tiVcWFOTNjYm6xMZQDk2H17sJSLKOqq1T6l1cvyNGlEk7xbnWgcepqD32+zRH7GjwsNnyYJDaOEEzkkRvz6JxGGtvMsxZecYgtFQz5B6/tATGVDz+lpgLiL3sEQEQSK9bhvfK/sQgBNQNfYnsidFJJ3muvWkzG4WK/cVMl1Uvf9DwuG7xbBjXqRPQaOjpmQRjWc25iWvMyMoHgJ0R8bfBLeXMerTyN+NLLzmhXNd5cQ36/K7xbl5HZtuycGzFut0offT3O80Tol3Kxw7hD3Dd1a6tktvRdqVz1aTxexVqrqjMeo0o0yNELLTghE/gNEKXVZMqN18wIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8091/order/payNotify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8091/order/create";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
