package com.quartz.demo.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

    /**
     * 发送邮件工具类:通过qq邮件发送,因为具有ssl加密,采用的是smtp协议
     *
     * @param loginAccount     登录邮箱的账号
     * @param loginAuthCode    登录qq邮箱时候需要的授权码:可以进入qq邮箱,账号设置那里"生成授权码"
     * @param sender           发件人
     * @param recipients       收件人:支持群发
     * @param emailSubject     邮件的主题
     * @param emailContent     邮件的内容
     * @param emailContentType 邮件内容的类型,支持纯文本:"text/plain;charset=utf-8";,带有Html格式的内容:"text/html;charset=utf-8"
     * @return
     */
    public static int sendEmail(final String loginAccount, final String loginAuthCode, String sender, String[] recipients,
                                String emailSubject, String emailContent, String emailContentType) {
        int res = 0;

        try {
            //跟smtp服务器建立一个连接
            Properties p = new Properties();
            //设置邮件服务器主机名
            p.setProperty("mail.host", "smtp.qq.com");
            //发送服务器需要身份验证,要采用指定用户名密码的方式去认证
            p.setProperty("mail.smtp.auth", "true");
            //发送邮件协议名称
            p.setProperty("mail.transport.protocol", "smtp");

            //开启SSL加密，否则会失败
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            p.put("mail.smtp.ssl.enable", "true");
            p.put("mail.smtp.ssl.socketFactory", sf);

            // 创建session
            Session session = Session.getDefaultInstance(p, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    //用户名可以用QQ账号也可以用邮箱的别名:第一个参数为邮箱账号,第二个为授权码
                    PasswordAuthentication pa = new PasswordAuthentication(loginAccount, loginAuthCode);
                    return pa;
                }
            });

            //设置打开调试状态
            session.setDebug(true);

            //可以发送几封邮件:可以在这里 for循环多次
            //声明一个Message对象(代表一封邮件),从session中创建
            MimeMessage msg = new MimeMessage(session);
            //邮件信息封装
            //1发件人
            msg.setFrom(new InternetAddress(sender));

            //2收件人:可以多个
            //一个的收件人
            //msg.setRecipient(RecipientType.TO, new InternetAddress("linsenzhong@126.com"));

            InternetAddress[] receptientsEmail = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                receptientsEmail[i] = new InternetAddress(recipients[i]);
            }

            //多个收件人
            msg.setRecipients(RecipientType.TO, receptientsEmail);

            //3邮件内容:主题、内容
            msg.setSubject(emailSubject);
            //msg.setContent("Hello, 我是debug!!!", );//纯文本
            msg.setContent(emailContent, emailContentType);//发html格式的文本
            //发送动作
            Transport.send(msg);
            System.out.println("邮件发送成功");
            res = 1;

        } catch (Exception e) {
            System.out.println("邮件发送失败: " + e.getMessage());

            res = 0;
        }
        return res;
    }

    public static void main(String[] args) throws Exception {

        int res = sendEmail(
                "qq@qq.com",
                "",
                "qq@qq.com",
                new String[]{
                        "qq@qq.com", "qq@qq.com" //这里就是一系列的收件人的邮箱了
                }, "重要通知",
                "波普得分必赢球之无解定律，简称波普定律。只要当定律发明者“波普”得到一分或多分，无论以何种方式得分，例如运动战得分或执行技术犯规罚球得分等等情况，只要波普得分，此定律立即生效。湖人要波普执行技术犯规罚球的原因就是要触发此定律！\n" +
                        "\n" +
                        "一旦定律生效，定律的结果即湖人赢球，不受任何环境、时间、人物等影响，每当定律将要到达无法生效的地步的时候，波普定律会自动触发隐藏定律来保证定律生效，即波普第二定律，如果第二定律也岌岌可危时，就会再生效波普第三定律，以此类推，直到定律结果无法改变的时候。另外，波普定律还会触发队友的技能包和buff或让对面触发debuff，来保证自己定律不受影响，例如勒布朗的隔扣技能，增强戴维斯的防守盖帽能力，削减对面巴恩斯进攻能力，更有甚者会动用大自然不可控因素，如巴恩斯最后时刻脚打滑。除此，波普还有一种守恒，就是当波普得分越来越多时，所需要触发的定律越复杂，即波普第n定律（n为正整数，n随波普得分的增加而增加，而n越大比赛赢下的程度越难），这就是波普的分数和比赛难度的守恒关系，即波普得分多比赛难度大，波普得分少比赛难度小，称之为波普守恒定律。当然想要推翻这个定律也很简单，就是让n=0，波普定律只有在n为正整数时生效，称之为零波危布定律（布:勒布朗-詹姆斯）。\n" +
                        "\n" +
                        "当然还存在着对波普定律的很多猜想，比如令n=101时，波普定律是否会生效呢，因为联盟最高分为100，所以当n=101是波普可能将不再属于联盟；再有当n为负整数时（即波普进了乌龙球时，参考周琦）定律是否生效，这些猜想还需要更多比赛去证实。",
                "text/html;charset=utf-8");

        System.out.println("\n发送结果:" + res);
    }
}