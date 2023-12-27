package org.stafloker.data.repositories.repositories_sql;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stafloker.data.models.User;
import org.stafloker.data.repositories.UserRepository;

import javax.persistence.Query;
import java.util.Optional;

@Repository
public class UserRepositorySql extends GenericRepositorySql<User> implements UserRepository {

    @Autowired
    public UserRepositorySql(Session session) {
        super(session);
    }

    @Override
    public Optional<User> searchByName(String name) {
        Query query = this.session.createQuery("FROM User WHERE name = :userName");
        query.setParameter("userName", name);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> searchByMobile(String mobileNumber) {
        Query query = this.session.createQuery("FROM User WHERE mobile = :mobileNumber");
        query.setParameter("mobileNumber", mobileNumber);
        return query.getResultList().stream().findFirst();
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
