package com.itberries2018.demo.auth.servicesimpl;

import com.itberries2018.demo.auth.entities.History;
import com.itberries2018.demo.auth.daointerfaces.HistoryDao;
import com.itberries2018.demo.auth.entities.User;
import com.itberries2018.demo.auth.models.ScoreRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Timestamp;
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
        history.setDate_result(convertStringToTimestamp(dateResult));
        user.getHistorySet().add(history);
        em.persist(user);
        return history;
    }

    @Override
    public int getBestScoreForUserById(Long id) {
        Query que = em.createQuery("select max(h.score) as score from History h , User u where u.id = h.user   \n"
                + "               and  u.id = :ids  group by u.username, score order by score desc");
        que.setParameter("ids", id);
        List<Integer> lstResult = que.getResultList();
        if (lstResult.size() > 0) {
            return lstResult.get(0);
        } else {
            return 0;
        }
    }

    private Timestamp convertStringToTimestamp(String date) {
        return Timestamp.valueOf(date);
    }


    @Override
    public List<ScoreRecord> getSortedData() {

        String query = "select u.username, max(h.score) as score "
                + "from History h , User u where u.id = h.user   group by u.username  order by score desc";

        List<Object[]> results = (List<Object[]>) em.createQuery(query).getResultList();
        final List<ScoreRecord> records = new ArrayList<>();

        for (Object[] note : results) {
            if (Long.parseLong(note[1].toString()) > 0) {
                records.add(new ScoreRecord(Long.parseLong(note[1].toString()), note[0].toString()));
            }
        }

        return records;
    }

}
