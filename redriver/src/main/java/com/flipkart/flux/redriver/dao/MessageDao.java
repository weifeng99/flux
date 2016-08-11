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

package com.flipkart.flux.redriver.dao;

import com.flipkart.flux.redriver.model.ScheduledMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

/**
 * Db interactions for <code>ScheduledMessage</code>s
 */
@Singleton
public class MessageDao {

    private SessionFactory sessionFactory;

    @Inject
    public MessageDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(ScheduledMessage scheduledMessage) {
        currentSession().save(scheduledMessage);
    }

    public List<ScheduledMessage> retrieveAll(ScheduledMessage scheduledMessage) {
        return currentSession().createCriteria(ScheduledMessage.class).list();
    }

    public void deleteInBatch(List<Long> messageIdsToDelete) {
        throw new UnsupportedOperationException("not implemented yet");
        // todo
    }
    /**
     * Provides the session which is bound to current thread.
     * @return Session
     */
    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

}
