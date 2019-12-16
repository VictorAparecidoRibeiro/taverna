/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author Victor Ribeiro
 */
public class SimpleAuthenticator extends Authenticator {
    
    public String username = null;
    public String password = null;

    public SimpleAuthenticator(String user, String pwd) {
        username = user;
        password = pwd;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
    
}
