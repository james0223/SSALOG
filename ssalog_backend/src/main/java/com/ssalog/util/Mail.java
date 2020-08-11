package com.ssalog.util;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Mail {

	public String sendMail(String reciver) {
		String host = "smtp.gmail.com";
		final String username = "ssalogofficial@gmail.com";
		final String password = "gps@none.vat";
		int port = 465;

		String recipient = reciver;
		String subject = "SSalog 이메일 인증 메일입니다.";
		String ran = make_randome();
		String body = "<table align=\"center\" cellspacing=\"0\" cellpadding=\"0\" width=\"748\" border\"0\"><tbody><tr><td style=\"BORDER-BOTTOM: #b5b5b5 2px solid\"><img style=\"OVERFLOW: visible; WIDTH: 120px; MARGIN-BOTTOM: -50px\" border=\"0\" src=\"https://ssalog.gq/upload/bmb321tthanmaum.jpg\" loading=\"lazy\"></td></tr><tr><td align=\"center\" style=\"FONT-SIZE: 29px; FONT-FAMILY: 돋움, dotum, sans-serif; BORDER-BOTTOM: #ccc 1px solid; FONT-WEIGHT: bold; COLOR: #333; PADDING-BOTTOM: 17px; PADDING-TOP: 40px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px\" width=\"748\" cellspacing=\"0\" cellpadding=\"0\">이메일 인증</td></tr><tr><td style=\"FONT-SIZE: 12px; FONT-FAMILY: 돋움, dotum, sans-serif; BORDER-BOTTOM: #ccc 1px solid; COLOR: #555; PADDING-BOTTOM: 13px; PADDING-TOP: 13px; PADDING-LEFT: 0px; LINE-HEIGHT: 1; PADDING-RIGHT: 0px\" width=\"748\" cellspacing=\"0\" cellpadding=\"0\"><br><p><br><h2>인증번호는 : [" +ran+ "] 입니다.</h2><br> <h2>해당 값을 Ssalog에 입력해주세요!</h2></p><br></td></tr><tr><td height=\"37\" cellspacing=\"0\" cellpadding=\"0\"></td></tr><tr><td style=\"LINE-HEIGHT: 0\" height=\"107\" cellspacing=\"0\" cellpadding=\"0\"><div style=\"BORDER-TOP: #ddd 1px solid; BORDER-RIGHT: #ddd 1px solid; BACKGROUND: #fafafa; BORDER-BOTTOM: #ddd 1px solid; PADDING-BOTTOM: 7px; PADDING-TOP: 8px; PADDING-LEFT: 0px; BORDER-LEFT: #ddd 1px solid; MARGIN: 0px 0px 5px; PADDING-RIGHT: 0px\"><p style=\"FONT-SIZE: 13px; FONT-FAMILY: 돋움, dotum, sans-serif; COLOR: #666; TEXT-ALIGN: center; LINE-HEIGHT: 1\">본 메일은 발신 전용이므로 회신 되지 않습니다. </p></td></tr></tbody></table>";//"";
		

		Properties props = System.getProperties();

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.prot", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			String un = username;
			String pw = password;
			protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
				return new javax.mail.PasswordAuthentication(un,pw);
			}
		});
		session.setDebug(true);
		Message mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(new InternetAddress(username));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			mimeMessage.setSubject(subject);
			mimeMessage.setHeader("Content-Type","text/html; charset=UTF-8");
			mimeMessage.setContent(body, "text/html; charset=UTF-8");
			Transport.send(mimeMessage);
		}catch(AddressException e) {
			e.printStackTrace();
		}catch(MessagingException e) {
			e.printStackTrace();
		}
		return ran;
	}
	public String make_randome() { 
		//인증 번호 생성기
        StringBuffer temp =new StringBuffer();
        Random rnd = new Random();
        for(int i=0;i<10;i++)
        {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
            case 0:
                // a-z
                temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                break;
            case 1:
                // A-Z
                temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                break;
            case 2:
                // 0-9
                temp.append((rnd.nextInt(10)));
                break;
            }
        }
        String AuthenticationKey = temp.toString();
        //System.out.println(AuthenticationKey);
        return AuthenticationKey;
	}
}
