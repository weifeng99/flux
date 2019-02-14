/*
 * Copyright 2012-2016, the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flipkart.flux.dao.iface;

import com.flipkart.flux.domain.StateMetaData;
import com.flipkart.flux.domain.Status;
import com.flipkart.flux.shard.ShardId;

import java.sql.Timestamp;
import java.util.List;

/**
 * <code>StateMetaDataDAO</code> interface provides methods to perform CR operations on {@link StateMetaData}
 *
 * @author akif.khan
 */
public interface StateMetaDataDAO {

    /**
     * Retrieves a stateMetaData by it's unique identifier
     */
    StateMetaData findById(String stateMachineInstanceId, Long id);

    /**
     * Retrieves all stateMetaData for a particular state-machine-id and like input dependent-event-name.
     */
    List findStateMetaDataByDependentEvent(String stateMachineId, String eventName);
}