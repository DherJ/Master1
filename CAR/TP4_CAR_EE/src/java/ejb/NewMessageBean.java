/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Classe de transfert d'objet dans la base de donnée, utilisée pour l'ajout de livre dans la base
 * @author jérôme
 */
@MessageDriven(mappedName = "jms/book", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
            @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NewMessageBean implements MessageListener {
    @Resource(mappedName = "jms/book")
    private Queue book;
    @Resource(mappedName = "jms/bookFactory")
    private ConnectionFactory bookFactory;
    
    @Resource
    private MessageDrivenContext mdc;
        
    @PersistenceContext(unitName = "HelloWorld_restPU")
    private EntityManager em;
        
    public NewMessageBean() {
    }
    
    public void onMessage(Message message) {
        ObjectMessage objectMessageRecu;
        try {
            if (message instanceof ObjectMessage) {
                objectMessageRecu = (ObjectMessage) message;
                if (objectMessageRecu.propertyExists("type")) {
                    String type = objectMessageRecu.getStringProperty("type");

                    if (type != null) {
                        System.out.println(type);
                        if (type.equals("book")) {
                            BookEntity book = (BookEntity) objectMessageRecu.getObject();
                            save(book);
                            System.out.println("Book has been added");
                        }
                    }
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }

    public void save(Object object) {
        em.persist(object);
    }

    private Message createJMSMessageForjmsBook(Session session, Object messageData) throws JMSException {
        // TODO create and populate message to send
        TextMessage tm = session.createTextMessage();
        tm.setText(messageData.toString());
        return tm;
    }

    private void sendJMSMessageToBook(Object messageData) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            connection = bookFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(book);
            messageProducer.send(createJMSMessageForjmsBook(session, messageData));
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    
}
