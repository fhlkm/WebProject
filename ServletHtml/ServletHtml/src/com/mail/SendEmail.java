package com.mail;





import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendEmail {
  static  String d_email = "enlanmaintenance@gmail.com",
            d_password = "HANLU1987",
            d_host = "smtp.gmail.com",
            d_port = "465",
//            m_to = "office@quadranglehouse.org",
            m_to = "enlanmaintenance@gmail.com",
            m_subject = "New Problem of Quadrangle";
//            m_text = "Hey, this is the testing email.";



	    // Those are the values that have the email information
	    public void send(String from, String pass, String host, String port, String to, String subject, String text) {

	        Properties props = new Properties();

	        // Read properties file.

	        props.put("mail.smtp.user", from);
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port", port);
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.socketFactory.port", port);
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.socketFactory.fallback", "false");

	        SecurityManager security = System.getSecurityManager();

	        try {
	            Authenticator auth = new SMTPAuthenticator();
	            Session session = Session.getInstance(props, auth);
	            MimeMessage msg = new MimeMessage(session);

	            msg.setText(text);
	            msg.setSubject(subject);
	            msg.setFrom(new InternetAddress(from));
	            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	            Transport.send(msg);
	        } catch (Exception mex) {
	            mex.printStackTrace();
	        }
	    }

	    public class SMTPAuthenticator extends javax.mail.Authenticator {

	        public PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(d_email, d_password);
	        }
	    }
	    /**
	     * 
	     * @param name The name of the employee who just update the info
	     * @param passage The content of the email
	     */
	    
	    public static void autoSend(String name,String passage){
	    	SendEmail mSendEmail = new SendEmail();
			mSendEmail.send(mSendEmail.d_email,mSendEmail.d_password, mSendEmail.d_host,mSendEmail.d_port, mSendEmail.m_to, mSendEmail.m_subject, passage);
			System.out.println("Email was Send");
	    }
	    
		public static void main(String[] args)
		{
//			SendEmail mSendEmail = new SendEmail();
//			mSendEmail.send(mSendEmail.d_email,mSendEmail.d_password, mSendEmail.d_host,mSendEmail.d_port, mSendEmail.m_to, mSendEmail.m_subject, mSendEmail.m_text);
//			System.out.println("Finish");
			String text = "Hi, \n "+"Tom"+ " just report a new problem, please check.";
			System.out.println(text);

				
		}
	}

