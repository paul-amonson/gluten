/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intel.oap.row;

import com.intel.oap.vectorized.JniInstance;
import io.kyligence.jni.engine.LocalEngine;

import java.io.IOException;

public class RowIterator {

  private LocalEngine localEngine;
  private boolean closed = false;

  public RowIterator() throws IOException {}

  public RowIterator(byte[] plan, String soFilePath) throws IOException {
    this.localEngine = JniInstance.getInstanceWithLibPath(soFilePath).buildLocalEngine(plan);
    this.localEngine.execute();
  }

  public boolean hasNext() throws IOException {
    return this.localEngine.hasNext();
  }

  public SparkRowInfo next() throws IOException {
    if (this.localEngine == null) {
      return null;
    }
    return this.localEngine.next();
  }

  public void close() {
    if (!closed) {
      if (this.localEngine != null) {
        try {
          this.localEngine.close();
        } catch (IOException e) {
        }
      }
      closed = true;
    }
  }
}