package com.eurodyn.qlack.test.cmd.services.search;

import com.eurodyn.qlack.fuse.search.dto.SearchHitDTO;
import com.eurodyn.qlack.fuse.search.dto.SearchResultDTO;
import com.eurodyn.qlack.fuse.search.dto.queries.HighlightField;
import com.eurodyn.qlack.fuse.search.dto.queries.InnerHits;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryBoolean;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryBoolean.BooleanType;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryExists;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryExistsNested;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryHighlight;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryMultiMatch;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryRange;
import com.eurodyn.qlack.fuse.search.dto.queries.QuerySort;
import com.eurodyn.qlack.fuse.search.dto.queries.QuerySpec;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryString;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryStringSpecField;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryStringSpecFieldNested;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryTerm;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryTermNested;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryTerms;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryTermsNested;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryWildcard;
import com.eurodyn.qlack.fuse.search.dto.queries.QueryWildcardNested;
import com.eurodyn.qlack.fuse.search.dto.queries.SimpleQueryString;
import com.eurodyn.qlack.fuse.search.service.SearchService;
import com.eurodyn.qlack.test.cmd.repository.ElasticEmployeeRepository;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author European Dynamics
 */

@Service
public class SearchServiceTest {

  private SearchService searchService;
  private ElasticEmployeeRepository elasticEmployeeRepository;

  @Autowired
  public SearchServiceTest(SearchService searchService,
      ElasticEmployeeRepository elasticEmployeeRepository) {
    this.searchService = searchService;
    this.elasticEmployeeRepository = elasticEmployeeRepository;
  }

  public void searchQueryRange() {
    QuerySort AGE_SORT_DESC = new QuerySort().setSort("age", SortOrder.DESC)
        .setSort("weight", SortOrder.ASC);

    SearchHitDTO byId = searchService
        .findById("employees", "employee", "135e9-8ec3-405f-91d4-a4201a3d09a9");

    System.out.println(byId.toString());

    System.out.println("-----------------------------");
    System.out.println("Testing query range");

    QuerySpec queryRange = new QueryRange().setTerm("age", 10, 50).setQuerySort(AGE_SORT_DESC);
    queryRange.setAggregate("age");
    queryRange.setAggregateSize(10);
    SearchResultDTO resultRange = searchService.search(queryRange);

    resultRange.getHits().forEach(searchHitDTO ->
        System.out.println(searchHitDTO.toString()));

    System.out.println("-----------------------------");
    Map<String, Long> aggregations = resultRange.getAggregations();

    aggregations.forEach((s, aLong) -> {
      System.out.println(s + " " + aLong);
    });
    System.out.println("-----------------------------");
    System.out.println("Testing query term");

    QuerySpec queryTerm = new QueryTerm().setTerm("firstName", "kendrick").setIndex(
        "employees").setType("employee").setQuerySort(AGE_SORT_DESC);
    queryTerm.exclude("age").exclude("car");
    SearchResultDTO resultTerm = searchService.search(queryTerm);

    System.out.println(resultTerm.getHits().toString());
    System.out.println("-----------------------------");
    System.out.println("Testing query boolean with term && range");

    QueryBoolean queryBoolean = new QueryBoolean();
    queryBoolean.setIndex("employees");
    queryBoolean.setTerm(new QueryTerm().setTerm("firstName", "ghostface"), BooleanType.MUSTNOT);
    queryBoolean.setQuerySort(AGE_SORT_DESC);

    SearchResultDTO resultBoolean = searchService.search(queryBoolean);

    System.out.println(resultBoolean.getHits().toString());
    System.out.println("-----------------------------");
    System.out.println("-----------------------------");
    System.out.println("-----------------------------");

    System.out.println("Testing query terms");

    List<String> ageTerms = new ArrayList<>();
    ageTerms.add("20");
    ageTerms.add("30");

    QueryTerms queryTerms = new QueryTerms().setTerm("age", ageTerms).setTerm("firstName",
        Arrays.asList("jane", "john"));
    queryTerms.setIndex("employees");
    queryTerms.setQuerySort(AGE_SORT_DESC);
    queryTerms.setHighlight(new QueryHighlight()
        .addField(new HighlightField()
            //            .setForceSource(true)
            .setField("firstName")
            //            .setType("unified")
            .setFragmentSize(255)
            .setNumberOfFragments(1)));

    SearchResultDTO resultTerms = searchService.search(queryTerms);
    System.out.println(resultTerms.getHits().toString());

    System.out.println("-----------------------------");
    System.out.println("-----------------------------");
    System.out.println("-----------------------------");

    System.out.println("Testing multi match");

    QueryMultiMatch multiMatch = new QueryMultiMatch().setTerm("rza", "firstName",
        "lastName");
    multiMatch.setIndex("employees");
    multiMatch.setQuerySort(AGE_SORT_DESC);

    SearchResultDTO resultMultiMatch = searchService.search(multiMatch);
    System.out.println(resultMultiMatch.getHits().toString());

    System.out.println("-----------------------------");
    System.out.println("-----------------------------");
    System.out.println("-----------------------------");

    System.out.println("Testing wildcard");

    QueryWildcard queryWildcard = new QueryWildcard().setTerm("firstName", "q*");
    queryWildcard.setIndex("employees");
    queryWildcard.setQuerySort(AGE_SORT_DESC);

    SearchResultDTO wildcard = searchService.search(queryWildcard);

    System.out.println(wildcard.getHits().toString());

    System.out.println("Testing simple string");

    SimpleQueryString simpleQueryString = new SimpleQueryString().setTerm("firstName",
        "method?", "OR");

    simpleQueryString.setQuerySort(AGE_SORT_DESC);
    simpleQueryString.setIndex("employees");

    SearchResultDTO searchSimple = searchService.search(simpleQueryString);

    System.out.println(searchSimple.toString());

    System.out.println("-----------------------------");
    System.out.println("-----------------------------");
    System.out.println("-----------------------------");

    System.out.println("Testing query string");

    QueryString queryString = new QueryString().setQueryStringValue("firstName: havoc");
    queryString.setIndex("employees");
    queryString.setQuerySort(AGE_SORT_DESC);

    SearchResultDTO resultQueryString = searchService.search(queryString);
    System.out.println(resultQueryString.getHits().toString());

    System.out.println("-----------------------------");
    System.out.println("-----------------------------");
    System.out.println("-----------------------------");

    System.out.println("Testing query spec string");

    QueryStringSpecField queryStringSpecField = new QueryStringSpecField().setTerm("firstName",
        "ra* em*", "OR");
    queryStringSpecField.setQuerySort(AGE_SORT_DESC);
    queryStringSpecField.setIndex("employees");

    SearchResultDTO qStringSpecResult = searchService.search(queryStringSpecField);
    System.out.println(qStringSpecResult.toString());

    System.out.println("-----------------------------");
    System.out.println("-----------------------------");
    System.out.println("-----------------------------");

    System.out.println("Testing query spec string nested");

    QueryStringSpecFieldNested queryStringSpecFieldNested = new QueryStringSpecFieldNested();
    queryStringSpecFieldNested
        .setTerm("car.make", "ferrari", "OR", "car", new ArrayList<>());
    queryStringSpecFieldNested.setQuerySort(AGE_SORT_DESC);
    queryStringSpecFieldNested.exclude("car");
    queryStringSpecFieldNested.exclude("firstName");
    queryStringSpecFieldNested.setPageSize(8000);

    InnerHits innerHits = new InnerHits();
    innerHits.setSize(100);
    innerHits.setHighlight(new QueryHighlight()
        .addField(new HighlightField()
            .setField("car.make")
            .setFragmentSize(255)
            .setNumberOfFragments(1)));
    innerHits.exclude("car.make");
    queryStringSpecFieldNested.setInnerHits(innerHits);

    SearchResultDTO search = searchService.search(queryStringSpecFieldNested);
    System.out.println(search.getHits().size());
    System.out.println(search.getTotalHits());
    System.out.println(search.getExecutionTime());

    System.out.println("-----------------------------");
    System.out.println("-----------------------------");
    System.out.println("-----------------------------");

    System.out.println("Testing query query exists");

    QueryExists queryExists = new QueryExists();
    queryExists.setField("lastName");
    queryExists.setIndex("employees");
    queryExists.setQuerySort(AGE_SORT_DESC);

    System.out.println(searchService.search(queryExists).toString());

    System.out.println("-----------------------------");
    System.out.println("-----------------------------");
    System.out.println("-----------------------------");

    System.out.println("Testing query exists nested");

    QueryExistsNested queryExistsNested = new QueryExistsNested().setTerm("car.model",
        "car");
    queryExistsNested.setIndex("employees");
    queryExistsNested.setType("employee");
    queryExistsNested.setQuerySort(AGE_SORT_DESC);

    System.out.println(searchService.search(queryExistsNested).toString());

    System.out.println("-----------");
    System.out.println("Testing query term nested");

    QueryTermNested queryTermNested = new QueryTermNested().setTerm("car.make", "ferrari",
        "car", Arrays.asList("car.model.keyword"));
    queryTermNested.setQuerySort(AGE_SORT_DESC);
    queryTermNested.setIndex("employees");
    queryTermNested.setType("employee");

    SearchResultDTO searchNested = searchService.search(queryTermNested);
    System.out.println(searchNested.toString());

    System.out.println("-----------");
    System.out.println("-----------");
    System.out.println("Testing query terms nested");

    List<String> terms = Arrays.asList("lamborghini", "ferrari");

    QueryTermsNested queryTermsNested =
        new QueryTermsNested().setTerm("car.make", terms,
            "car", Arrays.asList("car.model.keyword"));
    queryTermsNested.setQuerySort(AGE_SORT_DESC);
    queryTermsNested.setIndex("employees");
    queryTermsNested.setType("employee");

    SearchResultDTO searchNestedTerms = searchService.search(queryTermsNested);
    System.out.println(searchNestedTerms.toString());

    System.out.println("-----------");
    System.out.println("Testing query wildcard nested");

    QueryWildcardNested qw = new QueryWildcardNested().setTerm("car.make", "lam*",
        "car", Arrays.asList("car.model.keyword"));
    qw.setQuerySort(AGE_SORT_DESC);
    qw.setIndex("employees");
    qw.setType("employee");

    SearchResultDTO searchWNested = searchService.search(qw);
    System.out.println(searchWNested.toString());
  }
}