package com.byultudy.user.service;

import com.byultudy.user.dao.UserDao;
import com.byultudy.user.domain.Level;
import com.byultudy.user.domain.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class UserService {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;
    private UserDao userDao;

    private PlatformTransactionManager transactionManager;

    public void setUserDao(final UserDao userDao) {
        this.userDao = userDao;
    }

    public void setTransactionManager(final PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            List<User> users = userDao.getAll();
            for (User user : users) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    protected void upgradeLevel(final User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(final User user) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "mail.ksug.org");
        Session s = Session.getInstance(properties, null);

        MimeMessage message = new MimeMessage(s);
        try {
            message.setFrom(new InternetAddress("test@test.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("test@test.com"));
            message.setSubject("Upgrade 안내");
            message.setText("등급이 " + user.getLevel().name() + "로 올랐습니다.");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean canUpgradeLevel(final User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC:
                return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER:
                return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("Unknown level : " + currentLevel);
        }
    }

    public void add(final User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
