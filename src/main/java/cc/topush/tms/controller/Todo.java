package cc.topush.tms.controller;

/*-
 * #%L
 * actframework app demo - TODO (ebean)
 * %%
 * Copyright (C) 2018 ActFramework
 * %%
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
 * #L%
 */

import act.Act;
import act.db.DbBind;
import act.db.ebean.EbeanDao;
import cc.topush.tms.model.TodoItem;
import org.osgl.http.H;
import org.osgl.mvc.annotation.*;
import org.osgl.util.S;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * A Simple Todo application controller
 */
@SuppressWarnings("unused")
public class Todo {

    @Inject
    private EbeanDao<Long, TodoItem> dao;

    @GetAction
    public void home() {
    }

    @GetAction("/echo/{msg}")
    public String echo(String msg) {
        return msg + "d1ddsfd";
    }

    @GetAction("/foo")
    public String foo() {
        return "foo";
    }

    @GetAction("/list")
    public Iterable<TodoItem> list(String q) {
        if (S.notBlank(q)) {
            return dao.findBy("desc like", q);
        }
        return dao.findAll();
    }

    @PostAction("/create")
    @ResponseContentType(H.MediaType.JSON)
    public TodoItem testCreate(TodoItem todo) {
        return todo;
    }

    @PostAction("/list")
    public void post(String desc) {
        TodoItem item = new TodoItem(desc);
        dao.save(item);
    }

    /**
     * So we use the annotation `@DbBind` to specify that parameter `item`
     * should be bound to a database record by ID, and the value of the ID
     * is found by URL parameter named `no`.
     */
    @GetAction("/list/{no}")
    public TodoItem showItem(@DbBind("no") TodoItem item) {
        return item;
    }

    /**
     * Here we use `@DbBind` again to bind URL path variable named `item`
     * to a database record. The value of `item` is used as the key to
     * look for the record. Because the URL path variable name `item`
     * is exactly the same as the method parameter item, so we don't need
     * to specify the bind name as `@DbBind("item")`, which is how we did
     * for the {@link #showItem(TodoItem)} method. So this is tricky on
     * how to name your URL path variable. If you name it the same as your
     * binding parameter name, then you save passing value to the `@DbBind`
     * annotation
     *
     * The `@NotNull` annotation means if we can't find an item from
     * database by the `no` specified, then it shall respond to the request
     * with 404 Not Found. Note we didn't specify the `@NotNull` annotation
     * in the {@link #showItem(TodoItem)} method because it will anyway
     * return the `item` entity back and if it is `null`, the framework
     * will automatically respond with 404 Not Found
     */
    @PutAction("/list/{item}")
    public void update(@DbBind @NotNull TodoItem item, String desc) {
        item.setDesc(desc);
        dao.save(item);
    }

    @DeleteAction("/list/{id}")
    public void delete(long id) {
        dao.deleteById(id);
    }


}
