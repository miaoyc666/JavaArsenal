// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;


import

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        String key = "xxx";
        String secret = "xxx";
        String url = gen_query_url("baidu.com", key, secret);
        System.out.println(url);
    }

    public static String gen_query_url(String value, String key, String secret) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String baseUrl = "https://www.xxx.miayc.cn/v2/search";
        long current_time = System.currentTimeMillis();
        String totp = gen_ti_totp(secret, current_time);
        String token_tmp = key + totp;
        String param = String.format("q=%s", value);
        String token = md5(param + md5(token_tmp));
        String param_token = String.format("token=%s", token);
        return baseUrl + "?" + param + "&" + param_token;
    }

    public static String md5(String input) {
        try {
            // 创建MessageDigest对象，指定使用MD5算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将字符串转换为字节数组
            byte[] inputBytes = input.getBytes();
            // 更新摘要信息
            md.update(inputBytes);
            // 计算摘要值
            byte[] digestBytes = md.digest();
            // 将摘要值转换为16进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digestBytes) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return String.valueOf(sb);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String gen_ti_totp(String secret, long time) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String hexTime = OTP.timeInHex(time, 30);
        String code = OTP.create(secret, hexTime, 6, Type.TOTP);
        return code;
    }

    private static byte[] longToBytes(long x) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (x & 0xff);
            x >>= 8;
        }
        return result;
    }

}



