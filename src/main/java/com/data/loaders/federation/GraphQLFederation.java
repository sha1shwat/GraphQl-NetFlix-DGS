package com.data.loaders.federation;

import com.data.loaders.fetching.data;
import com.data.loaders.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.client.DefaultGraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.HttpResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@DgsComponent
public class GraphQLFederation {


    private RestTemplate dgsRestTemplate = new RestTemplate();

    private ObjectMapper obj = new ObjectMapper();

    private static final String URL = "http://localhost:9093/graphql";

    private static final String query = "{\n" +
            "    allEmployeeUsingFilter(filter : {\n" +
            "        ids : [\"a\",\"b\",\"c\",\"d\"]\n" +
            "    }){\n" +
            "        id\n" +
            "        firstName\n" +
            "        lastName\n" +
            "        position\n" +
            "        salary\n" +
            "        age\n" +
            "        organizationId\n" +
            "        \n" +
            "    }\n" +
            "}";

    public List<Employee> getData(List<String> ids) throws JsonProcessingException {
        String idString = ids.toString();
      //  String query1 = String.format(query,idString);
        DefaultGraphQLClient graphQLClient = new DefaultGraphQLClient(URL);

        GraphQLResponse response = graphQLClient.executeQuery(query, new HashMap<>(), (url, headers, body) -> {
            /**
             * The requestHeaders providers headers typically required to call a GraphQL endpoint, including the Accept and Content-Type headers.
             * To use RestTemplate, the requestHeaders need to be transformed into Spring's HttpHeaders.
             */
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.put(org.springframework.http.HttpHeaders.CONTENT_TYPE, Arrays.asList(MediaType.TEXT_PLAIN_VALUE));
            requestHeaders.put(org.springframework.http.HttpHeaders.ACCEPT, Arrays.asList(MediaType.APPLICATION_JSON_VALUE));
            requestHeaders.put("api-key", Arrays.asList("HOTELS-FLYFISH-81520512196122569198"));



            /**
             * Use RestTemplate to call the GraphQL service.
             * The response type should simply be String, because the parsing will be done by the GraphQLClient.
             */
            ResponseEntity<String> exchange = dgsRestTemplate.exchange(url, HttpMethod.POST, new HttpEntity(body,requestHeaders),String.class);

            /**
             * Return a HttpResponse, which contains the HTTP status code and response body (as a String).
             * The way to get these depend on the HTTP client.
             */
            return new HttpResponse(exchange.getStatusCodeValue(), exchange.getBody());
        });

         data data = response.dataAsObject(data.class);
    //    data data = obj.readValue(response.getJson(),data.class);
        return data.getEmployeeList();
    }

    private MultiValueMap<String, String> fetchLocusAPIHeadersforCityCodes() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put(org.springframework.http.HttpHeaders.CONTENT_TYPE, Arrays.asList(MediaType.TEXT_PLAIN_VALUE));
        headers.put(org.springframework.http.HttpHeaders.ACCEPT, Arrays.asList(MediaType.APPLICATION_JSON_VALUE));
        headers.put("api-key", Arrays.asList("HOTELS-FLYFISH-81520512196122569198"));
        return headers;
    }
}
