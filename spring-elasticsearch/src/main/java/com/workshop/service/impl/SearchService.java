package com.workshop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.lucene.search.Queries;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import com.workshop.entity.Kisi;
import com.workshop.entity.Shirt;
import com.workshop.repository.KisiRepository;
import com.workshop.repository.ShirtRepository;
import com.workshop.service.ISearchService;

@Service
public class SearchService implements ISearchService {

	@Autowired
	KisiRepository kisiRepository;

	@Autowired
	ShirtRepository shirtRepository;

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	public void setElasticsearchTemplate(ElasticsearchTemplate elasticsearchTemplate) {
		this.elasticsearchTemplate = elasticsearchTemplate;
	       if(!elasticsearchTemplate.indexExists("shirts")){
	       	elasticsearchTemplate.createIndex("shirts") ;
	       }
	       if(!elasticsearchTemplate.typeExists("shirts" , "shirt")){
	       	elasticsearchTemplate.putMapping(Shirt.class) ;
	       }
	   }

	public List<Kisi> customSearchKisi(String search) {
		return kisiRepository.getByCustomQuery(search);
	}

	public List<Shirt> getShirtByNameLike(String nameSearch) {
		return shirtRepository.findByNameLike(nameSearch);
	}

	public List<Shirt> getShirtListBetweenPrice(Double priceLow, Double priceUp) {
		List<Shirt> shirts = shirtRepository.findByPriceBetween(priceLow, priceUp);
		return shirts;
	}

//	private List<Shirt> executeQuery(final SearchRequest searchQuery) {
//		try {
//			val hits = restHighLevelClient.search(searchQuery, RequestOptions.DEFAULT).getHits().getHits();
//			return Arrays.stream(hits).map(SearchHit::getSourceAsString).map(ElasticRepo::toListingsData)
//					.collect(Collectors.toList());
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new RuntimeException("");
//		}
//	}
	/*
	 * private SearchResult<string> search(String indexName, String structuredQuery,
	 * int start, int size, List<string> sortOptions, String freeTextQuery, String
	 * docType) { try { QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
	 * if (StringUtils.isNotEmpty(structuredQuery)) { Expression expression =
	 * Expression.fromString(structuredQuery); queryBuilder =
	 * expression.getFilterBuilder(); }
	 * 
	 * BoolQueryBuilder filterQuery = QueryBuilders.boolQuery().must(queryBuilder);
	 * QueryStringQueryBuilder stringQuery =
	 * QueryBuilders.queryStringQuery(freeTextQuery); BoolQueryBuilder fq =
	 * QueryBuilders.boolQuery().must(stringQuery).must(filterQuery); final
	 * SearchRequestBuilder srb = elasticSearchClient.prepareSearch(indexName)
	 * .setQuery(fq) .setTypes(docType) .storedFields("_id") .setFrom(start)
	 * .setSize(size);
	 * 
	 * if (sortOptions != null) { sortOptions.forEach(sortOption ->
	 * addSortOptionToSearchRequest(srb, sortOption)); }
	 * 
	 * SearchResponse response = srb.get();
	 * 
	 * LinkedList<string> result =
	 * StreamSupport.stream(response.getHits().spliterator(), false)
	 * .map(SearchHit::getId) .collect(Collectors.toCollection(LinkedList::new));
	 * long count = response.getHits().getTotalHits();
	 * 
	 * return new SearchResult<string>(count, result); } catch (ParserException e) {
	 * throw new ApplicationException(Code.BACKEND_ERROR, e.getMessage(), e); } }
	 */

	public List<Shirt> getShirtsCustomSearch() {

		SearchResponse response = new SearchResponse();
		SearchHit[] searchHits = null;
		List<Shirt> shirts = new ArrayList<Shirt>();

		QueryBuilder queryBuilder = QueryBuilders.matchQuery("size", "L"); // matchAllQuery();

		// Shirts index'inde arama yaptığı gibi Kisiler index'inde de arama yapıyor.
		queryBuilder = QueryBuilders.matchQuery("ad", "Bora");

		// must ile filter farkina bak!!! Burada must matchAll ile tümünü getirip filter
		// ile filtre verildi.
		QueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery())
				.filter(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("size", "M")));

		// must ile M bedenler getirildi dönen sonuçlar içerisinden M beden ve beyaz
		// renkliler getirildi.
		builder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("size", "M")).filter(QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("size", "L")).must(QueryBuilders.termQuery("color", "white")));

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(builder); // QueryBuilders.boolQuery().filter(queryBuilder) .matchAllQuery());
		searchRequest.source(searchSourceBuilder);

		try {

			long resultCnt = 12345;

			response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

			// List<Aggregation> aggSearchResult = response.getAggregations().asList();

			// aggSearchResult.get(0);

			// response.getHits().forEach(hit -> shirts.add(hit.getId()));

			resultCnt = response.getHits().getTotalHits();

			System.out.println(resultCnt);

			searchHits = response.getHits().getHits();

			if (resultCnt > 0) {

				String indexNameString = searchHits[0].getIndex();
				searchHits[0].getSourceAsMap().get("size");

				for (SearchHit searchHit : searchHits) {
					if (searchHit.getIndex().equals("shirts")) {
						searchHit.getSourceAsString();
						Shirt shirt = new Shirt();
						shirt.setSize(searchHit.getSourceAsMap().get("size").toString());
						shirt.setColor(searchHit.getSourceAsMap().get("color").toString());
						shirt.setFabric(searchHit.getSourceAsMap().get("fabric").toString());
						shirt.setName(searchHit.getSourceAsMap().get("name").toString());
						shirt.setPrice((double) searchHit.getSourceAsMap().get("price"));
						shirts.add(shirt);
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * QueryBuilders.boolQuery().filter(QueryBuilders.nestedQuery(
		 * "searchData.stringFacets", QueryBuilders.boolQuery()
		 * .filter(QueryBuilders.termQuery("facetName","manufacturer"))
		 * .filter(QueryBuilders.termQuery("facetValue","fortis")) , ScoreMode.None))
		 */

		return shirts;
	}

	public SearchResponse getShirtsAggregationSearch() {

		SearchResponse response;

		TermsAggregationBuilder aggregation = AggregationBuilders.terms("color").field("color")
				.size(4)// the result is not complete if the size is not specified, 0: infinite
		// (see : https://www.elastic.co/guide/en/elasticsearch/reference/current/search-aggregations-bucket-terms-aggregation.html#_size)
		//.order(Terms.Order.aggregation("_count", false))
		 ;
		
		  response = elasticsearchTemplate.getClient().prepareSearch("shirts")
		  //.setTypes(Constants.TYPE_NAME) //here we use the same querybuilder as in the main search to get exactly the same filtering on current versions
		  //.setQuery(Queries.constructQuery(query)) 
		  .addAggregation(aggregation)
		  .execute().actionGet();
		 
		
		Client client = elasticsearchTemplate.getClient();
		
		response = client.prepareSearch("shirts")
				// .setTypes(Constants.TYPE_NAME)
				.addAggregation(aggregation).execute().actionGet();

		return response;
	}

}
