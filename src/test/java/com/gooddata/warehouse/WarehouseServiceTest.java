/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.warehouse;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WarehouseServiceTest {
    private WarehouseService service;

    private WarehouseS3Credentials s3Credentials = new WarehouseS3Credentials("region", "accessKey", "secretKey");

    @Mock
    private Warehouse warehouse;
    @Mock
    private RestTemplate restTemplate;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new WarehouseService(restTemplate);
        when(warehouse.getId()).thenReturn("instanceId");

        final WarehouseTask warehouseTask = mock(WarehouseTask.class);
        when(warehouseTask.getPollUri()).thenReturn("/poll/uri");

        final ResponseEntity response = mock(ResponseEntity.class);
        when(response.getBody()).thenReturn(warehouseTask);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class), anyCollection()))
                .thenReturn(response);
    }

    @Test
    public void addS3Credentials() {
        service.addS3Credentials(warehouse, s3Credentials);
    }

    @Test(expectedExceptions = WarehouseS3CredentialsException.class, expectedExceptionsMessageRegExp = ".*Unable to POST S3 credentials.*")
    public void addS3Credentials_shouldFailUponEmptyResponse() {
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class), anyCollection()))
                .thenReturn(null);
        service.addS3Credentials(warehouse, s3Credentials);
    }
}
