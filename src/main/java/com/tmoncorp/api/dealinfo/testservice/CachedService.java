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
package com.tmoncorp.api.dealinfo.testservice;


import com.tmoncorp.api.dealinfo.cache.annotation.MethodCache;
import org.springframework.stereotype.Repository;

@Repository
public class CachedService {

  //rustamchange
  @MethodCache(expiry = 120)
  public TestTypeClass getTestCache(TestTypeClass input) {
    System.out.println("---- Inside getTestCache input = " + input + " -----");
    input.a = 10;
    input.b = 20;
    input.c = 30;
    return input;
  }

}
