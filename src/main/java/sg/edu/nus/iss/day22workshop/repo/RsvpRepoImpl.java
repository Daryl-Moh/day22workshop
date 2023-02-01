package sg.edu.nus.iss.day22workshop.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.day22workshop.model.RSVP;

@Repository
public class RsvpRepoImpl {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String selectSQL = "select * from rsvp";
    private final String selectByNameSQL = "select * from rsvp where full_name like '%' ? '%'";
    private final String insertSQL = "insert into rsvp (full_name, email, phone, confirmation_date, comments) values (?, ?, ?, ?, ?)";
    private final String updateSQL = "update rsvp set full_name = ?, email= ?, phone = ?, confirmation_date = ?, comments = ? where id = ?";
    private final String countSQL = "select count(*) as cnt from rsvp";
    private final String findByIdSQL = "select * from rsvp where id = ?";

    public RSVP findById(Integer id){
        return jdbcTemplate.queryForObject(findByIdSQL, BeanPropertyRowMapper.newInstance(RSVP.class));
    }

    public List<RSVP> findAll() {
        List<RSVP> resultList = new ArrayList<RSVP>();
        resultList = jdbcTemplate.query(selectSQL, BeanPropertyRowMapper.newInstance(RSVP.class));
        return resultList;
    }

    public List<RSVP> findByName(String name){
        List<RSVP> resultList = new ArrayList<RSVP>();
        //resultList = jdbcTemplate.query(selectByNameSQL, new Object[]{name}, BeanPropertyRowMapper.newInstance(RSVP.class));
        resultList = jdbcTemplate.query(selectByNameSQL, BeanPropertyRowMapper.newInstance(RSVP.class), name);
        return resultList;
    }

    public Boolean save (RSVP rsvp) {
        Integer result = jdbcTemplate.update(insertSQL, rsvp.getFullName(), rsvp.getEmail(), 
        rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments());
        return result>0 ? true : false;
    }

    public Boolean update (RSVP rsvp) {
        Integer result = jdbcTemplate.update(updateSQL, rsvp.getFullName(), rsvp.getEmail(), 
        rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments(), rsvp.getId());
        return result>0 ? true : false;
    }

    public Integer countAll() {
        Integer countResult = 0;
        countResult = jdbcTemplate.queryForObject(countSQL, Integer.class);
        if (countResult == null) {
            return 0;
        } else {
            return countResult;
        }
    }

    @Transactional
    public int[] batchInsert(List<RSVP> rsvp) {
        return jdbcTemplate.batchUpdate(insertSQL, (BatchPreparedStatementSetter) new 
        BatchPreparedStatementSetter() {

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, rsvp.get(i).getFullName());
                ps.setString(2, rsvp.get(i).getEmail());
                ps.setInt(3, rsvp.get(i).getPhone());
                ps.setDate(4, rsvp.get(i).getConfirmationDate());
                ps.setString(1, rsvp.get(i).getComments());
            }
            
            public int getBatchSize(){
                return rsvp.size();
            }
        });
    }
    
}
