package com.eurodyn.qlack.test.cmd.services.search;

import com.eurodyn.qlack.fuse.search.dto.SearchResultDTO;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryMatch;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryMultiMatch;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryRange;
import com.eurodyn.qlack.fuse.search.dto.queries.QuerySort;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryString;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryStringSpecField;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryTerm;
import com.eurodyn.qlack.fuse.search.service.SearchService;
import com.eurodyn.qlack.test.cmd.dto.EmployeeDTO;
import com.eurodyn.qlack.test.cmd.repository.ElasticEmployeeRepository;
import java.sql.SQLOutput;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author European Dynamics
 */

@Service
public class SearchServiceTest {
    private SearchService searchService;
    private ElasticEmployeeRepository elasticEmployeeRepository;

    @Autowired
    public SearchServiceTest(SearchService searchService, ElasticEmployeeRepository elasticEmployeeRepository) {
        this.searchService = searchService;
        this.elasticEmployeeRepository = elasticEmployeeRepository;
    }

    public void searchUsingRepository() {

        List<EmployeeDTO> byAge = elasticEmployeeRepository.findByAge(30);
        byAge.forEach(b -> System.out.println(b.toString()));

        List<EmployeeDTO> byName = elasticEmployeeRepository.findByFirstName("Dwight");
        byName.forEach(employeeDTO -> System.out.println(employeeDTO.toString()));
    }


    public void searchQueryRange() {
        System.out.println("******************");
        System.out.println("Testing query range");
        QueryRange queryRange = new QueryRange() {};
        queryRange.setTerm("age", 28,30);
        queryRange.setIndex("employee");

        QuerySort querySort = new QuerySort();
        querySort.setSort("age", "asc");
        queryRange.setQuerySort(querySort);

        SearchResultDTO searchResultDTO = searchService.search(queryRange);
        System.out.println(searchResultDTO.getHits());
    }
}
