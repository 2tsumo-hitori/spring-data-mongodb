/*
 * Copyright 2025. the original author or authors.
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
package example.aot;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReadPreference;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Christoph Strobl
 * @since 2025/01
 */
public interface UserRepository extends CrudRepository<User, String> {

    User findOneByUsername(String username);

    Optional<User> findOptionalOneByUsername(String username);

    @Query("{ 'username' : '?0' }")
    List<User> findAllByAnnotatedQueryWithParameter(String username);

    @Query("""
        {
            'username' : '?0'
        }""")
    List<User> findAllByAnnotatedMultilineQueryWithParameter(String username);


    @ReadPreference("secondary")
    User findWithReadPreferenceByUsername(String username);

    Page<UserProjection> findUserProjectionBy(Pageable pageable);

    @Query(sort = "{ 'last_name' : -1}")
    List<User> findByLastnameAfter(String lastname);

    Long countUsersByLastnameLike(String lastname);

    Boolean existsUserByLastname(String lastname);

    @Query(fields = "{ '_id' : -1}")
    List<User> findByLastnameBefore(String lastname);

    List<User> findTop5ByUsernameLike(String username);

    List<User> findByLastnameOrderByFirstnameDesc(String lastname);

    List<User> findUserByLastnameLike(String lastname);

    List<User> findUserByLastnameStartingWith(String lastname, Pageable page);
    List<User> findUserByLastnameStartingWith(String lastname, Sort sort);
    List<User> findUserByLastnameStartingWith(String lastname, Limit limit);
    List<User> findUserByLastnameStartingWith(String lastname, Sort sort, Limit limit);

    Page<User> findUserByFirstnameStartingWith(String firstname, Pageable page);
    Slice<User> findUserByFirstnameLike(String firstname, Pageable page);
}
