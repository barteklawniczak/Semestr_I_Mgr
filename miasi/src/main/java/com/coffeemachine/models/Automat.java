package com.coffeemachine.models;

import java.security.Security;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class Automat {
	
	private int cups;
	private Coffee coffeeSelected;
	private double water;
	private double milk;
	private double coffee;
	
	public int getCups() {
		return cups;
	}
	public void setCups(int cups) {
		this.cups = cups;
	}
	public Coffee getCoffeeSelected() {
		return coffeeSelected;
	}
	public void setCoffeeSelected(Coffee coffeeSelected) {
		this.coffeeSelected = coffeeSelected;
	}
	public double getWater() {
		return water;
	}
	public void setWater(double water) {
		this.water = water;
	}
	public double getMilk() {
		return milk;
	}
	public void setMilk(double milk) {
		this.milk = milk;
	}
	public double getCoffee() {
		return coffee;
	}
	public void setCoffee(double coffee) {
		this.coffee = coffee;
	}
	
	public Automat(int cups, double water, double milk, double coffee) {
		this.cups = cups;
		this.water = water;
		this.milk = milk;
		this.coffee = coffee;
	}
	
	public void addToHashMap(Map<String, Object> variables) {
		variables.put("cups", String.valueOf(this.getCups()));
		variables.put("water", String.valueOf(this.getWater()));
		variables.put("milk", String.valueOf(this.getMilk()));
		variables.put("coffee", String.valueOf(this.getCoffee()));
		
		if(this.coffeeSelected != null) {
			variables.put("coffeeName",  String.valueOf(this.coffeeSelected.getName()));
			variables.put("coffeeWater", String.valueOf(this.coffeeSelected.getWater()));
			variables.put("coffeeMilk", String.valueOf(this.coffeeSelected.getMilk()));
			variables.put("coffeeCoffee", String.valueOf(this.coffeeSelected.getCoffee()));
			variables.put("coffeePrice", String.valueOf(this.coffeeSelected.getPrice()));
		}
	}
	
	public void decreaseResources() {
		this.milk-=this.coffeeSelected.getMilk();
		this.water-=this.coffeeSelected.getWater();
		this.coffee-=this.coffeeSelected.getCoffee();
		this.cups-=1;
	}
	
    /**
     * Send email using GMail SMTP server.
     *
     * @param username GMail username
     * @param password GMail password
     * @param recipientEmail TO recipient
     * @param ccEmail CC recipient. Can be empty if there is no CC recipient
     * @param title title of the message
     * @param message message to be sent
     * @throws AddressException if the email address parse failed
     * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
     */
    public static void Send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(username + "@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

        if (ccEmail.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }

        msg.setSubject(title);
        msg.setText(message, "utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

        t.connect("smtp.gmail.com", username, password);
        t.sendMessage(msg, msg.getAllRecipients());      
        t.close();
    }
	
}
