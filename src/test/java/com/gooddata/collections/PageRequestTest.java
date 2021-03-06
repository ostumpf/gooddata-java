package com.gooddata.collections;

import org.springframework.web.util.UriComponentsBuilder;
import org.testng.annotations.Test;

import java.net.URI;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PageRequestTest {

    @Test
    public void testGetPageUri() throws Exception {
        final PageRequest pageRequest = new PageRequest(12, 10);
        final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("test_uri");
        final URI pageUri = pageRequest.getPageUri(uriBuilder);
        assertThat(pageUri, notNullValue());
        assertThat(pageUri.toString(), is("test_uri?offset=12&limit=10"));
    }

    @Test
    public void testGetPageUriWithStringOffset() throws Exception {
        final PageRequest pageRequest = new PageRequest("17", 10);
        final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("test_uri");
        final URI pageUri = pageRequest.getPageUri(uriBuilder);
        assertThat(pageUri, notNullValue());
        assertThat(pageUri.toString(), is("test_uri?offset=17&limit=10"));
    }
}