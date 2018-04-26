package com.itberries2018.demo.servicesimpl;

import com.itberries2018.demo.entities.History;
import com.itberries2018.demo.daointerfaces.HistoryDao;
import com.itberries2018.demo.daointerfaces.UserDao;
import com.itberries2018.demo.entities.User;
import com.itberries2018.demo.models.ScoreRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class HistoryServiceJpaDao implements HistoryDao {
    @PersistenceContext
    private final EntityManager em;

    public HistoryServiceJpaDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public History add(String dateResult, int score, User user) {
        History history = new History();
        history.setScore(score);
        history.setUser_id(user);
        history.setDate_result(convertStringToDate(dateResult));

        try {
            em.persist(history);
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof ConstraintViolation) {
                throw new UserDao.DuplicateUserException("date", ex);
            } else {
                throw ex;
            }

        }
        return history;
    }

    private Date convertStringToDate(String date) {
        return Date.valueOf(date);
    }


    @Override
    public List<ScoreRecord>  getSortedData() {
        String query = "select   hist, pl from History as hist, User as pl where hist.user = pl.id";

        List<Object[]> results =  (List<Object[]>) em.createQuery(query).getResultList();
        final List<ScoreRecord> records = new ArrayList<>();
        for (Object[] hiAndpl : results) {
            History entityHist = (History) hiAndpl[0];
            User entityUser = (User) hiAndpl[1];
            records.add(new ScoreRecord(entityUser, entityHist));
        }
        records.sort((o1, o2) -> Long.compare(o2.getScore(), o1.getScore()));
        for (int i = 0; i < records.size(); ++i) {
            records.get(i).setId(i);
        }

        return records;
    }


}
