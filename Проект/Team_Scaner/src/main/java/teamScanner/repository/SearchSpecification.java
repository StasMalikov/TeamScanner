package teamScanner.repository;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import teamScanner.model.Category;
import teamScanner.model.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class SearchSpecification implements Specification<Event> {
    private Map<String, Object> searchMap;

    public SearchSpecification(Map<String, Object> searchMap) {
        this.searchMap = searchMap;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : searchMap.entrySet()) {
            if (entry.getKey().equals("name")) {
//                predicates.add(criteriaBuilder.like(
//                        criteriaBuilder.lower(root.get(entry.getKey())), "%" + entry.getValue().toString().toLowerCase()));
//                predicates.add(criteriaBuilder.in(root.get(entry.getKey())).value(entry.getValue()));
                predicates.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue().toString()));
            }
            if (entry.getKey().equals("description")) {
//                predicates.add(criteriaBuilder.like(
//                    criteriaBuilder.lower(root.get(entry.getKey())), "%" + entry.getValue().toString().toLowerCase()));
//                predicates.add(criteriaBuilder.in(root.get(entry.getKey())).value(entry.getValue()));

                predicates.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue().toString()));
            }
            if (entry.getKey().equals("category")) {
//                predicates.add(criteriaBuilder.like(
//                        criteriaBuilder.lower(root.get(entry.getKey())), "%" + entry.getValue().toString().toLowerCase()));
//                predicates.add(criteriaBuilder.in(root.get(entry.getKey())).value(entry.getValue()));
                predicates.add(criteriaBuilder.equal(root.get(entry.getKey()), (Category) entry.getValue()));
            }
            if (entry.getKey().equals("address")) {
//                predicates.add(criteriaBuilder.like(
//                        criteriaBuilder.lower(root.get(entry.getKey())), "%" + entry.getValue().toString().toLowerCase()));
//                predicates.add(criteriaBuilder.in(root.get(entry.getKey())).value(entry.getValue()));
                predicates.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue().toString()));
            }
//            if (entry.getKey().equals("dateEvent")) {
////                predicates.add(criteriaBuilder.like(
////                        criteriaBuilder.lower(root.get(entry.getKey())), "%" + entry.getValue().toString().toLowerCase()));
////                predicates.add(criteriaBuilder.in(root.get(entry.getKey())).value(entry.getValue()));
//                predicates.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
//            }
            if (entry.getKey().equals("city")) {
                predicates.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue().toString()));
            }


        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
