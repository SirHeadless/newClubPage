package business;

import intf.DemoBusinessRESTResourceProxy;
import intf.DemoHTTPHeaderNames;
import intf.PlayerRESTResourceProxy;
import pagination.PaginatedListWrapper;

import java.security.GeneralSecurityException;
import java.util.List;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.security.auth.login.LoginException;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import data.Person;

@Stateless( name = "PlayerRESTResource", mappedName = "ejb/PlayerRESTResource" )
public class PlayerRESTResource implements PlayerRESTResourceProxy {

	private static final long serialVersionUID = -3453457675671342629L;
	
    @PersistenceContext
    private EntityManager entityManager;

    private Integer countPersons() {
        Query query = entityManager.createQuery("SELECT COUNT(p.id) FROM Person p");
        return ((Long) query.getSingleResult()).intValue();
    }
    
    @SuppressWarnings("unchecked")
    private List<Person> findPersons(int startPosition, int maxResults, String sortFields, String sortDirections) {
        Query query = entityManager.createQuery("SELECT p FROM Person p ORDER BY " + sortFields + " " + sortDirections);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }
    
    private PaginatedListWrapper<Person> findPersons(PaginatedListWrapper<Person> wrapper) {
        wrapper.setTotalResults(countPersons());
        int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
        wrapper.setList(findPersons(start,
                                    wrapper.getPageSize(),
                                    wrapper.getSortFields(),
                                    wrapper.getSortDirections()));
        return wrapper;
    }
	
	@Override
	public PaginatedListWrapper<Person> listPersons(Integer page, String sortFields, String sortDirections) {
        PaginatedListWrapper<Person> paginatedListWrapper = new PaginatedListWrapper<>();
        paginatedListWrapper.setCurrentPage(page);
        paginatedListWrapper.setSortFields(sortFields);
        paginatedListWrapper.setSortDirections(sortDirections);
        paginatedListWrapper.setPageSize(10);
        return findPersons(paginatedListWrapper);
	}

	@Override
	public Person getPerson(Long id) {
        return entityManager.find(Person.class, id);
	}

	@Override
	public Person savePerson(Person person) {
        if (person.getId() == null) {
            Person personToSave = new Person();
            personToSave.setName(person.getName());
            personToSave.setDescription(person.getDescription());
            personToSave.setImageUrl(person.getImageUrl());
            entityManager.persist(person);
        } else {
            Person personToUpdate = getPerson(person.getId());
            personToUpdate.setName(person.getName());
            personToUpdate.setDescription(person.getDescription());
            personToUpdate.setImageUrl(person.getImageUrl());
            person = entityManager.merge(personToUpdate);
        }

        return person;
	}

	@Override
	public void deletePerson(Long id) {
        entityManager.remove(getPerson(id));
		
	}
}