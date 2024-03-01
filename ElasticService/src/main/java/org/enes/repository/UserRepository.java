package org.enes.repository;

import org.enes.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<User,String> {
}
