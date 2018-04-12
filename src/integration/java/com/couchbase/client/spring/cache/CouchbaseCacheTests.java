/*
 * Copyright (C) 2015 Couchbase Inc., the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.couchbase.client.spring.cache;


import com.tmoncorp.api.dealinfo.testservice.CachedService;
import com.tmoncorp.api.dealinfo.testservice.TestTypeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfiguration.class)
@ContextConfiguration(locations = {
        "classpath:spring/applicationContext-cache.xml"
})
public class CouchbaseCacheTests {

  @Autowired
  public CachedService service;

  //rustamchange
  @Test
  public void testGetTestCache() {
    System.out.println("-- before calling getTestCache ");

    TestTypeClass testTypeClass = new TestTypeClass();
    testTypeClass.a = 1;
    testTypeClass.b = 2;
    testTypeClass.c = 3;

    service.getTestCache(testTypeClass);
    System.out.println("-- after calling getTestCache ");

  }

}
